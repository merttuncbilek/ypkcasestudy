package com.mert.ypkcasestudy.data.model

import com.mert.ypkcasestudy.data.Result
import com.mert.ypkcasestudy.data.datasource.UserDataSource
import com.mert.ypkcasestudy.data.datasource.UserDataSourceListener
import com.mert.ypkcasestudy.data.entity.RegisterUserEntity
import com.mert.ypkcasestudy.data.entity.UserDetailEntity

/**
 * Created by Mert Tuncbilek on 2019-12-03.
 */
interface UserModelListener {
    fun userDetailResultReceived(result: Result<UserDetailEntity>)
    fun updateUserResultReceived(result: Result<Boolean>)
}

class UserModel(val dataSource: UserDataSource):
    UserDataSourceListener {

    private var listener: UserModelListener? = null

    fun getUser(username: String, listener: UserModelListener) {
        this.listener = listener
        dataSource.getUserDetail(username, this)
    }

    fun updateUser(username: String, name: String, surname: String, listener: UserModelListener) {
        this.listener = listener
        val entity = RegisterUserEntity(name, surname, username)
        dataSource.updateUser(entity, this)

    }

    override fun userDetailResultReceived(result: Result<UserDetailEntity>) {
        if (result is Result.Success) {
            this.listener?.userDetailResultReceived(result)
        }
    }

    override fun updateUserResultReceived(result: Result<Boolean>) {
        if (result is Result.Success) {
            this.listener?.updateUserResultReceived(result)
        }
    }
}