package org.postliu.todo

import android.content.Context
import org.postliu.todo.utils.ContextUtils

object ContextSDK {

    fun init(context: Context) {
        ContextUtils.instance.initContext(context)
    }
}