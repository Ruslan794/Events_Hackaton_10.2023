package com.riedera.events.presentation.di

import com.riedera.events.domain.AppRepository
import com.riedera.events.presentation.fragments.AccountFragment
import com.riedera.events.presentation.fragments.AddEventFragment
import com.riedera.events.presentation.fragments.ClubDetailsFragment
import com.riedera.events.presentation.fragments.EventDetailsFragment
import com.riedera.events.presentation.fragments.HomeFragment
import com.riedera.events.presentation.fragments.MyEventsFragment
import com.riedera.events.presentation.viewModels.AccountViewModel
import com.riedera.events.presentation.viewModels.AddEventViewModel
import com.riedera.events.presentation.viewModels.ClubDetailsViewModel
import com.riedera.events.presentation.viewModels.EventDetailsViewModel
import com.riedera.events.presentation.viewModels.HomeViewModel
import com.riedera.events.presentation.viewModels.MyEventsViewModel
import com.riedera.events.repository.ApiInterface
import com.riedera.events.repository.AppRepositoryImpl
import okhttp3.OkHttpClient
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    single<AppRepository> { AppRepositoryImpl(ApiInterface.create()) }

    viewModel { HomeViewModel(get()) }
    viewModel { AccountViewModel(get()) }
    viewModel { MyEventsViewModel(get()) }
    viewModel { AddEventViewModel(get()) }
    viewModel { EventDetailsViewModel(get()) }
    viewModel { ClubDetailsViewModel(get()) }

    fragment { HomeFragment() }
    fragment { AddEventFragment() }
    fragment { MyEventsFragment() }
    fragment { AccountFragment() }
    fragment { EventDetailsFragment() }
    fragment { ClubDetailsFragment() }
}