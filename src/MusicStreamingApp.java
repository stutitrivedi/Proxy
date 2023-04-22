import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface SongService {
    Song searchById(Integer songID);
    List<Song> searchByTitle(String title);
    List<Song> searchByAlbum(String album);
}

class RealSongService implements SongService {
    @Override
    public Song searchById(Integer songID) {
        // Simulate delay in real server call
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fetch song metadata from server using songID
        System.out.println("Fetching Song " + songID + " from RealSongService...");
        // Replace with actual implementation to fetch song metadata from server
        // using songID and return the Song object
        return new Song("Sweer Child O' Mine-" + songID, "Guns N' Roses", "Slash " + songID, 300);
    }

    @Override
    public List<Song> searchByTitle(String title) {
        // Simulate delay in real server call
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fetch songs metadata from server using title
        System.out.println("Fetching Songs by title from RealSongService...");
        // Replace with actual implementation to fetch songs metadata from server
        // using title and return a list of Song objects
        return List.of(new Song("Sweer Child O' Mine", "Guns N' Roses", "Slash", 300),
                new Song("Hotel California", "Kings of Hollywood", "Eagles", 180));
    }

    @Override
    public List<Song> searchByAlbum(String album) {
        // Simulate delay in real server call
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fetch songs metadata from server using album
        System.out.println("Fetching Songs by album from RealSongService...");
        // Replace with actual implementation to fetch songs metadata from server
        // using album and return a list of Song objects
        return List.of(new Song("Sweer Child O' Mine", "Guns n' Roses", "Slash", 300),
                new Song("Smells Like Teen Spirit", "Nirvana", "Kurt Cobain", 240),
                new Song("Can't Help Falling in Love", "Blue Hawaii", "Elvis presley", 200));
    }
}

class SongMetadataCacheProxy implements SongService {
    private SongService realSongService;
    private Map<Integer, Song> songCache;
    private Map<String, List<Song>> titleCache;
    private Map<String, List<Song>> albumCache;

    public SongMetadataCacheProxy(SongService realSongService) {
        this.realSongService = realSongService;
        this.songCache = new HashMap<>();
        this.titleCache = new HashMap<>();
        this.albumCache = new HashMap<>();
    }

    @Override
    public Song searchById(Integer songID) {
        if (songCache.containsKey(songID)) {
            System.out.println("\n Fetching Song " + songID + " from cache...");
            return songCache.get(songID);
        } else {
            Song song = realSongService.searchById(songID);
            songCache.put(songID, song);
            return song;
        }
    }

    @Override
    public List<Song> searchByTitle(String title) {
        if (titleCache.containsKey(title)) {
            System.out.println("Fetching Songs by title from cache...");
            return titleCache.get(title);
        } else {
            List<Song> songs = realSongService.searchByTitle(title);
            titleCache.put(title, songs);
            return songs;
        }
    }@Override
    public List<Song> searchByAlbum(String album) {
        if (albumCache.containsKey(album)) {
            System.out.println("Fetching Songs by album from cache...");
            return albumCache.get(album);
        } else {
            List<Song> songs = realSongService.searchByAlbum(album);
            albumCache.put(album, songs);
            return songs;
        }
    }}

class Song {
    private String title;
    private String album;
    private String artist;
    private int duration;public Song(String title, String album, String artist, int duration) {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
    }

// Getters and Setters

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", duration=" + duration +
                '}';
    }
}

public class MusicStreamingApp {
    public static void main(String[] args) {
// Create RealSongService
        RealSongService realSongService = new RealSongService();    // Create SongMetadataCacheProxy with RealSongService
        SongService songService = new SongMetadataCacheProxy(realSongService);

        // Search for songs using the methods from the SongService
        System.out.println(songService.searchById(1));
        System.out.println(songService.searchByTitle("Sweet Child O' Mine"));
        System.out.println(songService.searchByAlbum("Guns N' Roses"));

        // Perform the same searches again to see the advantage of using a proxy
        System.out.println("\n" + songService.searchById(1));
        System.out.println(songService.searchByTitle("Sweet Child O' Mine "));
        System.out.println(songService.searchByAlbum("Guns N' Roses "));
    }
}
