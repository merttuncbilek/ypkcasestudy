package com.mert.ypkcasestudy.data.model

import com.mert.ypkcasestudy.data.datasource.LoginDataSource
import com.mert.ypkcasestudy.data.datasource.LoginDataSourceListener
import com.mert.ypkcasestudy.data.Result
import com.mert.ypkcasestudy.data.entity.RegisterUserEntity

/**
 * Created by Mert Tuncbilek on 2019-12-03.
 */

interface LoginModelListener {
    fun loginResultReceived(result: Result<Boolean>)
    fun logoutResultReceived()
    fun registerResultReceived(result: Result<Boolean>)
}

class LoginModel(val dataSource: LoginDataSource):
    LoginDataSourceListener {

    private var listener: LoginModelListener? = null

    fun logout() {
        dataSource.logout()
    }

    fun login(username: String, listener: LoginModelListener) {
        this.listener = listener
        dataSource.login(username, this)
    }

    fun register(username: String, name: String, surname: String, listener: LoginModelListener) {
        this.listener = listener
        val entity = RegisterUserEntity(name, surname, username)
        dataSource.register(entity, this)
    }

    override fun loginResultReceived(result: Result<Boolean>) {
        if (result is Result.Success) {
            this.listener?.loginResultReceived(result)
        } else {
            this.listener?.loginResultReceived(Result.Success(false))
        }
    }

    override fun registerResultReceived(result: Result<Boolean>) {
        if (result is Result.Success) {
            this.listener?.loginResultReceived(result)
        }
    }

    override fun logoutResultReceived() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
