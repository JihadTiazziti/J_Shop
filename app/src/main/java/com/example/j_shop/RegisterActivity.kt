package com.example.j_shop

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val usernameEditText = findViewById<EditText>(R.id.username)
        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val termsCheckBox = findViewById<CheckBox>(R.id.termsCheckBox)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val googleButton = findViewById<ImageButton>(R.id.btnGoogle)
        val facebookButton = findViewById<ImageButton>(R.id.btnFacebook)
        val loginText = findViewById<TextView>(R.id.tvLogin)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (!termsCheckBox.isChecked) {
                Toast.makeText(this, "You must agree to the Terms & Conditions", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MarketActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        googleButton.setOnClickListener {

            Toast.makeText(this, "Google sign-up clicked", Toast.LENGTH_SHORT).show()
        }

        facebookButton.setOnClickListener {

            Toast.makeText(this, "Facebook sign-up clicked", Toast.LENGTH_SHORT).show()
        }

        loginText.setOnClickListener {

            Toast.makeText(this, "Go to Login Screen", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))  // Uncomment this line to navigate to LoginActivity
        }
    }
}
