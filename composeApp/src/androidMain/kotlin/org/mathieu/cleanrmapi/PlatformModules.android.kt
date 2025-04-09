package org.mathieu.cleanrmapi

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.mathieu.cleanrmapi.common.AndroidSoundPlayer
import org.mathieu.cleanrmapi.common.Interfaces.SoundPlayer
import org.mathieu.cleanrmapi.data.dataStoreModule
import org.mathieu.cleanrmapi.data.databaseBuilderModule
import org.mathieu.cleanrmapi.data.databaseModule

// Defines a Koin module that provides the Android implementation of the SoundPlayer interface
val soundModule = module {
    single<SoundPlayer> { AndroidSoundPlayer(androidContext()) }
}

// Collects all platform-specific modules for Android
actual fun platformModules() = listOf(
    databaseModule,
    databaseBuilderModule,
    dataStoreModule,
    soundModule
)