package com.example.mysqlapplication

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.mysqlapplication.databinding.ActivityReadBinding
import org.json.JSONObject


class ReadActivity : AppCompatActivity() {
    var arrayList = ArrayList<Student>()
    private lateinit var rvList : RecyclerView
    private lateinit var binding : ActivityReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        rvList = binding.rvList

        binding.mFloatingActionButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadAllStudents()
    }

    private fun loadAllStudents(){

        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()

                    val jsonArray = response?.optJSONArray("result")

                    if(jsonArray?.length() == 0){
                        loading.dismiss()
                        Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()
                    }

                    for(i in 0 until jsonArray?.length()!!){

                        val jsonObject = jsonArray.optJSONObject(i)
                        if (jsonObject != null) {
                            arrayList.add(Student(jsonObject.getString("id"),
                                jsonObject.getString("fname"),
                                jsonObject.getString("lname"),
                                jsonObject.getString("email"),
                                jsonObject.getString("major")))
                        }

                        if(jsonArray.length() - 1 == i){

                            loading.dismiss()
                            val adapter = ListStudentAdapter(applicationContext,arrayList)
                            adapter.notifyDataSetChanged()
                            rvList.adapter = adapter

                        }

                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    anError?.errorDetail?.toString()?.let { Log.d("ONERROR", it) }
                    Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
                }
            })


    }
}