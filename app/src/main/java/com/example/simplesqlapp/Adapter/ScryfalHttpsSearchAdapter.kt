package com.example.simplesqlapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.R
import kotlinx.android.synthetic.main.cart_in_cart_searcher_item.view.*


class ScryfalHttpsSearchAdapter(scryfallResponseAutocomplete : ScryfallResponseAutocomplete): RecyclerView.Adapter<CustomViewHolder>() {
    val scryfallResponseAutocomplete = scryfallResponseAutocomplete
    private var cardListener: RecycleViewSearchCartAdapter.OnItemClickListener? = null

    interface OnItemClickListener : RecycleViewSearchCartAdapter.OnItemClickListener {
        override fun onItemClick(position: Int)

   }

fun setOnItemClickListener(listener: OnItemClickListener) {
    cardListener = listener
}


    // numberOfItems
    override fun getItemCount(): Int {
        return scryfallResponseAutocomplete.total_values
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.cart_in_cart_searcher_item, parent, false)
        return CustomViewHolder(cellForRow, cardListener)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.cartName.text = scryfallResponseAutocomplete.data[position]
        holder.cartHolderId.text = ""
        //no need cartHolderId, made to don't show unnecessary text
    }

}

class CustomViewHolder(itemView: View, listener : RecycleViewSearchCartAdapter.OnItemClickListener?): RecyclerView.ViewHolder(itemView){
    var cartName: TextView = itemView.findViewById(R.id.top_text_View)
    var cartHolderId: TextView = itemView.findViewById(R.id.lower_text_View)


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
    }
}
