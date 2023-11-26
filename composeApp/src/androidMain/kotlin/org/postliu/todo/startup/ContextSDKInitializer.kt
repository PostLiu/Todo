package org.postliu.todo.startup

import android.content.Context
import androidx.startup.Initializer
import org.postliu.todo.ContextSDK

class ContextSDKInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        ContextSDK.init(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}