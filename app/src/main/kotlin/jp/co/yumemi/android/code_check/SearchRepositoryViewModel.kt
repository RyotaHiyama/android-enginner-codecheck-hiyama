/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import io.ktor.client.call.*
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

/**
 * OneFragment で使う
 */
class SearchRepositoryViewModel(
    val app: Application
) : ViewModel() {

    private val repository = Repository()


    // 検索結果
    suspend fun searchResults(inputText: String): List<Item> = withContext(Dispatchers.IO) {
        val items = mutableListOf<Item>()
        try {
            val response = repository.search(inputText)
            Log.i("res", response.toString())
            val jsonBody = JSONObject(response.receive<String>())

            val jsonItems = jsonBody.optJSONArray("items")

            // アイテムの個数分ループする
            if (jsonItems != null) {
                for (i in 0 until jsonItems.length()) {
                    val jsonItem = jsonItems.optJSONObject(i)
                    val name = jsonItem?.optString("full_name") ?: "no name"
                    val ownerIconUrl =
                        jsonItem?.optJSONObject("owner")?.optString("avatar_url") ?: "no owner"
                    val language = jsonItem?.optString("language") ?: "no language"
                    val stargazersCount = jsonItem?.optLong("stargazers_count") ?: 0
                    val watchersCount = jsonItem?.optLong("watchers_count") ?: 0
                    val forksCount = jsonItem?.optLong("forks_count") ?: 0
                    val openIssuesCount = jsonItem?.optLong("open_issues_count") ?: 0

                    items.add(
                        Item(
                            name = name,
                            ownerIconUrl = ownerIconUrl,
                            language = app.getString(R.string.written_language, language),
                            stargazersCount = stargazersCount,
                            watchersCount = watchersCount,
                            forksCount = forksCount,
                            openIssuesCount = openIssuesCount
                        )
                    )
                }
            }
            lastSearchDate = Date()
        } catch (e: Exception) {
            throw Exception("Error in searchResults(): ${e.message}")
        }
        return@withContext items.toList()
    }
}