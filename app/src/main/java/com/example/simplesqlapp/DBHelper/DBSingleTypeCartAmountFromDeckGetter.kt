package com.example.simplesqlapp.DBHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.simplesqlapp.Model.CartDeck


//import android.widget.ArrayAdapter

class DBSingleTypeCartAmountFromDeckGetter (context: Context, cartId: Int, deckId : Int):DBHelper(context) {


    private val deckId : Int = deckId
    private val cartId : Int = cartId

    val searchedCartList: List<CartDeck>
        get() {
            val foundCarts = ArrayList<CartDeck>()
            val selectQuery = "SELECT * FROM $CART_IN_SINGLE_DECK WHERE $CART_ID = $cartId AND $DECK_ID = $deckId"
            val db: SQLiteDatabase = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val cartDeck = CartDeck()
                    0
                    cartDeck.cartId = cursor.getInt(cursor.getColumnIndex(CART_ID))
                    cartDeck.deckId = cursor.getInt(cursor.getColumnIndex(DECK_ID))


                    foundCarts.add(cartDeck)
                } while (cursor.moveToNext())
            }
            db.close()
            return foundCarts

        }
}