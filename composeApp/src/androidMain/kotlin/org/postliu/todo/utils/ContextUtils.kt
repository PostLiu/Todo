package org.postliu.todo.utils

import android.content.Context
import android.view.Window

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

    private var mWindow: Window? = null

    val window: Window
        get() {
            requireNotNull(mWindow) { "please use initWindow(window: Window) init" }
            return mWindow!!
        }

    fun initContext(context: Context) {
        mContext = context
    }

    fun initWindow(window: Window) {
        mWindow = window
    }
}