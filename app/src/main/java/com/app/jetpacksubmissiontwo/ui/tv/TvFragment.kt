package com.app.jetpacksubmissiontwo.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.jetpacksubmissiontwo.R
import com.app.jetpacksubmissiontwo.databinding.TvFragmentBinding
import com.app.jetpacksubmissiontwo.viewmodel.FragmentModel
import com.app.jetpacksubmissiontwo.viewmodel.ViewModelFactory

class TvFragment : FragmentModel() {
    private lateinit var binding: TvFragmentBinding
    private val adapterTv: TvAdapter by lazy {
        TvAdapter().apply {
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
            val viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]
            viewModel.getTvPopular().observe(viewLifecycleOwner, {
                adapterTv.setDataAdapter(it)
            })
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tv_fragment, container, false)
        val gridCount = resources.getInteger(R.integer.grid_column_count)
        binding = TvFragmentBinding.bind(view)
        textViewInfo = binding.idTextInfoTv
        binding.idRecviewTv.apply {
            layoutManager = GridLayoutManager(context, gridCount)
            adapter = adapterTv
        }
        return view
    }
}