package thro.fpk.song_metadata;

public class SongMetadata {
    private final String artist;
    private final String album;
    private final String genre;

    public SongMetadata(String artist, String album, String genre) {
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Artist: " + artist + ", Album: " + album + ", Genre: " + genre;
    }
}
