package com.najmulsdeveloper.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class LoginActivity : AppCompatActivity(){


    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var login: Button
    private lateinit var signup: Button
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        initialize()
        auth = FirebaseAuth.getInstance()

        signup.setOnClickListener {
            val intent = Intent(this, signIn::class.java)
            startActivity(intent)
        }


        login.setOnClickListener {
            val emailAdress = email.text.toString()
            val passwordInput = password.text.toString()


            login(emailAdress, passwordInput)
        }


    }

    private fun login(emailAdress: String, passwordInput: String){
        //logic for log in user



        auth.signInWithEmailAndPassword(emailAdress, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {


                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@LoginActivity, "Hoyto tmr Account khula nai, Ar nahoy tmr Password Vhul Bhai/Bon.", Toast.LENGTH_SHORT).show()
                }
            }


    }

    fun initialize(){
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        signup = findViewById(R.id.signup)
    }
}