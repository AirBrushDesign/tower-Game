package tower_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy {
	
	private int width = 10, height = 30;
	private double x = 1200, y = 290;
	private double step = 0;
	private int live = 0;
	
	
	Enemy( )
	{
		// losowanie ¿ycia wrogowi
		while( this.live == 0 )
		{
			this.live = ( int ) Math.floor( Math.random()*100 );
		}	
		
		// losowanie kroku wroga
		while( this.step == 0 )
		{
			this.step = Math.random();
		}			
	}
	
	//przesuniêcie wroga o step
	public void move(){ x-=step; }
	
	
	public int damage( int da )
	{
		live -= da;
		return live - da;
	}
	
	//zwracanie punku po³o¿enia wroga
	public double getX(){ return x; }
	public double getY(){ return y; }
	
	//zwracanie wyskoœci i szerokoœci wroga
	public double getHeight(){ return ( double ) height; }
	public double getWidth(){ return ( double ) width ; }
	
	//rysowanie wroga
	public void drawEnemy( Graphics g )
	{
		Graphics2D g2d = ( Graphics2D ) g;
		Color c = new Color(255,0,0);
		g2d.setColor( c );
		
		// wróg w formie prostok¹ta 
		Rectangle enemy = new Rectangle( ( int ) x , ( int ) y, width, height);
		
		//wypisywanie nad wrogiem jego ¿ycia
		g2d.drawString( Integer.toString( live ), ( int ) x, ( int ) y-10 );
		g2d.draw( enemy );
		g2d.fill( enemy );
	}
}
