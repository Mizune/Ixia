package com.m1zyuk1.ixia

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.databinding.DataBindingUtil
import android.view.View
import android.widget.Toast
import com.m1zyuk1.ixia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.cameraButton.setOnClickListener{
            Toast.makeText(this,"Test button",Toast.LENGTH_SHORT).show()
        }
    }
}
