package com.hipaduck.chillaxingcat.di

import com.hipaduck.chillaxingcat.data.LocalDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        LocalDatabase.getInstance(androidApplication())
    }

    single(createdAtStart = false) {
        get<LocalDatabase>().getDayOffDao()
    }
    single(createdAtStart = false) {
        get<LocalDatabase>().getHolidayDao()
    }
    single(createdAtStart = false) {
        get<LocalDatabase>().getRestingTimeDao()
    }
}