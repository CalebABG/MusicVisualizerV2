package com.calebabg.soundcloud

import com.calebabg.abstractions.AudioResource
import com.calebabg.abstractions.LocalAudioSource
import com.calebabg.abstractions.UriAudioSource
import com.google.gson.Gson
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.RawResponse
import kong.unirest.Unirest
import kong.unirest.json.JSONObject
import com.calebabg.helpers.Utils

object SoundCloudApiHelper {
    private val CLIENT_ID: String = Utils.getSecret("MVSCTOKEN")
    private const val SOUNDCLOUD_API_RESOLVE_URL = "https://api.soundcloud.com/resolve?url="
    private val gson = Gson()

    fun init() {
        Unirest.config()
            .socketTimeout(5500)
            .connectTimeout(8000)
            .concurrency(10, 5)
            .setDefaultHeader("Accept", "application/json")
            .followRedirects(false)
            .enableCookieManagement(false)
            .automaticRetries(true)
    }

    operator fun get(url: String): Any? {
        var returnObj: Any? = null
        try {
            val responseJson: JSONObject = resolve(url)!!.getObject()
            //System.out.println(responseJson);
            val responseKind: String = responseJson.getString("kind")
            when (responseKind) {
                "track" -> returnObj = gson.fromJson(responseJson.toString(), Track::class.java)
                "playlist" -> returnObj = gson.fromJson(responseJson.toString(), Playlist::class.java)
                else -> {
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
        return returnObj
    }

    private fun getFullStreamUrl(streamUrl: String): String? {
        var fullStreamUrl: String? = null
        try {
            val streamApiEnd = "?consumer_key="
            val api = streamUrl + streamApiEnd + CLIENT_ID
            //System.out.println(api);
            val future = Unirest.get(api)
                .asJsonAsync { httpResponse: HttpResponse<JsonNode?> ->
                    httpResponse.ifFailure { jsonNodeHttpResponse: HttpResponse<JsonNode?> ->
                        println(
                            "I/E: " + jsonNodeHttpResponse.statusText
                        )
                    }
                }
            fullStreamUrl = future.thenApply { jsonNodeHttpResponse: HttpResponse<JsonNode?> ->
                jsonNodeHttpResponse.headers.getFirst("Location")
            }.join()
        } catch (ex: Exception) {
            println(ex.message)
        }
        return fullStreamUrl
    }

    private fun resolve(resourceUrl: String): JsonNode? {
        val resolveApiEnd = "&client_id="
        val api = SOUNDCLOUD_API_RESOLVE_URL + resourceUrl + resolveApiEnd + CLIENT_ID
        //System.out.println(api);

        val jsonResponse = arrayOf<JsonNode?>(null)
        Unirest.get(api).thenConsume { rawResponse: RawResponse ->
            val future =
                Unirest.get(rawResponse.headers.getFirst("Location"))
                    .asJsonAsync { httpResponse: HttpResponse<JsonNode?> ->
                        httpResponse.ifFailure { jsonNodeHttpResponse: HttpResponse<JsonNode?> ->
                            println(
                                "Error: " + jsonNodeHttpResponse.statusText
                            )
                        }
                    }
            try {
                jsonResponse[0] =
                    future.thenApply { obj: HttpResponse<JsonNode?> -> obj.body }
                        .join()
                //System.out.println(jsonResponse[0]);
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return jsonResponse[0]
    }

    fun getFullAudioResourcePath(audioResource: AudioResource): String? {
        var url: String? = ""
        if (audioResource is LocalAudioSource) {
            url = audioResource.mediaPath()
        } else if (audioResource is UriAudioSource) {
            url = getFullStreamUrl(audioResource.mediaPath())
        }
        return url
    }

    fun hasClientId(): Boolean {
        return CLIENT_ID.isNotEmpty()
    }
}