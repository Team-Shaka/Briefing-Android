package store.newsbriefing.app.core.common.util

import android.util.Log
import store.newsbriefing.app.core.common.BuildConfig

object BriefingLogger {

    private fun getClassName(): String {
        val ste = Thread.currentThread().stackTrace
        for (i in ste.indices) {
            if (ste[i].fileName != "BriefingLogger.kt") {
                return ste[i].fileName.replace(".kt", "")
            }
        }
        return "UnknownClass"
    }

    fun e(message: String?, TAG: String = getClassName()) {
        if (isDebugMode()) Log.e(TAG, buildLogMsg(message))
    }

    fun w(message: String?, TAG: String = getClassName()) {
        if (isDebugMode()) Log.w(TAG, buildLogMsg(message))
    }

    fun i(message: String?, TAG: String = getClassName()) {
        if (isDebugMode()) Log.i(TAG, buildLogMsg(message))
    }

    fun d(message: String?, TAG: String = getClassName()) {
        if (isDebugMode()) Log.d(TAG, buildLogMsg(message))
    }

    fun v(message: String?, TAG: String = getClassName()) {
        if (isDebugMode()) Log.v(TAG, buildLogMsg(message))
    }

    private fun isDebugMode(): Boolean = BuildConfig.DEBUG

    private fun buildLogMsg(message: String?): String {
        val ste = Thread.currentThread().stackTrace[4]
        return buildString {
            append("[")
            append(ste.fileName.replace(".kt", ""))
            append("::")
            append(ste.methodName)
            append("] ")
            message?.let { append(it) }
        }
    }
}