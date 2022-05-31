package com.example.testassigmentlogin.usecase

import com.example.testassigmentlogin.utils.DATASTORE_LOGGED_IN_EMAIL_KEY
import com.example.testassigmentlogin.utils.DatastoreManager
import javax.inject.Inject

class AddLoggedInEmailToDatastoreUseCase @Inject constructor(
    private val datastoreManager: DatastoreManager
) {
    suspend operator fun invoke(email: String) {
        datastoreManager.addToDatastore(DATASTORE_LOGGED_IN_EMAIL_KEY, email)
    }

}