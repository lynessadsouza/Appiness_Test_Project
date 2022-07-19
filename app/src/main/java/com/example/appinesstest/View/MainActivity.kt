package com.example.appinesstest.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appinesstest.R
import com.example.appinesstest.ViewModel.RetrofitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserListAdapter.onItemClickListner {
    private lateinit var viewModel: RetrofitViewModel

    lateinit var recyclerAdapter: UserListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        initViewModel()

        userSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText == "") {
                    initViewModel()
                } else {
                    filterUsers(newText)
                }
                return true
            }
        })
    }

    private fun filterUsers(newText: String) {
        viewModel = ViewModelProvider(this).get(RetrofitViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this) {
            if (it != null) {
                recyclerAdapter.setUserList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting List!!", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.filterUsers(newText)

    }

    private fun initRecyclerView() {
        userRecycler.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = UserListAdapter(this, this)
        userRecycler.adapter = recyclerAdapter
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(RetrofitViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this) {
            if (it != null) {
                recyclerAdapter.setUserList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting List!!", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.makeAPICall()
    }
    override fun onItemClick(position: Int) {
     Log.d("TAG","onItemClick")
    }
}