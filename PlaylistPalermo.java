// Michael Palermo
/* 1) The things I added to media is the concept of cost and 
 * made get and set  functions to manipulate the private 
 * data member and integrated it in each subclass. I also added
 *  a constructor that adds in hours if the media requires hours.
 * 2) The things I added to Song is the concept of album and 
 * I added that if there is no album to the song i.e. a single,
 * then the user enters "n/a" to set the variable to null
 * 3)The things I added to Movie is the concept of quality 
 * and made get and set functions to manipulate the data.
 * 4) The things I included in AudioBook is get and set functions
 * for the private data members author, reader, sampling rate,
 * and bit depth. I also added my custom toString function for 
 * AudioBook and the getType function. I added a constructor to 
 * initialize all the data and copy and pasted the same calculate
 * FileSize function from song because they have similar file 
 * formats.*/
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

abstract class Media {
	private String name;
	private int length;		// stores the length in seconds
	private int cost;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) { // length is in seconds
		if (length < 0) {
			this.length = 0;
		} else {
			this.length = length;
		}
	}
	public void setLength(int minutes, int seconds) {
		int len = 60*minutes + seconds;
		setLength(len);
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		if (cost < 0) {
			this.cost = 0;
		} else {
		this.cost = cost;
		}
	}
	public abstract String getType(); // what kind of media this is
	
	public abstract double calculateFileSize();
	
	public String toString() {
		return String.format("Type: %s\nName: %s\nLength: %d seconds",
			getType(),name,length);
	}
	public Media(String name, int seconds) {
		setName(name);
		setLength(seconds);
	}
	public Media(String name, int minutes, int seconds) {
		this(name,minutes*60+seconds);
	}
	public Media(String name, int hours, int minutes, int seconds) {
		this(name,(hours*60+minutes*60)+seconds);
	}
}

class Artist {
	private String name;
	private boolean isBand;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getIsBand() {
		return isBand;
	}
	public void setIsBand(boolean isBand) {
		this.isBand = isBand;
	}
	public void setIsBand(String yn) {
		yn = yn.trim().toLowerCase();
		if (yn.equals("y")) {
			isBand = true;
		} else {
			isBand = false;
		}
	}
	public Artist() {  // default constructor
		name = "Unknown";
		isBand = true;
	}
	public Artist(String name, boolean isBand) {
		setName(name);
		setIsBand(isBand);
	}
	public Artist(String name, String yn) {
		setName(name);
		setIsBand(yn);
	}
	public String getBandString() {
		if (isBand == true) {
			return "Band";
		} else {
			return "Solo Artist";
		}
	}
	public String toString() {
		return String.format("Name: %s\n%s", name, getBandString());
	}
}
class Song extends Media {
	private double samplingRate;  // stores the sampling freq in kHz
	private int bitDepth;  // stores the bitDepth in bits
	private Artist artist;  // ownership
	private String album;
	public double getSamplingRate() {
		return samplingRate;
	}
	public void setSamplingRate(double rate) {
		if (rate < 0) {
			samplingRate = 0;
		} else {
			samplingRate = rate;
		}
	}
	public Artist getArtist() {
		return artist; 
	}
	public void setArtist(Artist a) {
		artist = a;   // artist
	}
	public void setArtist(String name, boolean isBand) {
		artist = new Artist(name,isBand);
	}
	public void setArtist(String name, String yn) {
		artist = new Artist(name,yn);
	}
	public void setArtistName(String name) {
		artist.setName(name);  // delegation
	}
	public int getBitDepth() {
		return bitDepth;
	}
	public void setBitDepth(int bDepth) {
		if (bDepth < 0) {
			bitDepth = 0;
		} else {
			bitDepth = bDepth;
		}
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		if (album == "n/a") {
			album = null;
		}else {
		this.album = album;
		}
	}
	
