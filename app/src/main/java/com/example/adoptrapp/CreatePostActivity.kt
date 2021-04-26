package com.example.adoptrapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CreatePostActivity : AppCompatActivity() {
    lateinit var filepath : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_post_activity)

        val browse_button = findViewById<Button>(R.id.browse_button)
        browse_button.setOnClickListener{
            startFileChooser()
        }

        val sumit_button = findViewById<Button>(R.id.sumit_button)
        browse_button.setOnClickListener{
            uploadFile()
        }
    }

    private fun startFileChooser() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose an image"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 111 && resultCode == RESULT_OK && data != null){
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
        }
    }

    private fun uploadFile(){

        val db = FirebaseFirestore.getInstance()
        val uid = Firebase.auth.currentUser?.uid

        var currentUserName: String? = ""
        // Access the document assigned to the current user and allows them to grab the role
        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    // converts the grabbed docutment to ClassUser object class and takes the role field
                    currentUserName = document.toObject<ClassUser>()!!.fullName
                }
        }

        if(filepath != null){
            var pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()

            var imageRef: StorageReference = FirebaseStorage.getInstance().reference.child("image/image.jpg")
            imageRef.putFile(filepath)
                .addOnSuccessListener {
                    pd.dismiss()
                    Toast.makeText(applicationContext,"File uploaded", Toast.LENGTH_LONG).show()
                }
                .addOnCanceledListener {
                    pd.dismiss()
                    Toast.makeText(applicationContext,"Upload failed", Toast.LENGTH_LONG).show()
                }
        }
    }
}