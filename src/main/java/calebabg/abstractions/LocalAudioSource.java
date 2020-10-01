package calebabg.abstractions;

public class LocalAudioSource implements IAudioResource {
    private final String title;
    private final String resourcePath;

    public LocalAudioSource(String title, String resourcePath) {
        this.title = title;
        this.resourcePath = resourcePath;
    }

    public String id() { return ""; }

    public String title() {
        return title;
    }

    public String mediaPath() {
        return resourcePath;
    }

    public AudioResourceType resourceType() {
        return AudioResourceType.LOCAL_RESOURCE;
    }
}
