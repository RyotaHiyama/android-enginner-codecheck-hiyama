package jp.co.yumemi.android.code_check.screens.searchuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.ktor.client.call.*
import jp.co.yumemi.android.code_check.Repository
import jp.co.yumemi.android.code_check.TopActivity
import jp.co.yumemi.android.code_check.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

class SearchUsersViewModel: ViewModel() {
    private val repository = Repository()

    val itemLive = MutableLiveData<List<User>>()

    private val _navigateToFragmentSearchRepository = MutableLiveData<Boolean>()
    val navigateToFragmentSearchRepository: LiveData<Boolean>
        get() = _navigateToFragmentSearchRepository

    fun goRepositorySearchButtonClick() {
        _navigateToFragmentSearchRepository.value = true
    }

    fun navigateToFragmentSearchRepositoryComplete() {
        _navigateToFragmentSearchRepository.value = false
    }

    // 検索結果
    suspend fun searchUsesResults(inputText: String) = withContext(Dispatchers.IO) {
        val users = mutableListOf<User>()
        try {
            val response = repository.searchUsers(inputText)
            Log.i("res", response.toString())
            val jsonBody = JSONObject(response.receive<String>())

            val jsonItems = jsonBody.optJSONArray("items")

            // アイテムの個数分ループする
            if (jsonItems != null) {
                for (i in 0 until jsonItems.length()) {
                    val jsonItem = jsonItems.optJSONObject(i)
                    val name = jsonItem?.optString("login") ?: "no name"

                    users.add(
                        User(
                        name = name
                        )
                    )
                    itemLive.postValue(users)
                }
            }
            TopActivity.lastSearchDate = Date()
        } catch (e: Exception) {
            throw Exception("Error in searchResults(): ${e.message}")
        }
    }
}