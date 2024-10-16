package com.example.app5

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signUpButton = findViewById<Button>(R.id.button)
        signUpButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.button2)
        loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val username = findViewById<TextInputEditText>(R.id.editTextName).text.toString()
        val password = findViewById<TextInputEditText>(R.id.editTextPassword).text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val storedUsername = sharedPref.getString("username", "")
        val storedPassword = sharedPref.getString("password", "")

        if (username == storedUsername && password == storedPassword) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            navigateToUserProfile()
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToUserProfile() {
        val intent = Intent(this, UserProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
}
