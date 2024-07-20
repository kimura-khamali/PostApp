package com.brendakhamali.postapp

import ApiClient
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter(emptyList())
        recyclerView.adapter = adapter

        fetchPosts()
    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        fetchPosts()


    private fun fetchPosts(){
        val apiinterface = ApiClient.builtApiInterface(PostsApiInterface::class.java)
        val request = apiinterface.getPosts()
        request.enqueue(object : Callback<List<Post>> {
            override fun onResponse(p0: Call<List<Post>>, p1: Response<List<Post>>) {
               if (p1.isSuccessful){
                   val posts = p1.body()
                   if (posts != null) {
                       adapter.updatePosts(posts)
                   }
                   Toast.makeText(baseContext,"Fetch ${posts!!.size} posts",Toast.LENGTH_LONG).show()
               }
            }

            override fun onFailure(p0: Call<List<Post>>, p1: Throwable) {
               Toast.makeText(baseContext,p1.message.toString(),Toast.LENGTH_LONG
               )
            }
        })
    }
}