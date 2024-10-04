package com.example.moviesapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream
import java.net.URL

class ActivityDetailMovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val title = intent.getStringExtra("title")
        val releaseDate = intent.getStringExtra("release_date")
        val overview = intent.getStringExtra("overview")
        val posterPath = intent.getStringExtra("poster_path")

        val titleTextView: TextView = findViewById(R.id.detail_movie_title)
        val releaseDateTextView: TextView = findViewById(R.id.detail_movie_release_date)
        val overviewTextView: TextView = findViewById(R.id.detail_movie_overview)
        val posterImageView: ImageView = findViewById(R.id.detail_movie_poster)


        titleTextView.text = title
        releaseDateTextView.text = releaseDate
        overviewTextView.text = overview

        if (posterPath != null) {
            LoadImageTask(posterImageView).execute("https://image.tmdb.org/t/p/w500$posterPath")
        } else {
            posterImageView.setImageResource(R.drawable.placeholder)
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
