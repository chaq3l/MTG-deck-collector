package com.example.simplesqlapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.simplesqlapp.Adapter.ListCartDeckAdapter
import com.example.simplesqlapp.DBHelper.DBHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.CartDeck
import kotlinx.android.synthetic.main.activity_single_deck_content.*

class SingleDeckContent : AppCompatActivity() {

    internal lateinit var db: DBHelper
    internal var lstCartInDeck: List<Cart> = ArrayList<Cart>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_deck_content)
        val intent = intent
        val actualDisplayedDeckId = intent.getStringExtra("actualDeckId")
        val deckHeader = findViewById<TextView>(R.id.txt_single_deck_id)
        deckHeader.text = "Deck Id: "+actualDisplayedDeckId

        db = DBHelper(this, actualDisplayedDeckId)
        refreshData()
    }

    private fun refreshData() {
        lstCartInDeck = db.cartsInDeck

        val cartDeckAdapter =
            ListCartDeckAdapter(this@SingleDeckContent, lstCartInDeck, cd_cart_id, cd_cart_name, txt_second_parameter)
        //30:00
        cd_cart_in_deck_list.adapter = cartDeckAdapter
        lstCartInDeck = db.cartsInDeck

    }
}
