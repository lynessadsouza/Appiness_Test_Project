package com.example.appinesstest.View

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appinesstest.Model.HeirarchyList
import com.example.appinesstest.R


class UserListAdapter(
    var mlistner: MainActivity,
    private val context: Context
) : RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {
    private var userList: List<HeirarchyList>? = null


    fun setUserList(userList: List<HeirarchyList>?) {
        this.userList = userList
    }
    fun setUser(userList: HeirarchyList) {
        this.userList = listOf(userList)
    }
    interface onItemClickListner {
        fun onItemClick(position: Int)
    }

    class MyViewHolder(view: View, listner: onItemClickListner) : RecyclerView.ViewHolder(view) {
        val userName: TextView = view.findViewById(R.id.userName)
        val userDesignation: TextView = view.findViewById(R.id.userDesignation)
        val phone: ImageView = view.findViewById(R.id.phone)
        val message: ImageView = view.findViewById(R.id.message)
        init {
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.user_item, parent, false), mlistner
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userListData = userList?.get(position)
        holder.userName.text = userListData?.contactName
        holder.userDesignation.text = userListData?.designationName
        holder.phone.setOnClickListener {
            Log.d("TAG", "Phone Clicked")
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${userListData?.contactNumber}")
         context.   startActivity(intent)
        }
        holder.message.setOnClickListener {
            Log.d("TAG", "Message Clicked")
            val smsIntent = Intent(Intent.ACTION_VIEW)
            smsIntent.type = "vnd.android-dir/mms-sms"
            smsIntent.putExtra("address", "${userListData?.contactNumber}")
            smsIntent.putExtra("sms_body", "This is a test message")
           context. startActivity(smsIntent)
        }
    }

    override fun getItemCount(): Int {
        if (userList == null)
            return 0
        else return userList?.size!!
    }
}