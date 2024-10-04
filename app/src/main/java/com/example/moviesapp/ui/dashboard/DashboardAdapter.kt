package com.example.moviesapp.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.ui.Data.Genre

class DashboardAdapter(private val genres: List<Genre>, private val clickListener: (Genre) -> Unit) :
    RecyclerView.Adapter<DashboardAdapter.GenreViewHolder>() {

    class GenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val genreName: TextView = view.findViewById(R.id.genreName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genres[position]
        holder.genreName.text = genre.name
        holder.itemView.setOnClickListener { clickListener(genre) }
    }

    override fun getItemCount() = genres.size
}