package com.example.loginpage.models


data class ShortStoryGet(
    var id:Int?,
    var titulo:String = "",
    var sinopse:String = "",
    var capa:String = "",
    var status:Int?,
    var historia:String = "",
    var data:String = "",
    var premium:Int?,
    var classificacao:List<Classificacao>,
    var usuario:List<Usuario>,
    var tipo:List<Tipo>,
    var generos:List<Generos>,
    var curtido: Boolean,
    var favorito: Boolean,
    var lido: Boolean,
){
    override fun toString(): String {
        return super.toString()
    }
}