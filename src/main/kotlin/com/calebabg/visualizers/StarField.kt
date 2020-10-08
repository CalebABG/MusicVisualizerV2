package com.calebabg.visualizers

import com.calebabg.abstractions.Visualization
import com.calebabg.main.starCount
import com.calebabg.pojo.Star
import processing.core.PApplet

class StarField : Visualization {
    var stars = Array(starCount) { Star() }

    override fun update(app: PApplet) {
        for (s in stars) s.update(app)
    }

    override fun render(app: PApplet) {
        for (s in stars) s.render(app)
    }
}