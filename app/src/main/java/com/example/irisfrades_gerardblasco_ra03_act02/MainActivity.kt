package com.example.irisfrades_gerardblasco_ra03_act02

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private var soundMap = mutableMapOf<Int, Int>()

    private var currentKeyId: Int? = null
    private val keyList = mutableListOf<ImageButton>()

    private lateinit var seekBar: SeekBar
    private lateinit var constraintLayout: ConstraintLayout

    private var maxMovimiento = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = findViewById(R.id.seekBar)
        constraintLayout = findViewById(R.id.constraintLayout)
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
        soundMap[R.id.c2] = soundPool.load(this, R.raw.c2, 1)
        soundMap[R.id.d2] = soundPool.load(this, R.raw.d2, 1)
        soundMap[R.id.e2] = soundPool.load(this, R.raw.e2, 1)
        soundMap[R.id.f2] = soundPool.load(this, R.raw.f2, 1)
        soundMap[R.id.g2] = soundPool.load(this, R.raw.g2, 1)
        soundMap[R.id.a2] = soundPool.load(this, R.raw.a2, 1)
        soundMap[R.id.b2] = soundPool.load(this, R.raw.b2, 1)
        soundMap[R.id.c3] = soundPool.load(this, R.raw.c3, 1)
        soundMap[R.id.d3] = soundPool.load(this, R.raw.d3, 1)
        soundMap[R.id.e3] = soundPool.load(this, R.raw.e3, 1)
        soundMap[R.id.f3] = soundPool.load(this, R.raw.f3, 1)
        soundMap[R.id.g3] = soundPool.load(this, R.raw.g3, 1)
        soundMap[R.id.a3] = soundPool.load(this, R.raw.a3, 1)
        soundMap[R.id.b3] = soundPool.load(this, R.raw.b3, 1)
        soundMap[R.id.c4] = soundPool.load(this, R.raw.c4, 1)

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

        constraintLayout.post {
            maxMovimiento = (constraintLayout.width - seekBar.width).toFloat()
        }

        var lastProgress = 0

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val diferencia = progress - lastProgress

                if (diferencia > 0) {
                    constraintLayout.translationX -= diferencia
                }
                else if (diferencia < 0) {
                    constraintLayout.translationX -= diferencia
                }

                lastProgress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    fun disableKeyInteraction(){
        for(key in keyList){
            key.isClickable = false
            key.isFocusable = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val pointerCount = event.pointerCount

        for (i in 0 until pointerCount) {
            val posX = event.getX(i)
            val posY = event.getY(i)
            val pointerId = event.getPointerId(i)

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_MOVE -> {

                    val topKey = findTopKeyByElevation(posX, posY)

                    if (topKey != null && currentKeyId != topKey.id) {

                        currentKeyId = topKey.id

                        val soundId = soundMap[topKey.id]
                        if (soundId != null) {
                            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                        }

                        for (key in keyList) {
                            key.isPressed = key.id == topKey.id
                            key.refreshDrawableState()
                        }
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                    for (key in keyList) {
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

    fun findTopKeyByElevation(posX: Float, posY: Float): ImageButton? {
        return keyList
            .sortedByDescending { it.elevation }   // primero las más altas
            .firstOrNull { isTouchingKey(it, posX, posY) }
    }
}