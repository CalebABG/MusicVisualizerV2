package com.calebabg.abstractions

class UriAudioSource(private val id: String, private val title: String, private val resourcePath: String) : AudioResource {
    override fun id(): String = id
    override fun title(): String = title
    override fun mediaPath(): String = resourcePath
    override fun resourceType(): AudioResourceType = AudioResourceType.URI_RESOURCE
}