package com.example.moviesapp.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.ui.Data.Movie

class NotificationsAdapter(var watchlist: List<Movie>) : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_watchlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = watchlist[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return watchlist.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title)
        private val releaseDateTextView: TextView = itemView.findViewById(R.id.release_date)
        private val overviewTextView: TextView = itemView.findViewById(R.id.overview)
        private val posterImageView: ImageView = itemView.findViewById(R.id.poster)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            releaseDateTextView.text = movie.release_date
            overviewTextView.text = movie.overview

            // Load the poster image
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(posterImageView)
        }
    }
}