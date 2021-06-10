package com.app.jetpacksubmissiontwo.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.jetpacksubmissiontwo.R
import com.app.jetpacksubmissiontwo.databinding.DetailFragmentBinding
import com.app.jetpacksubmissiontwo.viewmodel.FragmentModel
import com.app.jetpacksubmissiontwo.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailFragment : FragmentModel() {
    private var id_data = 0
    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var bottomNavigationView: BottomNavigationView


    private fun setType() {
        if (arguments != null)
            arguments?.let {
                when {
                    it.containsKey("EXTRA_MOVIE") -> {
                        id_data = it.getInt("EXTRA_MOVIE")
                        viewModel.getMovieDetail(id_data)
                            .observe(viewLifecycleOwner, {
                                binding.idDetailCollap.title = it.original_title
                                binding.idDetailDescription.text = it.overview
                                binding.idDetailRating.text = it.vote_average.toString()
                                this.context?.let { it1 ->
                                    Glide.with(it1)
                                        .load("https://image.tmdb.org/t/p/w500/${it.poster_path}")
                                        .apply(
                                            RequestOptions()
                                        )
                                        .error(R.drawable.ic_broken_img)
                                        .into(binding.idDetailImg)
                                }
                            })
                    }
                    it.containsKey("EXTRA_TV") -> {
                        id_data = it.getInt("EXTRA_TV")
                        viewModel.getTvDetail(id_data)
                            .observe(viewLifecycleOwner, {
                                binding.idDetailCollap.title = it.original_name
                                binding.idDetailDescription.text = it.overview
                                binding.idDetailRating.text = it.vote_average.toString()
                                this.context?.let { it1 ->
                                    Glide.with(it1)
                                        .load("https://image.tmdb.org/t/p/w500/${it.poster_path}")
                                        .apply(
                                            RequestOptions()
                                        )
                                        .error(R.drawable.ic_broken_img)
                                        .into(binding.idDetailImg)
                                }
                            })
                    }
                    else -> {
                        Log.d("Log Detail", id_data.toString())
                    }
                }
            }
    }


    override fun onDisconnected() {}
    override fun onConnected() {
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        setType()
    }

    override fun queryApi(q: String) {}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        binding = DetailFragmentBinding.bind(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bottomNavigationView =
            requireActivity().findViewById<View>(R.id.id_bottom_naview) as BottomNavigationView
        bottomNavigationView.visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().invalidateOptionsMenu()
        inflater.inflate(R.menu.share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.id_share_btn -> {
            val intent: Intent = Intent(Intent.ACTION_SEND).setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, "https://github.com/FauzanNR/Apps")
            startActivity(Intent.createChooser(intent, "Share Via"))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavigationView.visibility = View.VISIBLE
        (activity as AppCompatActivity).supportActionBar?.show()
    }

}