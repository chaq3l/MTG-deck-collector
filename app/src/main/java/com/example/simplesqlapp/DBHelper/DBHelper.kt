package com.example.simplesqlapp.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.CartDeck
import com.example.simplesqlapp.Model.Deck


//import android.widget.ArrayAdapter

open class DBHelper (context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VER) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CART_TABLE_QUERY: String =
            ("CREATE TABLE $CART_TABLE ($CART_ID INTEGER PRIMARY KEY, $CART_NAME TEXT NOT NULL, $CART_MANA_COST TEXT, $CART_DESCRIPTION TEXT, $CART_COLOR TEXT, $CART_COLOR_IDENTITY TEXT)")
        val CREATE_DECK_TABLE_QUERY: String =
            ("CREATE TABLE $DECK_TABLE ($DECK_ID INTEGER PRIMARY KEY AUTOINCREMENT, $DECK_NAME TEXT NOT NULL)")
        val CREATE_CART_TO_DECK_TABLE: String =
            ("CREATE TABLE $CART_IN_SINGLE_DECK ($CART_IN_DECK_ID INTEGER PRIMARY KEY AUTOINCREMENT, $CART_ID INT, $DECK_ID INT)")
        //val CREATE_CART_TO_DECK_TABLE : String =("CREATE TABLE $CART_IN_SINGLE_DECK ($CART_IN_DECK_ID INTEGER PRIMARY KEY AUTOINCREMENT, $CART_ID INT, $CART_NAME INT, $CART_SECOND_ID TEXT, $DECK_ID INT, $DECK_NAME TEXT NOT NULL)")
        //val CREATE_TABLE_QUERY = ("CREATE TABLE "+ TABLE_NAME + " (" +COL_ID +" INT PRIMARY KEY, " + COL_NAME + " TEXT, " + COL_SECOND_ID + " INT)")
        db!!.execSQL(CREATE_CART_TABLE_QUERY)
        db.execSQL(CREATE_DECK_TABLE_QUERY)
        db.execSQL(CREATE_CART_TO_DECK_TABLE)

        val TRANSACTION : String = (
                "UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = $DECK_TABLE"
                )

       // db.execSQL(TRANSACTION)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $CART_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $DECK_TABLE")
        onCreate(db)
    }

    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "SCRYFAL.db"

        //Table
        val CART_TABLE = "Cart"
        val CART_ID = "CartId"
        val CART_NAME = "CartName"
        val CART_MANA_COST = "CartSecondaryId"
        val CART_DESCRIPTION = "CartText"
        val CART_COLOR = "CartColors"
        val CART_COLOR_IDENTITY = "CartColorIdentity"

        private val DECK_TABLE = "Deck"
        val DECK_ID = "DeckId"
        private val DECK_NAME = "DeckName"

        val CART_IN_SINGLE_DECK = "CartInDeck"
        val CART_IN_DECK_ID = "CartInDeckPrimaryKey"
        //Cart Id
        //Deck Id

    }

    fun readDataFromJSON(cart: Cart) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CART_ID, cart.id)
        values.put(CART_NAME, cart.name)
        values.put(CART_MANA_COST, cart.manaCost)
        values.put(CART_DESCRIPTION, cart.cartText)
        values.put(CART_COLOR, cart.cartColors)
        values.put(CART_COLOR_IDENTITY, cart.cartColorIdentity)

        db.insert(CART_TABLE, null, values)
        db.close()
    }


    //CRUD
    val allCarts: List<Cart>
        get() {
            val lstCarts = ArrayList<Cart>()
            val selectQuery = "SELECT * FROM $CART_TABLE"
            val db: SQLiteDatabase = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val cart = Cart()
                    cart.id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                    cart.name = cursor.getString(cursor.getColumnIndex(CART_NAME))
                    cart.manaCost = cursor.getString(cursor.getColumnIndex(CART_MANA_COST))
                    cart.cartColors = cursor.getString(cursor.getColumnIndex(CART_COLOR))
                    cart.cartColorIdentity = cursor.getString(cursor.getColumnIndex(
                        CART_COLOR_IDENTITY))

                    lstCarts.add(cart)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstCarts

        }

    fun addCart(cart: Cart) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CART_ID, cart.id)
        values.put(CART_NAME, cart.name)
        values.put(CART_MANA_COST, cart.manaCost)
        values.put(CART_DESCRIPTION, cart.cartText)

        db.insert(CART_TABLE, null, values)
        db.close()
    }

    fun updateCart(cart: Cart): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CART_ID, cart.id)
        values.put(CART_NAME, cart.name)
        values.put(CART_MANA_COST, cart.manaCost)
        values.put(CART_DESCRIPTION, cart.cartText)

        return db.update(CART_TABLE, values, "$CART_ID=?", arrayOf(cart.id.toString()))
    }

    fun deleteCart(cart: Cart) {
        val db = this.writableDatabase


        db.delete(CART_TABLE, "$CART_ID=?", arrayOf(cart.id.toString()))
        db.close()
    }


        private val allDecksMap2 : MutableMap<Int, Deck> = mutableMapOf()
        val allDecksMap: MutableMap<Int, Deck>
        get() {

            val lstDecksMap = ArrayList<Deck>()
            val selectQuery = "SELECT * FROM $DECK_TABLE"
            val db: SQLiteDatabase = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val deck = Deck()
                    deck.id = cursor.getInt(cursor.getColumnIndex(DECK_ID))
                    deck.name = cursor.getString(cursor.getColumnIndex(DECK_NAME))
                    allDecksMap2.put(deck.id, deck)
                    lstDecksMap.add(deck)
                } while (cursor.moveToNext())
            }
            db.close()
            return allDecksMap2
        }
        //allDecks.put(deck.it, deck)
        // można sprobować użycia val allDecks: MutableMap<Int, Deck>

    val allDecks: List<Deck>
        get() {
            val lstDecks = ArrayList<Deck>()
            val selectQuery = "SELECT * FROM $DECK_TABLE"
            val db: SQLiteDatabase = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val deck = Deck()
                    deck.id = cursor.getInt(cursor.getColumnIndex(DECK_ID))
                    deck.name = cursor.getString(cursor.getColumnIndex(DECK_NAME))


                    lstDecks.add(deck)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstDecks

        }

    fun addDeck(deck: Deck) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DECK_ID, deck.id)
        values.put(DECK_NAME, deck.name)


        db.insert(DECK_TABLE, null, values)
        db.close()
    }

    fun updateDeck(deck: Deck): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DECK_ID, deck.id)
        values.put(DECK_NAME, deck.name)


        return db.update(DECK_TABLE, values, "$DECK_ID=?", arrayOf(deck.id.toString()))
    }

    fun deleteDeck(deck: Deck) {
        val db = this.writableDatabase


        db.delete(DECK_TABLE, "$DECK_ID=?", arrayOf(deck.id.toString()))
        db.close()
    }


    fun addCartToDeck(cartDeck: CartDeck) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CART_ID, cartDeck.cartId)
        values.put(DECK_ID, cartDeck.deckId)


        db.insert(CART_IN_SINGLE_DECK, null, values)
        db.close()
    }

    fun deleteCartDeck(cartDeck: CartDeck) {
        val db = this.writableDatabase
        db.delete(
            CART_IN_SINGLE_DECK,
            "$CART_IN_DECK_ID=?",
            arrayOf(cartDeck.cartDeckPrimaryId.toString())
        )

        db.close()
    }

    fun deleteSameCartsFromDeck(cartDeck: CartDeck){
        val db = this.writableDatabase
        db.delete(CART_IN_SINGLE_DECK,"$CART_ID=? AND $DECK_ID=?", arrayOf(cartDeck.cartId.toString(), cartDeck.deckId.toString()))

        db.close()
    }

}