package com.najmulsdeveloper.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class massageAdapter(val context: Context, val massageList : ArrayList<Massage>) : RecyclerView.Adapter<ViewHolder>() {



    val ITEM_RECIEVE = 1;
    val ITEM_SENT = 2;



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 1){
            //inflate recieved
            val view : View = LayoutInflater.from(context).inflate(R.layout.recieved_massage, parent, false)
            return sentViewholder(view)


        }else{
            //inflate send
            val view : View = LayoutInflater.from(context).inflate(R.layout.send_massage, parent, false)
            return recievedViewholder(view)
        }
    }

    override fun getItemCount(): Int {

        return massageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMassage = massageList[position]


        if(holder.javaClass == sentViewholder::class.java){
            // Do the stuff for sent viewHolder



            val viweHolder = holder as sentViewholder
            holder.sentMassage.text = currentMassage.massage


            holder.sentMassage.text

        }
        else{
            //do the stuff for recieved viewholder

            val  viewHolder = holder as recievedViewholder
            holder.recievedMassage.text = currentMassage.massage

        }


    }


    override fun getItemViewType(position: Int): Int {

        val currentmassage = massageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentmassage.senderId)) {
            return ITEM_SENT
        }else{
            return ITEM_RECIEVE
        }
    }




    class sentViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){


        val sentMassage = itemView.findViewById<TextView>(R.id.txtSendMassage)

    }


    class recievedViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val recievedMassage = itemView.findViewById<TextView>(R.id.txtRecievedMassage)
    }



}