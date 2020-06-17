package com.example.simplesqlapp.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.R
import kotlinx.android.synthetic.main.row_cart_in_deck_layout.view.*

class ListCartDeckAdapter(internal var activity: Activity, internal var lstCartDeck: List<Cart>, internal var cd_cart_id: EditText,
                          internal var cd_cart_name: EditText, internal var txt_second_parameter: EditText
): BaseAdapter() {
    internal var inflater: LayoutInflater

    init{
        inflater=activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
        rowView = inflater.inflate(R.layout.row_cart_in_deck_layout,null)
        rowView.txt_cd_cart_id.text = lstCartDeck[position].id.toString()
        rowView.txt_cd_cart_name.text = lstCartDeck[position].name.toString()
        rowView.txt_cd_second_parameter.text = lstCartDeck[position].manaCost.toString()


        rowView.setOnClickListener{
            cd_cart_id.setText(rowView.txt_cd_cart_id.text.toString())
            cd_cart_name.setText(rowView.txt_cd_cart_name.text.toString())
            txt_second_parameter.setText(rowView.txt_cd_second_parameter.text.toString())
        }
        return rowView

    }

    override fun getItem(position: Int): Any {
        return lstCartDeck[position]
    }

    override fun getItemId(position: Int): Long {
        return lstCartDeck[position].cardDbId.toLong()
    }

    override fun getCount(): Int {
        return lstCartDeck.size
    }
}