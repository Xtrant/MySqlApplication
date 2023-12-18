package com.example.mysqlapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysqlapplication.databinding.ListStudentBinding

class ListStudentAdapter(applicationContext: Context, private val listStudent: ArrayList<Student>) : RecyclerView.Adapter<ListStudentAdapter.ListViewHolder>() {
    class ListViewHolder (var binding : ListStudentBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =ListStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder((binding))
    }

    override fun getItemCount(): Int = listStudent.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (id, fname, lname, email, major) = listStudent[position]
        holder.binding.tvId.text = id
        holder.binding.tvFname.text = fname
        holder.binding.tvLname.text = lname
        holder.binding.tvEmail.text = email
        holder.binding.tvMajor.text = major



    }
}