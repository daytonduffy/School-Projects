import java.util.NoSuchElementException;
import java.util.Iterator;

public class SongCollection implements Iterable<Song>{
    private DoublyLinkedNode<Song> head;
    private DoublyLinkedNode<Song> tail;
    private boolean playDirectionForward;
    
    public SongCollection() {
        head = null;
        tail = null;
        playDirectionForward = true;
        
    }
    
    public void setPlayDirection(boolean isForward) {
        playDirectionForward = isForward;
    }

    
    // adds song to the end/tail of this doubly linked list
    // when song is null, throws a NullPointerException
    public void add(Song song) {
        if(song == null) {
            throw new NullPointerException("Song is null");
        }
        
        if(head == null && tail == null) {
            DoublyLinkedNode<Song> songNode = new DoublyLinkedNode<Song>(song);
            head = songNode;
            tail = songNode;
        }else if(head.getNext() == null){
            DoublyLinkedNode<Song> songNode = new DoublyLinkedNode<Song>(song);
            head.setNext(songNode);
            songNode.setPrevious(head);
            tail = songNode;
        }else {
            DoublyLinkedNode<Song> songNode = new DoublyLinkedNode<Song>(song);
            songNode.setPrevious(tail);
            tail.setNext(songNode);
            tail = songNode;
        }
    }
    
    // removes and returns song from the front/head of this list
    // when list is empty, throws a NoSuchElementException
    public Song remove() {
        if(head == null) {
            throw new NoSuchElementException("List is empty");
        }
        
        
        if(head.getNext() == null) {
            Song song = head.getData();
            head = null;
            tail = null;
            return song;
        }else {
            Song song = head.getData();
        
            DoublyLinkedNode<Song> newHead = new DoublyLinkedNode<Song>(head.getNext().getData());
            
            newHead.setNext(head.getNext().getNext());
            head = newHead;
            return song;
        }
        
    }
    public interface Iterable<T> {
        public Iterator<Song> iterator();    
    }
    
    public Iterator<Song> iterator(){
        
        if(playDirectionForward) {
            Playlist list = new Playlist(head);
            return list;
        }else {
            ReversePlaylist list = new ReversePlaylist(tail);
            return list;
        }
    }
    
    
    
}
///////////////////////////////////////////////////////////////////////////////////
//For each of the following big-O time complexity analyses, consider the problem
//size to be the number of Songs that are stored within the argument songs, when
//the method is first called.
//
//For analysisMethodA, the big-O time complexity is _____O(1)_______.
//
//For analysisMethodB, the big-O time complexity is _____O(n)_______.
//
//For analysisMethodC, the big-O time complexity is _____O(n)_______.
//
///////////////////////////////////////////////////////////////////////////////////
