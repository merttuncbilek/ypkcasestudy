package com.mert.ypkcasestudy.data.datasource

import com.mert.ypkcasestudy.convertToDBKey
import com.mert.ypkcasestudy.data.FirebaseWrapper
import com.mert.ypkcasestudy.data.Result
import com.mert.ypkcasestudy.data.entity.RegisterUserEntity
import com.mert.ypkcasestudy.data.entity.UserDetailEntity

/**
 * Created by Mert Tuncbilek on 2019-12-03.
 */
interface UserDataSourceListener {
    fun userDetailResultReceived(result: Result<UserDetailEntity>)
    fun updateUserResultReceived(result: Result<Boolean>)
}

class UserDataSource {

    fun getUserDetail(username: String, listener: UserDataSourceListener) {

        FirebaseWrapper.getFromDatabase<UserDetailEntity>(username.convertToDBKey()) { success, data, message ->
            if (success) {
                listener.userDetailResultReceived(Result.Success(data!!))
            } else {
                listener.userDetailResultReceived(Result.Error(Exception(message)))
            }
        }

    }

    fun updateUser(entity: RegisterUserEntity, listener: UserDataSourceListener) {

        FirebaseWrapper.writeToDatabase(
            entity,
            entity.email!!.convertToDBKey()
        ) { success, message ->
            if (success) {
                listener.updateUserResultReceived(Result.Success(true))
            } else {
                listener.updateUserResultReceived(Result.Error(Exception(message)))
            }
        }

    }



}