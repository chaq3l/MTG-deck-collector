package com.example.simplesqlapp.Model

class Cart {
    var id:Int=0
    //var id:String?=null
    var name:String?=null
    //var secondaryId:String?=null
    //var secondaryId:Int=0
    var secondaryId:String?=null

    constructor(){}
    constructor(uniqueId:Int,name:String,secondaryId:String){
        this.id=uniqueId
        this.name=name
        //this.secondaryId=secondaryId
        this.secondaryId=secondaryId
    }
}