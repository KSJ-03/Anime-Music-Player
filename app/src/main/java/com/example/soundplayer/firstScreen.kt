package com.example.soundplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class firstScreen : AppCompatActivity() {

    lateinit var btnMHA:Button
    lateinit var btnDBS:Button
    lateinit var btnBL:Button
    lateinit var btnAOT:Button
    lateinit var btnOPM:Button
    lateinit var btnCSM:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        btnMHA=findViewById(R.id.btnMHA)
        btnDBS=findViewById(R.id.btnDBS)
        btnBL=findViewById(R.id.btnBL)
        btnAOT=findViewById(R.id.btnAOT)
        btnOPM=findViewById(R.id.btnOPM)
        btnCSM=findViewById(R.id.btnCSM)

        btnDBS.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            val poster=R.drawable.goku
            intent.putExtra("poster",poster)
            val audio=R.raw.goku_ui
            intent.putExtra("audio",audio)
            startActivity(intent)
        }
        btnMHA.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            val poster=R.drawable.mha
            intent.putExtra("poster",poster)
            val audio=R.raw.mha_yousayrun
            intent.putExtra("audio",audio)
            startActivity(intent)
        }
        btnBL.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            val poster=R.drawable.bluelock
            intent.putExtra("poster",poster)
            val audio=R.raw.bluelock_mainost
            intent.putExtra("audio",audio)
            startActivity(intent)
        }
        btnAOT.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            val poster=R.drawable.eren
            intent.putExtra("poster",poster)
            val audio=R.raw.aot_s4_ost
            intent.putExtra("audio",audio)
            startActivity(intent)
        }
        btnOPM.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            val poster=R.drawable.opm
            intent.putExtra("poster",poster)
            val audio=R.raw.opm_avmopening1
            intent.putExtra("audio",audio)
            startActivity(intent)
        }
        btnCSM.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            val poster=R.drawable.chainsawman
            intent.putExtra("poster",poster)
            val audio=R.raw.chainsawman_maintheme
            intent.putExtra("audio",audio)
            startActivity(intent)
        }

    }
}