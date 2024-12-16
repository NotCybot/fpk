package thro.fpk.song;

import thro.fpk.User;

public class SongProxy implements Song {
    private Song realSong;
    private final User user;

    public SongProxy(User user) {
        this.user = user;
    }

    @Override
    public void play() {
        if (realSong.isPremium() && !user.isPremium()) {
            System.out.println("You must be a premium user to play this song.");
        } else {
            realSong.play();
        }
    }

    @Override
    public boolean isPremium() {
        return realSong.isPremium();
    }
    public void setSong (Song song) {
        this.realSong = song;
    }
}
