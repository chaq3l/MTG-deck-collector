package com.example.simplesqlapp.Model

class CartDeck {
    //var cartDeckId:Int=0 DB HAS AN AUTO INCREMENTATION PRIMARY KAY
    var cartId:Int=0
    //var cartName:String?=null
    //var cartSecondaryId:String?=null
    var deckId:Int=0
    //var deckName:String?=null

    constructor(){}
    constructor(cartId:Int, deckId:Int){
        //this.cartDeckId=cartDeckId
        this.cartId=cartId
        //this.cartName=cartName
        //this.cartSecondaryId=cartSecondaryId
        this.deckId=deckId
        //this.deckName=deckName
    }
}