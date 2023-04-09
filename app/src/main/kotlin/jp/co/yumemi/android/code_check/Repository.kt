package jp.co.yumemi.android.code_check

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*


class Repository {
    suspend fun search(inputText: String): HttpResponse {
        val response: HttpResponse
        try {
            val client = HttpClient(Android)
            response = client.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", inputText)
            }
        } catch (e: Exception) {
            throw Exception("Error in searchResults(): ${e.message}")
        }

        return response
    }

    suspend fun searchUsers(inputText: String): HttpResponse {
        val response: HttpResponse
        try {
            val client = HttpClient(Android)
            response = client.get("https://api.github.com/search/users") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", inputText)
            }
        } catch (e: Exception) {
            throw Exception("Error in searchResults(): ${e.message}")
        }

        return response
    }

    suspend fun searchUserInfo(inputText: String): HttpResponse {
        val response: HttpResponse
        try {
            val client = HttpClient(Android)
            response = client.get("https://api.github.com/users/$inputText") {
                header("Accept", "application/vnd.github.v3+json")
            }
        } catch (e: Exception) {
            throw Exception("Error in searchResults(): ${e.message}")
        }

        return response
    }

}