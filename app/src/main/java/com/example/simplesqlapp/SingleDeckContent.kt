package com.example.simplesqlapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.Adapter.RecycleViewCartDeckAdapter

import com.example.simplesqlapp.DBHelper.SingleDeckDBHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.CartDeck
import kotlinx.android.synthetic.main.activity_single_deck_content.*

class SingleDeckContent : AppCompatActivity() {

    private var cartsInDeckLayoutManager: RecyclerView.LayoutManager? = null
    internal lateinit var db: SingleDeckDBHelper
    internal var lstCartInDeck: List<Cart> = ArrayList<Cart>()
    private var lstCartsInActualDeckList: List<CartDeck> = ArrayList<CartDeck>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actualDisplayedDeckId = intent.getStringExtra("actualDeckId")
        setContentView(R.layout.activity_single_deck_content)
        db = SingleDeckDBHelper(this, actualDisplayedDeckId)
        val intent = intent

        val actualDisplayedDeckIdInt:Int = Integer.parseInt(actualDisplayedDeckId)
        val deckHeaderName = findViewById<TextView>(R.id.txt_single_deck_name)
        deckHeaderName.text = db.allDecks[actualDisplayedDeckIdInt].name
        val deckHeaderId = findViewById<TextView>(R.id.txt_single_deck_id)
        deckHeaderId.text = "Deck Id: "+actualDisplayedDeckId



        refreshData(recyclerView, actualDisplayedDeckIdInt)

        button_to_main.setOnClickListener{
            val intent = Intent(this@SingleDeckContent, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun refreshData(mRecyclerView : RecyclerView, actualDisplayedDeckIdInt:Int) {
        mRecyclerView.setHasFixedSize(true)
        cartsInDeckLayoutManager = LinearLayoutManager(this)
        lstCartInDeck = db.cartsInDeck
        lstCartsInActualDeckList = db.cartsInSingleDeck

        mRecyclerView.adapter = RecycleViewCartDeckAdapter(this@SingleDeckContent, lstCartInDeck, lstCartsInActualDeckList)
        mRecyclerView.layoutManager = cartsInDeckLayoutManager

        (mRecyclerView.adapter as RecycleViewCartDeckAdapter).setOnItemClickListener(object : RecycleViewCartDeckAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                showCart(position)
            }

            override fun onDeleteClick(position: Int) {
                removeItem(position, mRecyclerView, actualDisplayedDeckIdInt)
            }
        })


    }
    fun removeItem(position: Int, mRecyclerView : RecyclerView, actualDisplayedDeckIdInt:Int) {

        (mRecyclerView.adapter as RecycleViewCartDeckAdapter).notifyItemRemoved(position)

            val cartDeck = CartDeck(
                lstCartsInActualDeckList[position].cartDeckPrimaryId,
                lstCartInDeck[position].id,
                actualDisplayedDeckIdInt
            )
            db.deleteCartDeck(cartDeck)

        refreshData(mRecyclerView, actualDisplayedDeckIdInt)
    }

    fun showCart(position: Int) {

        val rememberCartClicked:String = lstCartInDeck[position].id.toString()
        val intent2 = Intent(this@SingleDeckContent, SingleCartContent::class.java)
        intent2.putExtra("actualCartId", rememberCartClicked)
        startActivity(intent2)
        //refreshData(mRecyclerView, actualDisplayedDeckIdInt)
    }

}
