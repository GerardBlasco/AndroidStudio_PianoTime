package com.example.irisfrades_gerardblasco_ra03_act02

import android.annotation.SuppressLint
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private var soundMap = mutableMapOf<Int, Int>()

    private var keyId: Int = 0
    private val keyList = mutableListOf<ImageButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soundPool = SoundPool.Builder().setMaxStreams(2).build()

        keyList.add(findViewById(R.id.c4))
        keyList.add(findViewById(R.id.a3))
        keyList.add(findViewById(R.id.b3))

        disableKeyInteraction()

        soundMap[R.id.c4] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.a3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.b3] = soundPool.load(this, R.raw.faaa, 1)

        //loadKey(R.id.c4)
        //loadKey(R.id.a3)
        //loadKey(R.id.b3)
    }

    /*@SuppressLint("ClickableViewAccessibility")
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
    }*/

    fun disableKeyInteraction(){
        for(key in keyList){
            key.isClickable = false
            key.isFocusable = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val pointerCount = event.pointerCount
        println("TOUCH EVENT")
        for(i in 0 until pointerCount){
            val posX = event.getX(i)
            val posY = event.getY(i)
            val pointerId = event.getPointerId(i)

            for(key in keyList){
                if(isTouchingKey(key, posX, posY)){
                    val soundId = soundMap[key.id]

                    if(soundId != null){
                        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                    }
                }
            }
        }

        return true
    }

    fun isTouchingKey(key: ImageButton, posX: Float, posY: Float): Boolean{
        val keyPosition = IntArray(2)
        key.getLocationOnScreen(keyPosition)

        val left = keyPosition[0]
        val right = left + key.width
        val top = keyPosition[1]
        val bottom = top + key.height

        return posX > left && posX < right && posY > top && posY < bottom
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}