package com.calebabg.abstractions

import processing.core.PApplet

interface Visualization {
    fun update(app: PApplet)
    fun render(app: PApplet)
}