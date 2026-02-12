package com.markoapps.aisummerizer

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.markoapps.aisummerizer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initFirebase()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    fun initFirebase(){
        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // --- App Check ---
        if (BuildConfig.DEBUG) {
            // Debug provider (for testing on emulator or debug build)
            val providerFactory = DebugAppCheckProviderFactory.getInstance()
            FirebaseAppCheck.getInstance().installAppCheckProviderFactory(providerFactory)
        } else {
            // Release: Play Integrity provider (or SafetyNet for older devices)
            val providerFactory = PlayIntegrityAppCheckProviderFactory.getInstance()
            FirebaseAppCheck.getInstance().installAppCheckProviderFactory(providerFactory)
        }
    }
}