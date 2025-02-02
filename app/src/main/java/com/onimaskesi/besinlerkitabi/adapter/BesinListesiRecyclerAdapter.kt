package com.onimaskesi.besinlerkitabi.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.onimaskesi.besinlerkitabi.R
import com.onimaskesi.besinlerkitabi.databinding.BesinRecyclerRowBinding
import com.onimaskesi.besinlerkitabi.model.Besin
import com.onimaskesi.besinlerkitabi.util.gorselIndir
import com.onimaskesi.besinlerkitabi.util.placeholderYap
import com.onimaskesi.besinlerkitabi.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.besin_recycler_row.view.*
import kotlinx.android.synthetic.main.fragment_besin_detayi.view.*
import kotlinx.android.synthetic.main.fragment_besin_listesi.view.*

class BesinListesiRecyclerAdapter(val besinListesi : ArrayList<Besin>): RecyclerView.Adapter<BesinListesiRecyclerAdapter.BesinHolder>(), BesinClickListener {

    class BesinHolder(var view : BesinRecyclerRowBinding): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinHolder {

        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.besin_recycler_row,parent, false)
        val view = DataBindingUtil.inflate<BesinRecyclerRowBinding>(inflater, R.layout.besin_recycler_row,parent,false)
        return BesinHolder(view)

    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    override fun onBindViewHolder(holder: BesinHolder, position: Int) {

        holder.view.besin = besinListesi[position]
        holder.view.listener = this
        
        /*
        val besin = besinListesi.get(position)
        holder.itemView.besinIsmiTextView.text = besin.besinIsim
        holder.itemView.besinKalorisiTextView.text = besin.besinKalori

        val besinImageURL = besin.besinGorsel

        holder.itemView.besinImageView.gorselIndir(besinImageURL, placeholderYap(holder.itemView.context))


        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besin.uuid)
            Navigation.findNavController(it).navigate(action)
        }
         */
    }

    fun besinListesiGuncelle(yeniBesinListesi : List<Besin>){

        besinListesi.clear()
        besinListesi.addAll(ArrayList(yeniBesinListesi))
        notifyDataSetChanged()

    }

    override fun besinTiklandi(view: View) {
        val uuid = view.besinId.text.toString().toInt()
        val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(uuid)
        Navigation.findNavController(view).navigate(action)
    }

}