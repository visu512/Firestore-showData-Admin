package com.example.cloudfirestore2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.cloudfirestore2.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val arrayList = ArrayList<User>()
        val rvAdapter = RvAdapter(this, arrayList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = rvAdapter

        val db = Firebase.firestore


        // read data
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                arrayList.clear()   // clear array list
                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")

                    var user = document.toObject(User::class.java)
                    user.id = document.id
                    arrayList.add(user)
                }
                rvAdapter.notifyDataSetChanged()


                // Show or hide the empty message
                if (arrayList.isEmpty()) {
                    binding.emptyMessage.visibility = View.VISIBLE // Show message if list is empty
                    binding.recyclerView.visibility = View.GONE // Hide RecyclerView
                } else {
                    binding.emptyMessage.visibility = View.GONE // Hide message if there are items
                    binding.recyclerView.visibility = View.VISIBLE // Show RecyclerView

                }

            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()

            }


    }
}