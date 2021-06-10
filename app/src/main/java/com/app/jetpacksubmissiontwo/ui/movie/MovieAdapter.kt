package com.app.jetpacksubmissiontwo.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.jetpacksubmissiontwo.R
import com.app.jetpacksubmissiontwo.data.model.FilmModel
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissiontwo.databinding.ItemCardBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    var dataFilm = ArrayList<ResultMovie>()

    fun setDataAdapter(data: FilmModel) {
        dataFilm.clear()
        data.resultMovieModels.let { dataFilm.addAll(it) }
        notifyDataSetChanged()
    }

    class MovieHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultMovie: ResultMovie) {
            binding.apply {
                idItemRating.text = resultMovie.vote_average.toString()
                idItemTitle.text = resultMovie.title
                idItemDescription.text = resultMovie.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w400/${resultMovie.poster_path}")
                    .apply(
                        RequestOptions()
                    )
                    .error(R.drawable.ic_broken_img)
                    .into(idItemImg)
                itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("EXTRA_MOVIE", resultMovie.id)
                    Navigation.findNavController(itemView)
                        .navigate(R.id.detailFragment, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val data = dataFilm[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = dataFilm.size

}