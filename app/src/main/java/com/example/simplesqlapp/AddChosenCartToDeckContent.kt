package com.example.simplesqlapp


import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.Adapter.RecycleViewAllDecksAdapter
import com.example.simplesqlapp.DBHelper.DBHelper
import com.example.simplesqlapp.DBHelper.DBSingleTypeCartAmountFromDeckGetter
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.CartDeck
import com.example.simplesqlapp.Model.Deck
import kotlinx.android.synthetic.main.activity_deck_list_content.*


class AddChosenCartToDeckContent : AppCompatActivity() {



    private var decksLayoutManager: RecyclerView.LayoutManager? = null
    internal lateinit var db: DBHelper
    internal lateinit var dbSingleCartGetter: DBSingleTypeCartAmountFromDeckGetter
    internal var CartAmountInSingleDeckGetter : List<CartDeck> = ArrayList<CartDeck>()
    internal var lstDeck: List<Deck> = ArrayList<Deck>()
    internal var lstDeckMap: MutableMap<Int, Deck> = mutableMapOf()
    internal var lstCartMap: MutableMap<Int, Cart> = mutableMapOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DBHelper(this)

        val intent = intent
        val actualDisplayedCart = intent.getStringExtra("actualCartId")

        setContentView(R.layout.activity_deck_list_content)

        val header = findViewById<TextView>(R.id.actual_cart_id)
        header.text = "Cart Id: "+actualDisplayedCart
        val cartName = findViewById<TextView>(R.id.txt_cart_name)

        cartName.text = db.allCartsMap[Integer.parseInt(actualDisplayedCart)]!!.name

        refreshData(deckRecyclerView, Integer.parseInt(actualDisplayedCart),1)

        button_to_main.setOnClickListener{
            val intent = Intent(this@AddChosenCartToDeckContent, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {  // After a pause OR at startup
        super.onResume()


        val intent = intent
        val actualDisplayedCart = intent.getStringExtra("actualCartId")

        refreshData(deckRecyclerView, Integer.parseInt(actualDisplayedCart),1)
    }



    private fun refreshData(mRecyclerView : RecyclerView,actualDisplayedCart:Int,quantity: Int) {
        mRecyclerView.setHasFixedSize(true)
        decksLayoutManager = LinearLayoutManager(this)
        lstDeck = db.allDecks
        lstDeckMap = db.allDecksMap
        lstCartMap = db.allCartsMap
        //lstCartsInActualDeckList = db.cartsInSingleDeck

        mRecyclerView.adapter = RecycleViewAllDecksAdapter(this@AddChosenCartToDeckContent, lstDeck)
        mRecyclerView.layoutManager = decksLayoutManager

        (mRecyclerView.adapter as RecycleViewAllDecksAdapter).setOnItemClickListener(object : RecycleViewAllDecksAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                showDeck(position)
            }

            override fun onDeleteClick(position: Int) {
                removeItem(position, mRecyclerView, actualDisplayedCart, quantity)
            }

            override fun onAddClick(position: Int) {
                addItem(position, mRecyclerView, actualDisplayedCart, quantity)
            }


        })


    }
    fun removeItem(position: Int, mRecyclerView : RecyclerView,cartId:Int,quantity: Int) {
        //cartsInDeckList.removeAt(position)
        (mRecyclerView.adapter as RecycleViewAllDecksAdapter).notifyItemRemoved(position)

            val cartDeck = CartDeck(
               0,
                cartId,
                lstDeckMap[position]!!.id
            )
            db.deleteSameCartsFromDeck(cartDeck)

        Toast.makeText(this@AddChosenCartToDeckContent, "All "+ lstCartMap[cartId]!!.name.toString() + " carts has been removed from this deck", Toast.LENGTH_SHORT).show()

        refreshData(mRecyclerView,cartId,quantity)
    }

    fun addItem(position: Int, mRecyclerView: RecyclerView, cartId: Int, quantity:Int){
        val cartDeck = CartDeck(
            0,
            cartId,
            lstDeckMap[position]!!.id
        )
        db.addCartToDeck(cartDeck)
        dbSingleCartGetter = DBSingleTypeCartAmountFromDeckGetter(this, cartId, lstDeckMap[position]!!.id)
        CartAmountInSingleDeckGetter = dbSingleCartGetter.searchedCartList
        val actualAmountOfSingleTypeCartInSingleDeck = CartAmountInSingleDeckGetter.size
        Toast.makeText(this@AddChosenCartToDeckContent, "There is " + actualAmountOfSingleTypeCartInSingleDeck.toString()+ " "+ lstCartMap[cartId]!!.name.toString()+" carts in this deck", Toast.LENGTH_SHORT).show()

        refreshData(mRecyclerView,cartId,quantity)
    }



    fun showDeck(position: Int) {
        val intent = Intent(this@AddChosenCartToDeckContent, SingleDeckContent::class.java)
        intent.putExtra("actualDeckId", lstDeckMap[position]!!.id.toString())
        startActivity(intent)

    }



}
