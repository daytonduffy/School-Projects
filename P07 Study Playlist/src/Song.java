
public class Song {
    private String title;
    private String artist;
    
    //Method constructs a new Song object
    //Sets title and artist as given
    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }
    
    //Method returns string representation of the song
    //"TITLE by ARTIST"
    public String toString() {
        return (title + " by " + artist);
    }
 
    private String getArtist() {
        return artist;
    }
    private String getTitle() {
        return title;
    }
    
    //Method returns false if any part of the songs aren't the same
    public boolean equals(Object other) {
        
        if(!(other instanceof Song)) {
            return false;
        }
        if(!((Song) other).getArtist().equals(artist)) {//Pretty sure this works
            return false;
        }
        if(!((Song) other).getTitle().equals(title)) {
            return false;
        }
        
        return true;
    }
}
