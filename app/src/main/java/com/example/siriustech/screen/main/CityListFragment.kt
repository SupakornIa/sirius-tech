package com.example.siriustech.screen.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.siriustech.BR
import com.example.siriustech.R
import com.example.siriustech.base.BaseFragment
import com.example.siriustech.databinding.FragmentCityListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CityListFragment : BaseFragment<CityListViewModel, FragmentCityListBinding>() {
    override val viewModel: CityListViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.fragment_city_list

    override fun getViewModelBindingVariable(): Int = BR.viewModel

    override fun initView() {
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun initViewModel() {
    }

}