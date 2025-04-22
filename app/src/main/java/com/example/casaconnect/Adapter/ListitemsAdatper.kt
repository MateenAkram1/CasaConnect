package com.example.casaconnect.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.casaconnect.Activity.DetailActivity
import com.example.casaconnect.Adapter.ListitemsAdatper.Viewholder
import com.example.casaconnect.Domain.PropertyDomain
import com.example.casaconnect.databinding.ViewholderItemBinding

class ListitemsAdatper(var items: ArrayList<PropertyDomain?>) :
    RecyclerView.Adapter<Viewholder?>() {
    var context: Context? = null
    var binding: ViewholderItemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        binding =
            ViewholderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        context = parent.getContext()
        return Viewholder(binding!!)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        binding!!.titletxt.setText(items.get(position)!!.title)
        binding!!.pricetxt.setText("$" + items.get(position)!!.price)
        binding!!.sizetxt.setText(items.get(position)!!.size)
        if (items.get(position)!!.garage == true) {
            binding!!.garagetxt.setText("Car Garage")
        } else {
            binding!!.garagetxt.setText("No Garage")
        }
        binding!!.addresstxt.setText(items.get(position)!!.address)
        binding!!.bathtxt.setText(items.get(position)!!.bath.toString() + " Bath")
        binding!!.bedtxt.setText(items.get(position)!!.bed.toString() + " Bed")
        val drawableresid = holder.itemView.getResources().getIdentifier(
            items.get(position)!!.pickpath,
            "drawable",
            holder.itemView.getContext().getPackageName()
        )
        Glide.with(context!!).load(drawableresid).into(binding!!.pic)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("object", items.get(position))
                context!!.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)

        constructor(binding: ViewholderItemBinding) : super(binding.getRoot())
    }
}
