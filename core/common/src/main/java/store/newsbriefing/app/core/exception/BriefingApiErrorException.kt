package store.newsbriefing.app.core.exception

class BriefingApiErrorException(
    val errorCode: String,
    val errorMessage: String,
    val httpStatusCode: Int
) : Exception("$errorMessage [$errorCode]") {

    override fun toString(): String {
        return "BriefingApiErrorException(errorCode='$errorCode', errorMessage='$errorMessage', httpStatusCode=$httpStatusCode)"
    }
}