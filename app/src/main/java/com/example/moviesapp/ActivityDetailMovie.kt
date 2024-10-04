package com.example.moviesapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.example.moviesapp.ui.Data.Movie
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.InputStream
import java.net.URL

class ActivityDetailMovie : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var addToWatchlistButton: Button
    private lateinit var watchlist: MutableList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        supportActionBar?.hide()

        val title = intent.getStringExtra("title")
        val releaseDate = intent.getStringExtra("release_date")
        val overview = intent.getStringExtra("overview")
        val posterPath = intent.getStringExtra("poster_path")

        val titleTextView: TextView = findViewById(R.id.detail_movie_title)
        val releaseDateTextView: TextView = findViewById(R.id.detail_movie_release_date)
        val overviewTextView: TextView = findViewById(R.id.detail_movie_overview)
        val posterImageView: ImageView = findViewById(R.id.detail_movie_poster)
        ratingBar = findViewById(R.id.detail_movie_rating)
        addToWatchlistButton = findViewById(R.id.add_to_watchlist_button)

        titleTextView.text = title
        releaseDateTextView.text = releaseDate
        overviewTextView.text = overview

        if (posterPath != null) {
            LoadImageTask(posterImageView).execute("https://image.tmdb.org/t/p/w500$posterPath")
        } else {
            posterImageView.setImageResource(R.drawable.placeholder)
        }

        // Set the rating bar's properties
        ratingBar.numStars = 5
        ratingBar.stepSize = 0.5f
        ratingBar.rating = 0f // Initialize the rating to 0

        // Set a listener to handle rating changes
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser  ->
            if (fromUser ) {
                // Handle the rating change here
                Toast.makeText(this, "Rated $rating", Toast.LENGTH_SHORT).show()
            }
        }

        // Initialize the watchlist
        watchlist = mutableListOf()

        // Set a listener to handle the "Add to Watchlist" button click
        addToWatchlistButton.setOnClickListener {
            // Get the movie details
            val movie = Movie(
                title.toString(),
                releaseDate.toString(),
                overview.toString(),
                posterPath.toString()
            )

            // Add the movie to the watchlist
            watchlist.add(movie)

            // Save the watchlist to SharedPreferences
            val sharedPreferences = getSharedPreferences("watchlist", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("watchlist", Gson().toJson(watchlist))
            editor.apply()

            // Show a toast message
            Toast.makeText(this, "Added to Watchlist", Toast.LENGTH_SHORT).show()
        }
    }

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
