package com.example.casaconnect.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.casaconnect.Domain.PropertyDomain
import com.example.casaconnect.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    var binding: ActivityDetailBinding? = null
    private var `object`: PropertyDomain? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(getLayoutInflater())
        setContentView(binding!!.getRoot())
        this.bundles
        setVariable()
    }

    private fun setVariable() {
        binding!!.backbtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
        val drawableResourseid = getResources().getIdentifier(
            `object`!!.pickpath,
            "drawable",
            this@DetailActivity.getPackageName()
        )
        Glide.with(this@DetailActivity)
            .load(drawableResourseid)
            .into(binding!!.image1)
        binding!!.titletxt.setText(`object`!!.title)
        binding!!.typetxt.setText(`object`!!.type)
        binding!!.addresstxt.setText(`object`!!.address)
        binding!!.descriptiontxt.setText(`object`!!.description)
        binding!!.scoretxt.setText(`object`!!.score.toString())
        binding!!.pricetxt.setText("Price: " + `object`!!.price)
        binding!!.bedtxt.setText(`object`!!.bed.toString() + " Bed")
        binding!!.bathtxt.setText(`object`!!.bath.toString() + " bath")
        if (`object`!!.garage == true) {
            binding!!.garagetxt.setText("Yes")
        } else {
            binding!!.garagetxt.setText("No")
        }
    }

    private val bundles: Unit
        get() {
            `object` = getIntent().getSerializableExtra("object") as PropertyDomain?
        }
}