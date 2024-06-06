package com.najmulsdeveloper.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signIn : AppCompatActivity() {


    private lateinit var name : EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var signup: Button
    private lateinit var auth :FirebaseAuth
    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)


        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        initialize()

        signup.setOnClickListener {
            val name = name.text.toString()
            val emailAdress = email.text.toString()
            val passwordInput = password.text.toString()


            signUp(name, emailAdress, passwordInput)

        }



    }

    private fun signUp(name:String, emailAdress:String, passwordInput:String){
        //logic for sign in user


        auth.createUserWithEmailAndPassword(emailAdress, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code jor jumping


                    addUserFromDatabase(name, emailAdress, auth.currentUser?.uid!! )

                    val intent = Intent(this@signIn, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@signIn, "Kichu Error Ache Miya", Toast.LENGTH_SHORT).show()

                }
            }

    }



    private fun addUserFromDatabase(name:String, emailAdress: String, uid: String){
        dbRef = FirebaseDatabase.getInstance().getReference()

        dbRef.child("user").child(uid).setValue(users(name, emailAdress, uid))
    }


    fun initialize(){
        name = findViewById(R.id.Name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        signup = findViewById(R.id.signup)
    }
}