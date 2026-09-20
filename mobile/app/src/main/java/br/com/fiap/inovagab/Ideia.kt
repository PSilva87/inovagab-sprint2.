package br.com.fiap.inovagab

data class Ideia(

    var id: String = "",
    var titulo: String = "",
    var descricao: String = "",
    var status: String = "",
    var estrategiaId: String = "",
    var prioridade: String? = null,
    var pontuacao: Int? = null

)
