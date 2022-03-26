package com.example.siriustech.screen.main.controller

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.siriustech.screen.main.controller.viewholder.CityModelViewHolder
import com.example.siriustech.screen.main.model.CityListUi
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class CityListDataAdapter @Inject constructor() :
    PagingDataAdapter<CityListUi.CityUi, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<CityListUi.CityUi>() {

            override fun areItemsTheSame(
                oldItem: CityListUi.CityUi,
                newItem: CityListUi.CityUi
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CityListUi.CityUi,
                newItem: CityListUi.CityUi
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CityModelViewHolder)?.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CityModelViewHolder.create(parent)
    }

}