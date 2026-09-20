package br.com.fiap.inovagab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProjetoAdapter(
    private val lista: List<Projeto>
) : RecyclerView.Adapter<ProjetoAdapter.ProjetoViewHolder>() {

    class ProjetoViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val txtNome: TextView =
            view.findViewById(R.id.txtNome)

        val txtStatus: TextView =
            view.findViewById(R.id.txtStatus)

        val txtInvestimento: TextView =
            view.findViewById(R.id.txtInvestimento)

        val txtRetorno: TextView =
            view.findViewById(R.id.txtRetorno)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjetoViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_projeto,
                parent,
                false
            )

        return ProjetoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProjetoViewHolder,
        position: Int
    ) {

        val projeto = lista[position]

        holder.txtNome.text =
            projeto.nome

        holder.txtStatus.text =
            "Status: ${projeto.status}"

        holder.txtInvestimento.text =
            "Investimento: ${projeto.investimento}"

        holder.txtRetorno.text =
            "Retorno: ${projeto.retornoFinanceiro} | Progresso: ${projeto.progresso}%"
    }

    override fun getItemCount(): Int {

        return lista.size
    }
}
