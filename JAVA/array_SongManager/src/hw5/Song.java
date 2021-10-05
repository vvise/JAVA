package hw5;

public class Song {
	String title;//노래 제목, 노래 마다 다른값을 가짐
	String artist; //노래 부른 아티스트
	String year; //노래 발표년도
	
	Song(){
		this(null, null, null);
    }
 	
	public Song(String title, String artist, String year) {
		this.title=title;
		this.artist=artist;
		this.year=year;
	}
	public void setTitle(String title) {
		this.title= title;
	}
	public void setArtist(String artist) {
		this.artist= artist;
	}
	public void setYear(String year) {
		this.year= year;
	}
	//필드 title, artist, year의 값을 콘솔에 출력 
	public void show() {
		System.out.println(artist+":"+title+"("+year+")");
	}
}
