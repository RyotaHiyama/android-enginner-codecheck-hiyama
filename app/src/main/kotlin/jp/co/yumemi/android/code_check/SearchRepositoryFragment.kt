/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentSearchRepositoryBinding
import kotlinx.coroutines.launch

/**
* repositoryを検索する画面
 */
class SearchRepositoryFragment : Fragment() {
    private lateinit var binding: FragmentSearchRepositoryBinding
    private lateinit var viewModel: SearchRepositoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(this.activity).application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_repository, container, false)
        val viewModelFactory = SearchRepositoryViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchRepositoryViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: Item) {
                gotoRepositoryFragment(item)
            }
        })

        // 検索ボタンを押した時、入力された文字列を使って検索する
        binding.searchButton.setOnClickListener {
            val editText = binding.searchInputText.text.toString()
            if (editText != "") {
                viewModel.viewModelScope.launch{
                    try {
                        viewModel.searchResults(editText)
                    } catch (e: Exception){
                        Toast.makeText(activity, "検索中に予期せぬエラーが発生しました。やり直してください。", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // 検索結果を一覧表示
        viewModel.itemLive.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // recyclerViewにlayoutManager、dividerItemDecoration、adapterを設定
        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    // TwoFragmentへ遷移
    fun gotoRepositoryFragment(item: Item) {
        val action =
            SearchRepositoryFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(action)
    }
}
