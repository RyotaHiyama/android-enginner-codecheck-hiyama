/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryInfoBinding

/**
 * 選択したrepositoryの情報を表示する画面
 */
class RepositoryInfoFragment : Fragment(R.layout.fragment_repository_info) {

    private val args: RepositoryInfoFragmentArgs by navArgs()

    private var _binding: FragmentRepositoryInfoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        _binding = FragmentRepositoryInfoBinding.bind(view)

        val item = args.item

        // fragment_twoの各Viewにitemの情報を設定
        binding.ownerIconView.load(item.ownerIconUrl)
        binding.nameView.text = item.name
        binding.languageView.text = item.language
        binding.starsView.text = getString(R.string.count_stars, item.stargazersCount)
        binding.watchersView.text = getString(R.string.count_watchers ,item.watchersCount)
        binding.forksView.text = getString(R.string.count_forks, item.forksCount)
        binding.openIssuesView.text = getString(R.string.open_issues, item.openIssuesCount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}