package com.oktydeniz.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.oktydeniz.kotlincountries.R
import com.oktydeniz.kotlincountries.adapter.CountryAdapter
import com.oktydeniz.kotlincountries.viewModel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()
        countryListRecycler.adapter = countryAdapter

        swipeRefreshLayout.setOnRefreshListener {
            countryListRecycler.visibility = View.GONE
            errorText.visibility = View.GONE
            viewModel.refreshFromAPI()
            swipeRefreshLayout.isRefreshing = false

        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, { countries ->
            countries.let {
                countryListRecycler.visibility = View.VISIBLE
                countryAdapter.updateList(countries)

            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, { e ->
            e.let {
                if (it) {
                    errorText.visibility = View.VISIBLE

                } else {
                    errorText.visibility = View.GONE
                }
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, { loading ->
            loading.let {
                if (it) {
                    countryLoading.visibility = View.VISIBLE
                    countryListRecycler.visibility = View.GONE
                    errorText.visibility = View.GONE
                } else {
                    countryLoading.visibility = View.GONE

                }
            }

        })
    }
}