package com.example.simplesqlapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.simplesqlapp.Adapter.ListCartAdapter
import com.example.simplesqlapp.DBHelper.DBHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.Deck
import kotlinx.android.synthetic.main.activity_cart_creator.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream




class CartCreatorActivity : AppCompatActivity() {




    internal lateinit var db: DBHelper
    internal var lstCart: List<Cart> = ArrayList<Cart>()
    internal var lstDeck: List<Deck> = ArrayList<Deck>()
    val REQUEST_READ_EXTERNAL = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_creator)


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
                Integer.parseInt(cart_id.text.toString()),
                cart_name.text.toString(),
                cart_mana_cost.text.toString(),
                cart_mana_cost.text.toString(),
                cart_mana_cost.text.toString(),
                cart_mana_cost.text.toString()
            )
            db.addCart(cart)
            refreshData()
        }


        btn_update.setOnClickListener {
            val cart = Cart(
                Integer.parseInt(cart_id.text.toString()),
                cart_name.text.toString(),
                cart_mana_cost.text.toString(),
                cart_mana_cost.text.toString(),
                cart_mana_cost.text.toString(),
                cart_mana_cost.text.toString()
            )
            db.updateCart(cart)
            refreshData()
        }
        btn_delete.setOnClickListener {
            val cart = Cart(
                Integer.parseInt(cart_id.text.toString()),
                cart_name.text.toString(),
                cart_mana_cost.text.toString(),
                cart_mana_cost.text.toString(),
                cart_mana_cost.text.toString(),
                cart_mana_cost.text.toString()
            )
            db.deleteCart(cart)
            refreshData()

        }
        btn_readJSON.setOnClickListener {
            readFile()
        }




        btn_add_cart_to_deck.setOnClickListener {
            val cartId = cart_id.text.toString()
            if(cartId==""){}else{
            val intent = Intent(this@CartCreatorActivity, AddChosenCartToDeckContent::class.java)
            intent.putExtra( "actualCartId", cartId)
            startActivity(intent)
            }
        }



//        val getCartPrimaryNumberInDeck:Int= 0
//        btn_add_cart_to_deck.setOnClickListener {
//            if (cart_id.text.toString()==""){}else{
//
//            val cartDeck = CartDeck(
//            getCartPrimaryNumberInDeck.inc(),
//            Integer.parseInt(cart_id.text.toString()),
//            Integer.parseInt(deck_id.text.toString())
//
//        )
//            db.addCartToDeck(cartDeck)}
//        }

        val selectedCart = findViewById<EditText>(R.id.cart_id)
        btn_show_cart.setOnClickListener {
            val cart = selectedCart.text.toString()
            if (cart == "") {

            } else {

                val intent = Intent(this@CartCreatorActivity, SingleCartContent::class.java)
                intent.putExtra("actualCartId", cart)
                startActivity(intent)
            }
        }

        //val chosenCart = findViewById<EditText>(R.id.cart_id)
        btn_deck_menu.setOnClickListener {
            val intent2 = Intent(this@CartCreatorActivity, DeckCreatorActivity::class.java)

            startActivity(intent2)
        }

    }




        //Event


    private fun refreshData() {
        lstCart = db.allCarts

        val cartAdapter =
            ListCartAdapter(this@CartCreatorActivity, lstCart, cart_id, cart_name, cart_mana_cost)

        cart_list.adapter = cartAdapter

    }

    private fun readFile() {

        try {
            val inputStream: InputStream = assets.open("scryfall-default-cards — kopia.json")
           val json = inputStream.bufferedReader().use { it.readText() }

            var jsonarray = JSONArray(json)

            for (i in 0 until jsonarray.length() - 1) {

                var jsonObj = jsonarray.getJSONObject(i)
                val cart = Cart(
                    //Integer.parseInt(jsonObj.getString("id")),
                    i,
                    jsonObj.getString("name"),
                    jsonObj.getString("mana_cost"),
                    jsonObj.getString("oracle_text"),
                    jsonObj.getString("colors"),
                    jsonObj.getString("color_identity")

                )
                db.readDataFromJSON(cart)
            }
            refreshData()

        } catch (e: IOException) {
            e.printStackTrace();
        }
    }
}

