package com.calebabg.gui

import com.calebabg.helpers.MusicQueue
import com.calebabg.helpers.Utils
import com.calebabg.main.*
import com.calebabg.soundcloud.SoundCloudApiHelper
import controlP5.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import processing.core.PConstants

object Controls {
    private lateinit var tabCaptionLabelFont: ControlFont
    private lateinit var sliderFont: ControlFont
    private lateinit var buttonFont: ControlFont
    private lateinit var textFieldFont: ControlFont

    fun setupControls() {
        sliderFont  = ControlFont(visRef.get()!!.createFont("Georgia", 15f, true))
        buttonFont = ControlFont(visRef.get()!!.createFont("Georgia", 14f, true))
        textFieldFont = ControlFont(visRef.get()!!.createFont("Georgia", 14f, true))
        tabCaptionLabelFont = ControlFont(visRef.get()!!.createFont("Georgia", 14f, true))

        // will need to change all three values
        // if offset is changed dramatically
        val slOffset = 35f
        val slStartYLoc = 10f
        val slSpacing = 10f

        //slider width and height
        val slw = 255f
        //val slw = 265f
        val slh = 20f

        // group number 1
        val slidersGroup = cp5.addGroup("sliders (press q for help)")
            .setHeight(25)
            .setBackgroundHeight((slOffset * 9 + slSpacing).toInt())

        slidersGroup.captionLabel.font = sliderFont
        slidersGroup.captionLabel.align(PConstants.CENTER, PConstants.CENTER)

        var tempBHeight = slStartYLoc

        //smoothing slider
        val smoothingSlider = createSlider(0.04f, slidersGroup, "smoothing", 0f, slStartYLoc, slw, slh, 0.5f, 1.0f, smoothing)
            .addListener { p0 -> smoothing = p0.value }

        //amplitude slider
        tempBHeight = 1 * slOffset + slSpacing
        val amplitudeSlider = createSlider(0.02f, slidersGroup, "amplitude", 0f, tempBHeight, slw, slh, 1f, 300f, amplitude)
            .addListener { p0 -> amplitude = p0.value }

        //basskick slider and button
        tempBHeight = 2 * slOffset + slSpacing
        val bassKickSlider = createSlider(0.05f, slidersGroup, "bass-kick", 0f, tempBHeight, slw, slh, 0.1f, 120f, bassKick)
            .addListener { p0 -> bassKick = p0.value }

        val radiusSlider = createSlider(0.04f, slidersGroup, "radius", 0f, 3 * slOffset + slSpacing, slw, slh, 50f, 500f, circleRadius)
            .addListener { p0 -> circleRadius = p0.value }

        val barStepSlider = createSlider(0.3f, slidersGroup, "bar-step", 0f, 4 * slOffset + slSpacing, slw, slh, 1f, 30f, barStep.toFloat())
            .addListener { p0 -> barStep = p0.value.toInt() }

        val strokeWeightSlider = createSlider(0.03f, slidersGroup, "stroke-weight", 0f, 5 * slOffset + slSpacing, slw, slh, 1f, 60f, strokeWeight)
            .addListener { p0 -> strokeWeight = p0.value }

        val angleOffsetSlider = createSlider(0.004f, slidersGroup, "angle-offset", 0f, 6 * slOffset + slSpacing, slw, slh, 1f, 250f, angOffset)
            .addListener { p0 -> angOffset = p0.value }

        val backgroundStepSlider = createSlider(0.05f, slidersGroup, "background-step", 0f, 7 * slOffset + slSpacing, slw, slh, 10f, 180f, backgroundStep.toFloat())
            .addListener { p0 -> backgroundStep = p0.value.toInt() }

        val musicGainSlider = createSlider(0.02f, slidersGroup, "music-gain", 0f, 8 * slOffset + slSpacing, slw, slh, -30f, 10f, musicGain)
            .addListener { p0 ->
                musicGain = p0.value
                audioPlayer?.gain = musicGain
            }


        //Setup Accordion
        val musicQueueTabKey = "music"
        val controlsTabKey = "default"
        val soundCloudTabKey = "soundcloud"

        //captions
        val controlsTabCaption = "visual"
        val musicQueueTabCaption = "music"
        val soundcloudTabCaption = "soundcloud"

        cp5.addTab(controlsTabKey)
            .setHeight(25)
            .setColorLabel(visRef.get()!!.color(255))
            .setColorActive(visRef.get()!!.color(255, 128, 0))
            .setCaptionLabel(controlsTabCaption).captionLabel.font = sliderFont

        val controlsAccordion = cp5.addAccordion("controlsAcc")
            .setPosition(1f, 30f)
            .setWidth(slw.toInt())
            .addItem(slidersGroup)
            .moveTo(controlsTabKey)


        /*Music queue tab*/
        cp5.getTab(musicQueueTabKey)
            .activateEvent(true)
            .setColorActive(visRef.get()!!.color(255, 128, 0))
            .setHeight(25)
            .setId(1)
            .setCaptionLabel(musicQueueTabCaption).captionLabel.font = sliderFont

        val musicQueueGroup = cp5.addGroup("music-buttons")
            .setCaptionLabel("Buttons")
            .setHeight(25)
            .setBackgroundHeight(150)

        musicQueueGroup.captionLabel.font = sliderFont
        musicQueueGroup.captionLabel.align(PConstants.CENTER, PConstants.CENTER)

        val musicQueueAccordion = cp5.addAccordion("musicQueueAcc")
            .setPosition(1f, 30f)
            .setWidth(slw.toInt())
            .addItem(musicQueueGroup)
            .moveTo(musicQueueTabKey)

        //Button constants
        val btnOffset = 15f
        val btnStartYLoc = 10f
        val bw = 125f //button width
        val btnSpacingX = 5f //button horizontal spacing
        val bh = 25f //button width
        val btnSpacingY = 15f //button vertical spacing

        createButton(musicQueueGroup, "add-songs", 0f, btnStartYLoc, bw, bh).addListener {
            run {
                visRef.get()!!.addToMusicQueue(Utils.selectMusicFromGui())
            }
        }

        createButton(musicQueueGroup, "next-visual", bw + btnSpacingX, btnStartYLoc, bw, bh).addListener {
            run {
                visRef.get()!!.changeVisual()
            }
        }

        createButton(musicQueueGroup, "pause-song", 0f, 2 * btnOffset + btnSpacingY, bw, bh).addListener {
            run {
                visRef.get()!!.togglePlay()
            }
        }

        createButton(musicQueueGroup, "mute-song", bw + btnSpacingX, 2 * btnOffset + btnSpacingY, bw, bh).addListener {
            run {
                visRef.get()!!.toggleMute()
            }
        }

        createButton(musicQueueGroup, "toggle-dots", 0f, 4 * btnOffset + btnSpacingY + 5, bw, bh).addListener {
            run {
                visRef.get()!!.toggleBackground()
            }
        }

        createButton(musicQueueGroup, "next-song", bw + btnSpacingX, 4 * btnOffset + btnSpacingY + 5, bw, bh).addListener {
            run {
                visRef.get()!!.playNextSongInQueue()
            }
        }


        createButton(musicQueueGroup, "play-selected", 0f, 6 * btnOffset + btnSpacingY + 10, bw, bh).addListener {
            run {
                visRef.get()!!.playSelectedSongInQueue()
            }
        }

        musicQueue = MusicQueue(cp5, "dropdown")
        musicQueue.setLabel("Music Queue")
            .setPosition(1f, 160f)
            .setSize(400, 400)
            .setBarHeight(28)
            .setItemHeight(26)
            .setFont(visRef.get()!!.createFont("Georgia", 14f))
            .setType(ScrollableList.LIST) // currently supported DROPDOWN and LIST
            .moveTo(musicQueueGroup)

        /*Soundcloud Tab */
        if (SoundCloudApiHelper.hasClientId()) {
            val soundcloudTracksGroup = cp5.addGroup("search")
                .setHeight(25)
                .setBackgroundHeight(150)

            soundcloudTracksGroup.captionLabel.font = sliderFont
            soundcloudTracksGroup.captionLabel.align(PConstants.CENTER, PConstants.CENTER)

            val soundcloudAccordion = cp5.addAccordion("soundcloudAcc")
                .setPosition(1f, 30f)
                .setWidth(slw.toInt())
                .addItem(soundcloudTracksGroup)
                .moveTo(soundCloudTabKey)


            cp5.addTextlabel("soundcloud-text-label")
                .setText("Press 'Ctrl+V' to Paste a SoundCloud URL, or Enter One")
                .setPosition(0f, 10f)
                .setFont(tabCaptionLabelFont)
                .moveTo(soundcloudTracksGroup)

            soundCloudTextField = cp5.addTextfield("soundcloud-textfield")
                .setLabel("")
                .setPosition(2f, 35f)
                .setSize(400, 30)
                .setFont(textFieldFont)
                .setColor(visRef.get()!!.color(255))
                .moveTo(soundcloudTracksGroup)

            createButton(soundcloudTracksGroup, "paste-link", 1f, 75f, 120f, 25f).addListener {
                run {
                    visRef.get()!!.pasteSoundCloudUrl()
                }
            }

            createButton(soundcloudTracksGroup, "get-songs", 125f, 75f, 120f, 25f).addListener {
                GlobalScope.launch {
                    visRef.get()!!.searchSoundCloudTracks()
                }
            }

            createButton(soundcloudTracksGroup, "clear", 250f, 75f, 120f, 25f).addListener {
                run {
                    synchronized(soundCloudTextField) {
                        soundCloudTextField.clear()
                    }
                }
            }

            fetchingTracksLabel = cp5.addTextlabel("soundcloud-loading-text-label")
                .setText("Ready")
                .setPosition(380f, 80f)
                .setFont(tabCaptionLabelFont)
                .moveTo(soundcloudTracksGroup)

            cp5.getTab(soundCloudTabKey).id = 2

            cp5.addTab(soundCloudTabKey)
                .setHeight(25)
                .setColorLabel(visRef.get()!!.color(255))
                .setColorActive(visRef.get()!!.color(255, 128, 0))
                .setCaptionLabel(soundcloudTabCaption).captionLabel.font = sliderFont

            soundcloudAccordion.open()
        }

        musicQueueAccordion.open()
        controlsAccordion.open()
    }

