package tower_game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
import javax.swing.JComponent;

public class Armata extends JComponent{
		
		Point wstaw;
		int height, width;
		Point center;
		private Image image = null;
		private Image wheel = null;
		

		Armata( Point wstaw, int width ,int height)
		{
			this.width = width;
			this.height = height;
			this.wstaw = wstaw;
			this.center = new Point(( int )  width/3, ( int )  height/2 );
		}
			
		
		Armata( int px, int py, int width ,int height)
		{
			
			this.width = width;
			this.height = height;
			// okre�lanie punktu wstawienia
			this.wstaw = new Point( px, py );
			
			// wyliczanie �rodka do obrotu
			this.center = new Point(( int )  width/3, ( int )  height/2 );
	
		}
		
		// Pobieranie obrazka
		private Image getImage( String path )
		{
			Image tempImage = null;
			
			try
			{
				// pr�ba pobrania obrazka z zmiennej path
				URL imageURL = Armata.class.getResource( path );
				tempImage = Toolkit.getDefaultToolkit().getImage( imageURL );
			} 
			catch (Exception e)
			{
				System.out.println("Error Image");
			}
			//zwr�cenie gotowego obrazka 
			return tempImage;
			
		}

		// zwracanie punktu wstawienia
		private Point getCenter()
		{
			return this.wstaw;
		}
		
		//zwracanie x punktu wstawienia
		public double getCenterX()
		{
			return getCenter().getX();
		}
		
		//zwracanie y punktu wstawienia
		public double getCenterY()
		{
			return getCenter().getY();
		}
		
		// Rysowanie armaty
		public void drawGun( Graphics g, double tan ) {
	
			
			int cx = ( int ) center.getX();
		    int cy = ( int ) center.getY();
		
		    // je�eli wheel i image s� puste pobieranie obrazka
			if( wheel == null)
				wheel = getImage("wheel.png");
			
			if( image == null)
				image = getImage("armata.png");
			
			//przekszta�cenia (mo�liwo�� obrotu, translacji)  
			AffineTransform at = new AffineTransform();
			
			//przesuni�cie punktu (0,0) do �rodka miejsca w kt�rym chcemy obraca� armate
			at.translate( wstaw.getX()-cx  , wstaw.getY()-cy  );
			
			//obr�t armaty wok� punktu cx cy
			at.rotate( -tan, cx, cy); 
			
			// rysowanie obrazka armaty
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(image, at, this);
			
			//rysowanie obrazka ko�a armaty
			g2d.drawImage(wheel, (int) wstaw.getX()-cx/2, (int) wstaw.getY(), height, height , this);	
			
		}		
		
}


