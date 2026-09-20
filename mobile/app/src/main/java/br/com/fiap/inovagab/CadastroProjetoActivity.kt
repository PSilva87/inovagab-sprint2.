package br.com.fiap.inovagab

import android.os.Bundle
import android.app.DatePickerDialog
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar
import java.util.Locale

class CadastroProjetoActivity : AppCompatActivity() {
    private val estrategias = mutableListOf<Estrategia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_cadastro_projeto
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editNome =
            findViewById<EditText>(R.id.editNome)

        val editDescricao = findViewById<EditText>(R.id.editDescricao)
        val spinnerEstrategia = findViewById<Spinner>(R.id.spinnerEstrategia)

        val editInvestimento =
            findViewById<EditText>(R.id.editInvestimento)

        val editPrazo = findViewById<EditText>(R.id.editPrazo)
        var prazoParaApi = ""

        editPrazo.inputType = InputType.TYPE_NULL
        editPrazo.setOnClickListener {
            val calendario = Calendar.getInstance()
            DatePickerDialog(this, { _, ano, mes, dia ->
                prazoParaApi = String.format(Locale.US, "%04d-%02d-%02d", ano, mes + 1, dia)
                editPrazo.setText(String.format(Locale("pt", "BR"), "%02d/%02d/%04d", dia, mes + 1, ano))
            }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show()
        }

        val btnSalvar =
            findViewById<Button>(R.id.btnSalvar)
        btnSalvar.isEnabled = false
        carregarEstrategias(spinnerEstrategia, btnSalvar)

        btnSalvar.setOnClickListener {

            val investimento = editInvestimento.text.toString().replace(",", ".").toDoubleOrNull()
            if (investimento == null) {
                Toast.makeText(this, "Informe um investimento válido", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (prazoParaApi.isBlank()) {
                Toast.makeText(this, "Selecione o prazo no calendário", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val estrategia = estrategias.getOrNull(spinnerEstrategia.selectedItemPosition)
            if (estrategia == null) {
                Toast.makeText(this, "Selecione uma estratégia", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val body = JSONObject()
                .put("nome", editNome.text.toString())
                .put("descricao", editDescricao.text.toString())
                .put("estrategiaId", estrategia.id)
                .put("investimento", investimento)
                .put("prazo", prazoParaApi)
            ApiClient.request("POST", "projetos", SessionManager(this).token(), body, {
                Toast.makeText(this, "Projeto cadastrado!", Toast.LENGTH_LONG).show()
                finish()
            }, { error -> Toast.makeText(this, "Erro ao salvar: $error", Toast.LENGTH_LONG).show() })
        }
    }

    private fun carregarEstrategias(spinner: Spinner, btnSalvar: Button) {
        ApiClient.request("GET", "estrategias", SessionManager(this).token(), onSuccess = { response ->
            estrategias.clear()
            val array = JSONArray(response)
            for (index in 0 until array.length()) {
                val item = array.getJSONObject(index)
                if (item.optBoolean("ativa", true)) {
                    estrategias.add(Estrategia(item.optString("id"), item.optString("titulo"), item.optString("descricao")))
                }
            }
            spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estrategias.map { it.titulo }).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            btnSalvar.isEnabled = estrategias.isNotEmpty()
            if (estrategias.isEmpty()) {
                Toast.makeText(this, "Nenhuma estratégia ativa disponível", Toast.LENGTH_LONG).show()
            }
        }, onError = { error ->
            Toast.makeText(this, "Erro ao carregar estratégias: $error", Toast.LENGTH_LONG).show()
        })
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