    /**
     * Ease of use function for creating sliders from the controlP5 library
     * The last parameter can be omitted as can be seen in the above slider creations because
     * the default PFont for all sliders is the class static font: Font.SERIF, Font.PLAIN, 15
     */
    private fun createSlider(scrollSense: Float, group: Group, name: String, loc_x: Float, loc_y: Float, size_w: Float, size_h: Float, range_min: Float,
                             range_max: Float, init_v: Float, font: ControlFont = sliderFont): Slider {
        // add a vertical slider
        val tSlider = cp5.addSlider(name)
            .setPosition(loc_x, loc_y)
            .setSize(size_w.toInt(), size_h.toInt())
            .setRange(range_min, range_max)
            .setValue(init_v)
            .setScrollSensitivity(scrollSense)
            .setSliderMode(Slider.FLEXIBLE)
            .moveTo(group)

        tSlider.valueLabel.font = font
        tSlider.valueLabel.align(ControlP5.RIGHT, ControlP5.RIGHT)

        tSlider.captionLabel.font = font
        tSlider.captionLabel.align(ControlP5.LEFT, ControlP5.LEFT)

        return tSlider
    }

    /**
     * Ease of use function for creating buttons from the controlP5 library
     * The last parameter can be omitted as can be seen in the above button creations because
     * the default PFont for all buttons is the class static font: Font.SERIF, Font.PLAIN, 15
     */
    private fun createButton(group: Group? = null, name: String, loc_x: Float, loc_y: Float, size_w: Float, size_h: Float,
                             font: ControlFont = buttonFont): Button {
        // add a button
        val tButton = cp5.addButton(name)
            .setPosition(loc_x, loc_y)
            .setSize(size_w.toInt(), size_h.toInt())

        if (group != null) tButton.moveTo(group)

        tButton.captionLabel.font = font

        return tButton
    }
}