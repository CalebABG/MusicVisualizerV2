package calebabg.abstractions;

public interface IAudioResource {
    String id();
    String title();
    String mediaPath();
    AudioResourceType resourceType();
}
