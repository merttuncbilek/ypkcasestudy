package com.mert.ypkcasestudy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mert.ypkcasestudy.data.datasource.LoginDataSource
import com.mert.ypkcasestudy.data.model.LoginModel
import com.mert.ypkcasestudy.data.datasource.UserDataSource
import com.mert.ypkcasestudy.data.model.UserModel
import com.mert.ypkcasestudy.viewmodel.UserDetailViewModel
import com.mert.ypkcasestudy.viewmodel.LoginViewModel

/**
 * Created by Mert Tuncbilek on 2019-12-02.
 */

class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginModel = LoginModel(
                    dataSource = LoginDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class UserDetailViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            return UserDetailViewModel(
                userModel = UserModel(
                    dataSource = UserDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
