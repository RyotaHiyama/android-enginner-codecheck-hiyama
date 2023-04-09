package jp.co.yumemi.android.code_check.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
): Parcelable
