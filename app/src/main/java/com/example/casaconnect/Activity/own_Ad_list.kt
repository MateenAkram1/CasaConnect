package com.example.casaconnect.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.casaconnect.Adapter.ListitemsAdatper
import com.example.casaconnect.Domain.PropertyDomain
import com.example.casaconnect.R
import com.example.casaconnect.databinding.ActivityOwnAdListBinding
import com.example.casaconnect.databinding.ActivitySellerBinding

class own_Ad_list : AppCompatActivity() {
    private var binding: ActivityOwnAdListBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOwnAdListBinding.inflate(getLayoutInflater())
        setContentView(binding!!.root)
        initList()
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