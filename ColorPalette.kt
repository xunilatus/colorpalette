package com.example.colorpalette

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.max
import kotlin.math.min

class ColorPaletteView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    var hue = 0f
    var sat = 1f
    var value = 1f

    var listener: ((Int)->Unit)? = null

    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {

        val w = width.toFloat()
        val h = height.toFloat()

        for (x in 0 until width step 2) {
            hue = x * 360f / w

            val shader = LinearGradient(
                x.toFloat(),
                0f,
                x.toFloat(),
                h,
                Color.HSVToColor(floatArrayOf(hue,1f,1f)),
                Color.WHITE,
                Shader.TileMode.CLAMP
            )

            paint.shader = shader
            canvas.drawRect(
                x.toFloat(),
                0f,
                x+2f,
                h,
                paint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val x = max(0f,min(width.toFloat(),event.x))
        val y = max(0f,min(height.toFloat(),event.y))

        hue = x / width * 360f
        sat = 1f - y / height

        val color = Color.HSVToColor(
            floatArrayOf(hue,sat,value)
        )

        listener?.invoke(color)

        invalidate()

        return true
    }

    fun updateValue(v: Float) {

        value = v

        invalidate()

        listener?.invoke(
            Color.HSVToColor(floatArrayOf(hue, sat, value))
        )
    }

}
