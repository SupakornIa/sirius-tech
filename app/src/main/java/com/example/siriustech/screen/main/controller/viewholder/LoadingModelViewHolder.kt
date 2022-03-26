package com.example.siriustech.screen.main.controller.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.siriustech.databinding.ItemLoadingModelBinding

class LoadingModelViewHolder(private val binding: ItemLoadingModelBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        //We can bind in case there are condition to show or hide at footer
    }

    companion object {
        fun create(parent: ViewGroup): LoadingModelViewHolder {
            val binding =
                ItemLoadingModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return LoadingModelViewHolder(binding)
        }
    }
}