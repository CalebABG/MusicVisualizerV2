package com.calebabg.abstractions

interface AudioResource {
    fun id(): String
    fun title(): String
    fun mediaPath(): String
    fun resourceType(): AudioResourceType
}