package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserInfoActivity : AppCompatActivity() {

    private lateinit var firstNameView: TextView
    private lateinit var lastNameView: TextView
    private lateinit var userNameView: TextView
    private lateinit var updateBtn: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        firstNameView = findViewById(R.id.firstName_textView)
        lastNameView = findViewById(R.id.lastName_textView)
        userNameView = findViewById(R.id.userName_textView)
        updateBtn = findViewById(R.id.update_btn)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth.currentUser!!.uid)

        firebaseDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    val firstName = snapshot.child("firstName").value as String
                    val lastName = snapshot.child("lastName").value as String
                    val userName = snapshot.child("userName").value as String

                    firstNameView.text = firstName
                    lastNameView.text = lastName
                    userNameView.text = userName
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateInformation(view: View) {

        startActivity(Intent(this@UserInfoActivity, ProfileActivity::class.java))
    }
}