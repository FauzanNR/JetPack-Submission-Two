package com.app.jetpacksubmissiontwo.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.jetpacksubmissiontwo.R
import com.app.jetpacksubmissiontwo.data.model.TvModel
import com.app.jetpacksubmissiontwo.data.remote.network.response.ResultTv
import com.app.jetpacksubmissiontwo.databinding.ItemCardBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvAdapter : RecyclerView.Adapter<TvAdapter.TvHolder>() {

    private var dataTv = ArrayList<ResultTv>()

    fun setDataAdapter(data: TvModel) {
        dataTv.clear()
        data.resultModels.let { dataTv.addAll(it) }
        notifyDataSetChanged()
    }

    class TvHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultTv: ResultTv) {
            binding.apply {
                idItemRating.text = resultTv.vote_average.toString()
                idItemTitle.text = resultTv.name
                idItemDescription.text = resultTv.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w400/${resultTv.poster_path}")
                    .apply(
                        RequestOptions()
                    )
                    .error(R.drawable.ic_broken_img)
                    .into(idItemImg)
                itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("EXTRA_TV", resultTv.id)
                    Navigation.findNavController(itemView)
                        .navigate(R.id.detailFragment, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvHolder(binding)
    }

    override fun onBindViewHolder(holder: TvHolder, position: Int) {
        val data = dataTv[position]
        holder.bind(data)

    }

    override fun getItemCount(): Int = dataTv.size
}