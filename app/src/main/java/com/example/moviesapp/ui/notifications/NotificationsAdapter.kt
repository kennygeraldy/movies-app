package com.example.moviesapp.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.ui.Data.Movie

class NotificationsAdapter(private val watchlist: List<Movie>) : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_watchlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = watchlist[position]
        holder.titleTextView.text = movie.title
        holder.releaseDateTextView.text = movie.release_date
    }

    override fun getItemCount(): Int = watchlist.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val releaseDateTextView: TextView = itemView.findViewById(R.id.release_date_text_view)
    }
}