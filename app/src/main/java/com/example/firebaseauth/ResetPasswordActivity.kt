package com.example.firebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var userEmail: EditText
    private lateinit var resetButton: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        userEmail = findViewById(R.id.emailAddressField)
        resetButton = findViewById(R.id.reset_button)
        firebaseAuth = FirebaseAuth.getInstance()

        resetButton.setOnClickListener {

            resetPassword()
        }

    }

    private fun resetPassword(){

        var emailText = userEmail.text.toString().trim()

        if (TextUtils.isEmpty(emailText)){
            Toast.makeText(applicationContext, "This Field Cannot Be Empty", Toast.LENGTH_SHORT).show()
        }

        else {

            firebaseAuth.sendPasswordResetEmail(emailText).addOnCompleteListener(object : OnCompleteListener<Void>{

                override fun onComplete(task: Task<Void>) {

                    if (task.isSuccessful){
                        Toast.makeText(applicationContext, "Please check your email for Reset Code", Toast.LENGTH_SHORT).show()

                    }
                    else{

                        val error = task.exception?.message
                        Toast.makeText(applicationContext, "Error! " + error, Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }
}