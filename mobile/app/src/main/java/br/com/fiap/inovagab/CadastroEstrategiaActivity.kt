package br.com.fiap.inovagab

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class CadastroEstrategiaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cadastro_estrategia)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titulo =
            findViewById<EditText>(R.id.editTitulo)

        val descricao =
            findViewById<EditText>(R.id.editDescricao)

        val categoria = findViewById<EditText>(R.id.editCategoria)
        val campanha = findViewById<EditText>(R.id.editCampanha)
        val btnSalvar =
            findViewById<Button>(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            if (titulo.text.isBlank() || descricao.text.isBlank() || categoria.text.isBlank() || campanha.text.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            btnSalvar.isEnabled = false
            btnSalvar.text = "SALVANDO..."

            val body = JSONObject()
                .put("titulo", titulo.text.toString())
                .put("descricao", descricao.text.toString())
                .put("categoria", categoria.text.toString())
                .put("campanha", campanha.text.toString())
                .put("ativa", true)
            ApiClient.request("POST", "estrategias", SessionManager(this).token(), body, {
                Toast.makeText(this, "Estratégia cadastrada!", Toast.LENGTH_LONG).show()
                titulo.text.clear(); descricao.text.clear(); categoria.text.clear(); campanha.text.clear()
                btnSalvar.isEnabled = true
                btnSalvar.text = "SALVAR ESTRATÉGIA"
            }, { error ->
                Toast.makeText(this, "Erro ao salvar: $error", Toast.LENGTH_LONG).show()
                btnSalvar.isEnabled = true
                btnSalvar.text = "SALVAR ESTRATÉGIA"
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
