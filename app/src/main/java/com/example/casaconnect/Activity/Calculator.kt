package com.example.casaconnect.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.casaconnect.R
import com.example.casaconnect.databinding.ActivityAllUseradBinding
import com.example.casaconnect.databinding.ActivityCalculatorBinding

class Calculator : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCalculatorBinding.inflate(getLayoutInflater())
        setContentView(binding.getRoot())
        binding.CalBtn.setOnClickListener {

            val result = binding.priceTxt.text.toString().toInt() + binding.commTxt.text.toString().toInt() + binding.taxTxt.text.toString().toInt() + binding.DevTxt.text.toString().toInt()
            binding.ResTxt.text = "Result\n" + result.toString()
        }
    }
}