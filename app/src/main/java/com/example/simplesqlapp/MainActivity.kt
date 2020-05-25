package com.example.simplesqlapp

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.example.simplesqlapp.Adapter.ListCartAdapter
import com.example.simplesqlapp.Adapter.ListDeckAdapter
import com.example.simplesqlapp.DBHelper.DBHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.Deck
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

//import org.json.JSONArray
//import java.io.IOException
//import java.io.InputStream
//import android.widget.ArrayAdapter


class MainActivity : AppCompatActivity() {
    //28:00

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == REQUEST_READ_EXTERNAL) parseJason()
//    }



    internal lateinit var db: DBHelper
    internal var lstCart: List<Cart> = ArrayList<Cart>()
    internal var lstDeck: List<Deck> = ArrayList<Deck>()
    val REQUEST_READ_EXTERNAL = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL
                )

            } else {
                //???
            }


                db = DBHelper(this)
                refreshData()

                btn_add.setOnClickListener {
                    val cart = Cart(
                        Integer.parseInt(unique_cart_id.text.toString()),
                        cart_name.text.toString(),
                        cart_oracle_id.text.toString()
                    )
                    db.addCart(cart)
                    refreshData()
                }


                btn_update.setOnClickListener {
                    val cart = Cart(
                        Integer.parseInt(unique_cart_id.text.toString()),
                        cart_name.text.toString(),
                        cart_oracle_id.text.toString()
                    )
                    db.updateCart(cart)
                    refreshData()
                }
                btn_delete.setOnClickListener {
                    val cart = Cart(
                        Integer.parseInt(unique_cart_id.text.toString()),
                        cart_name.text.toString(),
                        cart_oracle_id.text.toString()
                    )
                    db.deleteCart(cart)
                    refreshData()

                }
                btn_readJSON.setOnClickListener {
                    readFile()
                }


                btn_add_deck.setOnClickListener {
                    val deck = Deck(
                        Integer.parseInt(deck_id.text.toString()),
                        deck_name.text.toString()
                    )
                    db.addDeck(deck)
                    refreshData()
                }


                btn_delete_deck.setOnClickListener {
                    val deck = Deck(
                        Integer.parseInt(deck_id.text.toString()),
                        deck_name.text.toString()
                    )
                    db.deleteDeck(deck)
                    refreshData()

                }

            }




        //Event


    private fun refreshData() {
        lstCart = db.allCarts

        val cartAdapter =
            ListCartAdapter(this@MainActivity, lstCart, unique_cart_id, cart_name, cart_oracle_id)
        //30:00
        cart_list.adapter = cartAdapter
        lstDeck = db.allDecks

        val deckAdapter =
            ListDeckAdapter(this@MainActivity, lstDeck, deck_id, deck_name)
        //30:00
        deck_list.adapter = deckAdapter
    }

    private fun readFile() {
        var json: String? = null
        try {
            val inputStream: InputStream = assets.open("scryfall-default-cards — kopia.json")
            json = inputStream.bufferedReader().use { it.readText() }

            var jsonarray = JSONArray(json)

            for (i in 0 until jsonarray.length() - 1) {

                var jsonObj = jsonarray.getJSONObject(i)
                val cart = Cart(
                    //Integer.parseInt(jsonObj.getString("id")),
                    i,
                    jsonObj.getString("name"),
                    jsonObj.getString("oracle_id")
                )
                db.readDataFromJSON(cart)
            }
            refreshData()

        } catch (e: IOException) {
            e.printStackTrace();
        }
    }
}

