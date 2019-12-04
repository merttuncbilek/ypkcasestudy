package com.mert.ypkcasestudy.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by Mert Tuncbilek on 2019-12-02.
 */

object FirebaseWrapper {

    var database = FirebaseDatabase.getInstance().reference

    fun writeToDatabase(entity: Any, key: String, response: (Boolean, String?) -> Unit ) {
        database.child("users").child(key).setValue(entity
        ) { error, _ ->
            if (error != null) {
                response(false, error!!.message)
            } else {
                response(true, null)
            }
        }
    }

    fun existInDatabase(userId: String, response: (Boolean, String?) -> Unit ) {

        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                return response(false, error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                return response(snapshot.exists(), null)
            }

        }

        database.child("users").child(userId).addListenerForSingleValueEvent(listener)
    }

    inline fun <reified Type> getFromDatabase(userId: String, crossinline response: (Boolean, Type?, String?) -> Unit ) {

        val listener = object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                response(false, null, error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                response(true, snapshot.getValue(Type::class.java), null)
            }

        }

        database.child("users").child(userId).addListenerForSingleValueEvent(listener)

    }
}