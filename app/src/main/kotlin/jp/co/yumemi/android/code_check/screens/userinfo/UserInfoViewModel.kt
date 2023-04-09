package jp.co.yumemi.android.code_check.screens.userinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.ktor.client.call.*
import jp.co.yumemi.android.code_check.Repository
import jp.co.yumemi.android.code_check.TopActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

class UserInfoViewModel: ViewModel() {
    private val repository = Repository()

    private val _name =  MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name
    private val _ownerIconUrl =  MutableLiveData<String>()
    val ownerIconUrl: LiveData<String>
        get() = _ownerIconUrl
    private val _twitterName =  MutableLiveData<String>()
    val twitterName: LiveData<String>
        get() = _twitterName
    private val _publicRepos =  MutableLiveData<String>()
    val publicRepos: LiveData<String>
        get() = _publicRepos
    private val _followers =  MutableLiveData<String>()
    val followers: LiveData<String>
        get() = _followers
    private val _following =  MutableLiveData<String>()
    val following: LiveData<String>
        get() = _following

    // 検索結果
    suspend fun searchUsesInfoResults(inputText: String) = withContext(Dispatchers.IO) {
        try {
            val response = repository.searchUserInfo(inputText)
            Log.i("res", response.toString())
            val jsonBody = JSONObject(response.receive<String>())

            val name = jsonBody.optString("login")
            val ownerIconUrl = jsonBody.optString("avatar_url")
            var twitterName = jsonBody.optString("twitter_username")
            // twitter登録してなければStringでnullが返ってくる
            if (twitterName == "null") {
                twitterName = "no twitter"
            }
            val publicRepos = jsonBody.optLong("public_repos").toString()
            val followers = jsonBody.optLong("followers").toString()
            val following = jsonBody.optLong("following").toString()

            _name.postValue(name)
            _ownerIconUrl.postValue(ownerIconUrl)
            _twitterName.postValue(twitterName)
            _publicRepos.postValue(publicRepos)
            _followers.postValue(followers)
            _following.postValue(following)

            TopActivity.lastSearchDate = Date()
        } catch (e: Exception) {
            throw Exception("Error in searchResults(): ${e.message}")
        }
    }
}