package SongManager;

import java.io.*;
import java.sql.*;

public class Song {
	String title;
	String artist;
	String year;
	
	//생성자
	Song(){
		this.title=null;
		this.artist=null;
		this.year=null;
	}
	Song(String title, String artist, String year){
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
	public String getTitle() {
		return this.title;
	}
	public String getArtist() {
		return this.artist;
	}
	public String getYear() {
		return this.year;
	}
	//필드 title, artist, year의 값을 콘솔에 출력 
	public String toString() {
		String str = title+":"+artist+"("+year+")";
		return str;
	}
}
