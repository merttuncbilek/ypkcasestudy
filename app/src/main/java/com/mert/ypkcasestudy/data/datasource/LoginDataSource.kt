package com.mert.ypkcasestudy.data.datasource

import com.mert.ypkcasestudy.convertToDBKey
import com.mert.ypkcasestudy.data.FirebaseWrapper
import com.mert.ypkcasestudy.data.Result
import com.mert.ypkcasestudy.data.entity.RegisterUserEntity

/**
 * Created by Mert Tuncbilek on 2019-12-03.
 */
interface LoginDataSourceListener {
    fun loginResultReceived(result: Result<Boolean>)
    fun registerResultReceived(result: Result<Boolean>)
    fun logoutResultReceived()
}

class LoginDataSource {

    fun login(username: String, listener: LoginDataSourceListener) {

        FirebaseWrapper.existInDatabase(username.convertToDBKey()) { success, message ->
            if (success) {
                listener.loginResultReceived(Result.Success(true))
            } else {
                listener.loginResultReceived(Result.Error(Exception(message)))
            }
        }

    }

    fun register(entity: RegisterUserEntity, listener: LoginDataSourceListener) {

        FirebaseWrapper.writeToDatabase(
            entity,
            entity.email!!.convertToDBKey()
        ) { success, message ->
            if (success) {
                listener.registerResultReceived(Result.Success(true))
            } else {
                listener.registerResultReceived(Result.Error(Exception(message)))
            }
        }

    }

    fun logout() {
        // TODO: revoke authentication
    }
}

