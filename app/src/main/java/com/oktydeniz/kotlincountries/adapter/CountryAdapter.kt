package com.oktydeniz.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oktydeniz.kotlincountries.databinding.CountryRowBinding
import com.oktydeniz.kotlincountries.model.CountriesModel
import com.oktydeniz.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.country_row.view.*

class CountryAdapter(
    private val countryList: ArrayList<CountriesModel>,
) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>(), CountryClickListener {

    class ViewHolder(var view: CountryRowBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.countryModel = countryList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateList(newList: List<CountriesModel>) {
        countryList.clear()
        countryList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val uuid = v.countryUUID.text.toString().toInt()
        val action =
            FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}