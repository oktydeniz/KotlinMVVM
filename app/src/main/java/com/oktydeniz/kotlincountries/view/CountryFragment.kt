package com.oktydeniz.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.oktydeniz.kotlincountries.databinding.FragmentCountryBinding
import com.oktydeniz.kotlincountries.viewModel.CountryViewModel
class CountryFragment : Fragment() {

    private lateinit var viewModel: CountryViewModel
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        arguments?.let {
            val uuid = CountryFragmentArgs.fromBundle(it).uniqueId
            viewModel.getDataFromRoom(uuid)
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countryModel.observe(viewLifecycleOwner, { country ->
            country.let {
                binding.country = it
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}