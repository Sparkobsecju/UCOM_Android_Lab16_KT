package com.example.lab16_kt

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.Random


class RandomService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return object : IRandomService.Stub() {
            override fun getRandom(seed: Long): Double {
                return Random(seed).nextDouble()
            }
        }
    }
}