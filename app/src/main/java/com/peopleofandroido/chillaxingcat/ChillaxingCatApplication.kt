package com.peopleofandroido.chillaxingcat

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.peopleofandroido.base.di.componentModule
import com.peopleofandroido.base.di.networkModule
import com.peopleofandroido.chillaxingcat.di.appViewModelModule
import com.peopleofandroido.chillaxingcat.di.repositoryModule
import com.peopleofandroido.chillaxingcat.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
internal var notificationManager: NotificationManager? = null
internal var alarmManager: AlarmManager? = null

class ChillaxingCatApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger() //androidLogger를 KoinLogger로 사용
            androidContext(this@ChillaxingCatApplication) // 해당 안드로이드 context 사용
                //koin.loadModules(arrayListOf(...)) //이렇게로도 가능
            modules(
                appViewModelModule,
                componentModule,
                repositoryModule,
                useCaseModule,
                networkModule,
            )
        }
    }
}