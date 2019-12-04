package com.mert.ypkcasestudy

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Mert Tuncbilek on 2019-12-03.
 */
open class BaseActivity: AppCompatActivity() {

    fun showMessage(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

}