	public String getType() {
		return "song";
	}
	public Song(String title, int seconds, double freq, int bDepth, 
	String artistName, String artistIsBand) {
		super(title,seconds);
		setSamplingRate(freq);
		setBitDepth(bDepth);
		artist = new Artist(artistName,artistIsBand);  // composition
	}
	public Song(String title, int minutes, int seconds, double freq,
	int bDepth, String artistName, String artistIsBand) {
		this(title,minutes*60+seconds,freq,bDepth,artistName,artistIsBand);
	}
	public Song(String title,String album, int minutes, int seconds, double freq,
	int bDepth, String artistName, String artistIsBand, int cost) {
		this(title,minutes,seconds,freq,bDepth,artistName,artistIsBand);
		setCost(cost);
		setAlbum(album);
	}
	public String toString() {
		return String.format("%s\nSampling rate: %.1f kHz\n"
				+ "Bit Depth: %d bits\nArtist: %s\nAlbum: \nCost: $%d",
			super.toString(), samplingRate, bitDepth,
			artist.toString(),getCost(), getAlbum()); 
	}
	public double calculateFileSize() {
		return samplingRate*1000*bitDepth/4*getLength()/1024/1024;
	}
}

class Movie extends Media {
	private String director;
	private int quality;

	public String getDirector() {
		return director;
	}
	public void setDirector(String dir) {
		director = dir;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		if (quality < 0) {
			quality = 0;
		} else {
			this.quality = quality;
		}
	}
	public String getType() {
		return "movie";
	}
	public double calculateFileSize() {
		return 1.2 * getLength();
	}
	public Movie(String title, int seconds, String director) {
		super(title,seconds);
		setDirector(director);
	}
	public Movie(String title, int hours, int minutes, int seconds, 
			String director,int cost,int quality){
		super(title,hours*60+minutes,seconds);
		setDirector(director);
		setCost(cost);
		setQuality(quality);
	}
	public String toString() {
		return String.format("%s\n%s\nCost: $%d\nQuality: %dp",super.toString(),
				director,getCost(),getQuality());
	}
}
/* The PlayList class stores a collection of songs */
class PlayList {
	private ArrayList<Media> media;
	public String toString() {
		String result = "";
		for (Media m : media) {
			result = result + m.toString() + "\n";
		}
		return result;
	}
	public void addMedia(Media m) {
		media.add(m);
	}
	public PlayList() {
		media = new ArrayList<Media>();
	}
}
/* The AudioBook class */
class AudioBook extends Media{
	private String author;
	private String reader;
	private double freq;
	private int bitDepth;

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getReader() {
		return reader;
	}
	public void setReader(String reader) {
		this.reader = reader;
	}
	public double getSamplingRate() {
		return freq;
	}
	public void setSamplingRate(double freq) {
		if (freq < 0) {
			freq = 0;
		} else {
			this.freq= freq;
		}
	}
	public int getBitDepth() {
		return bitDepth;
	}
	public void setBitDepth(int bitDepth) {
		this.bitDepth = bitDepth;
	}
	public String getType() {
		return "Audiobook";
	}
	public AudioBook(String name,String author,String reader,
			int hours, int minutes, int seconds,double freq, int bitDepth,int cost) {
		super(name,hours,minutes,seconds);
		setAuthor(author);
		setReader(reader);
		setSamplingRate(freq);
		setBitDepth(bitDepth);
		setCost(cost);
		
	}
	public double calculateFileSize() {
		return freq*1000*bitDepth/4*getLength()/1024/1024;
	}
	public String toString() {
		return String.format("%s\nAuthor: %s\nReader: %s"
				+ "\nSampling Rate: %.1f\nBit Depth: %d\nCost: %d",
				super.toString(),author,reader,freq,bitDepth,getCost());
	}
}
class OutputPlaylistController {
	private PlayList list;
	public OutputPlaylistController(PlayList list) {
		setList(list);
		
	}
	private void setList(PlayList list) {
		this.list = list;
		
	}
	public PlayList getList() {
		return list;
	}
	public void toConsole() {
		System.out.println(list);
	}
	public boolean toTextFile(String name) {
		try {
			String fname = null;
			PrintWriter pw = new PrintWriter(new File(fname));
			return true;
		}catch(Exception ex){
			return false;
		}
	}
}

