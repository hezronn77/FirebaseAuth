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

class MainActivity : AppCompatActivity() {

    private lateinit var signUpBtn: Button
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        signUpBtn = findViewById(R.id.signUp_btn)
        userEmail = findViewById(R.id.user_email)
        userPassword = findViewById(R.id.user_password)
        firebaseAuth = FirebaseAuth.getInstance()


        signUpBtn.setOnClickListener {

            registerNewUsers()
        }
    }

    private fun registerNewUsers(){
        var emailText = userEmail.text.toString().trim()
        var passwordText = userPassword.text.toString().trim()

        if (TextUtils.isEmpty(emailText) && TextUtils.isEmpty(passwordText)){
            Toast.makeText(applicationContext, "This field cannot be Empty", Toast.LENGTH_SHORT).show()
        }

        else{

            firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(object:OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {

                    if (task.isSuccessful){

                        Toast.makeText(applicationContext, "Account created Successfully", Toast.LENGTH_SHORT).show()

                        val user: FirebaseUser = firebaseAuth.currentUser!!
                        user.sendEmailVerification().addOnCompleteListener(object : OnCompleteListener<Void>{
                            override fun onComplete(task: Task<Void>) {
                                if (task.isSuccessful){
                                    Toast.makeText(applicationContext, "Please check your Email for Verification Code", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                }
                                else{
                                    val error = task.exception?.message

                                    Toast.makeText(applicationContext, "Error! " + error, Toast.LENGTH_SHORT).show()
                                }
                            }

                        })
                    }

                    else{

                        val error = task.exception?.message
                        Toast.makeText(applicationContext, "Error! " + error, Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    fun login(view: View) {
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
    }
}