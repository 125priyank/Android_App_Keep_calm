package com.example.temp1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*

class Registration : AppCompatActivity() {

    private var RegUsername: EditText? = null
    private var RegEmail: EditText? = null
    private var RegPassword: EditText? = null
    private var Register: Button? = null
    private var UserLogin: TextView? = null
    private var mAuth: FirebaseAuth? = null
    //private FirebaseAuth firebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar!!.hide()
        setupUIViews()
        mAuth = FirebaseAuth.getInstance()

        //firebaseAuth = FirebaseAuth.getInstance();

        Register!!.setOnClickListener {
            registration_loading.visibility = View.VISIBLE
            Register!!.isClickable = false
            createAccount(RegEmail!!.text.toString(), RegPassword!!.text.toString())
        }

        UserLogin!!.setOnClickListener { startActivity(Intent(this@Registration, login::class.java)) }

    }

    private fun setupUIViews() {
        RegUsername = findViewById(R.id.etRegName) as EditText
        RegEmail = findViewById(R.id.etRegEmail) as EditText
        RegPassword = findViewById(R.id.etRegPassword) as EditText
        Register = findViewById(R.id.btnRegister) as Button
        UserLogin = findViewById(R.id.tvUserLogin) as TextView
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")

        // Ye Validate vala function bna dena which checks for empty user and password fields
        if (!validateForm()) {
            registration_loading.visibility = View.GONE
            return
        }


        // [START create_user_with_email]
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = mAuth!!.currentUser
                    registration_loading.visibility = View.GONE
                    Toast.makeText(this@Registration, "Registered Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Registration, MainActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    registration_loading.visibility = View.GONE
                    Register!!.isClickable = true
                    Toast.makeText(this@Registration, "Registration Failure", Toast.LENGTH_SHORT).show()
                }
            }

        // [END create_user_with_email]
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = RegEmail!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            RegEmail!!.error = "Required."
            valid = false
        } else {
            RegEmail!!.error = null
        }

        val password = RegPassword!!.text.toString()
        if (TextUtils.isEmpty(password) or  (password.length < 6)) {
            RegPassword!!.error = "Required."
            valid = false
        } else {
            RegPassword!!.error = null
        }

        return valid
    }


    companion object {
        private val TAG = "Terminator_registration"
    }

}
