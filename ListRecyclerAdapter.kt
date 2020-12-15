package com.interview.mywallst_assignment.ui

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.interview.mywallst_assignment.R
import com.interview.mywallst_assignment.data_payload.MeteorsApiResponse
import com.interview.mywallst_assignment.data_payload.MeteorsApiResponseItem
import com.interview.mywallst_assignment.inflate
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat

class ListRecyclerAdapter(private val myList: ArrayList<MeteorsApiResponseItem?>) :
    RecyclerView.Adapter<ListRecyclerAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ListRecyclerAdapter.MyHolder {

        val inflatedView = parent.inflate(R.layout.list_item, false)
        return MyHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ListRecyclerAdapter.MyHolder, position: Int) {

        val detailItem = myList[position]
        val name = detailItem?.name ?: "Empty"
        val mass = detailItem?.mass ?: "Empty"
        val lati = detailItem?.reclat ?: "0.0000"
        val longi = detailItem?.reclong ?: "0.0000"
        val year = detailItem?.year ?: " Empty"
        val recClass = detailItem?.recclass ?: " Empty"
        val context = holder.itemView.context
        holder.bindDetails(context, name, lati, longi, mass, year, recClass)
    }

    override fun getItemCount(): Int = myList.size

    class MyHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var details: MeteorsApiResponse? = null

        fun bindDetails(context: Context, name: String, lati: String, longi: String,
            mass: String,
            fullDate: String,
            recClass: String)
        {
            this.details = details
            setUIElements(view, name, mass, recClass, fullDate, context)

            view.item_lin_lay.setOnClickListener {
                val intent = Intent(context, MapsActivity::class.java)
                intent.putExtra("Latitude", lati)
                intent.putExtra("Longitude", longi)
                intent.putExtra("Name", name)
                intent.putExtra("RecClass", recClass)
                context.startActivity(intent)
            }
        }

        // Method to set UI Elements
        private fun setUIElements(view: View, name: String, mass: String,
                                  recClass: String, fullDate: String, context: Context) {

            val formattedDate = formatFullDate(fullDate, context)
            view.year_tv.text = "date of fall:\n$formattedDate"
            view.name_tv.text = name
            view.mass_tv.text = "$mass gms"
            view.class_tv.text = "class:\n $recClass"
        }

        //Method to format full date for rich User Experience
        private fun formatFullDate(year: String, context: Context): String{
            val parser = SimpleDateFormat(context.getString(R.string.format_of_response_year))
            val formatter = SimpleDateFormat(context.getString(R.string.full_date_format))

            return formatter.format(parser.parse(year))
        }
    }


}