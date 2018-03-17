package com.m1zyuk1.ixia

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.m1zyuk1.ixia.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePostBinding

    companion object MakeIntent {
        val IMAGE_URL = "image_url"
        fun makeIntent(context: Context,imageUrl: String): Intent {
            var intent = Intent(context, CreatePostActivity::class.java)
            intent.putExtra(IMAGE_URL, imageUrl)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_post)
        setupUi()
    }

    private fun setupUi(){
        var url = intent.getStringExtra(IMAGE_URL) ?: ""
        binding.imageView.setImageURI(Uri.parse(url))
        Toast.makeText(this,"Camera Uri = " + url ,Toast.LENGTH_SHORT).show()
        supportActionBar?.title = "投稿する"
    }
}
