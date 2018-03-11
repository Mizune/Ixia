package com.m1zyuk1.ixia

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.m1zyuk1.ixia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val RESULT_CAMERA = 1001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.cameraButton.setOnClickListener{
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, RESULT_CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_CAMERA){
            if(data != null){
                var intent = data.extras
                var bitmap = intent.get("data") as Bitmap
                binding.imageView.setImageBitmap(bitmap)
            }
        }
    }
}
