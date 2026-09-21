package br.com.fiap.inovagab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import org.json.JSONObject
import java.util.Locale

class HomeLiderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home_lider)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        val txtProdutividade =
            findViewById<TextView>(R.id.txtProdutividade)

        val barraProdutividade =
            findViewById<ProgressBar>(R.id.barraProdutividade)

        val txtGraficoInvestimento =
            findViewById<TextView>(R.id.txtGraficoInvestimento)

        val txtGraficoRetorno =
            findViewById<TextView>(R.id.txtGraficoRetorno)

        val barraInvestimento =
            findViewById<ProgressBar>(R.id.barraInvestimento)

        val barraRetorno =
            findViewById<ProgressBar>(R.id.barraRetorno)

        val txtCalendarioExterno =
            findViewById<TextView>(R.id.txtCalendarioExterno)

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
            val produtividade = dashboard.optDouble("produtividadeMedia")
            txtProdutividade.text = "Produtividade média: ${String.format(Locale("pt", "BR"), "%.2f", produtividade)}%"
            barraProdutividade.progress = produtividade.coerceIn(0.0, 100.0).toInt()

            val investimento = dashboard.optDouble("investimentoTotal")
            val retorno = dashboard.optDouble("retornoFinanceiroTotal")
            val maiorValor = maxOf(investimento, retorno, 1.0)
            txtGraficoInvestimento.text = "Investimento  ${moeda(investimento)}"
            txtGraficoRetorno.text = "Retorno  ${moeda(retorno)}"
            barraInvestimento.progress = ((investimento / maiorValor) * 100).toInt()
            barraRetorno.progress = ((retorno / maiorValor) * 100).toInt()

            val feriado = dashboard.optJSONObject("proximoFeriadoNacional")
            txtCalendarioExterno.text = if (feriado == null) {
                "Calendário externo\nNão foi possível consultar os feriados neste momento."
            } else {
                "Calendário externo: próximo feriado\n${dataBrasileira(feriado.optString("data"))} — ${feriado.optString("nome")}"
            }

            val resultados = dashboard.optJSONObject("resultadosPorEstrategia")
            val resumo = StringBuilder()
            if (resultados != null && resultados.length() > 0) {
                resumo.append("Retorno por estratégia\n")
                val chaves = resultados.keys()
                while (chaves.hasNext()) {
                    val item = resultados.optJSONObject(chaves.next()) ?: continue
                    resumo.append("• ${item.optString("estrategiaTitulo")}: ")
                    resumo.append("${moeda(item.optDouble("retornoFinanceiroTotal"))} | ")
                    resumo.append("ROI ${String.format(Locale("pt", "BR"), "%.2f", item.optDouble("roiPercentual"))}% | ")
                    resumo.append("Prod. ${String.format(Locale("pt", "BR"), "%.2f", item.optDouble("produtividadeMedia"))}%\n")
                }
            } else {
                resumo.append("Ainda não há resultados financeiros por estratégia.")
            }
            txtResumoEstrategias.text = resumo.toString().trim()
        }, onError = { error -> Toast.makeText(this, "Erro ao carregar dashboard: $error", Toast.LENGTH_LONG).show() })
    }

    private fun moeda(valor: Double): String =
        String.format(Locale("pt", "BR"), "R$ %,.2f", valor)

    private fun dataBrasileira(data: String): String =
        if (data.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) "${data.substring(8, 10)}/${data.substring(5, 7)}/${data.substring(0, 4)}" else data

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { menuInflater.inflate(R.menu.menu_sessao, menu); return true }
    override fun onOptionsItemSelected(item: MenuItem): Boolean { if (item.itemId == R.id.menuSair) { encerrarSessao(); return true }; return super.onOptionsItemSelected(item) }
}
