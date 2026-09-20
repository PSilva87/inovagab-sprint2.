package br.com.fiap.inovagab

data class Projeto(

    var id: String = "",
    var nome: String = "",
    var status: String = "",
    var investimento: Double = 0.0,
    var retornoFinanceiro: Double = 0.0,
    var progresso: Int = 0

)
