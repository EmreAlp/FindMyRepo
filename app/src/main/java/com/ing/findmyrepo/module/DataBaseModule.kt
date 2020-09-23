package com.ing.findmyrepo.module

import org.koin.dsl.module

class DataBaseModule {

    val dataBaseModule = module {

        /*
        single {
            Room.databaseBuilder(
                androidApplication(),
                CharacterDatabase::class.java,
                androidApplication().baseContext.getString(R.string.app_name)
            ).build()
        }

        single {
            get<CharacterDatabase>().getNoteDao()
        }
         */
    }
}