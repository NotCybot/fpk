package thro.fpk.song;

import thro.fpk.song_metadata.SongMetadata;

public class StandardSong implements Song{
    private final SongMetadata metadata;
    private final String title;

    public StandardSong(String title, SongMetadata metadata) {
        this.title = title;
        this.metadata = metadata;
    }

    @Override
    public void play() {
        System.out.println("Playing standard song: " + title + ", " + metadata);
    }

    @Override
    public boolean isPremium() {
        return false;
    }
}
