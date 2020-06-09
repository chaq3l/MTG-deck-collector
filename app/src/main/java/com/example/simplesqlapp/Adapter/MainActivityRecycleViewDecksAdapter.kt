package com.example.simplesqlapp.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.Adapter.MainActivityRecycleViewDecksAdapter.ExampleViewHolder
import com.example.simplesqlapp.Model.Deck
import com.example.simplesqlapp.R

class MainActivityRecycleViewDecksAdapter(internal var activity: Activity, private val decksList: List<Deck>) : RecyclerView.Adapter<ExampleViewHolder>() {
    private var deckListener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)

    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        deckListener = listener
    }

    class ExampleViewHolder(itemView: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        //var mImageView: ImageView
        var deckName: TextView = itemView.findViewById(R.id.top_text_View)
        var deckId: TextView = itemView.findViewById(R.id.lower_text_View)
        var deleteDeck: ImageView = itemView.findViewById(R.id.image_delete)



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
            deleteDeck.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(Integer.parseInt(deckId.text.toString()))

                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.main_list_deck_item, parent, false)
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