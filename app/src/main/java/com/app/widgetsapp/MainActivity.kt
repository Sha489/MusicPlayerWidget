package com.app.widgetsapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var musicPlayer = MediaPlayer()
        var songList : ArrayList<Int> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songList.add(R.raw.moana)
        songList.add(R.raw.perfect)

        musicPlayer = MediaPlayer.create(applicationContext, songList[0])

        playBtn.setOnClickListener {
            if(musicPlayer.isPlaying) {
                musicPlayer.pause()
                playBtn.setBackgroundResource(R.drawable.play_black)
            } else {
                musicPlayer.start()
                playBtn.setBackgroundResource(R.drawable.pause_black)
            }
        }

        forward.setOnClickListener {
            musicPlayer.reset()
            musicPlayer = MediaPlayer.create(applicationContext, songList[1])
            playBtn.setBackgroundResource(R.drawable.pause_black)
            songTitle.setText("Perfect")
            musicPlayer.start()
        }

        pervious.setOnClickListener {
            musicPlayer.reset()
            musicPlayer = MediaPlayer.create(applicationContext, songList[0])
            playBtn.setBackgroundResource(R.drawable.pause_black)
            songTitle.setText("How far i'll go")
            musicPlayer.start()
        }

    }
}

