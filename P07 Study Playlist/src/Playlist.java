import java.util.NoSuchElementException;

public class Playlist implements java.util.Iterator<Song> {
    private DoublyLinkedNode<Song> curSong;
    
    //Song assumed to be the head
    public Playlist(DoublyLinkedNode<Song> song) {
        curSong = song;
    }

    public boolean hasNext() {
        if(curSong == null) {
            return false;
        }
        return true;
    }
    
    public Song next() {
        if(hasNext()) {
            Song temp = curSong.getData();
            curSong = curSong.getNext();
            return temp;
        }else {
            throw new NoSuchElementException("No more songs in playlist");
        }
        
    }

}