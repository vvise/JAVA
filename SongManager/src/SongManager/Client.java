package SongManager;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class Client extends JFrame{
	//SongManager클래스 선언
	SongManager songmanager = new SongManager();
	//text창
	JTextArea textarea = new JTextArea(5, 40);
	//버튼
	JButton add = new JButton("Add");
	JButton delete = new JButton("Delete");
	JButton searchartist =new JButton("Search By Artist");
	JButton searchtitle = new JButton("Search By Title");
	JButton modify =new JButton("Modify");
	JButton howmany = new JButton("How Many Songs");
	JButton showall =new JButton("Show All Songs");
	JButton exit =new JButton("Exit");
	//JLabel
	JLabel form = new JLabel("<html>add입력 형식:title,artist,year<br>"
			+ "delete입력 형식:title만 입력<br>"
			+"search by artist입력 형식:artist만 입력<br>"
			+"search by title입력 형식:title만 입력<br>"
			+"modify입력 형식:수정할 곡 title,(곡 정보 다시 입력)title,artist,year<br>"
			+"search by artist입력 형식:artist만 입력<br>"
			+ "</html>");
	
	//생성자
	public Client() {
	//스윙 프레임
	setTitle("Song관리 프로그램");
	setSize(450,350);
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//프레임 윈도우를 닫으면 프로그램 종료
	//컴포넌트
	Container contentPane = getContentPane();
	contentPane.setLayout(new FlowLayout());
	contentPane.add(textarea);
	contentPane.add(add);
	contentPane.add(delete);
	contentPane.add(searchartist);
	contentPane.add(searchtitle);
	contentPane.add(modify);
	contentPane.add(howmany);
	contentPane.add(showall);
	contentPane.add(exit);
	contentPane.add(form);
	//이벤트
	//add
	add.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Song song = new Song();
			String str=textarea.getText();
			try {
				String arr[] = str.split(",");
				song.title=arr[0];
				song.artist=arr[1];
				song.year=arr[2];
				songmanager.addSong(song);
				textarea.setText("노래가 추가되었습니다.");
				} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});
	//delete
	delete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				String title=textarea.getText();
				songmanager.deleteSong(title);
				textarea.setText("노래가 삭제되었습니다.");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});
	//searchartist 안됨
	searchartist.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				String artist=textarea.getText();
				ArrayList<Song> songlist =songmanager.searchSongByArtist(artist);
				textarea.setText("");
				for(int i=0;i<songlist.size();i++) {
					textarea.append(songlist.get(i).toString()+'\n');
				}
			}finally {
				System.out.println("finally..");
			}
		}
	});
	//searchtitle
	searchtitle.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				String title=textarea.getText();
				Song song=songmanager.searchSongByTitle(title);
				textarea.setText(song.toString());
			}finally {
				System.out.println("finally..");
			}
		}
	});
	//modify
	modify.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Song song = new Song();
			String str=textarea.getText();
			try {
				String arr[] = str.split(",");
				String modifyTitle=arr[0];
				song.title=arr[1];
				song.artist=arr[2];
				song.year=arr[3];
				songmanager.modifySong(modifyTitle, song);
				textarea.setText("노래가 수정 되었습니다.");		
				}finally {
					System.out.println("modify finally..");
				}
		}
	});
	//howmany
	howmany.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int songnum=songmanager.howManySongs();
			String strNum=String.valueOf(songnum);
			textarea.setText("총 곡의 수 : " +strNum);
		}
	});
	//showall 안됨
	showall.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				ArrayList<Song> songlist =songmanager.showAllSong();
				for(int i=0;i<songlist.size();i++) {
					textarea.append(songlist.get(i).toString()+'\n');
				}
				songlist.clear();//초기화
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});
	//exit
	exit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("프로그램 종료");
			setVisible(false);
			System.exit(0);
		}
	});
	}
}