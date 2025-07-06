package com.example.j_shop



import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val emailEditText = findViewById<EditText>(R.id.email)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val backToLoginText = findViewById<TextView>(R.id.tvBackToLogin)

        submitButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                // Afficher la boîte de dialogue pour entrer un nouveau mot de passe
                showResetPasswordDialog()
            }
        }

        backToLoginText.setOnClickListener {
            // Retourner à l'écran de connexion (finir l'activité actuelle)
            finish()
        }
    }

    private fun showResetPasswordDialog() {
        // Créer un AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reset Your Password")

        // Créer un EditText pour entrer un nouveau mot de passe
        val newPasswordEditText = EditText(this)
        newPasswordEditText.hint = "Enter your new password"

        // Ajouter le EditText à la boîte de dialogue
        builder.setView(newPasswordEditText)

        builder.setPositiveButton("Submit") { dialog, which ->
            val newPassword = newPasswordEditText.text.toString().trim()

            if (newPassword.isEmpty()) {
                Toast.makeText(this, "Please enter a new password", Toast.LENGTH_SHORT).show()
            } else {
                // Logique de réinitialisation du mot de passe
                resetPassword(newPassword)
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()  // Fermer la boîte de dialogue sans rien faire
        }

        builder.show()  // Afficher la boîte de dialogue
    }

    private fun resetPassword(newPassword: String) {
        // Logique pour réinitialiser le mot de passe
        // Par exemple, envoyer le nouveau mot de passe à votre API ou à la base de données

        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show()
        finish()  // Fermer l'activité actuelle après la réinitialisation
    }
}
