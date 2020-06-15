package com.example.simplesqlapp.Model

class Cart {
    var id:Int=0
    //var id:String?=null
    var name:String?=null
    //var secondaryId:String?=null
    //var secondaryId:Int=0
    var manaCost:String?=null
    var cartText:String?=null
    var cartColors:String?=null
    var cartImageUris : String? = null

    constructor(){}
    constructor(
        uniqueId:Int, name:String, manaCost:String,
        cartText:String, cartColors: String, cartImageUris: String
    ){
        this.id=uniqueId
        this.name=name
        //this.secondaryId=secondaryId
        this.manaCost=manaCost
        this.cartText=cartText
        this.cartColors=cartColors
        this.cartImageUris= cartImageUris
    }
}