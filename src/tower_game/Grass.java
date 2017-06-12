package tower_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JComponent;

public class Grass extends JComponent {
	
	Point wstaw = new Point( 0, 320 );
	int height = 50;
	int width = 1200;
	Color color = new Color( 37, 221, 24 );
	
	// pusty konstruktor definicja zmiennych zadeklarowanych domyœlnie
	Grass(){}
	
	// mo¿liwoœæ tworzenia trzwy za pomoc¹ podania argumentów
	Grass( Point wstaw, int height, int width, Color color )
	{
		this.wstaw = wstaw;
		this.height = height;
		this.width = width;
		this.color = color;	
	}
	
	//rysowanie trawy Prostok¹t o wymiarach podanych wzmiennych
	public void drawGrass( Graphics g )
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor( color );
		g2d.fillRect( (int) wstaw.getX(), (int) wstaw.getY(), width, height );
	}
		
}
