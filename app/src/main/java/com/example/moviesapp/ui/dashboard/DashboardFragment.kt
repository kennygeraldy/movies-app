package com.example.moviesapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentDashboardBinding
import com.example.moviesapp.ui.Data.GenreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var genreAdapter: DashboardAdapter
    private val apiKey = "291a8d80b917f68b7336ae3689f74420"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchGenres()

        return view
    }

    private fun fetchGenres() {
        RetrofitClient.instance.getMovieGenres(apiKey).enqueue(object : Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if (response.isSuccessful) {
                    response.body()?.genres?.let { genres ->
                        genreAdapter = DashboardAdapter(genres) { genre ->

                            Toast.makeText(context, "Clicked: ${genre.name}", Toast.LENGTH_SHORT).show()
                        }
                        recyclerView.adapter = genreAdapter
                    }
                }
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to load genres", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
