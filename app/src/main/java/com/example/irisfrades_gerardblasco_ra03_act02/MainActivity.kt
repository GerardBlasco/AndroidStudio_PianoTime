package com.example.irisfrades_gerardblasco_ra03_act02

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private var soundMap = mutableMapOf<Int, Int>()

    private var currentKeyId: Int? = null
    private val keyList = mutableListOf<ImageButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soundPool = SoundPool.Builder().setMaxStreams(2).build()

        // TECLAS BLANCAS
        keyList.add(findViewById(R.id.c2))
        keyList.add(findViewById(R.id.d2))
        keyList.add(findViewById(R.id.e2))
        keyList.add(findViewById(R.id.f2))
        keyList.add(findViewById(R.id.g2))
        keyList.add(findViewById(R.id.a2))
        keyList.add(findViewById(R.id.b2))
        keyList.add(findViewById(R.id.c3))
        keyList.add(findViewById(R.id.d3))
        keyList.add(findViewById(R.id.e3))
        keyList.add(findViewById(R.id.f3))
        keyList.add(findViewById(R.id.g3))
        keyList.add(findViewById(R.id.a3))
        keyList.add(findViewById(R.id.b3))
        keyList.add(findViewById(R.id.c4))

        // TECLAS NEGRAS
        keyList.add(findViewById(R.id.cb2))
        keyList.add(findViewById(R.id.db2))
        keyList.add(findViewById(R.id.fb2))
        keyList.add(findViewById(R.id.gb2))
        keyList.add(findViewById(R.id.ab2))
        keyList.add(findViewById(R.id.cb3))
        keyList.add(findViewById(R.id.db3))
        keyList.add(findViewById(R.id.fb3))
        keyList.add(findViewById(R.id.gb3))
        keyList.add(findViewById(R.id.ab3))

        disableKeyInteraction()

        // TECLAS BLANCAS
        soundMap[R.id.c2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.d2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.e2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.f2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.g2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.a2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.b2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.c3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.d3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.e3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.f3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.g3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.a3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.b3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.c4] = soundPool.load(this, R.raw.faaa, 1)

        // TECLAS NEGRAS
        soundMap[R.id.cb2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.db2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.fb2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.gb2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.ab2] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.cb3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.db3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.fb3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.gb3] = soundPool.load(this, R.raw.faaa, 1)
        soundMap[R.id.ab3] = soundPool.load(this, R.raw.faaa, 1)
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

        for(i in 0 until pointerCount){
            val posX = event.getX(i)
            val posY = event.getY(i)
            val pointerId = event.getPointerId(i)

            for(key in keyList){
                if(isTouchingKey(key, posX, posY) && currentKeyId != key.id){
                    currentKeyId = key.id

                    val soundId = soundMap[key.id]

                    if(soundId != null){
                        println("TOUCH EVENT")
                        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                    }
                    key.isPressed = true
                }
                else if(!isTouchingKey(key, posX, posY) && currentKeyId != key.id){
                    key.isPressed = false
                }

                key.refreshDrawableState()
            }

            when(event.action){
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    for(key in keyList){
                        key.isPressed = false
                    }

                    currentKeyId = null
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