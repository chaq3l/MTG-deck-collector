package com.example.simplesqlapp.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.simplesqlapp.Model.Cart



//import android.widget.ArrayAdapter

class DBHelper (context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER){

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY : String = ("CREATE TABLE $TABLE_NAME ($COL_ID TEXT PRIMARY KEY, $COL_NAME TEXT NOT NULL, $COL_SECOND_ID TEXT)")
        //val CREATE_TABLE_QUERY = ("CREATE TABLE "+ TABLE_NAME + " (" +COL_ID +" INT PRIMARY KEY, " + COL_NAME + " TEXT, " + COL_SECOND_ID + " INT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

companion object{
    private val DATABASE_VER = 1
    private val DATABASE_NAME = "SCRYFAL.db"

    //Table
    private val TABLE_NAME="Cart1"
    private val COL_ID="Id1"
    private val COL_NAME ="Name1"
    private val COL_SECOND_ID="SecondaryId1"
}

    //CRUD
    val allCarts:List<Cart>
    get(){
        val lstCarts = ArrayList<Cart>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db:SQLiteDatabase = this.writableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if (cursor.moveToFirst())
        {
            do{
                val cart = Cart()
                cart.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                cart.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                cart.secondaryId= cursor.getString(cursor.getColumnIndex(COL_SECOND_ID))

                lstCarts.add(cart)
            }while (cursor.moveToNext())
        }
        db.close()
        return lstCarts

    }



    fun readDataFromJSON(cart:Cart)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,cart.id)
        values.put(COL_NAME,cart.name)
        values.put(COL_SECOND_ID,cart.secondaryId)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun addCart(cart:Cart)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,cart.id)
        values.put(COL_NAME,cart.name)
        values.put(COL_SECOND_ID,cart.secondaryId)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateCart(cart:Cart):Int
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, cart.id)
        values.put(COL_NAME, cart.name)
        values.put(COL_SECOND_ID, cart.secondaryId)

        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(cart.id.toString()))

    }

    fun deleteCart(cart:Cart)
    {
        val db = this.writableDatabase


        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(cart.id.toString()))
        db.close()
    }
}