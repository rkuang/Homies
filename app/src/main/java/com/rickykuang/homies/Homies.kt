package com.rickykuang.homies

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

class Homies : Application() {
    companion object {
        lateinit var mAuth: FirebaseAuth
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}