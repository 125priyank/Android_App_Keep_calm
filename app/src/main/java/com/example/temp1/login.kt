package com.example.temp1

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*

class login : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private val TAG = "Terminator_login"
    private var Username: EditText? = null
    private var Password: EditText? = null
    private var Login: Button? = null
    private var Info: TextView? = null
    private var Signup: TextView? = null
    private var skip: TextView? = null
    private var ForgotPassword: TextView? = null
    private val counter = 5
    private var googleSignIn: SignInButton? = null
    private val TAG2 = "GoogleActivity"
    private val RC_SIGN_IN = 9001
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "Login"

        Username = findViewById < View >(R.id.etUsername) as EditText
        Password = findViewById<View>(R.id.etRegPassword) as EditText
        Login = findViewById<View>(R.id.btnLogin) as Button
        Info = findViewById<View>(R.id.tvInfo) as TextView
        Signup = findViewById<View>(R.id.tvRegister) as TextView
        skip = findViewById<View>(R.id.tvLoginSkip) as TextView
        ForgotPassword = findViewById<View>(R.id.tvForgotPassword) as TextView

        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("164518664571-4vg78dvn0aqf2j5uall3r7duia8asjv6.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        tvForgotPassword.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@login, MainActivity::class.java)
            startActivity(intent)
        })

        tvInfo.text = "  "

        tvLoginSkip.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@login,
                    MainActivity::class.java
                )
            )
        })

        btnLogin.setOnClickListener(View.OnClickListener {
            registration_loading_login.visibility = View.VISIBLE
            btnLogin!!.isClickable = false
            signIn(
                Username!!.getText().toString(),
                Password!!.getText().toString()
            )
        })

        tvRegister.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@login,
                    Registration::class.java
                )
            )
        })


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth?.getCurrentUser()
        if (currentUser != null)
        {
            Log.i(TAG, "Already login")
            val a = currentUser.displayName
            Log.i(TAG, "$a")
            Toast.makeText(this@login, "Logged in succesfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")

        if (!validateForm()) {
            registration_loading_login.visibility = View.GONE
            return
        }


        // [START sign_in_with_email]
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val user = mAuth?.getCurrentUser()
                registration_loading_login.visibility = View.GONE
                Toast.makeText(this@login, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@login, MainActivity::class.java))
            } else {
                registration_loading_login.visibility = View.GONE
                btnLogin!!.isClickable = true
                Toast.makeText(this@login, "Wrong Username or Password", Toast.LENGTH_SHORT).show()
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
            }
        }
        // [END sign_in_with_email]
    }

    private fun signOut() {
        mAuth?.signOut()
    }
    private fun validateForm(): Boolean {
        var valid = true

        val email = Username?.getText().toString()
        if (TextUtils.isEmpty(email)) {
            Username?.setError("Required.")
            Toast.makeText(this@login, "Username Error", Toast.LENGTH_SHORT).show()
            valid = false
        } else {
            Username?.setError(null)
        }

        val password = Password?.getText().toString()
        if (TextUtils.isEmpty(password) or  (password.length < 6)) {
            Password?.setError("Required.")
            Toast.makeText(this@login, "Should be six char long", Toast.LENGTH_SHORT).show()
            valid = false
        } else {
            Password?.setError(null)
        }

        return valid
    }


}