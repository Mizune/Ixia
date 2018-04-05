package com.m1zyuk1.ixia

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.m1zyuk1.ixia.databinding.ActivityCreatePostBinding
import com.m1zyuk1.ixia.databinding.ActivityDrawingDetailBinding
import com.m1zyuk1.ixia.model.Post

class DrawingDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrawingDetailBinding

    companion object MakeIntent {
        val POST_DATA = "post_data"
        fun makeIntent(context: Context, post: Post): Intent {
            var intent = Intent(context, DrawingDetailActivity::class.java)
            intent.putExtra(POST_DATA, post)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawing_detail)
        setupUi()
        Toast.makeText(this,(intent.getSerializableExtra(POST_DATA) as Post).title, Toast.LENGTH_SHORT).show()
    }

    private fun setupUi(){
        val post = intent.getSerializableExtra(POST_DATA) as Post
        binding.detailTitle.text = post.title
        binding.detailImage.setImageURI(Uri.parse(post.imagePath))
        binding.detailComment.text = post.comment
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_drawing_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.remove_button -> {
                Toast.makeText(applicationContext, "Select remove button.", Toast.LENGTH_SHORT).show()
                // デリート処理
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
