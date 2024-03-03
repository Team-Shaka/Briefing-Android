package store.newsbriefing.app.core.network.model.member

import com.google.gson.annotations.SerializedName

data class NetworkMemberDelete(
    @SerializedName("quitAt")
    val quitAt : String) {
}