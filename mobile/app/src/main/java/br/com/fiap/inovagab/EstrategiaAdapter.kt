package br.com.fiap.inovagab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EstrategiaAdapter(
    private val lista: List<Estrategia>
) : RecyclerView.Adapter<EstrategiaAdapter.EstrategiaViewHolder>() {

    class EstrategiaViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val txtTitulo: TextView =
            view.findViewById(R.id.txtTitulo)

        val txtDescricao: TextView =
            view.findViewById(R.id.txtDescricao)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EstrategiaViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_estrategia,
                parent,
                false
            )

        return EstrategiaViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EstrategiaViewHolder,
        position: Int
    ) {

        val estrategia = lista[position]

        holder.txtTitulo.text =
            estrategia.titulo

        holder.txtDescricao.text =
            "${estrategia.descricao}\nID: ${estrategia.id}"
    }

    override fun getItemCount(): Int {

        return lista.size
    }
}
