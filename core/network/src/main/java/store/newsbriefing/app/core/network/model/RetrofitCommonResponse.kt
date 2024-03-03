package store.newsbriefing.app.core.network.model

import com.google.gson.annotations.SerializedName

data class RetrofitCommonResponse<T>(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: T,
)