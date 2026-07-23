package com.example.colorpalette

import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.toString

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val picker = findViewById< ColorPaletteView>(R.id.picker)
        val slider = findViewById<SeekBar>(R.id.valueSlider)
        val preview = findViewById<View>(R.id.preview)
        val info = findViewById<TextView>(R.id.info)
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
        var currentHex = "#000000"

        picker.listener = { color ->

            preview.setBackgroundColor(color)

            val r = Color.red(color)
            val g = Color.green(color)
            val b = Color.blue(color)

            val hex = String.format("#%02X%02X%02X", r,g,b)

            currentHex = hex

            info.text =
                """
HEX: $hex

RGB($r,$g,$b)

CSS:

color:$hex;
background:$hex;
"""

        }

        slider.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {

                    picker.updateValue(progress / 100f)

                }

                override fun onStartTrackingTouch(s: SeekBar?) {}

                override fun onStopTrackingTouch(s: SeekBar?) {}

            })

        info.setOnClickListener {

            val text = info.text.toString()

            val clip = android.content.ClipData.newPlainText(
                "HEX",
                currentHex
            )

            clipboard.setPrimaryClip(clip)

            android.widget.Toast.makeText(
                this,
                "Copied color",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

}
