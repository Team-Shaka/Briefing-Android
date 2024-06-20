package store.newsbriefing.app.core.network.retrofit.calladapter

import com.google.gson.Gson
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import store.newsbriefing.app.core.exception.BriefingApiErrorException
import store.newsbriefing.app.core.network.model.RetrofitCommonResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): ResultCallAdapter<Any>? {
        if (Call::class.java != getRawType(returnType)) {
            return null
        }
        check(returnType is ParameterizedType)

        val responseType = getParameterUpperBound(0, returnType)
        return ResultCallAdapter(responseType)
    }
}

internal class ResultCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Call<R>> {
    override fun responseType() : Type = responseType

    override fun adapt(call: Call<R>): Call<R> {
        return ResultCall(call)
    }
}

internal class ResultCall<R>(private val delegate: Call<R>) : Call<R> {

    val gson : Gson = Gson()

    override fun enqueue(callback: Callback<R>) {
        delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback.onResponse(this@ResultCall, response)
                    } else {
                        callback.onFailure(this@ResultCall, BriefingApiErrorException("Error", "Response body is null", response.code()))
                    }
                } else {
                    val body = response.errorBody()?.string()
                    if (body != null) {
                        val commonResponse = gson.fromJson(body, RetrofitCommonResponse::class.java)
                        callback.onFailure(this@ResultCall, BriefingApiErrorException(commonResponse.code, commonResponse.message, response.code()))
                    } else {
                        callback.onFailure(this@ResultCall, BriefingApiErrorException("Error", "Error body is null", response.code()))
                    }
                }
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                callback.onFailure(this@ResultCall, t)
            }
        })
    }

    override fun clone(): Call<R> = ResultCall(delegate.clone())
    override fun execute(): Response<R> = throw UnsupportedOperationException()
    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
}