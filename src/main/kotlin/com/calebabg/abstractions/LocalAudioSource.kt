package com.calebabg.abstractions

class LocalAudioSource(private val title: String, private val resourcePath: String) : AudioResource {
    override fun id(): String = ""
    override fun title(): String = title
    override fun mediaPath(): String = resourcePath
    override fun resourceType(): AudioResourceType = AudioResourceType.LOCAL_RESOURCE
}