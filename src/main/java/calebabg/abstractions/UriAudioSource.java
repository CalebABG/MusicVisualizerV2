package calebabg.abstractions;

public class UriAudioSource implements IAudioResource {
    private final String id;
    private final String title;
    private final String resourcePath;

    public UriAudioSource(String id, String title, String resourcePath) {
        this.id = id;
        this.title = title;
        this.resourcePath = resourcePath;
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String mediaPath() {
        return resourcePath;
    }

    public AudioResourceType resourceType() {
        return AudioResourceType.URI_RESOURCE;
    }
}
