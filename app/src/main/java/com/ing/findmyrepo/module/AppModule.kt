package com.ing.findmyrepo.module

import androidx.lifecycle.MutableLiveData
import com.ing.findmyrepo.data.repository.UserRepository
import com.ing.findmyrepo.viewmodel.UserRepoDetailViewModel
import com.ing.findmyrepo.viewmodel.UserRepoViewModel
import org.koin.dsl.module

class AppModule {

    val appModule = module {

        single { MutableLiveData("") }

        single { UserRepository(get()) }

        single { UserRepoViewModel(get(), get()) }
        single { UserRepoDetailViewModel() }
    }
}