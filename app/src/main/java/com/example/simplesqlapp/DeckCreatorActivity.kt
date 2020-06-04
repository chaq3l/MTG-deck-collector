package com.example.simplesqlapp


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.example.simplesqlapp.Adapter.ListDeckAdapter
import com.example.simplesqlapp.DBHelper.DBHelper
import com.example.simplesqlapp.Model.Deck
import kotlinx.android.synthetic.main.activity_deck_creator.*



class DeckCreatorActivity : AppCompatActivity() {




    internal lateinit var db: DBHelper

    internal var lstDeck: List<Deck> = ArrayList<Deck>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_creator)



        db = DBHelper(this)
        refreshData()


        btn_update.setOnClickListener {
            val deck = Deck(
                Integer.parseInt(deck_id.text.toString()),
                deck_name.text.toString()
            )
            db.updateDeck(deck)
            refreshData()
        }


        btn_add.setOnClickListener {
            val deck = Deck(
                Integer.parseInt(deck_id.text.toString()),
                deck_name.text.toString()
            )
            db.addDeck(deck)
            refreshData()
        }


        btn_delete.setOnClickListener {
            val deck = Deck(
                Integer.parseInt(deck_id.text.toString()),
                deck_name.text.toString()
            )
            db.deleteDeck(deck)
            refreshData()

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

        val actualDeck = findViewById<EditText>(R.id.deck_id)
        btn_show_deck.setOnClickListener {
            val deck = actualDeck.text.toString()
            if (deck == "") {

            } else {

                val intent = Intent(this@DeckCreatorActivity, SingleDeckContent::class.java)
                intent.putExtra("actualDeckId", deck)
                startActivity(intent)
            }
        }

        btn_carts.setOnClickListener {

        }
    }



        //Event

    private fun refreshData() {

        lstDeck = db.allDecks

        val deckAdapter =
            ListDeckAdapter(this@DeckCreatorActivity, lstDeck, deck_id, deck_name)

        deck_list.adapter = deckAdapter
    }


}

