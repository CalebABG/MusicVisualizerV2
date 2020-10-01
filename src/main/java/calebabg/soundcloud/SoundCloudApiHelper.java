package calebabg.soundcloud;

import calebabg.abstractions.IAudioResource;
import calebabg.abstractions.LocalAudioSource;
import calebabg.abstractions.UriAudioSource;
import calebabg.main.Utils;
import com.google.gson.Gson;
import kong.unirest.*;
import org.json.JSONObject;

public class SoundCloudApiHelper {
    private static final String CLIENT_ID = Utils.Companion.getHtml("/token.txt", 256);
    private static final String SOUNDCLOUD_API_RESOLVE_URL = "https://api.soundcloud.com/resolve?url=";

    private static final Gson gson = new Gson();

    public static void Init() {
        Unirest.config()
                .socketTimeout(5500)
                .connectTimeout(8000)
                .concurrency(10, 5)
                .setDefaultHeader("Accept", "application/json")
                .followRedirects(false)
                .enableCookieManagement(false)
                .automaticRetries(true);
    }

    public static Object get(String url) {
        Object returnObj = null;

        try {
            JSONObject responseJson = resolve(url).getObject();
            //System.out.println(responseJson);

            String responseKind = responseJson.getString("kind");

            switch (responseKind) {
                case "track":
                    returnObj = gson.fromJson(responseJson.toString(), Track.class);
                    break;

                case "playlist":
                    returnObj = gson.fromJson(responseJson.toString(), Playlist.class);
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return returnObj;
    }

    private static String getFullStreamUrl(String streamUrl) {
        String fullStreamUrl = null;

        try {
            String streamApiEnd = "?consumer_key=";
            String api = streamUrl + streamApiEnd + SoundCloudApiHelper.CLIENT_ID;
            //System.out.println(api);

            var future = Unirest.get(api)
                    .asJsonAsync(httpResponse ->
                            httpResponse.ifFailure(jsonNodeHttpResponse ->
                                    System.out.println("I/E: " + jsonNodeHttpResponse.getStatusText())));

            fullStreamUrl = future.thenApply(jsonNodeHttpResponse ->
                    jsonNodeHttpResponse.getHeaders().getFirst("Location")).join();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return fullStreamUrl;
    }

    private static JsonNode resolve(String resourceUrl) {
        String resolveApiEnd = "&client_id=";
        String api = SOUNDCLOUD_API_RESOLVE_URL + resourceUrl + resolveApiEnd + CLIENT_ID;
        System.out.println(api);

        final JsonNode[] jsonResponse = {null};

        Unirest.get(api).thenConsume(rawResponse -> {
            var future = Unirest.get(rawResponse.getHeaders().getFirst("Location"))
                    .asJsonAsync(httpResponse ->
                            httpResponse.ifFailure(jsonNodeHttpResponse ->
                                    System.out.println("Error: " + jsonNodeHttpResponse.getStatusText())));

            try {
                jsonResponse[0] = future.thenApply(HttpResponse::getBody).join();
                //System.out.println(jsonResponse[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        return jsonResponse[0];
    }

    public static String getFullAudioResourcePath(IAudioResource audioResource) {
        String url = "";

        if (audioResource instanceof LocalAudioSource) {
            url = audioResource.mediaPath();
        } else if (audioResource instanceof UriAudioSource) {
            url = getFullStreamUrl(audioResource.mediaPath());
        }

        return url;
    }
}
