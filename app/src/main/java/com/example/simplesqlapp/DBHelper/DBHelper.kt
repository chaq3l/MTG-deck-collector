package com.example.simplesqlapp.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.Deck


//import android.widget.ArrayAdapter

class DBHelper (context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER){

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CART_TABLE_QUERY : String = ("CREATE TABLE $CART_TABLE ($CART_ID TEXT PRIMARY KEY, $CART_NAME TEXT NOT NULL, $CART_SECOND_ID TEXT)")
        val CREATE_DECK_TABLE_QUERY : String = ("CREATE TABLE $DECK_TABLE ($DECK_ID TEXT PRIMARY KEY, $DECK_NAME TEXT NOT NULL)")
        //val CREATE_TABLE_QUERY = ("CREATE TABLE "+ TABLE_NAME + " (" +COL_ID +" INT PRIMARY KEY, " + COL_NAME + " TEXT, " + COL_SECOND_ID + " INT)")
        db!!.execSQL(CREATE_CART_TABLE_QUERY)
        db.execSQL(CREATE_DECK_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $CART_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $DECK_TABLE")
        onCreate(db!!)
    }

companion object{
    private val DATABASE_VER = 1
    private val DATABASE_NAME = "SCRYFAL.db"

    //Table
    private val CART_TABLE="Cart"
    private val CART_ID="CartId"
    private val CART_NAME ="CartName"
    private val CART_SECOND_ID="CartSecondaryId"

    private val DECK_TABLE="Deck1"
    private val DECK_ID="Id1"
    private val DECK_NAME ="DeckName"

}

    fun readDataFromJSON(cart:Cart)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CART_ID,cart.id)
        values.put(CART_NAME,cart.name)
        values.put(CART_SECOND_ID,cart.secondaryId)

        db.insert(CART_TABLE, null, values)
        db.close()
    }


    //CRUD
    val allCarts:List<Cart>
    get(){
        val lstCarts = ArrayList<Cart>()
        val selectQuery = "SELECT * FROM $CART_TABLE"
        val db:SQLiteDatabase = this.writableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if (cursor.moveToFirst())
        {
            do{
                val cart = Cart()
                cart.id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                cart.name = cursor.getString(cursor.getColumnIndex(CART_NAME))
                cart.secondaryId= cursor.getString(cursor.getColumnIndex(CART_SECOND_ID))

                lstCarts.add(cart)
            }while (cursor.moveToNext())
        }
        db.close()
        return lstCarts

    }

    fun addCart(cart:Cart)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CART_ID,cart.id)
        values.put(CART_NAME,cart.name)
        values.put(CART_SECOND_ID,cart.secondaryId)

        db.insert(CART_TABLE, null, values)
        db.close()
    }

    fun updateCart(cart:Cart):Int
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CART_ID, cart.id)
        values.put(CART_NAME, cart.name)
        values.put(CART_SECOND_ID, cart.secondaryId)

        return db.update(CART_TABLE, values, "$CART_ID=?", arrayOf(cart.id.toString()))
    }

    fun deleteCart(cart:Cart)
    {
        val db = this.writableDatabase


        db.delete(CART_TABLE,"$CART_ID=?", arrayOf(cart.id.toString()))
        db.close()
    }


val allDecks:List<Deck>
    get(){
        val lstDecks = ArrayList<Deck>()
        val selectQuery = "SELECT * FROM $DECK_TABLE"
        val db:SQLiteDatabase = this.writableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if (cursor.moveToFirst())
        {
            do{
                val deck = Deck()
                deck.id = cursor.getInt(cursor.getColumnIndex(DECK_ID))
                deck.name = cursor.getString(cursor.getColumnIndex(DECK_NAME))


                lstDecks.add(deck)
            }while (cursor.moveToNext())
        }
        db.close()
        return lstDecks

    }

fun addDeck(deck: Deck)
{
    val db = this.writableDatabase
    val values = ContentValues()
    values.put(DECK_ID,deck.id)
    values.put(DECK_NAME,deck.name)


    db.insert(DECK_TABLE, null, values)
    db.close()
}

fun updateDeck(deck:Deck):Int
{
    val db = this.writableDatabase
    val values = ContentValues()
    values.put(DECK_ID, deck.id)
    values.put(DECK_NAME, deck.name)


    return db.update(DECK_TABLE, values, "$DECK_ID=?", arrayOf(deck.id.toString()))
}

fun deleteDeck(deck: Deck)
{
    val db = this.writableDatabase


    db.delete(DECK_TABLE,"$DECK_ID=?", arrayOf(deck.id.toString()))
    db.close()
}
}