public class PlaylistPalermo {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String doAgain = "y";
		int hours, minutes, seconds, bitDepth, cost, quality;
		double freq, fileSize;
		String songName,album, movieName, audioBookName,author,reader;
		Song mySong;
		Movie myMovie;
		AudioBook myAudioBook;
		PlayList list = new PlayList();
		String choice;  // whether they want a song or a movie
		String director;
		String artistName;
		String isBandYN;
		while (doAgain.equals("y")) {
			System.out.print("[m]ovie or [s]ong or [a]udiobook? ");
			choice = sc.nextLine().trim().toLowerCase();
			if (choice.equals("m")) {
				System.out.print("Enter the name of the movie: ");
				movieName = sc.nextLine();
				System.out.print("Enter the length in hours, minutes, & seconds: ");
				hours = sc.nextInt();
				minutes = sc.nextInt();
				seconds = sc.nextInt();
				sc.nextLine();  // throwing out the end-of-line
				System.out.print("Enter the name of the director: ");
				director = sc.nextLine();
				System.out.println("Enter the cost of the movie: ");
				cost = sc.nextInt();
				System.out.println("Enter the quality of the movie: ");
				quality = sc.nextInt();
				// Create the movie object
				myMovie = new Movie(movieName,hours,minutes,seconds,director,cost,quality);
				list.addMedia(myMovie);
			} else if (choice.equals("s")) {
				System.out.print("Enter the name of the song: ");
				songName = sc.nextLine();
				System.out.print("Enter the length in minutes and seconds: ");
				minutes = sc.nextInt();
				seconds = sc.nextInt();
				System.out.print("Enter the sampling frequency in kHz: ");
				freq = sc.nextDouble()*1000;
				System.out.print("Enter the bit depth in bits: ");
				bitDepth = sc.nextInt();
				sc.nextLine(); // throw out the Enter key
				System.out.print("Enter the name of the artist: ");
				artistName = sc.nextLine();
				System.out.print("Is the artist a band (y or n)? ");
				isBandYN = sc.nextLine().trim().toLowerCase();
				System.out.println("Enter the cost of the song: ");
				cost = sc.nextInt();
				System.out.println("Enter the album the song come from (if it is a single enter (n/a)): ");
				album = sc.nextLine();
				// create a Song object
				mySong = new Song(songName,album,minutes,seconds,freq,bitDepth,artistName,
					isBandYN,cost);
				list.addMedia(mySong);
			}else if(choice.equals("a")){ 
				System.out.println("Enter the name of the audiobook: ");
				audioBookName = sc.nextLine();
				System.out.println("Enter the name of the author: ");
				author = sc.nextLine();
				System.out.println("Enter the name of the reader of the audiobook: ");
				reader = sc.nextLine();
				System.out.println("Enter the length in hours, minutes, and seconds: ");
				hours = sc.nextInt();
				minutes = sc.nextInt();
				seconds = sc.nextInt();
				System.out.println("Enter the frequency in kHz: ");
				freq = sc.nextDouble();
				System.out.println("Enter the bit depth: ");
				bitDepth = sc.nextInt();
				System.out.println("Enter the cost: ");
				cost = sc.nextInt();
				//Create AudioBook object
				myAudioBook = new AudioBook(audioBookName,author,reader,hours,minutes,seconds,freq,bitDepth,cost);
				list.addMedia(myAudioBook);
				}
			System.out.println("Another? ");
			sc.nextLine();
			doAgain = sc.nextLine();
		}
		System.out.println("Here is your playlist: ");
		OutputPlaylistController oplc = new OutputPlaylistController(list);
		oplc.toConsole();
		String filename;
		System.out.println("Enter the name of the file ");
		filename = sc.nextLine();
		if (oplc.toTextFile(filename)) {
			System.out.println("Success!");
		}else {
			System.out.println("aw shiet");
		}
	}
}
