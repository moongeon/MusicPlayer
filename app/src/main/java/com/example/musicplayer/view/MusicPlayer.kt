package com.example.musicplayer.view

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.musicplayer.R
import com.example.musicplayer.databinding.MusicPlayerFragmentBinding
import com.example.musicplayer.viewModel.MusicPlayerViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlin.coroutines.coroutineContext

class MusicPlayer : Fragment() {

    val viewModel: MusicPlayerViewModel by viewModels()
    private lateinit var binding: MusicPlayerFragmentBinding
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MusicPlayerFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.Album.observe(viewLifecycleOwner, Observer {
            mediaPlayer.apply {
                setDataSource(it.file)
                prepare()
            }
            Glide.with(this)
                .load(it.image)
                .into(binding.albumImg)
            binding.seekBar.max = mediaPlayer.duration
        })
        binding.play.isSelected = false
        binding.play.setOnClickListener {
            mediaPlayer.start()
            timer()
            it.isSelected = it.isSelected.not()
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.progress = progress


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.seekTo(seekBar!!.progress)
            }


        })


    }

    private fun timer() {
        lifecycleScope.launch {
            while (mediaPlayer.isPlaying) {
                delay(200)
                withContext(Dispatchers.Main) {
                    binding.seekBar.progress = mediaPlayer.currentPosition
                    binding.textView.text = mediaPlayer!!.currentPosition.toString()
                }

            }
        }
    }


}