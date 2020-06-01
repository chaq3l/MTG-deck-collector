package com.example.simplesqlapp.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.CartDeck
import com.example.simplesqlapp.Model.Deck


//import android.widget.ArrayAdapter

class SingleCartDBHelper (context: Context, actualDeck:String?, actualCart: Int?):DBHelper(context,actualDeck) {



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
                    cart.secondaryId = cursor.getString(cursor.getColumnIndex(CART_SECOND_ID))

                    singleCart.add(cart)
                } while (cursor.moveToNext())
            }
            db.close()
            return singleCart

        }
}