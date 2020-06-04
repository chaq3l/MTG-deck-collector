package com.example.simplesqlapp.DBHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.simplesqlapp.Model.Cart


//import android.widget.ArrayAdapter

class SingleCartDBHelper (context: Context, actualCart: Int?):DBHelper(context) {



    private val actualCart : Int? = actualCart
    val singleCart: List<Cart>
        get() {
            val singleCart = ArrayList<Cart>()
            val selectQuery = "SELECT * FROM $CART_TABLE WHERE $CART_ID = $actualCart"
            val db: SQLiteDatabase = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val cart = Cart()
                    cart.id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                    cart.name = cursor.getString(cursor.getColumnIndex(CART_NAME))
                    cart.manaCost = cursor.getString(cursor.getColumnIndex(CART_MANA_COST))
                    cart.cartText = cursor.getString(cursor.getColumnIndex(CART_DESCRIPTION))

                    singleCart.add(cart)
                } while (cursor.moveToNext())
            }
            db.close()
            return singleCart

        }
}