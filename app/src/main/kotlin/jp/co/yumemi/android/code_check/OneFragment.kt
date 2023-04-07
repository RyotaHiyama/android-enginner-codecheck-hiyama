/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding

/**
* repositoryを検索する画面
 */
class OneFragment : Fragment(R.layout.fragment_one) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOneBinding.bind(view)
        val viewModel = OneViewModel(requireContext())

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: Item) {
                gotoRepositoryFragment(item)
            }
        })

        // searchInputTextのエディターアクションがIME_ACTION_SEARCHの場合、入力された文字列を使って検索し、結果をRecyclerViewに設定する
        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH && editText.text.toString() != "") {
                editText.text.toString().let {
                    viewModel.searchResults(it).apply {
                        adapter.submitList(this)
                    }
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
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
            OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(action)
    }
}

val diff_util = object : DiffUtil.ItemCallback<Item>() {
    // Itemのnameが同じならtrue
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.name == newItem.name
    }

    // Itemが同じならtrue
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}
