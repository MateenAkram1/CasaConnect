package com.example.casaconnect.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.casaconnect.R
import com.example.casaconnect.databinding.ActivityPostAdBinding

class post_ad : AppCompatActivity() {
    private var binding: ActivityPostAdBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding!!.root)

    }
}