package thro.fpk.song_metadata;

import thro.fpk.song.Song;

import java.util.HashMap;
import java.util.Map;

public class SongMetadataFactory {
    private final Map<String, SongMetadata> metadataPool = new HashMap<>();

    public SongMetadata getMetadata(String artist, String album, String genre) {
        String key = artist + album + genre;
        return metadataPool.computeIfAbsent(key, k -> new SongMetadata(artist, album, genre));
    }
}
