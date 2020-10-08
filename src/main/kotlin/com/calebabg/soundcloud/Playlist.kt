package com.calebabg.soundcloud

import java.util.ArrayList

class Playlist {
    val id: Int? = null
    val createdAt: String? = null
    val userId: Int? = null
    val duration: Int? = null
    val sharing: String? = null
    val tagList: String? = null
    val permalink: String? = null
    val description: String? = null
    val isStreamable: Boolean? = null
    val isDownloadable: Boolean? = null
    var genre: String? = null
    val release: String? = null
    val purchaseUrl: String? = null
    val labelId: Int? = null
    val labelName: String? = null
    val type: String? = null
    val playlistType: String? = null
    val ean: Int? = null
    var title: String? = null
    val releaseYear: Int? = null
    val releaseMonth: Int? = null
    val releaseDay: Int? = null
    val license: String? = null
    val uri: String? = null
    val permalinkUrl: String? = null
    val artworkUrl: String? = null

    val tracks: ArrayList<Track> = ArrayList()

    override fun toString(): String {
        return ("Playlist [id=" + id + ", created_at=" + createdAt
                + ", user_id=" + userId + ", duration="
                + duration + ", sharing=" + sharing + ", tag_list=" + tagList
                + ", permalink=" + permalink + ", description=" + description
                + ", streamable=" + isStreamable + ", downloadable="
                + isDownloadable + ", genre=" + genre + ", release=" + release
                + ", purchase_url=" + purchaseUrl + ", label_id=" + labelId
                + ", label_name=" + labelName + ", type=" + type
                + ", playlist_type=" + playlistType + ", ean=" + ean
                + ", title=" + title + ", release_year=" + releaseYear
                + ", release_month=" + releaseMonth + ", release_day="
                + releaseDay + ", license=" + license + ", uri=" + uri
                + ", permalink_url=" + permalinkUrl + ", artwork_url="
                + artworkUrl + ", tracks=" + tracks + "]")
    }
}