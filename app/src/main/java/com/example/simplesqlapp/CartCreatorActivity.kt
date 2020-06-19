package com.example.simplesqlapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
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

//        btn_readJSON.setOnClickListener {
//            readFile()
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
            val inputStream: InputStream = assets.open("")
           val json = inputStream.bufferedReader().use { it.readText() }

            var jsonarray = JSONArray(json)

            for (i in 0 until jsonarray.length() - 1) {

                var jsonObj = jsonarray.getJSONObject(i)
                val cart = Cart(
                    //Integer.parseInt(jsonObj.getString("id")),
                    "",
                    jsonObj.getString("name"),
                    jsonObj.getString("mana_cost"),
                    jsonObj.getString("oracle_text"),
                    jsonObj.getString("colors"),
                    jsonObj.getString("image_uris")

                )
                db.readDataFromJSON(cart)
            }
            refreshData()

        } catch (e: IOException) {
            e.printStackTrace();
        }
    }
}

