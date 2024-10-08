package com.hamzaazman.passwordgenerator.data.repository

import com.hamzaazman.passwordgenerator.data.source.local.MainRoomDB
import com.hamzaazman.passwordgenerator.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainRoomDB: MainRoomDB,
) : MainRepository