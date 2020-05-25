package com.example.simplesqlapp.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import com.example.simplesqlapp.Model.Deck
import com.example.simplesqlapp.R
import kotlinx.android.synthetic.main.row_deck_layout.view.*

class ListDeckAdapter (internal var activity: Activity, internal var lstDeck: List<Deck>, internal var deck_id: EditText,
                       internal var deck_name:EditText): BaseAdapter(){
    internal var inflater: LayoutInflater

    init{
        inflater=activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
        rowView = inflater.inflate(R.layout.row_deck_layout,null)
        rowView.txt_deck_id.text = lstDeck[position].id.toString()
        rowView.txt_deck_name.text = lstDeck[position].name.toString()


        rowView.setOnClickListener{
            deck_id.setText(rowView.txt_deck_id.text.toString())
            deck_name.setText(rowView.txt_deck_name.text.toString())

        }
        return rowView

    }

    override fun getItem(position: Int): Any {
        return lstDeck[position]
    }

    override fun getItemId(position: Int): Long {
        return lstDeck[position].id.toLong()
    }

    override fun getCount(): Int {
        return lstDeck.size
    }
}