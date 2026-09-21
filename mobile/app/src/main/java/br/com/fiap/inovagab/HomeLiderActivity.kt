package br.com.fiap.inovagab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import org.json.JSONObject
import java.util.Locale

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

        val txtProjetos =
            findViewById<TextView>(R.id.txtProjetos)

        val txtInvestimento =
            findViewById<TextView>(R.id.txtInvestimento)

        val txtRetorno =
            findViewById<TextView>(R.id.txtRetornoFinanceiro)

        val txtLucro =
            findViewById<TextView>(R.id.txtLucro)

        val txtRoi =
            findViewById<TextView>(R.id.txtRoi)

        val barraRoi =
            findViewById<ProgressBar>(R.id.barraRoi)

        val txtResumoEstrategias =
            findViewById<TextView>(R.id.txtResumoEstrategias)

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
            txtProjetos.text = "Projetos acompanhados: ${dashboard.optLong("totalProjetos")}" 
            txtInvestimento.text = "Investimento: ${moeda(dashboard.optDouble("investimentoTotal"))}"
            txtRetorno.text = "Retorno financeiro: ${moeda(dashboard.optDouble("retornoFinanceiroTotal"))}"
            txtLucro.text = "Lucro obtido: ${moeda(dashboard.optDouble("lucroTotal"))}"
            txtRoi.text = "ROI geral: ${String.format(Locale("pt", "BR"), "%.2f", dashboard.optDouble("roiPercentual"))}%"
            barraRoi.progress = dashboard.optDouble("roiPercentual").coerceIn(0.0, 100.0).toInt()

            val resultados = dashboard.optJSONObject("resultadosPorEstrategia")
            val resumo = StringBuilder()
            if (resultados != null && resultados.length() > 0) {
                resumo.append("Retorno por estratégia\n")
                val chaves = resultados.keys()
                while (chaves.hasNext()) {
                    val item = resultados.optJSONObject(chaves.next()) ?: continue
                    resumo.append("• ${item.optString("estrategiaTitulo")}: ")
                    resumo.append("${moeda(item.optDouble("retornoFinanceiroTotal"))} | ")
                    resumo.append("ROI ${String.format(Locale("pt", "BR"), "%.2f", item.optDouble("roiPercentual"))}%\n")
                }
            } else {
                resumo.append("Ainda não há resultados financeiros por estratégia.")
            }
            txtResumoEstrategias.text = resumo.toString().trim()
        }, onError = { error -> Toast.makeText(this, "Erro ao carregar dashboard: $error", Toast.LENGTH_LONG).show() })
    }

    private fun moeda(valor: Double): String =
        String.format(Locale("pt", "BR"), "R$ %,.2f", valor)
}
