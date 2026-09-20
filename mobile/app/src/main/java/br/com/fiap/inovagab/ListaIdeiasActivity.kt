package br.com.fiap.inovagab

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class ListaIdeiasActivity : AppCompatActivity() {
    private val lista = mutableListOf<Ideia>(); private lateinit var adapter: IdeiaAdapter; private lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_lista_ideias); session = SessionManager(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = IdeiaAdapter(lista, session.role(), { aprovar(it) }, { excluir(it) })
        findViewById<RecyclerView>(R.id.recyclerIdeias).apply { layoutManager = LinearLayoutManager(this@ListaIdeiasActivity); adapter = this@ListaIdeiasActivity.adapter }; buscarIdeias()
    }
    private fun buscarIdeias() {
        ApiClient.request("GET", if (session.role() == "GESTOR") "ideias" else "ideias/minhas", session.token(), onSuccess = { response ->
            lista.clear(); val array = JSONArray(response)
            for (i in 0 until array.length()) { val item = array.getJSONObject(i); lista.add(Ideia(item.optString("id"), item.optString("titulo"), item.optString("descricao"), item.optString("status"), item.optString("estrategiaId"), item.optString("prioridade").ifBlank { null }, if (item.isNull("pontuacao")) null else item.optInt("pontuacao"))) }; adapter.notifyDataSetChanged()
        }, onError = { Toast.makeText(this, "Erro ao buscar ideias: $it", Toast.LENGTH_LONG).show() })
    }
    private fun aprovar(ideia: Ideia) = ApiClient.request("PATCH", "ideias/${ideia.id}/aprovacao", session.token(), JSONObject().put("aprovada", true), { Toast.makeText(this, "Ideia aprovada!", Toast.LENGTH_LONG).show(); buscarIdeias() }, { Toast.makeText(this, "Erro ao aprovar: $it", Toast.LENGTH_LONG).show() })
    private fun excluir(ideia: Ideia) = ApiClient.request("DELETE", "ideias/${ideia.id}", session.token(), onSuccess = { Toast.makeText(this, "Ideia excluída!", Toast.LENGTH_LONG).show(); buscarIdeias() }, onError = { Toast.makeText(this, "Erro ao excluir: $it", Toast.LENGTH_LONG).show() })
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
