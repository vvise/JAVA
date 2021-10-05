package hw5;

import java.util.*;
import hw5.Song;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NullPointerException;

public class SongManager {
	public final int ARRAY_NUM = 50; // Song 객체관리를 위한 배열의 크기
	Song[] songList=new Song[ARRAY_NUM]; // Song 객체를 저장하는 1차원 배열 선언
	int nSong = 0; // 현재 전체 노래 개수를 저장. 노래 추가/삭제 시 값이 
	Scanner scanner = new Scanner(System.in);
	
	//생성자
	SongManager(){ // 필드(멤버변수) songList 배열을 생성
		for(int i=0; i<songList.length; i++) {
			songList[i] = new Song(null, null, null);
		}
	}

	public void addSong() {
		String title="";
		System.out.println("추가할 곡의 제목을 입력하세요");
		title = scanner.next();
		//songList[nSong-1].setTitle(scanner.next());
		
		for(int i=0; i<nSong; i++) {
			if(title.equals(songList[i].title)) {
				System.out.println("같은 이름의 노래가 있습니다.");
				return;
			}
		}
		nSong++;
		songList[nSong-1].setTitle(title);
		System.out.println("추가할 곡의 가수를 입력하세요");
		songList[nSong-1].setArtist(scanner.next());
		System.out.println("추가할 곡의 연도를 입력하세요");
		songList[nSong-1].setYear(scanner.next());
		
		System.out.println("추가되었습니다.");
	}

	public void deleteSong(String title) {
		for(int i=0;i<nSong;i++) {
			if(title.equals(songList[i].title)) {
				System.out.println("노래가 존재합니다.");
				for(int j=i;j<nSong;j++) {
					songList[j] = songList[j+1];
				}
				nSong--;
				System.out.println("삭제되었습니다.");
				return;
			}
		}
	}

	public Song[] searchSongByArtist(String artist, Song[] searchsong) {
		int count=0;
		//Song [] searchsong = new Song[nSong];
		
		for(int i=0; i<nSong; i++) {
			if(songList[i].artist.equals(artist)) {
				searchsong[count] = songList[i];
				count++;
			}
		}
		for(int j=0;j<nSong;j++) {
			System.out.println((j+1) + ") title=" + searchsong[j].title +
					"artist=" + searchsong[j].artist + "year=" + searchsong[j].year);
		}
		return searchsong;
	}

	public void searchSongByTitle(String title) {
		for(int i=0; i<nSong; i++) {
			if(title.equals(songList[i].title)) {
				System.out.println(songList[i].artist+"-"+songList[i].title
						+"("+ songList[i].year+ ")");
			}
		}
	}

	public void modifySong(String title, Song newSong) {
		System.out.print("수정할 곡 제목 입력 : ");
		newSong.artist = scanner.next();
		System.out.print("수정할 곡 가수 입력 : ");
		newSong.title = scanner.next();
		System.out.print("수정할 곡 연도 입력 : ");
		newSong.year = scanner.next();
		for(int i=0; i<nSong; i++) {
			if(title.equals(songList[i].title)) {
				songList[i].artist = newSong.artist;
				songList[i].title = newSong.title;
				songList[i].year = newSong.year;
				System.out.println("노래가 수정되었습니다.");
				//return;
			}
		}
	}

	public int howManySongs() {
		return nSong;
	}

	public void showAllSong() {
		for(int i=0; i<nSong; i++) {
			System.out.println(songList[i].artist + "-" 
		+ songList[i].title + "(" + songList[i].year + ")");
		}
	}

	private void showArray() {
		String printstring;
		int count=0;
		for(int j=0; j<songList.length; j++) {
			printstring="";
			//while(count==10) {
				if(songList[j].title==null) {
					printstring = " - ";
					System.out.print(printstring);
					count++;
					if(count==10) { 
						System.out.println();
						count=0;
					}
				}
				else {
					printstring = songList[j].title;
					System.out.print(printstring + " ");
					count++;
					if(count==10) {
						System.out.println();
						count=0;
					}
				}
		}
	}
	private static void readSongsFromFile(SongManager manager) {
		String str="";
		int i=0;
		String[] result;
		try {
			Scanner fileReader = new Scanner(new File("src\\\\hw5\\\\song_list")); 			
			while (fileReader.hasNextLine()) {
				str = fileReader.nextLine();
				result = str.split(":");
				manager.songList[i].title = result[0];
				manager.songList[i].artist = result[1];
				manager.songList[i].year = result[2];
				manager.nSong++;
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] argc) {
		// TODO Auto-generated method stub
		SongManager manager = new SongManager();
		Scanner scanner = new Scanner(System.in);
		readSongsFromFile(manager);	

		while (true) {
			System.out.print("1. 추가  ");
			System.out.print("2. 삭제  ");
			System.out.print("3. 아티스트별검색  ");
			System.out.print("4. 제목별검색  ");
			System.out.println("5. 수정  ");
			System.out.print("6. 총 노래 개수  ");
			System.out.print("7. 모든노래보기  ");
			System.out.print("8. 배열보기  ");
			System.out.println("9. 종료  ");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				manager.addSong();
				break;
			case 2:
				System.out.print("삭제할 노래 제목 = ");
				String titlename = scanner.next();
				manager.deleteSong(titlename); 
				break;
			case 3:
				Song[] artistsong =  new Song[manager.nSong];
				artistsong =  manager.searchSongByArtist("이문세", artistsong);
				break;
			case 4:
				Song titlesong = new Song();
				titlesong.title = scanner.next();
				manager.searchSongByTitle(titlesong.title);
				break;
			case 5:
				System.out.print("곡 정보를 수정합니다. 바꿀 곡 제목을 입력하시오.");
				String orginalsongtitle = scanner.next();
				Song modisong = new Song();
				manager.modifySong(orginalsongtitle,modisong);
				break;
			case 6:
				int howmany=manager.howManySongs();
				System.out.println("등록된 노래는 총 " + howmany + "곡입니다.");
				break;
			case 7:
				manager.showAllSong();
				break;
			case 8:
				manager.showArray();
				break;
			case 9:
				System.out.println("프로그램을 종료합니다.");
				scanner.close();
				System.exit(0);
			default:
				System.out.println("잘못 입력했습니다.");
				break;
			}
		}
	}
}