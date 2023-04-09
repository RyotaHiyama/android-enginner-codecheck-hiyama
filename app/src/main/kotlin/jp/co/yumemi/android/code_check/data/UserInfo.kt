package jp.co.yumemi.android.code_check.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val name: String,
    val ownerIconUrl: String,
    val twitterName: String,
    val publicRepos: Long,
    val followers: Long,
    val following: Long
): Parcelable