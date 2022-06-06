package com.example.testassigmentlogin.usecase

import com.example.testassigmentlogin.utils.DATASTORE_LOGGED_IN_EMAIL_KEY
import com.example.testassigmentlogin.utils.DatastoreManager
import timber.log.Timber
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val datastoreManager: DatastoreManager
) {


    suspend operator fun invoke() {
        Timber.d("Loggin out")
        datastoreManager.removeFromDatastore(DATASTORE_LOGGED_IN_EMAIL_KEY)
    }
}