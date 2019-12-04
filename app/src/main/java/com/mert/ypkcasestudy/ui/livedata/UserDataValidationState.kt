package com.mert.ypkcasestudy.ui.livedata

/**
 * Created by Mert Tuncbilek on 2019-12-02.
 */
data class UserDataValidationState(
    val usernameError: Int? = null,
    val nameError: Int? = null,
    val surnameError: Int? = null,
    val isDataValid: Boolean = false
)
