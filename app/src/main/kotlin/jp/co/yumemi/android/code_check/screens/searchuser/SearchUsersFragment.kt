package jp.co.yumemi.android.code_check.screens.searchuser

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.code_check.CustomUserAdapter
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.User
import jp.co.yumemi.android.code_check.databinding.FragmentSearchUsersBinding
import kotlinx.coroutines.launch

/**
 * userを検索する画面
 */
class SearchUsersFragment : Fragment() {
    private var binding: FragmentSearchUsersBinding? = null
    private lateinit var viewModel: SearchUsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_users, container, false)
        viewModel = ViewModelProvider(this)[SearchUsersViewModel::class.java]
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = CustomUserAdapter(object : CustomUserAdapter.OnItemClickListener {
            override fun itemClick(user: User) {
                gotoUserInfoFragment(user)
            }
        })

        binding?.searchButton?.setOnClickListener {
            val editText = binding?.searchInputText?.text.toString()
            if (editText != "") {
                viewModel.viewModelScope.launch{
                    try {
                        viewModel.searchUsesResults(editText)
                    } catch (e: Exception){
                        Toast.makeText(activity, "検索中に予期せぬエラーが発生しました。やり直してください。", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        viewModel.itemLive.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // リポジトリ検索画面へ遷移
        viewModel.navigateToFragmentSearchRepository.observe(viewLifecycleOwner) {
            if (it) {
                val action =
                    SearchUsersFragmentDirections.actionSearchUsersFragmentToSearchRepositoryFragment()
                findNavController().navigate(action)
                viewModel.navigateToFragmentSearchRepositoryComplete()
            }
        }

        // recyclerViewにlayoutManager、dividerItemDecoration、adapterを設定
        binding?.recyclerView.also {
            it?.layoutManager = layoutManager
            it?.addItemDecoration(dividerItemDecoration)
            it?.adapter = adapter
        }
    }

    // UserInfoFragmentへ遷移
    fun gotoUserInfoFragment(user: User) {
        val action =
            SearchUsersFragmentDirections.actionSearchUsersFragmentToUserInfoFragment(user = user)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.itemLive.removeObservers(viewLifecycleOwner)
        viewModel.navigateToFragmentSearchRepository.removeObservers(viewLifecycleOwner)
        binding = null
    }
}