package br.com.fiap.inovagab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        val btnIdeias =
            findViewById<Button>(R.id.btnIdeias)

        val btnListar =
            findViewById<Button>(R.id.btnListar)

        val btnEstrategias =
            findViewById<Button>(R.id.btnEstrategias)

        btnIdeias.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CadastroIdeiaActivity::class.java
                )
            )
        }

        btnListar.setOnClickListener {

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