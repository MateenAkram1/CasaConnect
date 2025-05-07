package com.example.casaconnect.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.casaconnect.Domain.NotificationModel
import com.example.casaconnect.Model.AdModel
import com.example.casaconnect.R
import com.example.casaconnect.databinding.ActivityPostAdBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class post_ad : AppCompatActivity() {
    private lateinit var binding: ActivityPostAdBinding
    private var imageUri: Uri? = null
    private var imageUrl: String? = null
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPostAdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setarr()
        setarr1()
        setarr2()
        binding.imgButton.setOnClickListener {
            pickImage()
        }

        binding.doneButton.setOnClickListener {
            if (imageUri == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            } else {
                uploadImageToImgBB()
            }
        }
    }

    private fun setarr() {
        val typeSpinner = findViewById<Spinner>(R.id.typeltxt)

        val types = resources.getStringArray(R.array.property_types)
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            types
        )
        typeSpinner.adapter = spinnerAdapter

    }

    private fun setarr1() {
        val typeSpinner = findViewById<Spinner>(R.id.gragetxt)

        val types = resources.getStringArray(R.array.garageYN)
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            types
        )
        typeSpinner.adapter = spinnerAdapter

    }

    private fun setarr2() {
        val typeSpinner = findViewById<Spinner>(R.id.size_txt2)

        val types = resources.getStringArray(R.array.size_types)
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            types
        )
        typeSpinner.adapter = spinnerAdapter

    }


    private fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imageUri?.let { addImagePreview(it) }
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addImagePreview(uri: Uri) {
        val imageView = ImageView(this)
        imageView.setImageURI(uri)
        val layoutParams = LinearLayout.LayoutParams(300, 300)
        layoutParams.setMargins(16, 16, 16, 16)
        imageView.layoutParams = layoutParams
         //binding.previewImageView.removeAllViews() // Clear any previous previews
         //binding.previewImageView.addView(imageView)
    }

    private fun uploadImageToImgBB() {
        val apiKey = "c00ccfa768659c2bfe6b8d107f4dc5d3"

        imageUri?.let { uri ->
            val inputStream = contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes() ?: return
            val base64Image = android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP)

            val client = OkHttpClient()
            val requestBody = FormBody.Builder()
                .add("key", apiKey)
                .add("image", base64Image)
                .build()

            val request = Request.Builder()
                .url("https://api.imgbb.com/1/upload")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(this@post_ad, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string() ?: ""
                    Log.d("ImgBB", "Raw response: $responseBody")

                    if (!response.isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(this@post_ad, "ImgBB error: $responseBody", Toast.LENGTH_LONG).show()
                        }
                        return
                    }

                    val json = JSONObject(responseBody)
                    imageUrl = json.getJSONObject("data").getString("url")
                    runOnUiThread { saveAdToFirestore() }
                }
            })
        }
    }



    private fun saveAdToFirestore() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        // 1) Grab a new DocumentReference – this reserves a unique ID
        val adsCollection = firestore.collection("ads")
        val newDocRef    = adsCollection.document()
        val newAdId      = newDocRef.id

        // 2) Build your AdModel with that ID
        val ad = AdModel(
            adid        = newAdId,
            title       = binding.titleTxt.text.toString(),
            address     = binding.addText.text.toString(),
            bed         = binding.bedTxt.text.toString(),
            bath        = binding.bathTxt.text.toString(),
            size        = binding.sizeTxt.text.toString() + " " + binding.sizeTxt2.selectedItem.toString(),
            garage      = binding.gragetxt.selectedItem.toString(),
            type        = binding.typeltxt.selectedItem.toString(),
            description = binding.desctxt.text.toString(),
            price       = binding.priceeTxt.text.toString(),
            imageUrls   = imageUrl ?: "",
            userId      = currentUser.uid
        )

        // 3) Write the document at that reference
        newDocRef.set(ad)
            .addOnSuccessListener {
                Toast.makeText(this, "Ad posted with ID $newAdId", Toast.LENGTH_SHORT).show()

                val now = System.currentTimeMillis()
                firestore.collection("users")
                    .whereEqualTo("role", "admin")
                    .get()
                    .addOnSuccessListener { userSnaps ->
                        for (userDoc in userSnaps.documents) {
                            val notifRef = firestore.collection("notifications").document()
                            val notif = NotificationModel(
                                id = notifRef.id,
                                userid = userDoc.id.toString(),
                                message = "New ad posted By: ${ad.userId}\nTitle: ${ad.title}",
                                timestamp = now
                            )
                            notifRef.set(notif)
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("post_ad", "Failed to fetch admins: ${e.message}", e)
                    }

                finish()
            }
            .addOnFailureListener { e ->
                Log.e("post_ad", "Firestore Failed: ${e.message}", e)
                Toast.makeText(this, "Failed to post ad: ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

}