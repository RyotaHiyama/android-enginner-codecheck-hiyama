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
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

/**
 * 選択したrepositoryの情報を表示する画面
 */
class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    private var binding: FragmentTwoBinding? = null
    private val _binding get() = if (binding != null) binding else throw NullPointerException("Expression 'binding' must not be null")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentTwoBinding.bind(view)

        val item = args.item

        // fragment_twoの各Viewにitemの情報を設定
        _binding?.ownerIconView?.load(item.ownerIconUrl)
        _binding?.nameView?.text = item.name
        _binding?.languageView?.text = item.language
        _binding?.starsView?.text = getString(R.string.count_stars, item.stargazersCount)
        _binding?.watchersView?.text = getString(R.string.count_watchers ,item.watchersCount)
        _binding?.forksView?.text = getString(R.string.count_forks, item.forksCount)
        _binding?.openIssuesView?.text = getString(R.string.open_issues, item.openIssuesCount)
    }
}
