package com.example.simplesqlapp.DBHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.simplesqlapp.Model.Cart


//import android.widget.ArrayAdapter

class DBCartSearcher (context: Context, searchedPhrase: String?):DBHelper(context) {



    private val searchedPhrase : String? = searchedPhrase
    val searchedCartList: List<Cart>
        get() {
            val foundCart = ArrayList<Cart>()
            val selectQuery = "SELECT * FROM $CART_TABLE WHERE $CART_NAME LIKE '$searchedPhrase'"
            val db: SQLiteDatabase = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val cart = Cart()
                    cart.cardDbId = cursor.getInt(cursor.getColumnIndex(CARD_DB_ID))
                    cart.id = cursor.getString(cursor.getColumnIndex(CART_ID))
                    cart.name = cursor.getString(cursor.getColumnIndex(CART_NAME))
                    cart.manaCost = cursor.getString(cursor.getColumnIndex(CART_MANA_COST))
                    cart.cartText = cursor.getString(cursor.getColumnIndex(CART_DESCRIPTION))

                    foundCart.add(cart)
                } while (cursor.moveToNext())
            }
            db.close()
            return foundCart

        }
}