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

        setContentView(R.layout.activity_single_deck_content)

        val intent = intent
        val actualDisplayedDeckId = intent.getStringExtra("actualDeckId")
        val actualDisplayedDeckIdInt:Int = Integer.parseInt(actualDisplayedDeckId)
        val deckHeader = findViewById<TextView>(R.id.txt_single_deck_id)
        deckHeader.text = "Deck Id: "+actualDisplayedDeckId

        db = SingleDeckDBHelper(this, actualDisplayedDeckId)
        refreshData(recyclerView, actualDisplayedDeckIdInt)
    }

    private fun refreshData(mRecyclerView : RecyclerView, actualDisplayedDeckIdInt:Int) {
        mRecyclerView.setHasFixedSize(true)
        cartsInDeckLayoutManager = LinearLayoutManager(this)
        lstCartInDeck = db.cartsInDeck
        lstCartsInActualDeckList = db.cartsInSingleDeck

        mRecyclerView.adapter = RecycleViewCartDeckAdapter(this@SingleDeckContent, lstCartInDeck, lstCartsInActualDeckList,  cd_cart_id, cd_cart_name, txt_second_parameter)
        mRecyclerView.layoutManager = cartsInDeckLayoutManager

        (mRecyclerView.adapter as RecycleViewCartDeckAdapter).setOnItemClickListener(object : RecycleViewCartDeckAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                showCart(position, "Clicked", mRecyclerView, actualDisplayedDeckIdInt)
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

    fun showCart(position: Int, text: String, mRecyclerView : RecyclerView, actualDisplayedDeckIdInt:Int) {

        val rememberCartClicked:String = lstCartInDeck[position].id.toString()
        val intent2 = Intent(this@SingleDeckContent, SingleCartContent::class.java)
        intent2.putExtra("actualCartId", rememberCartClicked)
        intent2.putExtra("actualDeckId", actualDisplayedDeckIdInt)
        startActivity(intent2)
        //refreshData(mRecyclerView, actualDisplayedDeckIdInt)
    }

}
