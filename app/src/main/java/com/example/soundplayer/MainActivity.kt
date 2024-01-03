package com.example.soundplayer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.format.DateUtils
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var mediaPlayer: MediaPlayer? = null
    lateinit var seekBar: SeekBar
    lateinit var runnable: Runnable
    lateinit var handler: Handler
    lateinit var tvPlayed: TextView
    lateinit var fabPlay: FloatingActionButton
    lateinit var fabPause: FloatingActionButton
    lateinit var fabStop: FloatingActionButton
    lateinit var totalDuration: TextView
    lateinit var ivPoster: ImageView
    lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBack = findViewById(R.id.btnBack)
        fabPlay = findViewById(R.id.fabPlay)
        fabPause = findViewById(R.id.fabPause)
        fabStop = findViewById(R.id.fabStop)

        tvPlayed = findViewById(R.id.tvPlayed)
        totalDuration = findViewById(R.id.tvDue)
        ivPoster = findViewById(R.id.ivPoster)

        seekBar = findViewById(R.id.seekBar)
        handler = Handler(Looper.getMainLooper())
        /*       A Handler allows communicating back with UI thread from other background thread. This is useful in android as android doesn’t allow other threads to communicate directly with UI thread.
                 The Handler class gives you the control to push work at the head, the tail or set a time based delay that will keep some work from being processed until that time has passed.
                 The Looper class keeps a thread alive and pop work off a queue to execute on
                 The units of work in android are explicitly defined as intent/runnable/messages,etc
                 The collection of all these is called a Handler Thread- dedicated thread for API callbacks
         */

        btnBack.setOnClickListener {

            val intent = Intent(this, firstScreen::class.java)
            startActivity(intent)
            /*
                        An Intent is a messaging object you can use to request an action from another app component.
                        An Activity represents a single screen in an app. You can start a new instance of an Activity by passing an Intent to startActivity(). The Intent describes the activity to start and carries any necessary data.

            */
            mediaPlayer?.stop()  //it is recommended in the documentation to first stop then reset and release
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }
        audioPlayer()
    }


    private fun audioPlayer() {

        val poster = intent.getIntExtra("poster", 0)
        val audio = intent.getIntExtra("audio", 0)

        ivPoster.setImageResource(poster)

        fabPlay.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, audio)
//            Convenience method to create a MediaPlayer for a given resource id.

                initialiseSeekBar()
            }
            mediaPlayer?.start()
        }

        fabPause.setOnClickListener {
            mediaPlayer?.pause()
        }

        fabStop.setOnClickListener {
            mediaPlayer?.stop()  //it is recommended in the documentation to first stop then reset and release
            mediaPlayer?.reset()
            mediaPlayer?.release()
//        Releases resources associated with this MediaPlayer object.
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }
    }

    private fun initialiseSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//        setOnSeekBarChangeListener: A callback that notifies clients when the progress level has been changed. This includes changes that were initiated by the user through a touch gesture or arrow key/trackball as well as changes that were initiated programmatically.

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                /*
                            Notification that the progress level has changed.Clients can use the fromUser parameter to distinguish user-initiated changes from those that occurred programmatically.
                            seekbar: SeekBar: The SeekBar whose progress has changed

                            progress	int: The current progress level. This will be in the range min..max where min and max were set by ProgressBar#setMin(int) and ProgressBar#setMax(int), respectively. (The default values for min is 0 and max is 100.)

                            fromUser	boolean: True if the progress change was initiated by the user.
                */
                if (fromUser) mediaPlayer?.seekTo(progress)

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
//            Notification that the user has started a touch gesture.

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
//            Notification that the user has finished a touch gesture.

            }

        })

        seekBar.max =
            mediaPlayer!!.duration  //as mediaPlayer is nullable, we need to add not-null-assignment operator
//    the duration in milliseconds, if no duration is available (for example, if streaming live content), -1 is returned.


        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition
            handler.postDelayed(runnable, 1000)

            tvPlayed = findViewById(R.id.tvPlayed)

            val playedTime = mediaPlayer!!.currentPosition / 1000

            val timePast = DateUtils.formatElapsedTime((playedTime).toLong())
            tvPlayed.text = timePast
        }
        handler.postDelayed(runnable, 1000)

        val duration = mediaPlayer!!.duration / 1000

        val maxMins = duration / 60
        val maxSec = duration % 60

        val totalDuration = findViewById<TextView>(R.id.tvDue)

        if (maxMins < 10) {
            if (maxSec < 10) {
                totalDuration.text = "0$maxMins:0$maxSec"
            } else {
                totalDuration.text = "0$maxMins:$maxSec"
            }
        } else {
            if (maxSec < 10) {
                totalDuration.text = "$maxMins:0$maxSec"
            } else {
                totalDuration.text = "$maxMins:$maxSec"
            }
        }

    }
}
