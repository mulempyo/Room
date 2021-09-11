package org.techtown.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import org.techtown.room.databinding.ActivityMainBinding

class MainActivity() : AppCompatActivity() {
    val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}
    var helper : RoomHelper ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        helper = Room.databaseBuilder(this,RoomHelper::class.java,"room_memo")
            .allowMainThreadQueries()
            .build()

        val adapter = RecyclerAdapter()
        adapter.helper = helper
        adapter.listData.addAll(helper?.roomMemoDao()?.getAll()?:listOf())
        binding.recyclerMemo.adapter = adapter
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        binding.buttonSave.setOnClickListener {
            if(binding.editMemo.text.toString().isNotEmpty()){
                val memo = RoomMemo(
                binding.editMemo.text.toString(),
                System.currentTimeMillis())
                helper?.roomMemoDao()?.insert(memo)
                    helper?.roomMemoDao()?.insert(memo)
                   adapter.listData.clear()
                    adapter.listData.addAll(helper?.roomMemoDao()?.getAll()?:listOf())
                    adapter.notifyDataSetChanged()
                binding.editMemo.setText("")
            }
        }
    }
}