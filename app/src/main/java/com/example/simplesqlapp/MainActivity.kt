package com.example.simplesqlapp

//import android.support.v7.app.AppCompatActivity
//import android.support.v4.app.ActivityCompat
import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.Adapter.MainActivityRecycleViewDecksAdapter
import com.example.simplesqlapp.DBHelper.DBCartSearcher
import com.example.simplesqlapp.DBHelper.DBHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.Deck
//import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import com.google.gson.GsonBuilder
import okhttp3.*
import com.example.simplesqlapp.Adapter.ScryfallResponseAutocomplete
import org.json.JSONArray
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
    val INTERNET = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_READ_EXTERNAL
            )

        } else{

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
                        //recView.layoutManager = LinearLayoutManager(this)
                        searchHttpsRequest(cart_name_input.text.toString(),this)
                    }


                    //uwaga na sprawdzanie permissionów, nie jest to jeszcze do końca sprawne

//                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)==PackageManager.PERMISSION_GRANTED){
//
//                    }else{
//                        requestStoragePermission();
//                    }


                }



                //val chosenCart = findViewById<EditText>(R.id.cart_id)
                btn_deck_menu.setOnClickListener {
                    val intent2 = Intent(this@MainActivity, DeckCreatorActivity::class.java)

                    startActivity(intent2)
                }





    }

    private fun requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

            AlertDialog.Builder(this)
                .setTitle("Permission")
                .setMessage("This application need permission for downloading data")
                .setNegativeButton("Cancel") { _, _ ->
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
                }

                .setNegativeButton("OK") { _, _ ->
                    Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                }
                .show()

        }else{
            ActivityCompat.requestPermissions(this,  Array<String>(1){Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_READ_EXTERNAL)
        }
    }



    override fun onResume() {  // After a pause OR at startup
        super.onResume()

        refreshData(main_decks_recycler_view_list)
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



    fun searchHttpsRequest(localCardName:String, context: Context){

        val url = "https://api.scryfall.com/cards/autocomplete?q=$localCardName"
        Log.d("RuntimeLog", "Will access url: $url")

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
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
                    val response = gson.fromJson(stringJsonResponse, ScryfallResponseAutocomplete::class.java)
                           // recView.adapter = ScryfalHttpsSearchAdapter(cardsObject)
                    test_text.text = response.total_values.toString()


                    if (Integer.parseInt(test_text.text.toString())==0){
                        //no results, user need to find angain
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("No results")
                        builder.setMessage("Please check if the correct data has been entered")
                        builder.setNeutralButton("Ok") { _: DialogInterface?, _: Int ->  }
                        builder.show()



                    }else{
                        first_found_cart.text = response.data[0].toString()

                        if (Integer.parseInt(test_text.text.toString())==1){
                            //while found only one cart
                            val cartName= response.data[0]
                            val intent = Intent(this@MainActivity, CartHttpSearcher::class.java)
                            intent.putExtra("searchingResponse", cartName)
                            intent.putExtra("onlyOneCardFounded", "1")
                            startActivity(intent)



                        }else
                        {
                            //while found manny carts

                            val intent = Intent(this@MainActivity, CartHttpSearcher::class.java)
                            intent.putExtra("searchingResponse", stringJsonResponse)
                            startActivity(intent)

                        }
                    }

                }

            }

        })

    }

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

//    private fun download() {
//        val f =  getFileFromAssets(this,"default-     cards-20200610050431.json")
//        //Toast.makeText(this@MainActivity, "In download function", Toast.LENGTH_SHORT).show()
//        Log.e("Progress", "Starting downloading")
//        val url = "https://archive.scryfall.com/bulk-data/default-cards/default-cards-20200610050431.json"
//        Fuel.download(url)
//            .fileDestination { response, url ->  File.createTempFile("carts", ".json", f) }
//            .progress { readBytes, totalBytes ->
//                val progress = readBytes.toFloat() / totalBytes.toFloat() * 100
//                Log.w("Progress", progress.toString())
//                Toast.makeText(this@MainActivity, "Bytes downloaded $readBytes / $totalBytes ($progress %)", Toast.LENGTH_SHORT).show()
//
//            }
//            .response { req, res, result -> }
//
//
//    }
//
//    @Throws(IOException::class)
//    fun getFileFromAssets(context: Context, fileName: String): File = File(context.cacheDir, fileName)
//        .also {
//            if (!it.exists()) {
//                it.outputStream().use { cache ->
//                    context.assets.open(fileName).use {
//                        it.copyTo(cache)
//                    }
//                }
//            }
//        }
//



