package com.example.simplesqlapp.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.Adapter.RecycleViewAllDecksAdapter.ExampleViewHolder
import com.example.simplesqlapp.Model.Deck
import com.example.simplesqlapp.R

class RecycleViewAllDecksAdapter(internal var activity: Activity, private val decksList: List<Deck>) : RecyclerView.Adapter<ExampleViewHolder>() {
    private var deckListener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
        fun onAddClick(position:Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        deckListener = listener
    }

    class ExampleViewHolder(itemView: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        //var mImageView: ImageView
        var deckName: TextView = itemView.findViewById(R.id.top_text_View)
        var deckId: TextView = itemView.findViewById(R.id.lower_text_View)
        var removeCartFromDeck: ImageView = itemView.findViewById(R.id.image_delete)
        var addCartToDeck: ImageView = itemView.findViewById(R.id.image_add)


        init {
           // mImageView = itemView.findViewById(R.id.imageView)
            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(Integer.parseInt(deckId.text.toString()))
                    }
                }
            }
            removeCartFromDeck.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(Integer.parseInt(deckId.text.toString()))

                    }
                }
            }
            addCartToDeck.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onAddClick(Integer.parseInt(deckId.text.toString()))

                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.deck_item_from_deck_list_content, parent, false)
        return ExampleViewHolder(v, deckListener)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = decksList[position]

        holder.deckId.text = currentItem.id.toString()
        holder.deckName.text = currentItem.name.toString()


    }

    override fun getItemCount(): Int {
        return decksList.size
    }

}