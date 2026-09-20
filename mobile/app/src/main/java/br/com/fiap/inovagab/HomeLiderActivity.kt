package br.com.fiap.inovagab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import org.json.JSONObject

class HomeLiderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home_lider)

        val txtTotal =
            findViewById<TextView>(R.id.txtTotal)

        val txtAprovadas =
            findViewById<TextView>(R.id.txtAprovadas)

        val txtPendentes =
            findViewById<TextView>(R.id.txtPendentes)

        val btnEstrategia =
            findViewById<Button>(R.id.btnEstrategia)

        val btnProjetos =
            findViewById<Button>(R.id.btnProjetos)

        val btnVisualizarEstrategias =
            findViewById<Button>(R.id.btnVisualizarEstrategias)

        btnEstrategia.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CadastroEstrategiaActivity::class.java
                )
            )
        }

        btnProjetos.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ListaProjetosActivity::class.java
                )
            )
        }

        btnVisualizarEstrategias.setOnClickListener {
            startActivity(Intent(this, ListaEstrategiasActivity::class.java))
        }

        ApiClient.request("GET", "dashboard", SessionManager(this).token(), onSuccess = { response ->
            val dashboard = JSONObject(response)
            txtTotal.text = "Total Ideias: ${dashboard.optLong("totalIdeias")}" 
            txtAprovadas.text = "Ideias Aprovadas: ${dashboard.optLong("ideiasAprovadas")}" 
            txtPendentes.text = "Ideias Pendentes: ${dashboard.optLong("ideiasPendentes")}" 
        }, onError = { error -> Toast.makeText(this, "Erro ao carregar dashboard: $error", Toast.LENGTH_LONG).show() })
    }
}
