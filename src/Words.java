import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Words {
	private ArrayList<String> words;
	private String wordSelected; 
	//private String guessString;
	/*
	 * private int randomInt; private boolean check, win; private String answer,
	 * displayWord;
	 */
	public Words(){
		try {
			words = getText();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		wordSelected = returnRandomString();
		//guessString = "";
		/*
		 * answer = returnRandomString(); displayWord = ""; setDisplayWord(); check =
		 * false; win = false;
		 */
	}
	
	public ArrayList <String> getText() throws FileNotFoundException {
		ArrayList<String> temp = new ArrayList<String>();
		BufferedReader br;
		String word;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("wordlist.csv"),"UTF-8"));
			while((word = br.readLine())!=null) {
				temp.add(word);
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	public String getRandomWord() {
		return wordSelected = returnRandomString();	
	}
	
	public int returnRandomInt(int min, int max){
		return (int)(Math.random()*(max-min+1)+min);
	}
	
	public String returnRandomString(){
		return words.get(returnRandomInt(0, words.size()-1));
	}

	public ArrayList<String> getWords() {
		return words;
	}

	public void setWords(ArrayList<String> words) {
		this.words = words;
	}

	/*
	 * public String getGuessString() { return guessString; }
	 * 
	 * public void setGuessString(String guessString) { this.guessString =
	 * guessString; }
	 */

	public void setWordSelected(String wordSelected) {
		this.wordSelected = wordSelected;
	}

	public String getWordSelected() {
		return wordSelected;
	}
	
	
	
	/*public void setDisplayWord(){
		for (int i =0; i<answer.length(); i++){
			if (answer.charAt(i) == ' '){
				displayWord += " ";
			}
			else displayWord += "-";
		}
	}
	
	public String returnDisplayWord(){
		return displayWord;
	}
	
	public String returnAnswer(){
		return answer;
	}
	
	public boolean checkChar(char c){
		check = false;
		for (int i = 0; i < answer.length(); i++){
			if (c == answer.charAt(i)){
				displayWord = displayWord.substring(0, i) + answer.charAt(i) + displayWord.substring(i+1);	
				check  = true;
			}
		}
		return check;
	}
	
	public boolean checkWin(){
		if (displayWord.equals(answer)){
			win = true;
		}
		return win;
	}
	
	public void reset(){
		answer = returnRandomString();
		displayWord = "";
		setDisplayWord();
		check = false;
		win = false;
	}*/
}
