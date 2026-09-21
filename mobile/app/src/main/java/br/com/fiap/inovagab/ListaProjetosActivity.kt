package br.com.fiap.inovagab

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class ListaProjetosActivity : AppCompatActivity() {
    private val lista = mutableListOf<Projeto>()
    private lateinit var adapter: ProjetoAdapter
    private lateinit var sessao: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_projetos)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sessao = SessionManager(this)
        adapter = ProjetoAdapter(lista, sessao.role().orEmpty(), ::mostrarAtualizarProgresso, ::mostrarRegistrarResultado, ::confirmarExclusao)
        findViewById<RecyclerView>(R.id.recyclerProjetos).apply {
            layoutManager = LinearLayoutManager(this@ListaProjetosActivity)
            adapter = this@ListaProjetosActivity.adapter
        }
        carregarProjetos()
    }

    private fun carregarProjetos() {
        ApiClient.request("GET", "projetos", sessao.token(), onSuccess = { response ->
            lista.clear()
            val array = JSONArray(response)
            for (i in 0 until array.length()) {
                val item = array.getJSONObject(i)
                lista.add(Projeto(item.optString("id"), item.optString("nome"), item.optString("status"), item.optDouble("investimento"), item.optDouble("retornoFinanceiro"), item.optInt("progresso")))
            }
            adapter.notifyDataSetChanged()
        }, onError = { Toast.makeText(this, "Erro ao buscar projetos: $it", Toast.LENGTH_LONG).show() })
    }

    private fun mostrarAtualizarProgresso(projeto: Projeto) {
        val campo = EditText(this).apply { inputType = InputType.TYPE_CLASS_NUMBER; hint = "De 0 a 100"; setText(projeto.progresso.toString()) }
        AlertDialog.Builder(this).setTitle("Atualizar progresso").setMessage("${projeto.nome}: informe o percentual atual.").setView(campo)
            .setNegativeButton("Cancelar", null).setPositiveButton("Salvar") { _, _ ->
                val progresso = campo.text.toString().toIntOrNull()
                if (progresso == null || progresso !in 0..100) Toast.makeText(this, "Digite um número de 0 a 100.", Toast.LENGTH_LONG).show()
                else ApiClient.request("PATCH", "projetos/${projeto.id}/progresso", sessao.token(), JSONObject().put("progresso", progresso), {
                    Toast.makeText(this, "Progresso atualizado!", Toast.LENGTH_SHORT).show(); carregarProjetos()
                }, { Toast.makeText(this, "Não foi possível atualizar: $it", Toast.LENGTH_LONG).show() })
            }.show()
    }

    private fun mostrarRegistrarResultado(projeto: Projeto) {
        val formulario = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(48, 0, 48, 0) }
        val resultado = EditText(this).apply { hint = "Resultado obtido" }
        val retorno = EditText(this).apply { hint = "Retorno financeiro (ex.: 15000)"; inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL }
        formulario.addView(resultado); formulario.addView(retorno)
        AlertDialog.Builder(this).setTitle("Registrar resultado").setMessage(projeto.nome).setView(formulario)
            .setNegativeButton("Cancelar", null).setPositiveButton("Salvar") { _, _ ->
                val valor = retorno.text.toString().replace(',', '.').toDoubleOrNull()
                if (resultado.text.isBlank() || valor == null || valor < 0) Toast.makeText(this, "Preencha o resultado e um retorno válido.", Toast.LENGTH_LONG).show()
                else {
                    val corpo = JSONObject().put("resultados", resultado.text.toString()).put("retornoFinanceiro", valor)
                    ApiClient.request("PATCH", "projetos/${projeto.id}/resultados", sessao.token(), corpo, {
                        Toast.makeText(this, "Resultado registrado!", Toast.LENGTH_SHORT).show(); carregarProjetos()
                    }, { Toast.makeText(this, "Não foi possível registrar: $it", Toast.LENGTH_LONG).show() })
                }
            }.show()
    }

    private fun confirmarExclusao(projeto: Projeto) {
        AlertDialog.Builder(this).setTitle("Excluir projeto?")
            .setMessage("O projeto '${projeto.nome}' será removido. Esta ação não pode ser desfeita.")
            .setNegativeButton("Cancelar", null).setPositiveButton("Excluir") { _, _ ->
                ApiClient.request("DELETE", "projetos/${projeto.id}", sessao.token(), onSuccess = {
                    Toast.makeText(this, "Projeto excluído.", Toast.LENGTH_SHORT).show(); carregarProjetos()
                }, onError = { Toast.makeText(this, "Não foi possível excluir: $it", Toast.LENGTH_LONG).show() })
            }.show()
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
