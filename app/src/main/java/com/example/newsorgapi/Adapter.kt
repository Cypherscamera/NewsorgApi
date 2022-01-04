package com.example.newsorgapi

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapiretrofit.Article
import kotlinx.android.synthetic.main.items.view.*

class Adapter(val context: Context, val articles : List<Article>) : RecyclerView.Adapter<Adapter.newsviewholder>(){

    class newsviewholder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val newsimage : ImageView = itemView.findViewById(R.id.imageView)
        val newstitle : TextView = itemView.findViewById(R.id.title)
        val newsdesc : TextView = itemView.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsviewholder {

        val view = LayoutInflater.from(context).inflate(R.layout.items, parent, false)
        return newsviewholder(view)

    }

    override fun onBindViewHolder(holder: newsviewholder, position: Int) {

        val item = articles[position]
        holder.newstitle.text = item.title
        holder.newsdesc.text = item.description
        Glide.with(context).load(item.urlToImage).into(holder.newsimage)
        holder.itemView.setOnClickListener{
            val col = Color.parseColor("#FF0000")
            val builder = CustomTabsIntent.Builder().setToolbarColor(col)
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(item.url))

    }
    }

    override fun getItemCount(): Int {
        return articles.size
    }


}