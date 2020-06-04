package com.example.simplesqlapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.Adapter.RecycleViewAllDecksAdapter
import com.example.simplesqlapp.DBHelper.DBHelper
import com.example.simplesqlapp.Model.CartDeck
import com.example.simplesqlapp.Model.Deck
import kotlinx.android.synthetic.main.activity_deck_list_content.*



class AddChosenCartToDeckContent : AppCompatActivity() {



    private var decksLayoutManager: RecyclerView.LayoutManager? = null
    internal lateinit var db: DBHelper
    internal var lstDeck: List<Deck> = ArrayList<Deck>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DBHelper(this)
        val intent = intent
        val actualDisplayedCart = intent.getStringExtra("actualCartId")

        setContentView(R.layout.activity_deck_list_content)

        val header = findViewById<TextView>(R.id.actual_cart_id)
        header.text = "Cart Id: "+actualDisplayedCart
        val cartName = findViewById<TextView>(R.id.txt_cart_name)
        cartName.text = db.allCarts[Integer.parseInt(actualDisplayedCart)].name

        refreshData(deckRecyclerView, Integer.parseInt(actualDisplayedCart),1)

        button_to_main.setOnClickListener{
            val intent = Intent(this@AddChosenCartToDeckContent, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun refreshData(mRecyclerView : RecyclerView,actualDisplayedCart:Int,quantity: Int) {
        mRecyclerView.setHasFixedSize(true)
        decksLayoutManager = LinearLayoutManager(this)
        lstDeck = db.allDecks
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
                lstDeck[position].id
            )
            db.deleteSameCartsFromDeck(cartDeck)

        refreshData(mRecyclerView,cartId,quantity)
    }

    fun addItem(position: Int, mRecyclerView: RecyclerView, cartId: Int, quantity:Int){
        val cartDeck = CartDeck(
                0,
        cartId,
        lstDeck[position].id
        )

        for(i in 1..quantity){
        db.addCartToDeck(cartDeck)
        }
        refreshData(mRecyclerView,cartId,quantity)
    }

    fun showDeck(position: Int) {
        val intent = Intent(this@AddChosenCartToDeckContent, SingleDeckContent::class.java)
        intent.putExtra("actualDeckId", position.toString())
        startActivity(intent)

    }

}
