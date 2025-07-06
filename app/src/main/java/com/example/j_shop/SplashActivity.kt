package com.example.j_shop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Utilise le thème Splash ici (voir étape 2)
        setContentView(R.layout.activity_splash)

        // Délai pour simuler chargement
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MarketActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 2 secondes
    }
}
