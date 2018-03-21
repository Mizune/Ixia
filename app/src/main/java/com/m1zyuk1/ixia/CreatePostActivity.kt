package com.m1zyuk1.ixia

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.m1zyuk1.ixia.databinding.ActivityCreatePostBinding
import com.m1zyuk1.ixia.model.Post
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePostBinding

    companion object MakeIntent {
        val IMAGE_URL = "image_url"
        val RESPONSE_POST = "response_post"
        fun makeIntent(context: Context, imageUrl: String): Intent {
            var intent = Intent(context, CreatePostActivity::class.java)
            intent.putExtra(IMAGE_URL, imageUrl)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post)
        setupUi()
    }

    private fun setupUi() {
        var url = intent.getStringExtra(IMAGE_URL) ?: ""
        binding.imageView.setImageURI(Uri.parse(url))
        Toast.makeText(this, "Camera Uri = " + url, Toast.LENGTH_SHORT).show()
        supportActionBar?.title = "保存内容"
        binding.submitButton.setOnClickListener {
            if (isValid()){
                var post = Post(binding.postTitle.text.toString(), url, Date(), binding.postComment.toString())
                var intent = Intent()
                intent.putExtra(RESPONSE_POST,post)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun isValid():Boolean{

        if (binding.postTitle.toString().isEmpty()){
            return false
        }
        return true
    }
}
