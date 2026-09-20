package br.com.fiap.inovagab

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IdeiaAdapter(private val lista: List<Ideia>, private val role: String, private val onAprovar: (Ideia) -> Unit, private val onExcluir: (Ideia) -> Unit) : RecyclerView.Adapter<IdeiaAdapter.IdeiaViewHolder>() {
    class IdeiaViewHolder(view: View) : RecyclerView.ViewHolder(view) { val titulo: TextView = view.findViewById(R.id.txtTitulo); val descricao: TextView = view.findViewById(R.id.txtDescricao); val status: TextView = view.findViewById(R.id.txtStatus); val aprovar: Button = view.findViewById(R.id.btnAprovar); val excluir: Button = view.findViewById(R.id.btnExcluir) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IdeiaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ideia, parent, false))
    override fun onBindViewHolder(holder: IdeiaViewHolder, position: Int) { val ideia = lista[position]; holder.titulo.text = ideia.titulo; holder.descricao.text = ideia.descricao; holder.status.text = "Status: ${ideia.status}"; holder.status.setTextColor(if (ideia.status == "APROVADA") Color.rgb(19, 138, 74) else Color.rgb(0, 103, 177)); holder.aprovar.visibility = if (role == "GESTOR" && ideia.status != "APROVADA") View.VISIBLE else View.GONE; holder.excluir.visibility = if (role == "OPERADOR") View.VISIBLE else View.GONE; holder.aprovar.setOnClickListener { onAprovar(ideia) }; holder.excluir.setOnClickListener { onExcluir(ideia) } }
    override fun getItemCount() = lista.size
}
