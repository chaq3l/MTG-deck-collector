package com.example.simplesqlapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import android.support.v4.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.simplesqlapp.Adapter.ListCartAdapter
import com.example.simplesqlapp.Adapter.ListDeckAdapter
import com.example.simplesqlapp.DBHelper.DBHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.CartDeck
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


        btn_carts.setOnClickListener {
            val intent = Intent(this@MainActivity, CartCreatorActivity::class.java)

            startActivity(intent)
        }



        val getCartPrimaryNumberInDeck:Int= 0
        btn_add_cart_to_deck.setOnClickListener {
            if (cart_id.text.toString()==""){}else{

            val cartDeck = CartDeck(
            getCartPrimaryNumberInDeck.inc(),
            Integer.parseInt(cart_id.text.toString()),
            Integer.parseInt(deck_id.text.toString())

        )
            db.addCartToDeck(cartDeck)}
        }

        val actualDeck = findViewById<EditText>(R.id.deck_id)
        btn_show_deck.setOnClickListener {
            val deck = actualDeck.text.toString()
            if (deck == "") {

            } else {

                val intent = Intent(this@MainActivity, SingleDeckContent::class.java)
                intent.putExtra("actualDeckId", deck)
                startActivity(intent)
            }
        }

        //val chosenCart = findViewById<EditText>(R.id.cart_id)
        btn_deck_menu.setOnClickListener {
            val intent2 = Intent(this@MainActivity, DeckCreatorActivity::class.java)

            startActivity(intent2)
        }

    }


        //Event


    private fun refreshData() {
        lstCart = db.allCarts

        val cartAdapter =
            ListCartAdapter(this@MainActivity, lstCart, cart_id, cart_name, cart_mana_cost)
        //30:00
        cart_list.adapter = cartAdapter
        lstDeck = db.allDecks

        val deckAdapter =
            ListDeckAdapter(this@MainActivity, lstDeck, deck_id, deck_name)
        //30:00
        deck_list.adapter = deckAdapter
    }

}

