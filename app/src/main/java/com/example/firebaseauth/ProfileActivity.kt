package com.example.firebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var userName: EditText
    private lateinit var updateButton: Button
    //firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name)
        userName = findViewById(R.id.user_name)
        updateButton = findViewById(R.id.update_button)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth.currentUser!!.uid)

        updateButton.setOnClickListener {

            saveUserProfile()
        }
    }

    private fun saveUserProfile(){

        val userFirstName = firstName.text.toString().trim()
        val userLastName = lastName.text.toString().trim()
        val userNameText = userName.text.toString().trim()

        if (TextUtils.isEmpty(userFirstName) && TextUtils.isEmpty(userLastName) && TextUtils.isEmpty(userNameText)){

            Toast.makeText(applicationContext, "This Field Cannot Be Empty", Toast.LENGTH_SHORT).show()
        }

        else{

            val userInfo = HashMap<String, Any>()
            userInfo.put("firstName", userFirstName)
            userInfo.put("lastName", userLastName)
            userInfo.put("userName", userNameText)

            firebaseDatabase.updateChildren(userInfo).addOnCompleteListener(object : OnCompleteListener<Void>{
                override fun onComplete(task: Task<Void>) {

                    if (task.isSuccessful){

                        Toast.makeText(applicationContext, "Your Information Was Updated Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val error = task.exception?.message
                        Toast.makeText(applicationContext, "Error! " + error, Toast.LENGTH_SHORT).show()
                    }
                }


            })
        }
    }
}