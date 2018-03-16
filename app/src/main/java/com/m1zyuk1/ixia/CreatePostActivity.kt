package com.m1zyuk1.ixia

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class CreatePostActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_create_post)
        var url = intent.getStringExtra(IMAGE_URL) ?: "Unknown Uri"
        Toast.makeText(this,"Camera Uri = " + url ,Toast.LENGTH_SHORT).show()
    }
}
