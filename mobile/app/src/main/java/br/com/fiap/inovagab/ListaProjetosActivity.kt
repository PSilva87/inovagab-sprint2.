package br.com.fiap.inovagab

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class ListaProjetosActivity : AppCompatActivity() {
    private val lista = mutableListOf<Projeto>()
    private lateinit var adapter: ProjetoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_lista_projetos)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = ProjetoAdapter(lista)
        findViewById<RecyclerView>(R.id.recyclerProjetos).apply { layoutManager = LinearLayoutManager(this@ListaProjetosActivity); adapter = this@ListaProjetosActivity.adapter }
        ApiClient.request("GET", "projetos", SessionManager(this).token(), onSuccess = { response ->
            lista.clear(); val array = JSONArray(response)
            for (i in 0 until array.length()) { val item = array.getJSONObject(i); lista.add(Projeto(item.optString("id"), item.optString("nome"), item.optString("status"), item.optDouble("investimento"), item.optDouble("retornoFinanceiro"), item.optInt("progresso"))) }
            adapter.notifyDataSetChanged()
        }, onError = { Toast.makeText(this, "Erro ao buscar projetos: $it", Toast.LENGTH_LONG).show() })
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
