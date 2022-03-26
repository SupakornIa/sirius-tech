package com.example.siriustech.screen.main.controller.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.siriustech.databinding.ItemCityModelBinding
import com.example.siriustech.screen.main.model.CityListUi

class CityModelViewHolder(private val binding: ItemCityModelBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(city: CityListUi.CityUi?) {
        city?.let {
            binding.name = "${it.name}, ${it.country} "
            binding.executePendingBindings()
        }
    }
}