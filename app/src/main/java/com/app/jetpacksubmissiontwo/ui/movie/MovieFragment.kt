package com.app.jetpacksubmissiontwo.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.jetpacksubmissiontwo.R
import com.app.jetpacksubmissiontwo.databinding.MovieFragmentBinding
import com.app.jetpacksubmissiontwo.viewmodel.FragmentModel
import com.app.jetpacksubmissiontwo.viewmodel.ViewModelFactory


class MovieFragment : FragmentModel() {
    private lateinit var binding: MovieFragmentBinding
    private val adapterMovie: MovieAdapter by lazy {
        MovieAdapter().apply {
            notifyDataSetChanged()
        }
    }

    override fun onDisconnected() {
        textViewInfo.visibility = View.VISIBLE
    }

    override fun onConnected() {
        observeData()
        textViewInfo.visibility = View.INVISIBLE
    }

    override fun queryApi(q: String) {}

    private fun observeData() {
        if (isConnected and this.isAdded) {
            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            viewModel.getMoviePopular().observe(viewLifecycleOwner, {
                adapterMovie.setDataAdapter(it)
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_fragment, container, false)

        val gridCount = resources.getInteger(R.integer.grid_column_count)
        binding = MovieFragmentBinding.bind(view)
        textViewInfo = binding.idTextInfoMovie
        binding.idRecviewMovie.apply {
            layoutManager = GridLayoutManager(context, gridCount)
            adapter = adapterMovie
        }

        return view
    }
}