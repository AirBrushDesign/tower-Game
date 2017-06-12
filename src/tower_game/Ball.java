package tower_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Ball {
	
	private double speed, alfa1; 
	
	// czas od momentu wystrzelenia pocisku
	private double Time = 0, 
	x, y;
	private int width = 5, height = 5;
	
	Ball(double speed, double alfa, int x , int y, int widthLufy )
	{
		
	// wstawienie pocisku w miejscu w który jest skierowana armata ( x, y )
		this.x = x + widthLufy*Math.cos( alfa );
		this.y = y - widthLufy*Math.sin( alfa );
		
	//inicjalizacja prêdkoœci
		this.speed = speed;
		
	//inicjalizacja pocz¹tkowego k¹ta 
		this.alfa1 = alfa;
		
		System.out.println( "x "+x+" y "+y );
	}
	
	// Zwracanie X i Y pocisku
	public double getX(){ return x; }
	public double getY(){ return y; }
	
	public void move()
	{
		// poruszanie pocisku zwiêkszenie czasu 
		Time += 0.01 ;
		
		// korzystaj¹c z ruchu w rzucie ukoœnym obliczenie x i Y
		// wziêcie pod uwagê odwrotnego uk³adu wspó³rzêdnych
		x +=  speed*Time*Math.cos( alfa1 )  ;
		y -=  speed*Time*Math.sin( alfa1 ) - 8/2*Time*Time ;
	
		
	}
	
	// rysowanie pocisku
	public void drawBall( Graphics g )
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor( new Color(20,20,20) );
		g2d.fillOval( (int ) x , ( int ) y , width, height);
		
	}

}
