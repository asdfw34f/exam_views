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

class Adapter(private val dataList: List<Entity.Record>, private val userId:Int, private val db: DataBase.AppDatabase):
    RecyclerView.Adapter<Adapter.MyViewHolder>() {
        class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

            val textview1:TextView = itemView.findViewById(R.id.tvStart_EndCityCode1)
            val textview2:TextView = itemView.findViewById(R.id.tvStart_EndCityName1)
            val textview3:TextView = itemView.findViewById(R.id.tvStartDate1)
            val textview4:TextView = itemView.findViewById(R.id.tvEndDate1)
            val textview5:TextView = itemView.findViewById(R.id.tvPrice1)
            val button:Button = itemView.findViewById(R.id.btFav11)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            val currentItem = dataList[position]
            val user = userId

            val fav1Result = db.favDao().checkFav(currentItem.idRecord, user)
            setFavButtonColor(holder.button, fav1Result)

            holder.textview1.text = "${currentItem.startCityCode} + ${currentItem.endCityCode}"
            holder.textview2.text = "${currentItem.startCity} + ${currentItem.endCity}"
            holder.textview3.text = "Start date ${currentItem.startDate}"
            holder.textview4.text = "End date ${currentItem.endDate}"
            holder.textview5.text = "Price ${currentItem.price} RUB"


            holder.button.setOnClickListener{
                toggleFav(
                    holder.button,
                    currentItem.idRecord,
                    userId,
                    fav1Result
                )
            }

            holder.button.setOnClickListener{
                toggleFav(
                    holder.button,
                    currentItem.idRecord,
                    userId,
                    fav1Result
                )
            }
        }
    }

    private fun setFavButtonColor(button: Button, result: Entity.Fav?){
        if (result == null){
            button.setBackgroundColor(Color.GRAY)
            button.text = "Save"
        }else{
            button.setBackgroundColor(Color.RED)
            button.text = "Already saved"
        }
    }

    private fun toggleFav(button: Button, idRecord: Int, idUser:Int, result: Entity.Fav?){
        CoroutineScope(Dispatchers.IO).launch {
            if(result==null){
                db.favDao().insertFav(Entity.Fav(idRecord = idRecord, idUser = idUser))
                setFavButtonColor(button, db.favDao().checkFav(idRecord, idUser))
            }else{
                db.favDao().deleteFav(result)
                setFavButtonColor(button, null)
            }
        }
    }
}