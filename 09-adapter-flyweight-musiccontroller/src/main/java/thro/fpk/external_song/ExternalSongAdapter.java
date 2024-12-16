package thro.fpk.external_song;

import thro.fpk.song.Song;

public class ExternalSongAdapter implements Song {
    private final ExternalSong externalSong;

    public ExternalSongAdapter(ExternalSong externalSong) {
        this.externalSong = externalSong;
    }

    @Override
    public void play() {
        System.out.println("Playing external song: " + "Track" + externalSong.trackName + ", Artist: " + externalSong.musician + ", no further informations available.");
    }

    @Override
    public boolean isPremium() {
        return true;
    }
}
