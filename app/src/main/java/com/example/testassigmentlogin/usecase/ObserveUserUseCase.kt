package com.example.testassigmentlogin.usecase

import com.example.testassigmentlogin.model.domain.User
import com.example.testassigmentlogin.utils.DATASTORE_LOGGED_IN_EMAIL_KEY
import com.example.testassigmentlogin.utils.DatastoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveUserUseCase @Inject constructor(
    private val datastoreManager: DatastoreManager
) {

    operator fun invoke(): Flow<User?> {
        return datastoreManager.observeKeyValue(DATASTORE_LOGGED_IN_EMAIL_KEY).map {
            if (it != null) {
                User(it)
            } else {
                null
            }
        }
    }
}