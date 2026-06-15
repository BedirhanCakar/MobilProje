package com.example.mobil.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.mobil.data.local.VivolanceDatabase
import com.example.mobil.data.local.getDatabaseBuilder
import com.example.mobil.data.repository.HealthRepositoryImpl
import com.example.mobil.domain.repository.HealthRepository
import com.example.mobil.ui.viewmodel.HealthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlinx.coroutines.Dispatchers
import org.koin.core.scope.Scope

val databaseModule = module {
    single<VivolanceDatabase> {
        // androidContext() should be passed from the platform side startKoin or handled via expect/actual
        // For simplicity in commonMain, we assume the builder is correctly configured
        getDatabaseBuilder()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single { get<VivolanceDatabase>().userDao() }
    single { get<VivolanceDatabase>().vitalSignDao() }
    single { get<VivolanceDatabase>().appointmentDao() }
    single { get<VivolanceDatabase>().logDao() }
}

val repositoryModule = module {
    single<HealthRepository> { HealthRepositoryImpl(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { HealthViewModel(get()) }
}
