package com.mert.ypkcasestudy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mert.ypkcasestudy.R
import com.mert.ypkcasestudy.data.Result
import com.mert.ypkcasestudy.data.model.UserModel
import com.mert.ypkcasestudy.data.model.UserModelListener
import com.mert.ypkcasestudy.data.entity.UserDetailEntity
import com.mert.ypkcasestudy.ui.livedata.UserDetailUIObject
import com.mert.ypkcasestudy.isValidEmail
import com.mert.ypkcasestudy.isValidUserField
import com.mert.ypkcasestudy.ui.livedata.UserDataValidationState
import com.mert.ypkcasestudy.ui.livedata.UpdateUserResult
import com.mert.ypkcasestudy.ui.livedata.UserDetailResult

/**
 * Created by Mert Tuncbilek on 2019-12-03.
 */

class UserDetailViewModel(private val userModel: UserModel) : ViewModel(),
    UserModelListener {

    private val _loginForm = MutableLiveData<UserDataValidationState>()
    val userDataValidationState: LiveData<UserDataValidationState> = _loginForm

    private val _userDetailResult = MutableLiveData<UserDetailResult>()
    val userDetailResult: LiveData<UserDetailResult> = _userDetailResult

    private val _updateUserResult = MutableLiveData<UpdateUserResult>()
    val updateUserResult: LiveData<UpdateUserResult> = _updateUserResult

    fun getUser(username: String) {
        userModel.getUser(username, this)
    }

    fun updateUser(username: String, name: String, surname: String) {
        userModel.updateUser(username, name, surname, this)
    }

    fun loginDataChanged(username: String? = null, name: String? = null, surname: String? = null) {
        if (username != null && !username.isValidEmail()) {
            _loginForm.value =
                UserDataValidationState(usernameError = R.string.invalid_username)
        } else if (name != null && !name.isValidUserField()) {
            _loginForm.value = UserDataValidationState(nameError = R.string.invalid_name)
        } else if (surname != null && !surname.isValidUserField()) {
            _loginForm.value =
                UserDataValidationState(nameError = R.string.invalid_surname)
        } else {
            _loginForm.value = UserDataValidationState(isDataValid = true)
        }
    }

    override fun userDetailResultReceived(result: Result<UserDetailEntity>) {
        if (result is Result.Success) {
            _userDetailResult.value = UserDetailResult(
                data = UserDetailUIObject(
                    name = result.data.name!!,
                    surname = result.data.surname!!,
                    email = result.data.email!!
                )
            )
        } else {
            _userDetailResult.value =
                UserDetailResult(error = R.string.service_error_user_not_found)
        }
    }

    override fun updateUserResultReceived(result: Result<Boolean>) {
        if (result is Result.Success && result.data) {
            _updateUserResult.value = UpdateUserResult(true)
        } else {
            _updateUserResult.value = UpdateUserResult(
                false,
                error = R.string.service_error_user_not_found
            )
        }
    }

}