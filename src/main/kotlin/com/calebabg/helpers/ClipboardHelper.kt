package com.calebabg.helpers

import java.awt.Toolkit
import java.awt.datatransfer.*
import java.io.IOException

class ClipboardHelper : ClipboardOwner {
    override fun lostOwnership(clipboard: Clipboard, contents: Transferable) {}

    var clipboardContents: String?
        get() {
            var result = ""
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            //odd: the Object param of getContents is not currently used
            val contents = clipboard.getContents(null)
            val hasTransferableText = contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)
            if (hasTransferableText) {
                try {
                    result = contents!!.getTransferData(DataFlavor.stringFlavor) as String
                } catch (ex: UnsupportedFlavorException) {
                    println(ex)
                    ex.printStackTrace()
                } catch (ex: IOException) {
                    println(ex)
                    ex.printStackTrace()
                }
            }
            return result
        }

        set(string) {
            val stringSelection = StringSelection(string)
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            clipboard.setContents(stringSelection, this)
        }
}