package com.project.gains.data.manager

import android.content.Context
import com.project.gains.domain.manager.ContextManager


class ContextManagerImpl(private val context: Context) : ContextManager {
    override fun getContext(): Context {
        return context.applicationContext
    }
}