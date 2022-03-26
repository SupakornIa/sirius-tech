package com.example.siriustech.screen.main.controller

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.siriustech.screen.main.controller.viewholder.LoadingModelViewHolder

class LoadingStateAdapter : LoadStateAdapter<LoadingModelViewHolder>() {
    override fun onBindViewHolder(holder: LoadingModelViewHolder, loadState: LoadState) {
        when (loadState) {
            LoadState.Loading -> holder.bind()
            else -> {}
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingModelViewHolder {
        return LoadingModelViewHolder.create(parent)
    }

}