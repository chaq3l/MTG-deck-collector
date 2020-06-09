package com.example.simplesqlapp

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import android.support.v4.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.Adapter.ListCartAdapter
import com.example.simplesqlapp.Adapter.ListDeckAdapter
import com.example.simplesqlapp.Adapter.MainActivityRecycleViewDecksAdapter
import com.example.simplesqlapp.DBHelper.DBCartSearcher
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
    internal lateinit var dbSearcher : DBCartSearcher
    private var decksLayoutManager: RecyclerView.LayoutManager? = null
    internal var lstDeck: List<Deck> = ArrayList<Deck>()
    internal var lstFoundCart: List<Cart> = ArrayList<Cart>()
    internal var lstDeckMap: MutableMap<Int, Deck> = mutableMapOf()

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
        refreshData(main_decks_recycler_view_list)


        btn_carts.setOnClickListener {
            val intent = Intent(this@MainActivity, CartCreatorActivity::class.java)

            startActivity(intent)
        }

        image_search.setOnClickListener {

            if (cart_name_input.text.toString()==""){

                //nic nie wpisano


            }else{
                val inputPhrase = cart_name_input.text.toString()
                dbSearcher = DBCartSearcher(this, inputPhrase)
                lstFoundCart = dbSearcher.searchedCartList

                if (lstFoundCart.isEmpty()){
                    //no results, user need to find angain
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("No results")
                    builder.setMessage("Please check if the correct data has been entered")
                    builder.setNeutralButton("Ok") { _: DialogInterface?, _: Int ->  }
                    builder.show()



                }else{
                    if (lstFoundCart.size==1){
                        //while found only one cart
                        val cartId= lstFoundCart[0].id.toString()
                        val intent = Intent(this@MainActivity, SingleCartContent::class.java)
                        intent.putExtra("actualCartId", cartId)
                        startActivity(intent)



                    }else
                    {
                        //while found manny carts

                        val intent = Intent(this@MainActivity, CartSearcher::class.java)
                        intent.putExtra("searchingPhrase", inputPhrase)
                        startActivity(intent)

                    }
                }
            }
        }



        //val chosenCart = findViewById<EditText>(R.id.cart_id)
        btn_deck_menu.setOnClickListener {
            val intent2 = Intent(this@MainActivity, DeckCreatorActivity::class.java)

            startActivity(intent2)
        }

    }


        //Event

    private fun refreshData(mRecyclerView : RecyclerView) {
        mRecyclerView.setHasFixedSize(true)
        decksLayoutManager = LinearLayoutManager(this)
        lstDeck = db.allDecks
        lstDeckMap = db.allDecksMap


        mRecyclerView.adapter = MainActivityRecycleViewDecksAdapter(this@MainActivity, lstDeck)
        mRecyclerView.layoutManager = decksLayoutManager

        (mRecyclerView.adapter as MainActivityRecycleViewDecksAdapter).setOnItemClickListener(object : MainActivityRecycleViewDecksAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                showDeck(position)
            }

            override fun onDeleteClick(position: Int) {
                removeItem(position, mRecyclerView)
            }
        })


    }
    fun removeItem(position: Int, mRecyclerView : RecyclerView) {

        (mRecyclerView.adapter as MainActivityRecycleViewDecksAdapter).notifyItemRemoved(position)

        val deck = lstDeckMap[position]!!.name?.let {
            Deck(
                lstDeckMap[position]!!.id,
                it

            )
        }
        if (deck != null) {
            db.deleteDeck(deck)
        }

        refreshData(mRecyclerView)
    }

    fun showDeck(position: Int) {
        val intent = Intent(this@MainActivity, SingleDeckContent::class.java)
        intent.putExtra("actualDeckId", lstDeckMap[position]!!.id.toString())
        startActivity(intent)
    }


//    private fun refreshData() {
//        lstCart = db.allCarts
//
//
//        lstDeck = db.allDecks
//
//        val deckAdapter =
//            ListDeckAdapter(this@MainActivity, lstDeck, deck_id, deck_name)
//        //30:00
//        deck_list.adapter = deckAdapter
//    }

}

