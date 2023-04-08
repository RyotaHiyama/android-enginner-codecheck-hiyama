package jp.co.yumemi.android.code_check

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchRepositoryViewModelFactory(private val app: Application): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchRepositoryViewModel::class.java)){
            return SearchRepositoryViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}