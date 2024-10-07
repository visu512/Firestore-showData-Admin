package com.example.cloudfirestore2

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cloudfirestore2.databinding.ActivityUpdateBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UpdateActivity : AppCompatActivity() {

    val binding: ActivityUpdateBinding by lazy {
        ActivityUpdateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val db = Firebase.firestore


        binding.name.setText(intent.getStringExtra("NAME"))
        binding.pass.setText(intent.getStringExtra("PASS"))

        binding.update.setOnClickListener {
            val user = hashMapOf(
                "name" to binding.name.text.toString(),
                "pass" to binding.pass.text.toString()

            )

            db.collection("users").document(intent.getStringExtra("ID")!!).set(user)
            finish()
        }



    }
}
