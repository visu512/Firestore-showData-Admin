
package com.example.cloudfirestore2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudfirestore2.databinding.RvDesignBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RvAdapter(val context: Context, val list: List<User>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    class ViewHolder(val binding: RvDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Correct way to use ViewBinding with RecyclerView
        val binding = RvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.text = list[position].name
        holder.binding.pass.text = list[position].pass

        // update button functionality
        holder.binding.update.setOnClickListener {
            val intent = Intent(context,UpdateActivity::class.java) // go to update activity
            intent.putExtra("NAME",list[position].name)
            intent.putExtra("PASS",list[position].pass)
            intent.putExtra("ID",list[position].id)

            context.startActivity(intent)

        }


        // delete button functionality
        holder.binding.delete.setOnClickListener {
            val userId = list[position].id // Get the ID from the user object
            // Check if userId is valid (not null)
            if (userId != null) {
                val db = Firebase.firestore
                db.collection("users").document(userId).delete()
                    .addOnSuccessListener {
                        Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show()

                        // Remove item from the list and notify the adapter
                        (list as MutableList).removeAt(position)  // Cast list to MutableList
                        notifyItemRemoved(position)
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "User ID is null!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
