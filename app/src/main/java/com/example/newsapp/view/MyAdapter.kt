package com.example.newsapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.News


class MyAdapter(val itemsList: ArrayList<News>, private val onItemClick: (News) -> Unit
): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){
        var itemTitle: TextView
        var itemDesc: TextView
        var itemImg: ImageView
        var cardView: CardView = itemView.findViewById(R.id.cardView)
        init {
            itemTitle = itemView.findViewById(R.id.title_txt)
            itemDesc = itemView.findViewById(R.id.description_txt)
            itemImg = itemView.findViewById(R.id.news_image)
        }
        fun bind(news: News) {
            itemTitle.text = news.title
            itemDesc.text = news.description

            // Set click listener on the whole CardView
            cardView.setOnClickListener {
                Log.d("MyAdapter", "Card clicked: ${news.title}")
                onItemClick(news)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(v)
    }
    override fun getItemCount(): Int {
        return itemsList.size
    }
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val article = itemsList[position]
        holder.bind(article)
        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.itemImg)
    }

}