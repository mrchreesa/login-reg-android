package com.example.app5

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Get user data
        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val fullName = sharedPref.getString("fullName", "") ?: ""
        val dateOfBirth = sharedPref.getString("dateOfBirth", "") ?: ""
        val email = sharedPref.getString("email", "") ?: ""
        val sex = sharedPref.getString("sex", "") ?: ""

        // Display user data
        findViewById<TextView>(R.id.textViewFullName).text = "Full Name: $fullName"
        findViewById<TextView>(R.id.textViewDateOfBirth).text = "Date of Birth: $dateOfBirth"
        findViewById<TextView>(R.id.textViewEmail).text = "Email: $email"
        findViewById<TextView>(R.id.textViewSex).text = "Sex: $sex"

        // Logout button
        findViewById<Button>(R.id.buttonLogout).setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
