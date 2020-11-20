package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun profile(view: View) {
        startActivity(Intent(this@TestActivity, ProfileActivity::class.java))
    }

    fun profileInformation(view: View) {
        startActivity(Intent(this@TestActivity, UserInfoActivity::class.java))
    }

    fun uploadImg(view: View) {
        startActivity(Intent(this@TestActivity, FirebaseStorageActivity::class.java))
    }
}