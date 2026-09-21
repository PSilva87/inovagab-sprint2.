package br.com.fiap.inovagab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProjetoAdapter(
    private val lista: List<Projeto>,
    private val perfil: String,
    private val aoAtualizarProgresso: (Projeto) -> Unit,
    private val aoRegistrarResultado: (Projeto) -> Unit,
    private val aoExcluir: (Projeto) -> Unit
) : RecyclerView.Adapter<ProjetoAdapter.ProjetoViewHolder>() {

    class ProjetoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNome: TextView = view.findViewById(R.id.txtNome)
        val txtStatus: TextView = view.findViewById(R.id.txtStatus)
        val txtInvestimento: TextView = view.findViewById(R.id.txtInvestimento)
        val txtRetorno: TextView = view.findViewById(R.id.txtRetorno)
        val btnAtualizarProgresso: Button = view.findViewById(R.id.btnAtualizarProgresso)
        val btnRegistrarResultado: Button = view.findViewById(R.id.btnRegistrarResultado)
        val btnExcluirProjeto: Button = view.findViewById(R.id.btnExcluirProjeto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjetoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_projeto, parent, false)
        return ProjetoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjetoViewHolder, position: Int) {
        val projeto = lista[position]
        holder.txtNome.text = projeto.nome
        holder.txtStatus.text = "Status: ${projeto.status}"
        holder.txtInvestimento.text = "Investimento: R$ ${"%.2f".format(projeto.investimento)}"
        val prazoFormatado = try { LocalDate.parse(projeto.prazo).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) } catch (_: Exception) { projeto.prazo.ifBlank { "Não informado" } }
        holder.txtRetorno.text = "Retorno: R$ ${"%.2f".format(projeto.retornoFinanceiro)} | Progresso: ${projeto.progresso}%\nPrazo: $prazoFormatado"

        val podeGerenciar = perfil == "GESTOR"
        holder.btnAtualizarProgresso.visibility = if (podeGerenciar) View.VISIBLE else View.GONE
        holder.btnRegistrarResultado.visibility = if (podeGerenciar) View.VISIBLE else View.GONE
        holder.btnExcluirProjeto.visibility = if (podeGerenciar) View.VISIBLE else View.GONE
        holder.btnAtualizarProgresso.setOnClickListener { aoAtualizarProgresso(projeto) }
        holder.btnRegistrarResultado.setOnClickListener { aoRegistrarResultado(projeto) }
        holder.btnExcluirProjeto.setOnClickListener { aoExcluir(projeto) }
    }

    override fun getItemCount() = lista.size
}
