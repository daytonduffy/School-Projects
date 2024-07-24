import java.util.Iterator;

public class SongTests {
    public static void main(String[] args) {
        SongCollection songs = new SongCollection();

        Song song1 = new Song("Song1", "Artist1");
        Song song2 = new Song("Song2", "Artist2");
        Song song3 = new Song("Song3", "Artist3");
        Song song4 = new Song("Song4", "Artist4");
        Song song5 = new Song("Song5", "Artist5");

        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);
        songs.add(song5);


        //analysisMethodA(songs);
        analysisMethodB(songs);
        //analysisMethodC(songs);

        
        boolean testSong = testSong();
        if(testSong) {
            System.out.println("Hero-Song");
        }else {
            System.out.println("Zero-Song");
        }
        
        boolean testCollection = testSongCollection();
        if(testCollection) {
            System.out.println("Hero-Collection");
        }else {
            System.out.println("Zero-Collecion");
        }
        
        boolean testPlaylist = testPlaylist();
        if(testPlaylist) {
            System.out.println("Hero-Playlist");
        }else {
            System.out.println("Zero-Playlist");
        }
        
       
        
    }
    
    
    public static boolean testSong(){
        Song test1 = new Song("title", "artist");
        Song test2 = new Song("title", "artist");
        
        String toString = test1.toString();
        if(!toString.equals("title by artist")) {
            return false;
        }
        
        if(test1.equals("bad")) {
            return false;
        }
        
        if(!test1.equals(test2)) {
            return false;
        }
        
        return true;
    }
    
    public static boolean testSongCollection() {
        Song song1 = new Song("Song1", "Artist1");
        Song song2 = new Song("Song2", "Artist2");
        Song song3 = new Song("Song3", "Artist3");
        Song song4 = new Song("Song4", "Artist4");
        Song song5 = new Song("Song5", "Artist5");
        
        
        SongCollection playlist1 = new SongCollection();
        
        playlist1.add(song1);
        playlist1.add(song2);
        playlist1.add(song3);
        playlist1.add(song4);
        playlist1.add(song5);
        
        Song test1 = playlist1.remove();
        if(!test1.equals(song1)) {
            return false;
        }
        
        //just here to test limits of class
        playlist1.remove();
        playlist1.remove();
        playlist1.remove();
        playlist1.remove();
        
        playlist1.add(song1);
        
        return true;
    }
    
    
    public static boolean testPlaylist() {
        SongCollection songs1 = new SongCollection();
        SongCollection songs2 = new SongCollection();
        SongCollection songs3 = new SongCollection();
        
        Song song1 = new Song("Song1", "Artist1");
        Song song2 = new Song("Song2", "Artist2");
        Song song3 = new Song("Song3", "Artist3");
        Song song4 = new Song("Song4", "Artist4");
        Song song5 = new Song("Song5", "Artist5");
        
        songs1.add(song1);
        songs1.add(song2);
        songs1.add(song3);
        songs1.add(song4);
        songs1.add(song5);
        
        songs2.add(song1);
        songs2.add(song2);
        songs2.add(song3);
        songs2.add(song4);
        songs2.add(song5);
        
        songs3.add(song1);
        songs3.add(song2);
        songs3.add(song3);
        songs3.add(song4);
        songs3.add(song5);
        
        Iterator<Song> playlist = songs1.iterator();
        for(Song song: songs1) {
            System.out.println(playlist.next());
        }
        
        
        
        return true;
    }
    public static void analysisMethodA(SongCollection songs) {
        songs.add(new Song("C is for Cookie.", "Cookie Monster"));
        songs.add(new Song("Rubber Duckie.", "Ernie"));
        songs.add(new Song("Elmo's Song.", "Elmo"));
       }
    public static void analysisMethodB(SongCollection songs) {
        SongCollection copy = new SongCollection();
        for(Song song: songs) {
        copy.add(song);
        }
        for(Song song: copy) {
        System.out.println(song);
        }
       }
       
    public static void analysisMethodC(SongCollection songs) {
        Iterator<Song> playlist = songs.iterator();
        for(int i=0;i<1000;i++)
        if(playlist.hasNext())
        System.out.println( playlist.next() );
       }
}
