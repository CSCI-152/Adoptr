package com.example.adoptrapp

import android.annotation.SuppressLint
import android.app.Activity
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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.create_post_activity.*
import java.text.SimpleDateFormat
import java.util.*

class CreatePostActivity : AppCompatActivity() {

    lateinit var filepath : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_post_activity)

        val browseButton = findViewById<Button>(R.id.browse_button)
        browseButton.setOnClickListener{
            startFileChooser()
        }


        val submitButton = findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener{
            uploadFile()
        }
    }

    private fun startFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Choose an image"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            filepath = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            imageView.setImageBitmap(bitmap)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun uploadFile(){

        val uid = Firebase.auth.currentUser?.uid

        val title = create_post_title.text.toString().trim() //createpost titile
        val description = editText.text.toString().trim() //createpost description
        val animalTag = spinner.selectedItem.toString().trim()
        val ageTag = spinner2.selectedItem.toString().trim()
        val genderTag = spinner3.selectedItem.toString().trim()

        if(title.isNotEmpty() && description.isNotEmpty() && filepath != null){
            val pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()

            val time = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())
            val imgID = uid+time
            var imgURL: String? =""
            var postCreation: PostModel

            //insert image happen below here
            val imageRef: StorageReference = FirebaseStorage.getInstance().reference.child("image/$imgID")
            imageRef.putFile(filepath)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext,"File uploaded", Toast.LENGTH_SHORT).show()
                    imageRef.downloadUrl.addOnSuccessListener {
                        imgURL = it.toString()
                        postCreation = PostModel(uid, title, description, time, imgID, imgURL, animalTag, ageTag, genderTag)

                        pd.dismiss()

                        //insert title and descript of pet happen below here
                        val createPost = FirebaseFirestore.getInstance()

                        createPost.collection("Listings")
                            .add(postCreation)
                            .addOnSuccessListener {
                                Toast.makeText(applicationContext,"Post inserted.", Toast.LENGTH_LONG).show()
                            }
                        finish()
                    }
                }
                .addOnCanceledListener {
                    pd.dismiss()
                    Toast.makeText(applicationContext,"Upload failed", Toast.LENGTH_LONG).show()
                }
        }
        else{
            Toast.makeText(applicationContext,"All fields are require", Toast.LENGTH_LONG).show()
        }
    }
}