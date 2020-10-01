package calebabg.main

import calebabg.abstractions.IAudioResource
import calebabg.abstractions.UriAudioSource
import calebabg.gui.Controls
import calebabg.gui.InstructionsWindow
import calebabg.gui.MVUILogger
import calebabg.soundcloud.Playlist
import calebabg.soundcloud.SoundCloudApiHelper
import calebabg.soundcloud.Track
import calebabg.visualizers.*
import controlP5.ControlP5
import ddf.minim.Minim
import ddf.minim.analysis.FFT
import kong.unirest.Unirest
import processing.core.PApplet
import java.awt.EventQueue
import java.awt.event.KeyEvent
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class Visualizer : PApplet() {

    /* Setup Helper Functions */
    private fun setupMinim() {
        minim = Minim(this)
        if (MV_DEBUG) minim.debugOn()
    }

    private fun setupAudioPlayer(){
        audioPlayer = minim.loadFile("init.mp3")
    }

    private fun setupFFT() {
        // Setup FFT
        fft = FFT(audioPlayer?.bufferSize()!!, audioPlayer?.sampleRate()!!)
        fft.logAverages(minBandwidth, bandsPerOctave)
        fft.window(FFT.GAUSS)

        avgSize = fft.avgSize()
        fftSmooth = FloatArray(avgSize)

        println(avgSize)
    }


    /* PApplet Overrides */
    override fun settings() {
        setupMinim()
        setupAudioPlayer()
        setupFFT()

        visRef = WeakReference(this)

        size(900, 740, JAVA2D)
        smooth(2)
    }

    override fun setup() {
        strokeCap(SQUARE)
        noiseDetail(1, 0.95f)

        surface.setTitle("MVisualizer V2!")
        surface.setResizable(true)
        surface.setFrameRate(85f)
        surface.setIcon(loadImage(iconFile))

        backgroundVis = GridBackground()

        visualizers = Vector(mutableListOf(
                BubbleBass(),
                StarField(),
                CircularPulse(),
                ColorSignal(),
                DualColorSignal(),
                MixedWaveSignal(),
                ShapeWave(),
                BassNation()
        ))

        /* Setup ControlP5 */
        cp5 = ControlP5(this)
        Controls.setupControls()

        // Need to call this to configure Unirest
        SoundCloudApiHelper.Init()
    }

    override fun stop() {
        try {
            audioPlayer?.close()
            minim.stop()
            super.stop()
        }
        catch (e: Exception) {
            println("Shutdown Error: ${e.message}")
        }
        finally {
            // close resources
            Unirest.shutDown()

            // Exit app
            this.exit()
        }
    }

    override fun draw() {
        try {
            updateMusicQueue()

            fft.forward(audioPlayer?.mix)

            Utils.applySmoothingToFFT(fftSmooth, fft, smoothing)

            // Fill background
            background(0f, 0f, 0f, 120f)

            cAmplitude = lerp(cAmplitude, smoothing * fft.calcAvg(1f, 150f), .1f)
            ellipseR = constrain(cAmplitude * bassKick, 0.1f, circleRadius * 65.0f)
            starSpeed = map(ellipseR, 0.0F, 165 * 2.0F - 50.0F, 0.1F, 6.2F)
            maxDrawHeight = (height / 2) * 0.25f
            spectrumScaleFactor = maxVal - minVal + 0.00001f

            if (drawBackground) {
                backgroundVis.update(this)
                backgroundVis.render(this)
            }

            if (visualizers.size > 0){
                visualizers[visualMode].update(this)
                visualizers[visualMode].render(this)
            }

            renderAudioDurationBar()
        }
        catch (e: Exception) {
            MVUILogger.logException(e)
        }
    }

    override fun mouseReleased() {
        if (mouseY in (height - timeMeterHeight).toInt()..height) {
            val audioPlayerSongLength = audioPlayer?.length()?.toFloat()!!
            val cuePos = map(mouseX.toFloat(), 0f, width.toFloat(), 0f, audioPlayerSongLength)

            audioPlayer?.cue(cuePos.toInt())
        }
    }

    override fun keyReleased() {
        if (key == 'c') changeTransform()

        if (key == 'm') toggleMute()
        if (key == 'n') playNextSongInQueue()

        if (key == 'p') pasteSoundCloudUrl()
        if (key == 'q') EventQueue.invokeLater { InstructionsWindow.getInstance(null) }

        if (key == 's') toggleBackground()
        if (key == 'v') addToMusicQueue(Utils.selectMusicFromGui())

        if (key == 'x') changeVisual()
        if (key == 'z') EventQueue.invokeLater { MVUILogger.getInstance(null) }

        if (keyCode == KeyEvent.VK_SPACE) togglePlay()

        if ((keyEvent.isShiftDown && keyEvent.isControlDown) && keyCode == KeyEvent.VK_Q) exitProcess(-1)
    }


    /* Class Methods */
    private fun renderAudioDurationBar() {
        val audioPlayerPosition = audioPlayer?.position()?.toFloat()!!
        val audioPlayerLength = audioPlayer?.length()?.toFloat()!!

        val posX = map(audioPlayerPosition, 0f, audioPlayerLength, 0f, width.toFloat())

        pushStyle()
        colorMode(HSB)

        noStroke()
        fill(map(audioPlayerPosition, 0f, audioPlayerLength, 0f, 255f), 255f, 255f)
        rect(0f, height - timeMeterHeight, posX, timeMeterHeight)

        popStyle()
    }


    // Function has no usage references but needs to be here because Gui library (ControlP5) uses
    // reflection to find method, and then handles index selection on ScrollableList
    fun dropdown(dropDownSelectedIndex: Int) {
        println("Dropdown - Selected Index: ${selectedSongIndex()} -- Parameter: $dropDownSelectedIndex")
    }


    fun pasteSoundCloudUrl() {
        synchronized(soundCloudTextField){
            soundCloudTextField.text = clipboardHelper.clipboardContents
        }
    }

    fun searchSoundCloudTracks() {
        var errorOccurred = false
        var errorMessage = ""

        val resourceUrl = soundCloudTextField.text

        fun setStatus(error: String) {
            errorOccurred = true
            errorMessage = error
        }

        fun resetStatus() {
            errorOccurred = false
            errorMessage = ""
        }

        if ((resourceUrl.startsWith("https://") ||
                        resourceUrl.startsWith("http://"))) {

            fetchingTracksLabel.setText("Fetching...")

            try {
                val tracks = fetchSoundCloudAudioResource(resourceUrl)
                if (!tracks.isNullOrEmpty()) {
                    addToMusicQueue(tracks)
                } else {
                    setStatus("Could not get requested resource(s) :(, please try again")
                }
            } catch (e: Exception) {
                setStatus(e.message.toString())
            }

            if (errorOccurred) {
                fetchingTracksLabel.setText("Error: $errorMessage - Try Again! :(")
            } else {
                fetchingTracksLabel.setText("Ready")
            }
        } else {
            fetchingTracksLabel.setText("Enter a valid url :D please!")
        }
    }

    private fun fetchSoundCloudAudioResource(url: String): ArrayList<IAudioResource>? {
        var audioSources: ArrayList<IAudioResource>? = null

        val soundCloudResource = SoundCloudApiHelper.get(url)

        if (soundCloudResource != null) {
            audioSources = ArrayList<IAudioResource>()

            when (soundCloudResource) {
                is Track -> {
                    val uriAudioResource = getAudioFromSoundCloudTrack(soundCloudResource)
                    audioSources.add(uriAudioResource)
                }

                is Playlist -> {
                    // Filter stream-able tracks
                    soundCloudResource.tracks.removeIf { !it.isStreamable }
                    soundCloudResource.tracks.forEach {
                        val uriAudioResource = getAudioFromSoundCloudTrack(it)
                        audioSources.add(uriAudioResource)
                    }
                }
            }
        }

        return audioSources
    }


    /* Methods for AudioPlayer & Music Queue */
    private fun getAudioFromSoundCloudTrack(track: Track): UriAudioSource {
        val trackId = track.id.toString()
        val trackTitle = track.title.take(titleLength)
        val trackMediaPath = track.streamUrl

        return UriAudioSource(trackId, trackTitle, trackMediaPath)
    }


    fun addToMusicQueue(audioResources: ArrayList<IAudioResource>?) {
        if (!audioResources.isNullOrEmpty()) {
            for (audioElement in audioResources) {
                addToMusicQueue(audioElement.title(), audioElement)
            }

            /*set selected song index*/
            setMusicQueueSelectedIndex()
        }
    }

    private fun addToMusicQueue(resourceKey: String, audioResource: IAudioResource) {
        synchronized(musicQueue) {
            musicQueue.addItem(resourceKey, audioResource)
        }
    }


    private fun updateMusicQueue() {
        synchronized(musicQueue) {
            val previousSongFinishedPlaying: Boolean = audioPlayer?.isPlaying == playerPaused

            if (musicQueue.isNotEmpty && previousSongFinishedPlaying) {
                playNextSongInQueue()
            } else if (musicQueue.isEmpty && previousSongFinishedPlaying) {
                if (playerNotClosed) {
                    audioPlayer?.close()
                    playerNotClosed = false
                    println("Player Closed = ${!playerNotClosed}")
                }
            }
        }
    }

    private fun setMusicQueueSelectedIndex() {
        musicQueue.value = musicQueue.value
    }


    fun playNextSongInQueue() {
        if (musicQueue.isNotEmpty) {
            playNextSong()
        }
    }

    private fun playNextSong() {
        synchronized(musicQueue) {
            val nextSong: IAudioResource = musicQueue.dequeue<IAudioResource>()
            playAudioResource(nextSong, true)
        }
    }


    fun playSelectedSongInQueue() {
        if (musicQueue.isNotEmpty) {
            val selectedSong = getSelectedSong()
            playAudioResource(selectedSong, true)
        }
    }

    private fun getSelectedSong(): IAudioResource {
        val index = selectedSongIndex()
        return musicQueue.getItem(index)["value"] as IAudioResource
    }


    private fun playAudioResource(audioResource: IAudioResource, closeLastSong: Boolean = false) {
        try {
            val songTitle = audioResource.title()
            val fullAudioResourcePath = SoundCloudApiHelper.getFullAudioResourcePath(audioResource)

            if (fullAudioResourcePath.isEmpty()) return

            // Make sure that boolean values don't conflict in mainLoop
            if (playerPaused) togglePlay()

            // Close player if it's not closed already
            if (closeLastSong) {
                audioPlayer?.close()
                playerNotClosed = false
                println("Closed Last Song")
            }

            // Pop song from queue
            musicQueue.removeItem(songTitle)
            println("Queue Pop - selectedSongIndex: ${selectedSongIndex()}")

            // Load song and set title
            audioPlayer = minim.loadFile(fullAudioResourcePath, bufferSize)
            surface.setTitle(songTitle)

            // Setup fft
            fft = FFT(audioPlayer?.bufferSize()!!, audioPlayer?.sampleRate()!!)
            fft.logAverages(minBandwidth, bandsPerOctave)
            fft.window(FFT.GAUSS)

            avgSize = fft.avgSize()
            fftSmooth = FloatArray(avgSize)
            println("FFTAvgSize: $avgSize")

            // Set player gain and play
            audioPlayer?.gain = musicGain
            audioPlayer?.play()

            // If success, the playing song and audioPlayer are not closed
            playerNotClosed = true

            // Set song queue selected index
            musicQueue.value = 0f
        }
        catch (e: Exception) {
            println(e.message)
        }
    }


    /* Methods for Changing AudioPlayer State */
    fun togglePlay() {
        playerPaused = if (audioPlayer?.isPlaying!!) {
            audioPlayer?.pause()
            true
        } else {
            audioPlayer?.play()
            false
        }

        cp5.getController("pause-song")
                .setCaptionLabel(if (audioPlayerPaused()) "play-song" else "pause-song")
    }

    fun toggleMute() {
        if (audioPlayerMuted()) audioPlayer?.unmute()
        else audioPlayer?.mute()

        cp5.getController("mute-song")
                .setCaptionLabel(if (audioPlayerMuted()) "un-mute-song" else "mute-song")
    }


    /* Methods for Changing Visuals */
    fun changeVisual() {
        visualMode = ++visualMode % visualizers.size
    }

    private fun changeTransform() {
        transformMode = ++transformMode % 4
    }

    fun toggleBackground(){
        drawBackground = !drawBackground
    }
}