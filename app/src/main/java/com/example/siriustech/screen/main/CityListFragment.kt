package com.example.siriustech.screen.main

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.siriustech.BR
import com.example.siriustech.R
import com.example.siriustech.base.BaseFragment
import com.example.siriustech.databinding.FragmentCityListBinding
import com.example.siriustech.screen.main.controller.CityListController
import com.example.siriustech.screen.main.controller.CityListDataAdapter
import com.example.siriustech.screen.main.controller.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class CityListFragment : BaseFragment<CityListViewModel, FragmentCityListBinding>() {

    @Inject
    lateinit var controller: CityListController

    @Inject
    lateinit var cityListDataAdapter: CityListDataAdapter

    override val viewModel: CityListViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.fragment_city_list

    override fun getViewModelBindingVariable(): Int = BR.viewModel

    override fun initView() {
        setupRecyclerView()
        setupEditText()
    }

    private fun setupEditText() {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.search(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = cityListDataAdapter.withLoadStateFooter(LoadingStateAdapter())
        }
    }


    override fun initViewModel() {
        viewModel.searchEfficiencyTimeConsumeDisplayLiveData.observe(
            viewLifecycleOwner,
            binding.textViewTimeConsume::setText
        )

        viewModel.pagingDataLiveData.observe(viewLifecycleOwner) { pagingData ->
            viewLifecycleOwner.lifecycleScope.launch {
                cityListDataAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

}