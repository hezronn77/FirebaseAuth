package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var emailAddress: EditText
    private lateinit var passCode: EditText
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailAddress = findViewById(R.id.user_emailAddress)
        passCode = findViewById(R.id.user_passCode)
        firebaseAuth = FirebaseAuth.getInstance()
        loginButton = findViewById(R.id.login_button)


        loginButton.setOnClickListener {

            loginUser()
        }

    }

    private fun loginUser(){

       var emailText = emailAddress.text.toString().trim()
        var passwordText = passCode.text.toString().trim()

        if (TextUtils.isEmpty(emailText) && TextUtils.isEmpty(passwordText)){

            Toast.makeText(applicationContext, "This Field Cannot Be Empty", Toast.LENGTH_SHORT).show()
        }


        else{

            firebaseAuth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(object: OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {

                    if (task.isSuccessful){
                        Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()

                        val user: FirebaseUser = firebaseAuth.currentUser!!

                        if (user.isEmailVerified){
                            startActivity(Intent(this@LoginActivity, TestActivity::class.java))
                        }

                        else{
                            Toast.makeText(applicationContext, "Please verify Your Email", Toast.LENGTH_SHORT).show()
                        }
                    }

                    else{

                        val error = task.exception?.message

                        Toast.makeText(applicationContext, "Error! " + error, Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

    }

    fun resetPassword(view: View) {

        startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java))
    }


}