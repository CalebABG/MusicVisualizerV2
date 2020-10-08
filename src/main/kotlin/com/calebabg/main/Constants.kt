package com.calebabg.main

import com.calebabg.abstractions.Visualization
import com.calebabg.helpers.ClipboardHelper
import com.calebabg.helpers.MusicQueue
import controlP5.ControlP5
import controlP5.Textfield
import controlP5.Textlabel
import ddf.minim.AudioPlayer
import ddf.minim.Minim
import ddf.minim.analysis.FFT
import java.awt.Image
import java.awt.Toolkit
import java.lang.ref.WeakReference
import java.util.*

lateinit var visRef: WeakReference<Visualizer>

lateinit var fft: FFT
lateinit var cp5: ControlP5
lateinit var minim: Minim

lateinit var musicQueue: MusicQueue
var audioPlayer: AudioPlayer? = null

lateinit var fetchingTracksLabel: Textlabel
lateinit var soundCloudTextField: Textfield

var clipboardHelper: ClipboardHelper = ClipboardHelper()

lateinit var backgroundVis: Visualization
lateinit var visualizers: Vector<Visualization>

var fftSmooth = FloatArray(1)
var audioFormats = arrayOf("mp3", "wav", "aiff", "au")


// Strings
const val iconFile = "icon.png"
var iconImage: Image = Toolkit.getDefaultToolkit().getImage(Visualizer::class.java.classLoader.getResource(iconFile))


// Booleans
const val MV_DEBUG = false

var drawBackground = false
var playerPaused = false
var playerNotClosed = true


// Ints
const val titleLength = 45

var bassBubbleCount = 100
var starCount = 350
var avgSize = 0
var bufferSize = 2048
var minBandwidth = 2000
var bandsPerOctave = 300
var barStep = 1
var backgroundStep = 50
var transformMode = 0
var visualMode = 0


//  Floats
var musicGain = -8f
var starSpeed = 1f
var timeMeterHeight = 12f
var minVal = 0.0f
var maxVal = 0.0f
var smoothing = 0.85f
var cAmplitude = 90f
var ellipseR = 0f
var bassKick = 1.2f
var circleRadius = 165.0f
var strokeWeight = 1.3f
var amplitude = 1.5f
var angOffset = 2.18f
var angle = 0.0f
var offset = -0.07014108f /*random value between -PI and PI*/
var maxDrawHeight = 1.0f
var spectrumScaleFactor = 1.0f


// Lambdas
var selectedSongIndex = { musicQueue.value.toInt() }
var audioPlayerPaused = { !audioPlayer?.isPlaying!! }
var audioPlayerMuted = { audioPlayer?.isMuted!! }