package com.kareemdev.myplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util
import com.kareemdev.myplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val URL_VIDEO_DICODING = "https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4"
        const val URL_AUDIO = "https://github.com/dicodingacademy/assets/raw/main/android_intermediate_academy/bensound_ukulele.mp3"
    }

    private val viewBinding by lazy (LazyThreadSafetyMode.NONE){
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var player: ExoPlayer? = null

    private fun initializePlayer(){
        val mediaItem = MediaItem.fromUri(URL_VIDEO_DICODING)
        val anotherMediaItem = MediaItem.fromUri(URL_AUDIO)

        player = ExoPlayer.Builder(this).build().also { exoPLayer ->
            viewBinding.videoView.player = exoPLayer
            exoPLayer.setMediaItem(mediaItem)
            exoPLayer.addMediaItem(anotherMediaItem)
            exoPLayer.prepare()
        }
    }

    private fun releasePlayer(){
        player?.release()
        player = null
    }

    override fun onStart() {
        super.onStart()
        if(Util.SDK_INT >= 24){
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        if(Util.SDK_INT < 24 && player == null){
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if(Util.SDK_INT <= 24){
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if(Util.SDK_INT >= 24){
            releasePlayer()
        }
    }

    private fun hideSystemUI(){
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, viewBinding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        /*val player = ExoPlayer.Builder(this).build()
        viewBinding.videoView.player = player

        val mediaItem = MediaItem.fromUri(URL_VIDEO_DICODING)
        val anotherMediaItem = MediaItem.fromUri(URL_AUDIO)
        player.setMediaItem(mediaItem)
        player.addMediaItem(anotherMediaItem)
        player.prepare()*/
    }
}