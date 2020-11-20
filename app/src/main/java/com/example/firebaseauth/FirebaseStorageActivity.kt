package com.example.firebaseauth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*

class FirebaseStorageActivity : AppCompatActivity() {

    private lateinit var imgView: ImageView
    private lateinit var uploadBtn: Button
    private lateinit var imageUri: Uri
    private lateinit var storage: FirebaseStorage
    private lateinit var imageReference: StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_storage)

        imgView = findViewById(R.id.imageView)
        uploadBtn = findViewById(R.id.upload_img_btn)

        storage = FirebaseStorage.getInstance()
        imageReference = storage.reference.child("Images")

        imgView.setOnClickListener {

            chooseImg()
        }

        uploadBtn.setOnClickListener {

            addImageToStorage()
        }
    }

    private fun chooseImg() {
        val gallery = Intent()
        gallery.type = "image/*"
        gallery.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(gallery, 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null){
            imageUri = data.data!!


           try {
               val image = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                imgView.setImageBitmap(image)
            }
            catch (error: IOException){

                Toast.makeText(applicationContext, "Error " + error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addImageToStorage() {

        if (imageUri != null){
            val reference = imageReference.child(UUID.randomUUID().toString())
            reference.putFile(imageUri).addOnCompleteListener(object :OnCompleteListener<UploadTask.TaskSnapshot>{
                override fun onComplete(task: Task<UploadTask.TaskSnapshot>) {
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show()
                    }

                    else{

                        val error = task.exception?.message
                        Toast.makeText(applicationContext, "Error " + error , Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }
}