package com.calebabg.soundcloud

class Track {
    val id: Int? = null
    val createdAt: String? = null
    val userId: Int? = null
    val duration: Int? = null
    val isCommentable: Boolean = false
    val state: String? = null
    val sharing: String? = null
    val tagList: String? = null
    val permalink: String? = null
    val description: String? = null
    val isStreamable: Boolean = false
    val isDownloadable: Boolean = false
    val genre: String? = null
    val release: String? = null
    val purchaseUrl: String? = null
    val labelId: Int? = null
    val labelName: String? = null
    val isrc: String? = null
    val videoUrl: String? = null
    val trackType: String? = null
    val keySignature: String? = null
    val bpm: String? = null
    val title: String? = null
    val releaseYear: Int? = null
    val releaseMonth: Int? = null
    val releaseDay: Int? = null
    val originalFormat: String? = null
    val originalContentSize: Int? = null
    val license: String? = null
    val uri: String? = null
    val permalinkUrl: String? = null
    val artworkUrl: String? = null
    val waveformUrl: String? = null
    val streamUrl: String? = null
    val downloadUrl: String? = null
    val playbackCount: Int? = null
    val downloadCount: Int? = null
    val favoritingsCount: Int? = null
    val commentCount: Int? = null
    val attachmentsUri: String? = null

    override fun toString(): String {
        return ("Track [id=" + id + ", created_at=" + createdAt + ", user_id="
                + userId + ", duration=" + duration + ", commentable="
                + isCommentable + ", state=" + state + ", sharing=" + sharing
                + ", tag_list=" + tagList + ", permalink=" + permalink
                + ", streamable=" + isStreamable
                + ", downloadable=" + isDownloadable + ", genre=" + genre
                + ", release=" + release + ", purchase_url=" + purchaseUrl
                + ", label_id=" + labelId + ", label_name=" + labelName
                + ", isrc=" + isrc + ", video_url=" + videoUrl
                + ", track_type=" + trackType + ", key_signature="
                + keySignature + ", bpm=" + bpm + ", title=" + title
                + ", release_year=" + releaseYear + ", release_month="
                + releaseMonth + ", release_day=" + releaseDay
                + ", original_format=" + originalFormat
                + ", original_content_size=" + originalContentSize
                + ", license=" + license + ", uri=" + uri + ", permalink_url="
                + permalinkUrl + ", artwork_url=" + artworkUrl
                + ", waveform_url=" + waveformUrl
                + ", stream_url=" + streamUrl + ", download_url="
                + downloadUrl + ", playback_count=" + playbackCount
                + ", download_count=" + downloadCount + ", favoritings_count="
                + favoritingsCount + ", comment_count=" + commentCount
                + ", attachments_uri=" + attachmentsUri + "]")
    }
}