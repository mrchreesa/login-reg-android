package com.example.app5

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*
import java.security.MessageDigest
import java.security.SecureRandom
import android.util.Base64
import at.favre.lib.crypto.bcrypt.BCrypt

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    private fun registerUser() {
        val username = findViewById<TextInputEditText>(R.id.editTextUsername).text.toString()
        val password = findViewById<TextInputEditText>(R.id.editTextPassword).text.toString()
        val confirmPassword = findViewById<TextInputEditText>(R.id.editTextConfirmPassword).text.toString()
        val fullName = findViewById<TextInputEditText>(R.id.editTextFullName).text.toString()
        val email = findViewById<TextInputEditText>(R.id.editTextEmail).text.toString()
        val dateOfBirth = findViewById<TextInputEditText>(R.id.editTextDateOfBirth).text.toString()

        val radioGroupSex = findViewById<RadioGroup>(R.id.radioGroupSex)
        val selectedSex = when (radioGroupSex.checkedRadioButtonId) {
        R.id.radioButtonMale -> "Male"
        R.id.radioButtonFemale -> "Female"
        else -> null  
    }

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || fullName.isEmpty() || email.isEmpty() || dateOfBirth.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return
        }
          if (selectedSex == null) {
        Toast.makeText(this, "Please select your sex", Toast.LENGTH_SHORT).show()
        return
    }

        if (!isValidDate(dateOfBirth)) {
            Toast.makeText(this, "Invalid date format. Please use DD/MM/YYYY", Toast.LENGTH_SHORT).show()
            return
        }

        // Store user data
        val hashedPassword = hashPassword(password)
        
        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("username", username)
            putString("password", hashedPassword)
            putString("fullName", fullName)
            putString("email", email)
            putString("dateOfBirth", dateOfBirth)
            putString("sex", selectedSex)
            apply()
        }

        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun isValidDate(date: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}
