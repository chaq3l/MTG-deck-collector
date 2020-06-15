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
                0,
                deck_name.text.toString()
            )
            db.addDeck(deck)
            sortDecks()
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

        btn_back_to_main.setOnClickListener {
            val intent = Intent(this@DeckCreatorActivity, MainActivity::class.java)

            startActivity(intent)
        }

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
            val intent = Intent(this@DeckCreatorActivity, CartCreatorActivity::class.java)

            startActivity(intent)
        }
    }

    fun sortDecks() {
        val deckList: List<Deck> = db.allDecks
        val deckListSorted: List<Deck> = ArrayList<Deck>()
        for (i in deckList.indices) {
            val deck = Deck(
                i,
                deckList[i].name.toString()
            )
            deckListSorted.toMutableList().add(deck)
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

