package com.example.loginpage.models

import com.google.gson.annotations.SerializedName

data class Dashboard(
    var receita: ReceitaGerada,
    var curtidas: Curtidas,
    var favoritos: Favoritos,
    var lidos: Lidos,
    var compras: Compras,
    var recomendacoes: Recomendacoes,

    @SerializedName("dados_usuarios")
    var userData: PorcentagemUsuarios,

    @SerializedName("weekSell")
    var vendasSemana: Vendas,

    var roundedData: Int
)

data class ReceitaGerada (
    @SerializedName("receita_gerada")
    var receitaGerada: Double
)

data class Curtidas (
    @SerializedName("id_anuncio")
    var idAnnoncement: Int?,

    @SerializedName("quantidade_curtidas")
    var qtdCurtidas: String
)

data class Favoritos(
    @SerializedName("id_anuncio")
    var idAnnoncement: Int?,

    @SerializedName("quantidade_favoritos")
    var qtdFavoritos: String
)

data class Lidos (
    @SerializedName("id_anuncio")
    var idAnnoncement: Int?,

    @SerializedName("quantidade_lidos")
    var qtdLidos: String
)

data class Compras (
    @SerializedName("id_anuncio")
    var idAnnoncement: Int?,

    @SerializedName("quantidade_compras")
    var qtdCompras: String
)

data class Recomendacoes(
    @SerializedName("quantidade_recomendacoes")
    var qtdRecomendacoes: String
)

data class PorcentagemUsuarios(
    @SerializedName("porcentagem_somente_escritor")
    var escritores: String,

    @SerializedName("porcentagem_somente_leitor")
    var leitores: String
)

data class Vendas(
    var dates: List<Dias>,
    var sales: List<DadosVendas>,
    var invoicing: List<Double>
)

data class Dias(
    @SerializedName("data_compra")
    var dataCompra: String
)

data class DadosVendas(
    @SerializedName("quantidade_vendas")
    var qtdVendas: String
)

//data class Faturamento(
//    var faturamento: Double
//)