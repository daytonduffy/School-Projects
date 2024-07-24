import java.util.NoSuchElementException;

public class ReversePlaylist implements java.util.Iterator<Song> {
    private DoublyLinkedNode<Song> curSong;
    
    //Song assumed to be the tail
    public ReversePlaylist(DoublyLinkedNode<Song> song) {
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
            curSong = curSong.getPrevious();
            return temp;
        }else {
            throw new NoSuchElementException("No more songs in playlist");
        }
        
    }

}