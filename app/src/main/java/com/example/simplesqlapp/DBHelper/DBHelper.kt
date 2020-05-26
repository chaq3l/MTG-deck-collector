package com.example.simplesqlapp.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.CartDeck
import com.example.simplesqlapp.Model.Deck


//import android.widget.ArrayAdapter

class DBHelper (context: Context,actualDeck:String?):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER){

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CART_TABLE_QUERY : String = ("CREATE TABLE $CART_TABLE ($CART_ID INTEGER PRIMARY KEY, $CART_NAME TEXT NOT NULL, $CART_SECOND_ID TEXT)")
        val CREATE_DECK_TABLE_QUERY : String = ("CREATE TABLE $DECK_TABLE ($DECK_ID INTEGER PRIMARY KEY, $DECK_NAME TEXT NOT NULL)")
        val CREATE_CART_TO_DECK_TABLE : String =("CREATE TABLE $CART_IN_SINGLE_DECK ($CART_IN_DECK_ID INTEGER PRIMARY KEY AUTOINCREMENT, $CART_ID INT, $DECK_ID INT)")
        //val CREATE_CART_TO_DECK_TABLE : String =("CREATE TABLE $CART_IN_SINGLE_DECK ($CART_IN_DECK_ID INTEGER PRIMARY KEY AUTOINCREMENT, $CART_ID INT, $CART_NAME INT, $CART_SECOND_ID TEXT, $DECK_ID INT, $DECK_NAME TEXT NOT NULL)")
        //val CREATE_TABLE_QUERY = ("CREATE TABLE "+ TABLE_NAME + " (" +COL_ID +" INT PRIMARY KEY, " + COL_NAME + " TEXT, " + COL_SECOND_ID + " INT)")
        db!!.execSQL(CREATE_CART_TABLE_QUERY)
        db.execSQL(CREATE_DECK_TABLE_QUERY)
        db.execSQL(CREATE_CART_TO_DECK_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $CART_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $DECK_TABLE")
        onCreate(db)
    }

companion object{
    private val DATABASE_VER = 1
    private val DATABASE_NAME = "SCRYFAL.db"

    //Table
    private val CART_TABLE="Cart"
    private val CART_ID="CartId"
    private val CART_NAME ="CartName"
    private val CART_SECOND_ID="CartSecondaryId"

    private val DECK_TABLE="Deck"
    private val DECK_ID="DeckId"
    private val DECK_NAME ="DeckName"

    private val CART_IN_SINGLE_DECK="CartInDeck"
    private val CART_IN_DECK_ID="CartInDeckPrimaryKey"
    //Cart Id
    //Deck Id

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


    fun addCartToDeck(cartDeck: CartDeck)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CART_ID,cartDeck.cartId)
        //values.put(CART_NAME,cartDeck.cartName)
        //values.put(CART_SECOND_ID,cartDeck.cartSecondaryId)
        values.put(DECK_ID,cartDeck.deckId)
        //values.put(DECK_NAME,cartDeck.deckName)

        db.insert(CART_IN_SINGLE_DECK, null, values)
        db.close()
    }
    private val displayedDeckId = actualDeck
    val cartsInDeck:List<Cart>
    get() {
        val lstCartsInDeck = ArrayList<Cart>()

        //val selectQuery = "SELECT $CART_ID, $CART_NAME, $CART_SECOND_ID, $DECK_NAME, $DECK_ID  FROM $CART_IN_SINGLE_DECK WHERE $DECK_ID = 1"

        val selectQuery:String? = "SELECT * FROM $CART_TABLE C INNER JOIN $CART_IN_SINGLE_DECK CD ON C.$CART_ID=CD.$CART_ID WHERE $DECK_ID = $displayedDeckId"
        val db: SQLiteDatabase = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val cart = Cart()
                cart.id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                cart.name = cursor.getString(cursor.getColumnIndex(CART_NAME))
                cart.secondaryId = cursor.getString(cursor.getColumnIndex(CART_SECOND_ID))
                //cartDeck.deckId = cursor.getInt(cursor.getColumnIndex(DECK_ID))

                lstCartsInDeck.add(cart)
            } while (cursor.moveToNext())
        }
        db.close()
        return lstCartsInDeck
    }
}