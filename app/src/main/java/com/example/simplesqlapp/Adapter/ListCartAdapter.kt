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
import kotlinx.android.synthetic.main.row_layout.view.*


class ListCartAdapter(internal var activity: Activity, internal var lstCart: List<Cart>, internal var unique_cart_id:EditText,
                      internal var cart_name:EditText, internal var deck_id:EditText):BaseAdapter() {
    internal var inflater:LayoutInflater

    init{
        inflater=activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       val rowView: View
        rowView = inflater.inflate(R.layout.row_layout,null)
        rowView.txt_unique_cart_id.text = lstCart[position].id.toString()
        rowView.txt_cart_name.text = lstCart[position].name.toString()
        rowView.txt_cart_id.text = lstCart[position].secondaryId.toString()

        rowView.setOnClickListener{
            unique_cart_id.setText(rowView.txt_unique_cart_id.text.toString())
            cart_name.setText(rowView.txt_cart_name.text.toString())
            deck_id.setText(rowView.txt_cart_id.text.toString())
        }
        return rowView

    }

    override fun getItem(position: Int): Any {
        return lstCart[position]
    }

    override fun getItemId(position: Int): Long {
        return lstCart[position].id.toLong()
    }

    override fun getCount(): Int {
        return lstCart.size
    }
}