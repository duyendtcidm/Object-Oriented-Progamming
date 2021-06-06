package brickPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener{

	private boolean play = false;
	private int score = 0;
	
	private int totalBrick = 27;
	
	private Timer timer;
	private int delay = 5;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public GamePlay() {
		map = new MapGenerator(3, 9);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 700, 600);
		
		// draw map
		map.draw((Graphics2D)g);
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 30));
		g.drawString("" + score, 600, 40);
		
		//borders
		g.setColor(Color.pink);
		g.fillRect(0, 0, 2, 600);
		g.fillRect(0, 0, 700, 2);
		g.fillRect(682 , 0, 2, 600);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 80, 6);
		
		// ball
		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		// Win
		if (totalBrick <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("YOU WON", 300, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Press ENTER to play again", 200, 350);
		}
		
		//Game over
		if(ballposY > 580) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game over", 200, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Press ENTER to play again", 200, 350);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (play) {
		if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 80, 10))) {
			ballYdir = - ballYdir;
		}
		
		A: for (int i = 0; i < map.map.length; i++) {
			for (int j = 0; j < map.map[0].length; j++) {
				if (map.map[i][j] > 0) {
					int brickX = j*map.brickWidth + 40;
					int brickY = i*map.brickHeight + 40;
					int brickWidth = map.brickWidth;
					int brickHeight = map.brickHeight;
					
					Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
					Rectangle ballRectangle = new Rectangle(ballposX, ballposY, 20, 20);
					Rectangle bricRectangle = rectangle;
					
					if(ballRectangle.intersects(bricRectangle)) {
						map.setBrickValue(0,i,j);
						totalBrick --;
						score += 5;
						
						if (ballposX + 19 <= bricRectangle.x || ballposX + 1 >= bricRectangle.x + bricRectangle.width) {
							ballXdir = -ballXdir;
						} else {
							ballYdir = - ballYdir;
						}
						break A;
					}
				}
			}
		}
	
			ballposX += ballXdir;
			ballposY += ballYdir;
			//left
			if (ballposX < 10) {
				ballXdir = - ballXdir;
			}
			//top
			if (ballposY < 10) {
				ballYdir = - ballYdir;
			}
			//right
			if (ballposX > 660) {
				ballXdir = - ballXdir;
			}
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 13) {
				playerX = 13;
			} else {
				moveLeft();
			}
		}	
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 100;
				ballposY = 200;
				ballXdir = -1;
				ballYdir = -2;
				
				totalBrick = 27;
				score = 0;
				
				playerX = 310;
				
				map = new MapGenerator(3, 9);
				repaint();
				
			}
		}
	}
	public void moveRight() {
		play = true;
		playerX += 10;
	}
	public void moveLeft() {
		play = true;
		playerX -= 10;		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



}
