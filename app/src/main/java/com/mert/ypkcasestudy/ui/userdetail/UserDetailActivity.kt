package com.mert.ypkcasestudy.ui.userdetail

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mert.ypkcasestudy.*
import com.mert.ypkcasestudy.ui.UserDetailViewModelFactory
import com.mert.ypkcasestudy.databinding.*
import com.mert.ypkcasestudy.viewmodel.UserDetailViewModel

/**
 * Created by Mert Tuncbilek on 2019-12-03.
 */
class UserDetailActivity : BaseActivity() {

    private lateinit var userDetailViewModel: UserDetailViewModel

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)

        val username = findViewById<EditText>(R.id.username)
        val name = findViewById<EditText>(R.id.name)
        val surname = findViewById<EditText>(R.id.surname)
        val update = findViewById<Button>(R.id.update)
        val loading = findViewById<ProgressBar>(R.id.loading)

        userDetailViewModel = ViewModelProviders.of(this, UserDetailViewModelFactory())
            .get(UserDetailViewModel::class.java)

        userDetailViewModel.userDataValidationState.observe(this@UserDetailActivity, Observer {
            val loginState = it ?: return@Observer

            update.isEnabled = loginState.isDataValid

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

        userDetailViewModel.userDetailResult.observe(this@UserDetailActivity, Observer {
            val userDetailResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (userDetailResult.error != null) {
                super.showMessage(userDetailResult.error)
            }
            userDetailResult.data?.let {
                binding.userdetailmodel = it
            }

        })

        userDetailViewModel.updateUserResult.observe(this@UserDetailActivity, Observer {
            val updateUserResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (updateUserResult.error != null) {
                super.showMessage(updateUserResult.error)
            }
            if (updateUserResult.success) {
                super.showMessage(R.string.user_update_success)
                userDetailViewModel.getUser(username.text.toString())
            }
        })

        update.setOnClickListener {
            hideKeyboard()
            loading.visibility = View.VISIBLE
            userDetailViewModel.updateUser(username.text.toString(), name.text.toString(), surname.text.toString())
        }

        username.afterTextChanged {
            userDetailViewModel.loginDataChanged(username = username.text.toString())
        }

        name.afterTextChanged {
            userDetailViewModel.loginDataChanged(name = name.text.toString())
        }

        surname.afterTextChanged {
            userDetailViewModel.loginDataChanged(surname = surname.text.toString())
        }

        getUserDetail()
    }

    private fun getUserDetail() {
        val usernameFromIntent = intent.getStringExtra(Constants.Username.name)
        userDetailViewModel.getUser(usernameFromIntent)
    }

}