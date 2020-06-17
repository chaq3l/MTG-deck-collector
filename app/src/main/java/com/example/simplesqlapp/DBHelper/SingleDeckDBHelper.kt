package com.example.simplesqlapp.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.CartDeck
import com.example.simplesqlapp.Model.Deck


//import android.widget.ArrayAdapter

open class SingleDeckDBHelper (context: Context, actualDeck:String?):DBHelper(context) {


    private val displayedDeckId = actualDeck
    val cartsInDeck: List<Cart>
        get() {
            val lstCartsInDeck = ArrayList<Cart>()

            //val selectQuery = "SELECT $CART_ID, $CART_NAME, $CART_SECOND_ID, $DECK_NAME, $DECK_ID  FROM $CART_IN_SINGLE_DECK WHERE $DECK_ID = 1"

            val selectQuery: String? =
                "SELECT * FROM $CART_TABLE C INNER JOIN $CART_IN_SINGLE_DECK CD ON C.$CARD_DB_ID=CD.$CARD_DB_ID WHERE $DECK_ID = $displayedDeckId"
            val db: SQLiteDatabase = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val cart = Cart()
                    cart.cardDbId = cursor.getInt(cursor.getColumnIndex(CARD_DB_ID))
                    cart.id = cursor.getString(cursor.getColumnIndex(CART_ID))
                    cart.name = cursor.getString(cursor.getColumnIndex(CART_NAME))
                    cart.manaCost = cursor.getString(cursor.getColumnIndex(CART_MANA_COST))
                    //cartDeck.deckId = cursor.getInt(cursor.getColumnIndex(DECK_ID))

                    lstCartsInDeck.add(cart)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstCartsInDeck
        }


    val cartsInSingleDeck: List<CartDeck>
        get() {
            val lstCartsInSingleDeck = ArrayList<CartDeck>()

            //val selectQuery = "SELECT $CART_ID, $CART_NAME, $CART_SECOND_ID, $DECK_NAME, $DECK_ID  FROM $CART_IN_SINGLE_DECK WHERE $DECK_ID = 1"

            val selectQuery: String? =
                "SELECT $CART_IN_DECK_ID FROM $CART_IN_SINGLE_DECK C INNER JOIN $CART_TABLE CD ON C.$CARD_DB_ID=CD.$CARD_DB_ID WHERE $DECK_ID = $displayedDeckId"
            val db: SQLiteDatabase = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {

                do {

                    val cartDeck = CartDeck()
                    cartDeck.cartDeckPrimaryId =
                        cursor.getInt(cursor.getColumnIndex(CART_IN_DECK_ID))

                    lstCartsInSingleDeck.add(cartDeck)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstCartsInSingleDeck
        }


}