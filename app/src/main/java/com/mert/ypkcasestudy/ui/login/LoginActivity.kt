package com.mert.ypkcasestudy.ui.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.*
import com.mert.ypkcasestudy.*
import com.mert.ypkcasestudy.ui.LoginViewModelFactory

import com.mert.ypkcasestudy.ui.userdetail.UserDetailActivity
import com.mert.ypkcasestudy.viewmodel.LoginViewModel

/**
 * Created by Mert Tuncbilek on 2019-12-02.
 */
class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel

    private var isRegisterModeActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val name = findViewById<EditText>(R.id.name)
        val surname = findViewById<EditText>(R.id.surname)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val register = findViewById<TextView>(R.id.register)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.userDataValidationState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }

            if (loginState.nameError != null) {
                name.error = getString(loginState.nameError)
            }

            if (loginState.surnameError != null) {
                surname.error = getString(loginState.surnameError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                super.showMessage(loginResult.error)
            }
            if (loginResult.success) {
                val intent = Intent(this, UserDetailActivity::class.java)
                intent.putExtra(Constants.Username.name, username.text.toString())
                startActivity(intent)
            }
        })

        loginViewModel.registerResult.observe(this@LoginActivity, Observer {
            val registerResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (registerResult.error != null) {
                super.showMessage(registerResult.error)
            }
            if (registerResult.success) {
                val intent = Intent(this, UserDetailActivity::class.java)
                intent.putExtra(Constants.Username.name, username.text.toString())
                startActivity(intent)
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(username = username.text.toString())
        }

        name.afterTextChanged {
            loginViewModel.loginDataChanged(name = name.text.toString())
        }

        surname.afterTextChanged {
            loginViewModel.loginDataChanged(surname = surname.text.toString())
        }

        login.setOnClickListener {
            hideKeyboard()
            loading.visibility = View.VISIBLE
            if (isRegisterModeActive) {
                loginViewModel.register(username.text.toString(), name.text.toString(), surname.text.toString())
            } else {
                loginViewModel.login(username.text.toString())
            }

        }

        register.setOnClickListener {
            name.visibility = View.VISIBLE
            surname.visibility = View.VISIBLE
            login.text = getString(R.string.action_register)
            register.visibility = View.INVISIBLE
            isRegisterModeActive = true
        }

    }

}

