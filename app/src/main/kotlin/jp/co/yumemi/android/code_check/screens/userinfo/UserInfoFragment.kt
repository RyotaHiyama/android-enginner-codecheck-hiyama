package jp.co.yumemi.android.code_check.screens.userinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentUsersInfoBinding
import kotlinx.coroutines.launch

/**
* ユーザーの詳細情報を検索して掲載する画面
 */
class UserInfoFragment : Fragment() {
    private val args: UserInfoFragmentArgs by navArgs()
    private var binding: FragmentUsersInfoBinding? = null
    private lateinit var viewModel: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users_info, container, false)
        viewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = args.user.name

        // 前の画面でタッチしたユーザーの詳細情報を検索
        viewModel.viewModelScope.launch {
            viewModel.searchUsesInfoResults(user)
        }

        // ユーザーアイコンの設定
        viewModel.ownerIconUrl.observe(viewLifecycleOwner) {
            binding?.ownerIconView?.load(it)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.ownerIconUrl.removeObservers(viewLifecycleOwner)
        binding = null
    }

}