package com.example.simplesqlapp


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.Adapter.ScryfalHttpsSearchAdapter
import com.example.simplesqlapp.Adapter.ScryfallResponseAutocomplete
import com.example.simplesqlapp.Adapter.ScryfallResponseImageUri
import com.example.simplesqlapp.Adapter.ScryfallResponseNamed
import com.example.simplesqlapp.DBHelper.DBCartSearcher
import com.example.simplesqlapp.Model.Cart

import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_cart_finder.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


class CartHttpSearcher : AppCompatActivity() {

    private var cardsHttpSearchLayoutManager: RecyclerView.LayoutManager? = null
    internal lateinit var db: DBCartSearcher
    internal var lstFoundCart: List<Cart> = ArrayList<Cart>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onlyOneCardFound = intent.getStringExtra("onlyOneCardFounded")
        val actualPhrase = intent.getStringExtra("searchingResponse")
//        val actualDisplayedDeckIdINT =Integer.parseInt(actualDisplayedDeckId1)-1
//        val actualDisplayedDeckId = actualDisplayedDeckIdINT.toString()
        setContentView(R.layout.activity_cart_finder)

        if (onlyOneCardFound==null){
            refreshData(recyclerView, actualPhrase)
        }else{
            namedHttpRequest(actualPhrase, this)
            }




        button_to_main.setOnClickListener {
            val intent = Intent(this@CartHttpSearcher, MainActivity::class.java)
            startActivity(intent)
        }

        search_button.setOnClickListener {
            //searchHttpsRequest(cart_name_input.text.toString(), this)
            if (txt_search_again.text.toString() == "") {

                //nic nie wpisano


            } else {
                //recView.layoutManager = LinearLayoutManager(this)
                searchHttpsRequest(txt_search_again.text.toString(), this)


            }
        }


    }

    private fun refreshData(mRecyclerView: RecyclerView, actualPhrase: String) {
        mRecyclerView.setHasFixedSize(true)
        cardsHttpSearchLayoutManager = LinearLayoutManager(this)


        //listOfCards.Recycler().
        val gson = GsonBuilder().create()
        val cardsObject = gson.fromJson(actualPhrase, ScryfallResponseAutocomplete::class.java)


        runOnUiThread {
            mRecyclerView.adapter = ScryfalHttpsSearchAdapter(cardsObject)
            mRecyclerView.layoutManager = cardsHttpSearchLayoutManager

            (mRecyclerView.adapter as ScryfalHttpsSearchAdapter).setOnItemClickListener(object :
                ScryfalHttpsSearchAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    showCart(position, cardsObject)
                }

            })

        }
    }


    fun showCart(position: Int, cardsObject: ScryfallResponseAutocomplete) {

        val rememberCartClicked: String = cardsObject.data[position]
        namedHttpRequest(rememberCartClicked, this)


//        val intent = Intent(this@CartHttpSearcher, SingleCartContent::class.java)
//        intent.putExtra("actualCartName", rememberCartClicked)
//        startActivity(intent)
        //refreshData(mRecyclerView, actualDisplayedDeckIdInt)
    }

    fun searchHttpsRequest(localCardName: String, context: Context) {

        var url = "https://api.scryfall.com/cards/autocomplete?q=$localCardName"
        Log.d("RuntimeLog", "Will access url: $url")

        var request = Request.Builder().url(url).build()

        var client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("RuntimeLog", "HTTP Request failed")

            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("RuntimeLog", "HTTP Request success: $response")
                var stringJsonResponse = response.body?.string()
                Log.d("RuntimeLog", "Response: $stringJsonResponse")

                //listOfCards.Recycler().
                var gson = GsonBuilder().create()


                runOnUiThread {
                    var response =
                        gson.fromJson(stringJsonResponse, ScryfallResponseAutocomplete::class.java)
                    // recView.adapter = ScryfalHttpsSearchAdapter(cardsObject)


                    if (Integer.parseInt(response.total_values.toString()) == 0) {
                        //no results, user need to find angain
                        var builder = AlertDialog.Builder(context)
                        builder.setTitle("No results")
                        builder.setMessage("Please check if the correct data has been entered")
                        builder.setNeutralButton("Ok") { _: DialogInterface?, _: Int -> }
                        builder.show()


                    } else {
                       // first_found_cart.text = response.data[0].toString()

                        if (Integer.parseInt(response.total_values.toString()) == 1) {
                            //while found only one cart
                            var cartName = response.data[0]

                            namedHttpRequest(cartName, context)


                        } else {
                            //while found manny carts

                            if (stringJsonResponse != null) {
                                refreshData(recyclerView, stringJsonResponse)
                            }


                        }
                    }

                }

            }

        })

    }

    fun namedHttpRequest(localCardName: String, context: Context) {

        val url = "https://api.scryfall.com/cards/named?fuzzy=$localCardName"
        Log.d("RuntimeLog", "Will access url: $url")

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("RuntimeLog", "HTTP Request failed")

            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("RuntimeLog", "HTTP Request success: $response")
                val stringJsonResponse = response.body?.string()
                Log.d("RuntimeLog", "Response: $stringJsonResponse")

                //listOfCards.Recycler().
                val gson = GsonBuilder().create()


                runOnUiThread {
                    val response =
                        gson.fromJson(stringJsonResponse, ScryfallResponseNamed::class.java)
                    // recView.adapter = ScryfalHttpsSearchAdapter(cardsObject)

                    db = DBCartSearcher(context, response.name.toString())
                    lstFoundCart = db.searchedCartList
                    if (lstFoundCart.isEmpty()) {
                        //no results, adding cart to db
                        var colorsList: List<String>? = null
                        colorsList = response.colors
                        var colors = ""
                        if (colorsList.size == 1) {
                            colors = response.colors[0]

                        } else {
                            val sb = StringBuilder()
                            for (x in colorsList.indices) {
                                val a = colorsList[x]
                                val b = ' '
                                sb.append(a).append(b)
                            }
                            colors = sb.toString()
                        }
                        val ImageUri = response.image_uris.large
                        Log.d("gsonImageUri", ImageUri)

                        val cart = Cart(
                            response.oracle_id,
                            response.name,
                            response.mana_cost,
                            response.oracle_text,
                            colors,
                            ImageUri.toString()

                        )
                        db.addCart(cart)

                        lstFoundCart = db.searchedCartList
                        val cartId= lstFoundCart[0].cardDbId.toString()
                        val intent = Intent(this@CartHttpSearcher, SingleCartContent::class.java)
                        intent.putExtra("actualCartId", cartId)
                        startActivity(intent)


                    } else {
                        if (lstFoundCart.size == 1) {
                            //while found only one cart
                            val cartId = lstFoundCart[0].cardDbId.toString()
                            val intent =
                                Intent(this@CartHttpSearcher, SingleCartContent::class.java)
                            intent.putExtra("actualCartId", cartId)
                            startActivity(intent)


                        } else {
                            //while found manny carts,
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("Manny results id local DB.")
                            builder.setMessage("In local database are cards with the same or very similar names. Please check if the correct data has been entered or delete redundant cards")
                            builder.setNeutralButton("Ok") { _: DialogInterface?, _: Int -> }
                            builder.show()


                        }

                    }
                }
            }
        })
    }

}
