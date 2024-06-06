package com.najmulsdeveloper.chatapplication

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {


    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var massageBox : EditText
    private lateinit var sendButton : ImageView
    private lateinit var massageAdapter: massageAdapter
    private lateinit var massageList : ArrayList<Massage>
    private lateinit var dbRef : DatabaseReference


    var senderRoom : String? = null
    var recieverRoom : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        val name = intent.getStringExtra("name")
        val recieverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = recieverUid + senderUid
        recieverRoom = senderUid + recieverUid
        dbRef = FirebaseDatabase.getInstance().getReference()

        supportActionBar?.title = name


        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        massageBox = findViewById(R.id.massageBox)
        sendButton = findViewById(R.id.sendButton)

        massageList = ArrayList()
        massageAdapter = massageAdapter(this, massageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = massageAdapter



        dbRef.child("chats").child(senderRoom!!).child("massages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    massageList.clear()

                    for (postSnapshot in snapshot.children){

                        val Massage = postSnapshot.getValue(Massage::class.java)
                        massageList.add(Massage!!)

                    }

                    massageAdapter.notifyDataSetChanged()


                }

                override fun onCancelled(error: DatabaseError) {


                }

            })


        sendButton.setOnClickListener{
            val Massage = massageBox.text.toString()
            val massageObject = Massage(Massage, senderUid)

            dbRef.child("chats").child(senderRoom!!).child("massages").push()
                .setValue(massageObject).addOnSuccessListener {
                    dbRef.child("chats").child(recieverRoom!!).child("massages").push()
                        .setValue(massageObject)
                }
            massageBox.setText("")
            


        }

    }

}
