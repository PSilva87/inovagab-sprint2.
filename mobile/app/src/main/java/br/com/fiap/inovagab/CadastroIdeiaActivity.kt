package br.com.fiap.inovagab

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject

class CadastroIdeiaActivity : AppCompatActivity() {
    private val estrategias = mutableListOf<Estrategia>()
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_ideia)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val titulo = findViewById<EditText>(R.id.editTitulo)
        val descricao = findViewById<EditText>(R.id.editDescricao)
        spinner = findViewById(R.id.spinnerEstrategia)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        btnSalvar.isEnabled = false
        carregarEstrategias(btnSalvar)
        btnSalvar.setOnClickListener {
            val estrategia = estrategias.getOrNull(spinner.selectedItemPosition) ?: run {
                Toast.makeText(this, "Selecione uma estratégia", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val body = JSONObject().put("titulo", titulo.text.toString()).put("descricao", descricao.text.toString()).put("estrategiaId", estrategia.id)
            ApiClient.request("POST", "ideias", SessionManager(this).token(), body, {
                Toast.makeText(this, "Ideia cadastrada!", Toast.LENGTH_LONG).show(); titulo.text.clear(); descricao.text.clear()
            }, { error -> Toast.makeText(this, "Erro ao salvar: $error", Toast.LENGTH_LONG).show() })
        }
    }

    private fun carregarEstrategias(btnSalvar: Button) {
        ApiClient.request("GET", "estrategias", SessionManager(this).token(), onSuccess = { response ->
            estrategias.clear(); val array = JSONArray(response)
            for (index in 0 until array.length()) { val item = array.getJSONObject(index); if (item.optBoolean("ativa", true)) estrategias.add(Estrategia(item.optString("id"), item.optString("titulo"), item.optString("descricao"))) }
            spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estrategias.map { it.titulo }).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            btnSalvar.isEnabled = estrategias.isNotEmpty()
            if (estrategias.isEmpty()) Toast.makeText(this, "Nenhuma estratégia ativa disponível", Toast.LENGTH_LONG).show()
        }, onError = { error -> Toast.makeText(this, "Erro ao carregar estratégias: $error", Toast.LENGTH_LONG).show() })
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
