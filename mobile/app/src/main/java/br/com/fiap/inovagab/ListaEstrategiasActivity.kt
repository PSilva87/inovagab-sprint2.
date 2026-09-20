package br.com.fiap.inovagab

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class ListaEstrategiasActivity : AppCompatActivity() {
    private val lista = mutableListOf<Estrategia>()
    private lateinit var adapter: EstrategiaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_lista_estrategias)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = EstrategiaAdapter(lista)
        findViewById<RecyclerView>(R.id.recyclerEstrategias).apply { layoutManager = LinearLayoutManager(this@ListaEstrategiasActivity); adapter = this@ListaEstrategiasActivity.adapter }
        ApiClient.request("GET", "estrategias", SessionManager(this).token(), onSuccess = { response ->
            lista.clear(); val array = JSONArray(response)
            for (i in 0 until array.length()) { val item = array.getJSONObject(i); lista.add(Estrategia(item.optString("id"), item.optString("titulo"), item.optString("descricao"), item.optString("categoria"), item.optString("campanha"), item.optBoolean("ativa", true))) }
            adapter.notifyDataSetChanged()
        }, onError = { Toast.makeText(this, "Erro ao buscar estratégias: $it", Toast.LENGTH_LONG).show() })
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
