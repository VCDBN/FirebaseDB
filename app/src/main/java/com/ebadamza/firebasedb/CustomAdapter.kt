package com.ebadamza.firebasedb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CustomAdapter(var data: List<Movie>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var Name: TextView = view.findViewById(R.id.MovieName)
        var Year: TextView = view.findViewById(R.id.Year)
        var Imdb: TextView = view.findViewById(R.id.Imdb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.Name.text = item.name.toString()
        holder.Year.text = item.year.toString()
        holder.Imdb.text = item.imdbRating.toString()
    }

    // Now implement on click: https://medium.com/@amsavarthan/the-modern-approach-to-handle-item-click-on-recyclerview-6292cca3178d
}
