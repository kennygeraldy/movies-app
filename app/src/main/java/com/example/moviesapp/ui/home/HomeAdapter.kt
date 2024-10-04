package com.example.myapplications

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.ActivityDetailMovie
import com.example.moviesapp.R
import com.example.moviesapp.ui.Data.Movie
import java.io.InputStream
import java.net.URL

class HomeAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<HomeAdapter.MovieViewHolder>() {
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movie_title)
        val releaseDate: TextView = itemView.findViewById(R.id.movie_release_date)
        val overview: TextView = itemView.findViewById(R.id.movie_overview)
        val poster: ImageView = itemView.findViewById(R.id.movie_poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title ?: "No title"
        holder.releaseDate.text = movie.release_date ?: "No release date"
        holder.overview.text = movie.overview ?: "No overview"

        val posterPath = movie.poster_path
        if (posterPath != null) {
            LoadImageTask(holder.poster).execute("https://image.tmdb.org/t/p/w500$posterPath")
        } else {
            holder.poster.setImageResource(R.drawable.placeholder)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ActivityDetailMovie::class.java).apply {
                putExtra("title", movie.title)
                putExtra("release_date", movie.release_date)
                putExtra("overview", movie.overview)
                putExtra("poster_path", movie.poster_path)
            }
            context.startActivity(intent)
        }
    }


    override fun getItemCount() = movies.size

    private class LoadImageTask(private val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg params: String?): Bitmap? {
            val url = params[0]
            return try {
                val inputStream: InputStream = URL(url).openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            result?.let { imageView.setImageBitmap(it) }
        }
    }
}