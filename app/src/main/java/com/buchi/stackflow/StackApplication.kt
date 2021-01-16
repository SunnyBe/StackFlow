package com.buchi.stackflow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StackApplication() : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
