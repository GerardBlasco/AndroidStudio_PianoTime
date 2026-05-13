package com.example.irisfrades_gerardblasco_ra03_act02

import android.annotation.SuppressLint
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private var soundMap = mutableMapOf<Int, Int>()

    private var keyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soundPool = SoundPool.Builder().setMaxStreams(2).build()

        soundMap[R.id.c4] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.a3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.b3] = soundPool.load(this, R.raw.faaa, 1)

        loadKey(R.id.c4)
        loadKey(R.id.a3)
        loadKey(R.id.b3)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun loadKey(id: Int){
        val key = findViewById<ImageButton>(id)

        key.setOnTouchListener { v, event ->

            when(event.action){
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    val soundId = soundMap[id]
                    if(soundId != null){
                        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                    }
                }
            }
            false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val pointerCount = event.pointerCount

        for(i in 0 until pointerCount){
            
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}