package com.example.moviesapp.ui.Data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(private val context: Context) {
    private val dbHelper = MovieDbHelper(context)
    private val db = dbHelper.writableDatabase

    fun addMovieToWatchlist(movie: Movie) {
        val values = ContentValues()
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.title)
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.release_date)
        db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun getWatchlist(): List<Movie> {
        val cursor = db.query(
            MovieContract.MovieEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val watchlist = mutableListOf<Movie>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE))
            val releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE))
            watchlist.add(Movie(title, releaseDate, "", ""))
        }
        cursor.close()
        return watchlist
    }
}

object MovieContract {
    object MovieEntry {
        const val TABLE_NAME = "movies"
        const val COLUMN_TITLE = "title"
        const val COLUMN_RELEASE_DATE = "release_date"
    }
}

class MovieDbHelper(context: Context) : SQLiteOpenHelper(context, "movies.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE ${MovieContract.MovieEntry.TABLE_NAME} (" +
                "${MovieContract.MovieEntry.COLUMN_TITLE} TEXT PRIMARY KEY, " +
                "${MovieContract.MovieEntry.COLUMN_RELEASE_DATE} TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${MovieContract.MovieEntry.TABLE_NAME}")
        onCreate(db)
    }
}