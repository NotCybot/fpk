package thro.fpk;

import thro.fpk.song.Song;
import thro.fpk.song.SongProxy;

public class User {
    private final String name;
    private final boolean isPremium;
    private final SongProxy songProxy;

    public User(String name, boolean isPremium) {
        this.name = name;
        this.isPremium = isPremium;
        this.songProxy = new SongProxy(this);
    }
    public boolean isPremium() {
        return isPremium;
    }
    public  String getName() {
        return name;
    }
    void playSong(Song song) {
        System.out.println("User: " + name);
        songProxy.setSong(song);
        songProxy.play();
    }
}
