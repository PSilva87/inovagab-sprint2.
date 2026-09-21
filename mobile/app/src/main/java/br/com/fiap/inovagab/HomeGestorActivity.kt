package br.com.fiap.inovagab

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        val btnGerenciarProjetos =
            findViewById<Button>(R.id.btnGerenciarProjetos)

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

        btnGerenciarProjetos.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ListaProjetosActivity::class.java
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { menuInflater.inflate(R.menu.menu_sessao, menu); return true }
    override fun onOptionsItemSelected(item: MenuItem): Boolean { if (item.itemId == R.id.menuSair) { encerrarSessao(); return true }; return super.onOptionsItemSelected(item) }
}
