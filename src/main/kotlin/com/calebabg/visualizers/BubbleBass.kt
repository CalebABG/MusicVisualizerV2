package com.calebabg.visualizers

import com.calebabg.abstractions.Visualization
import com.calebabg.main.bassBubbleCount
import com.calebabg.main.fftSmooth
import com.calebabg.pojo.BassBubble
import processing.core.PApplet

class BubbleBass : Visualization {
    private var bassBubbles = Array(bassBubbleCount) { BassBubble() }

    override fun update(app: PApplet) {
        val fftCappedVal = fftSmooth.size.coerceAtMost(bassBubbles.size)
        for (i in 0 until fftCappedVal) bassBubbles[i].update(app)
    }

    override fun render(app: PApplet) {
        val fftCappedVal = fftSmooth.size.coerceAtMost(bassBubbles.size)
        for (i in 0 until fftCappedVal) bassBubbles[i].render(app)
    }
}