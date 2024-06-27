package com.example.lab16_kt

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), ServiceConnection {
    private lateinit var button1: Button
    private lateinit var textView1: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        button1 = findViewById(R.id.button1)
        textView1 = findViewById(R.id.textView1)
        val serviceIntent = Intent(this, RandomService::class.java)
        bindService(serviceIntent, this, BIND_AUTO_CREATE)
        button1.setOnClickListener { getRandomFromService() }
    }

    private fun getRandomFromService() {
        try {

            val result = randomService!!.getRandom(System.currentTimeMillis())
            textView1.text = "取得的結果是${result}"
        } catch (e: RemoteException) {
            textView1.text = "oops, 錯誤是${e.message.toString()}"
        }
    }

    override fun onDestroy() {
        unbindService(this)
        super.onDestroy()
    }

    var randomService: IRandomService? = null
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        randomService = IRandomService.Stub.asInterface(service)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        randomService = null
    }
}