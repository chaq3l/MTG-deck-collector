package com.example.simplesqlapp.Model

class CartDeck {
    var cartDeckPrimaryId:Int=0 //DB HAS AN AUTO INCREMENTATION PRIMARY KAY
    var cartId:Int=0
    //var cartName:String?=null
    //var cartSecondaryId:String?=null
    var deckId:Int=0
    //var cartNumberInSingleDeck:Int=0
    //var deckName:String?=null

    constructor(){}
    constructor(cartDeckPrimaryId:Int, cartId:Int, deckId:Int){
        this.cartDeckPrimaryId = cartDeckPrimaryId
        this.cartId=cartId
        //this.cartName=cartName
        //this.cartSecondaryId=cartSecondaryId
        this.deckId=deckId

        //this.deckName=deckName
    }
}