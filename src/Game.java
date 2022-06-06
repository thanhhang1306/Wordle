import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener{
	private BufferedImage back;
	private int SCREEN_WIDTH = 1000, SCREEN_HEIGHT = 700;
	private int BOX_SIZE = 70, BOX_XCOOR = 300, BOX_YCOOR = 30;
	private Words word;
	private int key;
	private Board board;  
	private int letter, guess;
	private int[] guessList = {0, 1, 2, 3, 4, 5};
	private char screen;
	private String wordSelected, wordGuessed;
	private ArrayList<ArrayList<Integer>> yellowLetter, greenLetter;
	private boolean check; 
	private String message; 
	private ImageIcon main, rule, win, lose; 


	public Game() {
		new Thread(this).start();	
		back=null;
		this.addKeyListener(this);
		this.addMouseListener(this);
		key = -1;
		guess = 0;
		letter = 0;
		screen = 'M';
		word = new Words();
		board = new Board();
		wordGuessed = "";
		wordSelected = word.getWordSelected().toUpperCase();
		yellowLetter = new ArrayList<ArrayList<Integer>>();
		greenLetter = new ArrayList<ArrayList<Integer>>();
		check = false; 
		message = "";
		main = new ImageIcon("1.png");
		rule = new ImageIcon("2.png");
		win = new ImageIcon("3.png");
		lose = new ImageIcon("4.png");
	}

	public void run() {
		try {
			while(true) {
				Thread.currentThread().sleep(30);
				repaint();
			}
		}
		catch(Exception e) {}
	}

	public void paint (Graphics g)
	{
		Graphics2D twoDgraph = (Graphics2D)g;
		//take a snap shop of the current screen and same it as an image
		//that is the exact same width and height as the current screen
		if (back==null) {
			back =(BufferedImage) (createImage(getWidth(), getHeight()));
		}

		//create a graphics reference to the back ground image
		//we will draw all changes on the background image
		Graphics g2d = back.createGraphics();

		//this clears the old image, like an EtchASketch. If you see the old image when we learn motion, you deleted this line.
		g2d.clearRect(0, 0, getSize().width, getSize().height); 

		drawScreen(g2d);

		//This line tells the program to draw everything above. If you delete this, nothing will show up.

		twoDgraph.drawImage(back, 0, 0, null);
	}

	public void drawScreen(Graphics g) {
		switch(screen) {
		case 'M': 
			g.drawImage(main.getImage(),0,0,SCREEN_WIDTH, SCREEN_HEIGHT, this);
			break;
		case 'R': 
			g.drawImage(rule.getImage(),0,-20,SCREEN_WIDTH, SCREEN_HEIGHT, this);
			break;
		case 'G': 
			drawGridLines(g);
			if(check) {
				getMatchType(checkMatch()); 
				fillWord(g);
				check = false;
			}
			colorLetter(g);
			fillWord(g);
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString(message, SCREEN_WIDTH/2 - metrics.stringWidth(message)/2, 550);
			checkWin();
			break;
		case 'W':
			g.drawImage(win.getImage(),0,-20,SCREEN_WIDTH, SCREEN_HEIGHT, this);
			drawWinLoseMessage(g);
			break;
		case 'L': 
			g.drawImage(lose.getImage(),0,-20,SCREEN_WIDTH, SCREEN_HEIGHT, this);
			drawWinLoseMessage(g);
			break;
		}
	}

	public void drawWinLoseMessage(Graphics g) {
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.setFont(new Font("Garamond", Font.ITALIC, 30));
		g.setColor(Color.white);
		g.drawString("The word was: " + wordSelected.toLowerCase(), SCREEN_WIDTH/2 - metrics.stringWidth("The word was: " + wordSelected.toLowerCase()), 570);
		g.drawString("Click on the screen to reset", 350, 600);
	}
	public void drawGridLines(Graphics g){
		g.setColor(Color.white);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 6; j++) {
				g.drawRect(BOX_XCOOR + i*80, BOX_YCOOR + j*80, BOX_SIZE, BOX_SIZE);
			}
		}
	}


	private void fillWord(Graphics g) {
		//System.out.println(board.getValues(0, 0));
		g.setFont(new Font("Garamond", Font.BOLD, 30));
		g.setColor(Color.white);
		FontMetrics metrics = getFontMetrics(g.getFont());
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 6; j++) {
				if(board.getValues(i, j)!='.')
					g.drawString(Character.toString(board.getValues(i, j)), BOX_XCOOR + i*80 + BOX_SIZE/2 - metrics.charWidth(board.getValues(i, j))/2, BOX_YCOOR + j*80 + (BOX_SIZE+metrics.getAscent())/2);
				else g.drawString("", BOX_XCOOR + i*80 + BOX_SIZE/2 - metrics.charWidth(board.getValues(i, j))/2, BOX_YCOOR + j*80 + BOX_SIZE/2 - metrics.getAscent());

			}
		}
	}

	// Method to check for repeating letters
	public boolean checkRepeating() {
		return wordSelected.length()!=wordSelected.chars().distinct().count();
	}

	public ArrayList<Integer> checkMatch() {
		ArrayList<Integer> temp = new ArrayList<>();
		for(int i = 0; i < wordGuessed.length();i++) {
			char letter = wordGuessed.charAt(i);
			if(wordSelected.indexOf(letter)!=-1) {
				temp.add(i);
			}

		}
		System.out.println(temp);
		return temp;	
	}

	public void getMatchType(ArrayList<Integer> a) {	
		ArrayList<Integer>greenTemp = new ArrayList<Integer>();
		ArrayList<Integer>yellowTemp = new ArrayList<Integer>();

		for(int i = 0; i < a.size();i++) {
			if(Character.compare(wordGuessed.charAt(a.get(i)),wordSelected.charAt(a.get(i)))==0) {
				greenTemp.add(a.get(i));
			}
			else yellowTemp.add(a.get(i));
		}
		greenLetter.add(greenTemp);
		yellowLetter.add(yellowTemp);
	}

	public void colorLetter(Graphics g) {	
		for(int h = 0; h<=guess-1; h++) {
			for(int i = 0; i < 5; i++) {
				if(!(yellowLetter.isEmpty()) && !(greenLetter.isEmpty())) {
					if(yellowLetter.get(h).contains(i)) {
						Color yellow = new Color(255,221,89);
						g.setColor(yellow);
						g.fillRect(BOX_XCOOR + i*80,BOX_YCOOR + h*80, BOX_SIZE, BOX_SIZE);
					}
					else if (greenLetter.get(h).contains(i)) {
						Color green = new Color(120,177,89);
						g.setColor(green);
						g.fillRect(BOX_XCOOR + i*80,BOX_YCOOR + h*80, BOX_SIZE, BOX_SIZE);
					}
					else {
						Color gray = new Color(115,115,115);
						g.setColor(gray);
						g.fillRect(BOX_XCOOR + i*80,BOX_YCOOR + h*80, BOX_SIZE, BOX_SIZE);
					}
				}
			}
		}

	}

	public void checkWin() {
		if(wordSelected.equals(wordGuessed)) 
			screen = 'W';
		else if(guess == 6)
			screen = 'L';
	}

	public void reset() {
		wordSelected = word.getRandomWord().toUpperCase();
		wordGuessed = "";
		yellowLetter.clear();
		greenLetter.clear();
		check = false; 
		message = "";
		guess = 0;
		letter = 0;
		board.setBoard();

	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		key = arg0.getKeyCode();
		char charKey = (char)(key);
		if(screen=='G') {
			if(Character.isLetter(charKey) && letter<5 && guess < 6){
				board.setValue(letter, guessList[guess], charKey);
				letter++;
			}
			else if(key == KeyEvent.VK_BACK_SPACE && letter!=0) {
				letter--;
				board.setValue(letter,guessList[guess],'.');


			}
			if(key == KeyEvent.VK_ENTER && (!(Character.toString(board.getValues(4, guessList[guess])).equals(".")))) {
				wordGuessed = "";
				for(int i = 0; i < 5; i++) {
					if(wordGuessed.length()<5){
						wordGuessed += board.getValues(i, guessList[guess]);

					}
				}
				if(word.getWords().contains(wordGuessed.toLowerCase())) {	
					guess++;
					letter=0;
					check = true;
					message = "";
				}
				else {
					for (int i = 0; i < 5; i++) {
						board.setValue(i, guessList[guess], '.');
						letter = 0;
						message = "Word is not in word list!";
					}
				}
				System.out.println(wordSelected);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		switch(screen) {
		case 'M': 
			screen = 'R';
			break;
		case 'R':
			screen = 'G';
			break;
		case 'W': 
			screen = 'M';
			reset();
			break;
		case 'L': 
			screen = 'M';
			reset();
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}



}


