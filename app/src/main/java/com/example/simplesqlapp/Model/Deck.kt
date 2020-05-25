package com.example.simplesqlapp.Model

class Deck {
    var id:Int=0
    var name:String?=null

    constructor(){}
    constructor(Id:Int,name:String){
        this.id=Id
        this.name=name
    }
}