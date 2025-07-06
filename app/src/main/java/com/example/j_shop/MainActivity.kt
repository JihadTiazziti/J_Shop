package com.example.j_shop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val letsGoButton = findViewById<Button>(R.id.button_lets_go)
        val signInText = findViewById<TextView>(R.id.text_signin)

        letsGoButton.setOnClickListener {
            // Aller à l'activité d'enregistrement
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        signInText.setOnClickListener {
            // Aller à l'activité de connexion
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
