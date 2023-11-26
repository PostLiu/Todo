package org.postliu.todo.utils

import android.content.Context

class ContextUtils {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ContextUtils()
        }
    }

    private var mContext: Context? = null

    val context: Context
        get() {
            requireNotNull(mContext) { "please use initContext(context: Context) init" }
            return mContext!!
        }

    fun initContext(context: Context) {
        mContext = context
    }
}