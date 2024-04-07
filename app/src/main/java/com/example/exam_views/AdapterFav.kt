package com.example.exam_views

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exam_views.room.DataBase
import com.example.exam_views.room.Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdapterFav(private val dataList:List<Entity.Record>, private val userId:Int, private val db:DataBase.AppDatabase):RecyclerView.Adapter<AdapterFav.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView1:TextView = itemView.findViewById(R.id.tvStart_EndCityCode1)
        val textView2:TextView = itemView.findViewById(R.id.tvStart_EndCityName1)
        val textView3:TextView = itemView.findViewById(R.id.tvStartDate1)
        val textView4:TextView = itemView.findViewById(R.id.tvEndDate1)
        val textView5:TextView = itemView.findViewById(R.id.tvPrice1)
        val button: Button = itemView.findViewById(R.id.btFav11)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFav.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterFav.MyViewHolder, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentItem = dataList[position]
            val user = userId
            val fav1Result = db.favDao().checkFav(currentItem.idRecord, user)

            setFavButtonColor(holder.button, fav1Result)
            holder.button.text = "Delete"

            holder.textView1.text = "${currentItem.startCityCode} + ${currentItem.endCityCode}"
            holder.textView2.text = "${currentItem.startCity} + ${currentItem.endCity}"
            holder.textView3.text = "Start date ${currentItem.startDate}"
            holder.textView4.text = "End date${currentItem.endDate}"
            holder.textView5.text = "Price: ${currentItem.price} RUB"
            holder.button.setOnClickListener{
                toggleFav(holder.button, currentItem.idRecord, userId, fav1Result)
            }
        }
    }

    private fun toggleFav(button: Button, idRecord: Int, userId: Int, result: Entity.Fav?) {
        CoroutineScope(Dispatchers.IO).launch {
            if (result == null){
                db.favDao().insertFav(Entity.Fav(idRecord = idRecord, idUser = userId, ))
                setFavButtonColor(button, db.favDao().checkFav(idRecord, userId))
            }else{
                db.favDao().deleteFav(result)
                setFavButtonColor(button, null)
            }
        }
    }

    private fun setFavButtonColor(button: Button, fav1Result: Entity.Fav?) {
        if (fav1Result == null){
            button.setBackgroundColor(Color.GRAY)
            button.text = "Already removed"
        }else{
            button.setBackgroundColor(Color.RED)
            button.text = "Remove"
        }
    }

    override fun getItemCount() = dataList.size
}