package com.example.message

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.message.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import com.example.message.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myContact=Contact("Eng","0112233445")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Dispaly UI
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        //setContentView(R.layout.activity_main)

        //Assign attribute of oal variable to UI variable
        binding.contact=myContact
        binding.buttonUpdate.setOnClickListener {
            binding.apply {
                contact?.name="my new name"
                contact?.phone="1111111111"
                invalidateAll()//refresh the UI
            }
        }

        buttonSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        //Create an Explicit Intent for the second page
        val intent=Intent(this,Main2Activity::class.java)
        //Prepare extra
        val message=editTextMessage.text.toString()
        intent.putExtra(EXTRA_MESSAGE,message)
        //start activity no return value
        //startActivity(intent)
        //start activity with return value(s)
        startActivityForResult(intent, REQUEST_REPLY)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode== REQUEST_REPLY){
            if(resultCode==Activity.RESULT_OK){
                val reply=data?.getStringExtra(MainActivity.EXTRA_REPLY)
                textViewReply.text=String.format("%s : %s",getString(R.string.reply),reply)
            }else{
                textViewReply.text=String.format("%s : %s",getString(R.string.reply),"No reply")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object{
        const val EXTRA_MESSAGE="com.example.message.MESSAGE"
        const val EXTRA_REPLY="com.example.message.REPLY"
        const val REQUEST_REPLY=1
    }
}
