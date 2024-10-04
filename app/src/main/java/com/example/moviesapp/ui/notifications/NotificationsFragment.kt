package com.example.moviesapp.ui.notifications

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentNotificationsBinding
import com.example.moviesapp.ui.Data.Database
import com.example.moviesapp.ui.Data.Movie
import com.google.gson.Gson

class NotificationsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var watchlistAdapter: NotificationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_watchlist, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        watchlistAdapter = NotificationsAdapter(emptyList())
        recyclerView.adapter = watchlistAdapter

        loadWatchlist()

        return view
    }

    private fun loadWatchlist() {
        val sharedPreferences = requireContext().getSharedPreferences("watchlist", MODE_PRIVATE)
        val watchlistJson = sharedPreferences.getString("watchlist", "")
        val watchlist = Gson().fromJson(watchlistJson, Array<Movie>::class.java).toList()

        watchlistAdapter.watchlist = watchlist
        watchlistAdapter.notifyDataSetChanged()
    }
}