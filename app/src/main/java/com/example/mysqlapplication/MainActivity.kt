package com.example.mysqlapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.mysqlapplication.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

class MainActivity : AppCompatActivity(){
    private lateinit var i: Intent
    private lateinit var studentId: TextInputEditText
    private lateinit var studentFirstName: TextInputEditText
    private lateinit var studentLastName: TextInputEditText
    private lateinit var studentEmail: TextInputEditText
    private lateinit var studentMajor: TextInputEditText
    private lateinit var binding: ActivityMainBinding
    private lateinit var sbmtRegBtn: Button
    private lateinit var sbmtEdtBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        i = intent

        if(i.hasExtra("editmode")){

            if(i.getStringExtra("editmode").equals("1")){

                onEditMode()

            }

        }
        studentId = binding.txId
        studentFirstName = binding.txFname
        studentLastName = binding.txLname
        studentEmail = binding.txEmail
        studentMajor = binding.txMajor
        sbmtRegBtn = binding.submitRegistration
        sbmtEdtBtn = binding.submitEdit

        sbmtRegBtn.setOnClickListener {
            create()
        }

    }

    private fun onEditMode(){

        studentId.setText(i.getStringExtra("id"))
        studentFirstName.setText(i.getStringExtra("fname"))
        studentLastName.setText(i.getStringExtra("lname"))
        studentEmail.setText(i.getStringExtra("email"))
        studentMajor.setText(i.getStringExtra("major"))

        sbmtRegBtn.visibility = View.GONE
        sbmtEdtBtn.visibility = View.VISIBLE

    }

    private fun create(){
        val loading = ProgressDialog(this)
        loading.setMessage("Menambahkan data...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.CREATE)
            .addBodyParameter("id",studentId.text.toString())
            .addBodyParameter("fname",studentFirstName.text.toString())
            .addBodyParameter("lname",studentLastName.text.toString())
            .addBodyParameter("email",studentEmail.text.toString())
            .addBodyParameter("major", studentMajor.text.toString() )
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){
                        this@MainActivity.finish()
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