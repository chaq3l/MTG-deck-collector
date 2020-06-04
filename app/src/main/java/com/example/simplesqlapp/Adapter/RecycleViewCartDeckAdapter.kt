package com.example.simplesqlapp.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.Adapter.RecycleViewCartDeckAdapter.ExampleViewHolder
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.CartDeck
import com.example.simplesqlapp.R

class RecycleViewCartDeckAdapter(internal var activity: Activity, private val cartsInDeckList: List<Cart>, private val cartsInActualDeckList: List<CartDeck>) : RecyclerView.Adapter<ExampleViewHolder>() {
    private var cartListener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        cartListener = listener
    }

    class ExampleViewHolder(itemView: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        //var mImageView: ImageView
        var cartName: TextView = itemView.findViewById(R.id.top_text_View)
        var cartManaCost: TextView = itemView.findViewById(R.id.lower_text_View)
        var removeCartFromDeck: ImageView = itemView.findViewById(R.id.image_delete)

        init {
           // mImageView = itemView.findViewById(R.id.imageView)
            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position)
                    }
                }
            }
            removeCartFromDeck.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position)

                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cart_in_deck_item, parent, false)
        return ExampleViewHolder(v, cartListener)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = cartsInDeckList[position]
        //val currentItem2 = cartsInActualDeckList[position]
        holder.cartName.text = currentItem.name
        holder.cartManaCost.text = currentItem.manaCost.toString()


    }

    override fun getItemCount(): Int {
        return cartsInDeckList.size
    }

}