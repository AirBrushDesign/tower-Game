package tower_game;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameBoard extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	
//timer 
	Timer timer;
	
	
// czy gra siê zapêtla
	boolean isRunning, 
	
// czy kliknêliœmy przycisk
	clickDown = false ;
	
// deklaracja Armaty, trawnika, 
//	œrodka Armaty wzglêdem którego bêdziemy wykonywaæ obrót
	Armata armata;
	Grass trawka;
	Point CenterArmata;
	
// Definicja zmiennych  
	
	// max szybkoœæ pocisku
	double MAXSPEED = 10; 
	
	// min szybkoœæ pocisku
	double MINSPEED = 3; 
	
	// pocz¹tkowa szybkoœæ pocisku
	double INITSPEED = 3; 
	
	
	// k¹t wystrza³u, prêdkoœæ chwilowa, krok zmiany prêdkoœci
	double alfa, speed = INITSPEED, speedStep = 0.1;
	
	
// lista pocisków i wrogów
	ArrayList<Ball> balls;
	ArrayList<Enemy> enemys;
	
// etykiety bierz¹cej prêdkoœci i koñca gry	
	JLabel speedInfo;
	JLabel gameover;
	
	
// definicja okienka w którym plansza bêdzie wyœwietlana ( potrzebne do zamkniêcia wewn¹trze tej klasy )	
	JFrame mainWindow;
	
	GameBoard( JFrame window )
	{	
		
		setFocusable(true);
	    setFocusTraversalKeysEnabled(false);
		
	 // dodanie listenerów 
		addKeyListener( this );
		addMouseListener( this );
		addMouseMotionListener( this );
		
	// ustawienie t³a 
		setBackground( new Color( 66, 208, 237 ) );
	
	//inicjacja nowej planszy
		initGame();
	     
	}
	
	public void initGame()
	{
		
	//dodanie etykiety prêdkoœci do planszy	
		speedInfo = new JLabel();
		add(speedInfo);
	
		
		gameover = new JLabel();
		
	// mo¿liwoœæ u¿ywania setLocation ( bez grida )
		gameover.setLayout( null );
		gameover.setLocation(273, 200);
		
		gameover.setText("Game Over");
		add(gameover);
		
	// inicjalizacja armaty i pod³o¿a 
		CenterArmata = new Point(30, 300);
		armata = new Armata( CenterArmata , 50, 20  );
		trawka = new Grass();
		
	// inicjalizacja funkcji i zmiennych, umo¿liwjaj¹cych restart gry
		timerAndLists();
		
	}
	
	
	public void timerAndLists()
	{
		
		// czyszczenie/ tworzenie list wrogów i pocisków
		balls = new ArrayList<>();
		enemys = new ArrayList<>();

		// inicjalizacja nowego timera
		 timer = new Timer();
		 
		 // dodanie zadania do timera (Pêtla g³ówna gry )
	     timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 5);
	     
	     // spown wrogów
	     timer.scheduleAtFixedRate(new TimerTask() {
	    	  @Override
	    	  public void run() {
	    		 spownEnemy();
	    	  }
	    	}, 2*1000, 2*1000);
	     
	    // wystartowanie pêtli gry
	 	isRunning = true;
	 	
	 	
	 	gameover.setVisible(false);
	}
	
	
	
	public void makeInterface()
	{
		// wyœwietlanie informacji o prêdkoœci pocz¹tkowej pocisku
		speedInfo.setText("Moc:"+( int )(speed/MAXSPEED*100 )+" %");
		
	// kiedy naciœniemy przycisk zwiêkszamy bie¿¹c¹ prêdkoœæ 
	// oscyluj¹c pomiêdzy wartoœciami MINSPEED i MAX SPEED 
		
		if( clickDown )
		{
			if( speed < MINSPEED )
				speedStep = 0.1;
			if( speed > MAXSPEED )
				speedStep = -0.1;
			
				speed += speedStep;
		}
		
	}
	
	
	// funkcja rysuj¹ca grê
	@Override
	protected void paintComponent(Graphics g) {
		
		// za ka¿dym razem rysujemy od nowa wszystkie elementy w nowej pozycji
		super.paintComponent( g );
		
		// rysowanie pod³o¿a 
		trawka.drawGrass( g );
		
		// rysowanie ka¿dgo pocisku
		for(int i = 0; i < balls.size(); i++)
		{
			balls.get(i).drawBall( g );
		}
		
		// rysowanie ka¿dego wroga
		for(int i = 0; i < enemys.size(); i++)
		{
			enemys.get(i).drawEnemy( g );
		}
		
		// rysowanie armaty
		armata.drawGun( g, alfa );	

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
		// wykrywanie puszczenia przycisku myszy
		
		 if (e.getButton() == MouseEvent.BUTTON1) {
		        clickDown = false;
		        
		        // dodanie nowego pocisku do listy 
		        balls.add(new Ball(speed, alfa, 30,295, 30));
		        
		        //po wyszczeleniu pocisku powrót bie¿¹cej prêdkoœci do prêdkoœci pocz¹tkowej
		        speed = INITSPEED;
		    }
			
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int MouseX = e.getX();
		int MouseY = e.getY();
		
		// obliczanie k¹ta nachylenia armaty, 
		// bie¿emy pod uwagê specyfikê uk³adu wspó³rz¹dnych w javie 
	    alfa = Math.atan2( armata.getCenterY()-MouseY, MouseX-armata.getCenterX() );
		
	    // ograniczenie k¹ta nachylenia od 0 do 90 stopni
		if( Math.PI/2 <= alfa )
			alfa = Math.PI/2;
		
		if(0 >= alfa )
			alfa = 0 ;
		
	}
	

	// Przycisk wcisiêty 
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (e.getButton() == MouseEvent.BUTTON1)
	        clickDown = true;    
		
	}
    
	
	// obs³uga przycisków klawiatury 
	@Override
	public void keyPressed(KeyEvent arg0) {
		if( !isRunning )
		{
			int sing = arg0.getKeyCode();
			//przycisk a
			if( sing == 65 ) 
			{
				
				System.out.println("restart");
				timerAndLists();
			}
		}
	}
	
	// Rych Pocisków
	public void moveBalls()
	{
		// zmiana po³o¿enia ka¿dego pocisku 
		for(int i = 0; i < balls.size(); i++)
		{
			Ball current = 	balls.get(i);
			
			// je¿eli pocisk spadnie poni¿ej okna jest usuwany w celu optymalizacji
			if(current.getY() > 350)
				balls.remove(i);
			else
				current.move();
		
		}
	}
	
	// poruszanie wrogów 
	
	public void  moveEnemys()
	{
		for(int i = 0; i < enemys.size(); i++)
		{
			Enemy current = enemys.get(i);
			
			// je¿eli wróg przejdzie odpowiedni obszar gra zostaje przerwana
			if(current.getX() < 10)
			{
				enemys.remove(i);
				isRunning = false;
			}
			// w przeciwnym razie wróg zostaje poruszony 
			else
				current.move();
		}
	}

	// sprawdzanie kolizji pocisków z wrogami
	public void checkColisions()
	{
		for( int i = 0; i < enemys.size(); i++)
		{
			for( int j = 0; j < balls.size(); j++)
			{
				Enemy curEn= enemys.get( i );
				Ball curBal = balls.get( j );
				
			
				//wykrywanie kolzji w momêcie kiedy wspó³rzêdne pocisku pokryj¹ siê  
				// z kszta³tem wroga ( prostok¹tem )
				// pierwszy if sprawdzenie w osi X, drugi sprawdzenie w osi Y 
				
				if( curEn.getX() < curBal.getX() &&  curEn.getX()+curEn.getWidth() > curBal.getX())
					if(  ( curEn.getY() + curEn.getHeight() ) > curBal.getY() && curEn.getY()  < curBal.getY())
					{
						// wyslosowanie obra¿eñ 
						int damage = (int) Math.floor( Math.random()*50 );
						
						//jeœli skoñczy³o siê ¿ycie wrogowi usuwamy go z planszy
						if( curEn.damage( damage ) < 0 )
							enemys.remove( i );
						
						// usuwamy pocisk po ka¿dym trafieniu w wroga
						balls.remove( j );
						
					}
				}		
		}
	}

	// Scalenie funkcji do g³ównej pêtli
	public void doGame()
	{
	
		moveBalls();
		moveEnemys();
		checkColisions();
	}
	
	// Tworzenie nowego wroga
	public void spownEnemy(){	enemys.add( new Enemy() );   }

	
	// nowa prywatna klasa rozszerzaj¹ca TimerTask wykonuj¹c¹ funkcje w pêtli g³ównej 
	
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
   
        // jeœli gra nie zosta³a przrwana  
        	if( isRunning )
        	{
        	
        		// poruszanie przeciwnikami, pociskami, sprawdzanie kolizji
        			doGame();
        			
        		//rysowanie elementów gry po zmianie pozycji
        			repaint();
        			
        		// zmiana w interface wyœwietlanie aktualnej prêdkoœci pocisku 	
        			makeInterface();
        	}
        	else 
        	{
        		// wy³¹czenie timera
        		timer.cancel();
        		gameover.setVisible( true );
        		
        	}
        }
    }
    
 
    
    @Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}
	
}

