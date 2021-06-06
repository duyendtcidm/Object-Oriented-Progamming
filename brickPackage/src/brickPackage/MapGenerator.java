package brickPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	public int map[][];
	public int brickWidth;
	public int brickHeight;	
	
	public MapGenerator(int row, int col) {
		map = new int[row][col];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;
			}
		}
		brickHeight = 350/col;
		brickWidth = 200/row;
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] > 0) {
					g.setColor(Color.pink);
					g.fillRect(j*brickWidth + 40, i*brickHeight + 80, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(4));
					g.setColor(Color.black);
					g.drawRect(j*brickWidth + 40, i*brickHeight + 80, brickWidth, brickHeight);
				}
			}
		}
	}
	
	public void setBrickValue (int value, int row, int col) {
		map[row][col] = value;
	}

}
