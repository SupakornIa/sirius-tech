package com.example.siriustech.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavBackStackEntry

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {
    protected abstract val viewModel: VM
    protected lateinit var binding: DB

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    protected abstract fun getViewModelBindingVariable(): Int
    protected abstract fun initView()
    protected abstract fun initViewModel()
    protected open fun handleBackStackEntry(navBackStackEntry: NavBackStackEntry) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(getViewModelBindingVariable(), viewModel)
        }

        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
    }

}