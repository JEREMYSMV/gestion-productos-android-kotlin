package com.isil.android.pa3

import android.app.Application
import com.isil.android.pa3.di.AppContainer

// Clase Application: crea el contenedor de dependencias una sola vez
class PA3App : Application() {

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }

    companion object {
        lateinit var container: AppContainer
            private set
    }
}