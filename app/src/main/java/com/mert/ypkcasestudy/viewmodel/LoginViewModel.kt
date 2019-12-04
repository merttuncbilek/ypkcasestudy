package com.mert.ypkcasestudy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mert.ypkcasestudy.data.model.LoginModel
import com.mert.ypkcasestudy.data.Result

import com.mert.ypkcasestudy.R
import com.mert.ypkcasestudy.data.model.LoginModelListener
import com.mert.ypkcasestudy.isValidEmail
import com.mert.ypkcasestudy.isValidUserField
import com.mert.ypkcasestudy.ui.livedata.UserDataValidationState
import com.mert.ypkcasestudy.ui.livedata.LoginResult
import com.mert.ypkcasestudy.ui.livedata.RegisterResult

/**
 * Created by Mert Tuncbilek on 2019-12-02.
 */

class LoginViewModel(private val loginModel: LoginModel) : ViewModel(),
    LoginModelListener {

    private val _loginForm = MutableLiveData<UserDataValidationState>()
    val userDataValidationState: LiveData<UserDataValidationState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun login(username: String) {
        loginModel.login(username, this)
    }

    fun register(username: String, name: String, surname: String) {
        loginModel.register(username, name, surname, this)
    }

    fun loginDataChanged(username: String? = null, name: String? = null, surname: String? = null) {
        if (username != null && !username.isValidEmail()) {
            _loginForm.value =
                UserDataValidationState(usernameError = R.string.invalid_username)
        } else if (name != null && !name.isValidUserField()) {
            _loginForm.value = UserDataValidationState(nameError = R.string.invalid_name)
        } else if (surname != null && !surname.isValidUserField()) {
            _loginForm.value =
                UserDataValidationState(surnameError = R.string.invalid_surname)
        } else {
            _loginForm.value = UserDataValidationState(isDataValid = true)
        }
    }

    override fun loginResultReceived(result: Result<Boolean>) {
        if (result is Result.Success && result.data) {
            _loginResult.value = LoginResult(success = true)
        } else {
            _loginResult.value =
                LoginResult(success = false, error = R.string.login_failed)
        }
    }

    override fun registerResultReceived(result: Result<Boolean>) {
        if (result is Result.Success && result.data) {
            _registerResult.value = RegisterResult(success = true)
        } else {
            _registerResult.value =
                RegisterResult(
                    success = false,
                    error = R.string.register_failed
                )
        }
    }

    override fun logoutResultReceived() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
