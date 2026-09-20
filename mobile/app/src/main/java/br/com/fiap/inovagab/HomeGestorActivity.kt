package br.com.fiap.inovagab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeGestorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home_gestor)

        val btnProjeto =
            findViewById<Button>(R.id.btnProjeto)

        val btnIdeias =
            findViewById<Button>(R.id.btnIdeias)

        val btnEstrategias =
            findViewById<Button>(R.id.btnEstrategias)

        btnProjeto.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CadastroProjetoActivity::class.java
                )
            )
        }

        btnIdeias.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ListaIdeiasActivity::class.java
                )
            )
        }

        btnEstrategias.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ListaEstrategiasActivity::class.java
                )
            )
        }
    }
}