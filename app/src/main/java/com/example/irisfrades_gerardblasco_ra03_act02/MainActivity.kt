package com.example.irisfrades_gerardblasco_ra03_act02

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private var soundMap = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soundPool = SoundPool.Builder().setMaxStreams(1).build()
    }

    private fun loadKey(id: Int){
        val key = findViewById<ImageButton>(id)

        key.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    val soundId = soundMap[id]
                    if(soundId != null){
                        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                    }
                }
            }
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}