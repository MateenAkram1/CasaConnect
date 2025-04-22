package com.example.casaconnect.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.casaconnect.Adapter.ListitemsAdatper
import com.example.casaconnect.Domain.PropertyDomain
import com.example.casaconnect.R
import com.example.casaconnect.databinding.ActivityMainBinding
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(getLayoutInflater())
        setContentView(binding!!.getRoot())
        initList()
        initBottommenu()
        binding!!.sellButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@MainActivity, SellerActivity::class.java))
            }
        })
    }

    private fun initBottommenu() {
        binding!!.bottommenu.setOnItemSelectedListener(object :
            ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(i: Int) {
                if (i == R.id.profile) {
                    startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                }
            }
        })
    }

    private fun initList() {
        val items = ArrayList<PropertyDomain?>()
        items.add(
            PropertyDomain(
                "Appartment",
                "Royal Appartment",
                "Lahore",
                "pic_1",
                "Nair o Nair",
                200,
                3,
                2,
                "5 Kanal",
                true,
                5.0
            )
        )
        items.add(
            PropertyDomain(
                "House",
                "Royal House",
                "Isb",
                "pic_2",
                "Nair o Nair",
                200,
                3,
                2,
                "5 Kanal",
                true,
                2.5
            )
        )
        items.add(
            PropertyDomain(
                "Vila",
                "Royal Vila",
                "Lahore",
                "pic_3",
                "Nair o Nair",
                200,
                3,
                2,
                "5 Kanal",
                true,
                2.5
            )
        )
        items.add(
            PropertyDomain(
                "Appartment",
                "Royal House",
                "Lahore",
                "pic_4",
                "Nair o Nair",
                200,
                3,
                2,
                "5 Kanal",
                true,
                2.5
            )
        )
        binding!!.viewlist.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        binding!!.viewlist.setAdapter(ListitemsAdatper(items))
    }
}