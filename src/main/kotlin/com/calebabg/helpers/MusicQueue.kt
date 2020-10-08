package com.calebabg.helpers

import controlP5.ControlP5
import controlP5.ScrollableList

class MusicQueue(controlP5: ControlP5?, s: String?) : ScrollableList(controlP5, s) {
    fun peek(): Map<String, Any>? {
        var returnMap: Map<String, Any>? = null

        if (items.isNotEmpty()) {
            returnMap = getItem(0)
        }

        return returnMap
    }

    fun <T> dequeue(): T? {
        var returnObj: Any? = null
        val peekItem = peek()

        if (peekItem != null) {
            returnObj = peekItem["value"]

            // remove item
            val itemName = peekItem["name"] as String?
            removeItem(itemName)
        }

        return if (returnObj != null) returnObj as T else null
    }

    val isEmpty: Boolean get() = items.isEmpty()
    val isNotEmpty: Boolean get() = !isEmpty

    fun size(): Int {
        return items.size
    }
}