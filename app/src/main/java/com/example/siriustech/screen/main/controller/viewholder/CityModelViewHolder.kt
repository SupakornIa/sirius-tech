package com.example.siriustech.screen.main.controller.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.siriustech.databinding.ItemCityModelBinding
import com.example.siriustech.screen.main.model.CityListUi

class CityModelViewHolder(
    private val binding: ItemCityModelBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(city: CityListUi.CityUi?) {
        city?.let {
            binding.name = "${it.name}, ${it.country} "
            binding.executePendingBindings()
        }

        binding.root.setOnClickListener { city?.onClick?.invoke() }
    }

    companion object {
        fun create(parent: ViewGroup): CityModelViewHolder {
            return ItemCityModelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let(::CityModelViewHolder)
        }
    }
}