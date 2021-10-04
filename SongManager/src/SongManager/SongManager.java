package SongManager;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.NullPointerException;

public class SongManager {
	//public final int ARRAY_NUM = 50; // Song 객체관리를 위한 배열의 크기
	ArrayList<Song> songList=new ArrayList<Song>(); // Song 객체를 저장하는 1차원 배열 선언
	Scanner scanner = new Scanner(System.in);
	Connection connect = null;
	Statement stmt=null;
	ResultSet srs=null;
	
	//생성자
	SongManager(){ //sql로딩
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("DB 연결 완료");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("JDBC 드라이버 로드 에러");
		} 
		/*catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB 연결 오류");
		}*/
	}
	//노래추가	
	public void addSong(Song song) throws SQLException, UnsupportedEncodingException {
		try {
		//연결
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/songdb?serverTimezone=UTC", "root","5700");
		//statement 생성
		stmt = connect.createStatement();
		//쿼리전송
		String insertText="insert into song(title, artist, year) values('"
				+ song.getTitle() + "','" + song.getArtist() + "'," + song.getYear() + ");";
		stmt.executeUpdate(insertText);
		System.out.println("추가되었습니다.");
		}catch(SQLException e) {
			System.out.println("SQLException 발생");
			e.printStackTrace();
		}
	}
	//노래 삭제
	public void deleteSong(String title) throws SQLException, UnsupportedEncodingException{
		//연결
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/songdb?serverTimezone=UTC", "root","5700");
			//statement 생성
			stmt = connect.createStatement();
			//쿼리전송
			String deleteText="delete from song where title='" +title+ "';";
			stmt.executeUpdate(deleteText);
			System.out.println("노래가 삭제되었습니다.");
		}catch(SQLException e) {
			System.out.println("SQLException 발생");
			e.printStackTrace();
		}
	}
	//artist로 검색
	public ArrayList<Song> searchSongByArtist(String artist){
		try {
			//연결
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/songdb?serverTimezone=UTC", "root","5700");
			//statement 생성
			stmt = connect.createStatement();
			//쿼리 전송
			String searchArtistText = "select * from song where artist='" +artist+"';";
			srs = stmt.executeQuery(searchArtistText);
			//System.out.println(artist +"노래 출력");
			System.out.println(artist +"노래 arraylist에 저장");
			//arraylist에 저장
			while(srs.next()){
				Song song =new Song();
				song.setTitle(srs.getString("title"));
				song.setArtist(srs.getString("artist"));
				song.setYear(srs.getString("year"));
				songList.add(song);
			}
			/*
			//sql문에서 그대로 출력
			while (srs.next()) {
				System.out.print(srs.getString("title"));
				System.out.print(":" + srs.getString("artist"));
				System.out.println("(" + srs.getInt("year") + ")");
			}
			*/
		}catch(SQLException e) {
			System.out.println("SQLException 발생");
			e.printStackTrace();
		}
		return songList;
	}
	//title로 검색
	public Song searchSongByTitle(String title) {
		Song asong=new Song();
		//연결
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/songdb?serverTimezone=UTC", "root","5700");
			//statement 생성
			stmt = connect.createStatement();
			//쿼리전송
			String searchTitleText="select * from song where title='" +title+ "';";
			srs = stmt.executeQuery(searchTitleText);
			while(srs.next()){
				asong.setTitle(srs.getString("title"));
				asong.setArtist(srs.getString("artist"));
				asong.setYear(srs.getString("year"));
			}
		}catch(SQLException e) {
			System.out.println("SQLException 발생");
			e.printStackTrace();
		}
		return asong;
	}
	//노래 수정
	public void modifySong(String title, Song newsong) {
		try {
			//연결
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/songdb?serverTimezone=UTC", "root","5700");
			//statement 생성
			stmt = connect.createStatement();
			//쿼리전송
			String modifyText="update song set title='"
					+ newsong.getTitle() + "',artist='" + newsong.getArtist() + "',year=" + newsong.getYear() 
					+'\t'+ "where title='" + title + "';";
			stmt.executeUpdate(modifyText);
			System.out.println("곡 정보가 변경되었습니다.");
			}catch(SQLException e) {
				System.out.println("SQLException 발생");
				e.printStackTrace();
		}
	}
	//노래 개수
	public int howManySongs() {
		int nSong=0;
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/songdb?serverTimezone=UTC", "root","5700");
			//statement 생성
			stmt = connect.createStatement();
			//쿼리전송
			String searchTitleText="select * from song;";
			srs = stmt.executeQuery(searchTitleText);
			while(srs.next()){
				nSong++;
			}
		}catch(SQLException e) {
			System.out.println("SQLException 발생");
			e.printStackTrace();
		}
		return nSong;
	}
	//모든 노래 출력
	ArrayList<Song> showAllSong() throws SQLException{
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/songdb?serverTimezone=UTC", "root","5700");
			//statement 생성
			stmt = connect.createStatement();
			//쿼리전송
			String searchTitleText="select * from song;";
			srs = stmt.executeQuery(searchTitleText);
			/*while(srs.next()){
				System.out.print(srs.getString("title"));
				System.out.print(":" + srs.getString("artist"));
				System.out.println("(" + srs.getInt("year") + ")");
			}*/
			while(srs.next()) {
				Song song =new Song();
				song.setTitle(srs.getString("title"));
				song.setArtist(srs.getString("artist"));
				song.setYear(srs.getString("year"));
				songList.add(song);
			}
		}catch(SQLException e) {
			System.out.println("SQLException 발생");
			e.printStackTrace();
		}
		return songList;
	}
	//종료
	public void exit() {
		try {
			if(stmt != null ) stmt.close();
			if(connect != null) connect.close();
			if(srs != null) srs.close();
			System.out.println("프로그램을 종료합니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//리스트에 저장되어있는 노래 출력
	void printSongList(ArrayList<Song> songList){
		for(Song song:songList) {
			System.out.println(String.format("%s:%s(%s)", 
					song.title, song.artist, song.year));
		}
	}
	//song 객체에 있는 노래 출력 
	void printSong(Song song){
		System.out.println(String.format("%s:%s(%d)", 
					song.title, song.artist, song.year));
	}
	//메인함수
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//SongManager songmanager = new SongManager();
		//ArrayList songlist = new ArrayList<Song>();
		//songmanager.addSong(new Song("좋아좋아", "조정석", 2020));
		//songmanager.addSong(new Song("Next Level", "에스파", 2021));
		//songlist = songmanager.searchSongByArtist("오마이걸");
		//songmanager.printSongList(songlist);
		//Song song=songmanager.searchSongByTitle("Next Level");
		//songmanager.printSong(song);
		//songmanager.modifySong("좋아좋아", new Song("좋아좋아", "조정석", 2021));
		//System.out.println("곡의 총 개수는 "+songmanager.howManySongs());
		//songmanager.exit();
		Client client = new Client();
		//songlist=songmanager.showAllSong();
		//songmanager.printSongList(songlist);
	}
}
