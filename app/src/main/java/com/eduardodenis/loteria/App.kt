package com.eduardodenis.loteria

import android.app.Application
import com.eduardodenis.loteria.data.AppDataBase

class App : Application() {

    lateinit var db: AppDataBase

    override fun onCreate() {
        super.onCreate()

        db = AppDataBase.getInstance(this)

    }

}
