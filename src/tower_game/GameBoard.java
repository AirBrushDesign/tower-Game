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
	
	
// czy gra si� zap�tla
	boolean isRunning, 
	
// czy klikn�li�my przycisk
	clickDown = false ;
	
// deklaracja Armaty, trawnika, 
//	�rodka Armaty wzgl�dem kt�rego b�dziemy wykonywa� obr�t
	Armata armata;
	Grass trawka;
	Point CenterArmata;
	
// Definicja zmiennych  
	
	// max szybko�� pocisku
	double MAXSPEED = 10; 
	
	// min szybko�� pocisku
	double MINSPEED = 3; 
	
	// pocz�tkowa szybko�� pocisku
	double INITSPEED = 3; 
	
	
	// k�t wystrza�u, pr�dko�� chwilowa, krok zmiany pr�dko�ci
	double alfa, speed = INITSPEED, speedStep = 0.1;
	
	
// lista pocisk�w i wrog�w
	ArrayList<Ball> balls;
	ArrayList<Enemy> enemys;
	
// etykiety bierz�cej pr�dko�ci i ko�ca gry	
	JLabel speedInfo;
	JLabel gameover;
	
	
// definicja okienka w kt�rym plansza b�dzie wy�wietlana ( potrzebne do zamkni�cia wewn�trze tej klasy )	
	JFrame mainWindow;
	
	GameBoard( JFrame window )
	{	
		
		setFocusable(true);
	    setFocusTraversalKeysEnabled(false);
		
	 // dodanie listener�w 
		addKeyListener( this );
		addMouseListener( this );
		addMouseMotionListener( this );
		
	// ustawienie t�a 
		setBackground( new Color( 66, 208, 237 ) );
	
	//inicjacja nowej planszy
		initGame();
	     
	}
	
	public void initGame()
	{
		
	//dodanie etykiety pr�dko�ci do planszy	
		speedInfo = new JLabel();
		add(speedInfo);
	
		
		gameover = new JLabel();
		
	// mo�liwo�� u�ywania setLocation ( bez grida )
		gameover.setLayout( null );
		gameover.setLocation(273, 200);
		
		gameover.setText("Game Over");
		add(gameover);
		
	// inicjalizacja armaty i pod�o�a 
		CenterArmata = new Point(30, 300);
		armata = new Armata( CenterArmata , 50, 20  );
		trawka = new Grass();
		
	// inicjalizacja funkcji i zmiennych, umo�liwjaj�cych restart gry
		timerAndLists();
		
	}
	
	
	public void timerAndLists()
	{
		
		// czyszczenie/ tworzenie list wrog�w i pocisk�w
		balls = new ArrayList<>();
		enemys = new ArrayList<>();

		// inicjalizacja nowego timera
		 timer = new Timer();
		 
		 // dodanie zadania do timera (P�tla g��wna gry )
	     timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 5);
	     
	     // spown wrog�w
	     timer.scheduleAtFixedRate(new TimerTask() {
	    	  @Override
	    	  public void run() {
	    		 spownEnemy();
	    	  }
	    	}, 2*1000, 2*1000);
	     
	    // wystartowanie p�tli gry
	 	isRunning = true;
	 	
	 	
	 	gameover.setVisible(false);
	}
	
	
	
	public void makeInterface()
	{
		// wy�wietlanie informacji o pr�dko�ci pocz�tkowej pocisku
		speedInfo.setText("Moc:"+( int )(speed/MAXSPEED*100 )+" %");
		
	// kiedy naci�niemy przycisk zwi�kszamy bie��c� pr�dko�� 
	// oscyluj�c pomi�dzy warto�ciami MINSPEED i MAX SPEED 
		
		if( clickDown )
		{
			if( speed < MINSPEED )
				speedStep = 0.1;
			if( speed > MAXSPEED )
				speedStep = -0.1;
			
				speed += speedStep;
		}
		
	}
	
	
	// funkcja rysuj�ca gr�
	@Override
	protected void paintComponent(Graphics g) {
		
		// za ka�dym razem rysujemy od nowa wszystkie elementy w nowej pozycji
		super.paintComponent( g );
		
		// rysowanie pod�o�a 
		trawka.drawGrass( g );
		
		// rysowanie ka�dgo pocisku
		for(int i = 0; i < balls.size(); i++)
		{
			balls.get(i).drawBall( g );
		}
		
		// rysowanie ka�dego wroga
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
		        
		        //po wyszczeleniu pocisku powr�t bie��cej pr�dko�ci do pr�dko�ci pocz�tkowej
		        speed = INITSPEED;
		    }
			
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int MouseX = e.getX();
		int MouseY = e.getY();
		
		// obliczanie k�ta nachylenia armaty, 
		// bie�emy pod uwag� specyfik� uk�adu wsp�rz�dnych w javie 
	    alfa = Math.atan2( armata.getCenterY()-MouseY, MouseX-armata.getCenterX() );
		
	    // ograniczenie k�ta nachylenia od 0 do 90 stopni
		if( Math.PI/2 <= alfa )
			alfa = Math.PI/2;
		
		if(0 >= alfa )
			alfa = 0 ;
		
	}
	

	// Przycisk wcisi�ty 
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (e.getButton() == MouseEvent.BUTTON1)
	        clickDown = true;    
		
	}
    
	
	// obs�uga przycisk�w klawiatury 
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
	
	// Rych Pocisk�w
	public void moveBalls()
	{
		// zmiana po�o�enia ka�dego pocisku 
		for(int i = 0; i < balls.size(); i++)
		{
			Ball current = 	balls.get(i);
			
			// je�eli pocisk spadnie poni�ej okna jest usuwany w celu optymalizacji
			if(current.getY() > 350)
				balls.remove(i);
			else
				current.move();
		
		}
	}
	
	// poruszanie wrog�w 
	
	public void  moveEnemys()
	{
		for(int i = 0; i < enemys.size(); i++)
		{
			Enemy current = enemys.get(i);
			
			// je�eli wr�g przejdzie odpowiedni obszar gra zostaje przerwana
			if(current.getX() < 10)
			{
				enemys.remove(i);
				isRunning = false;
			}
			// w przeciwnym razie wr�g zostaje poruszony 
			else
				current.move();
		}
	}

	// sprawdzanie kolizji pocisk�w z wrogami
	public void checkColisions()
	{
		for( int i = 0; i < enemys.size(); i++)
		{
			for( int j = 0; j < balls.size(); j++)
			{
				Enemy curEn= enemys.get( i );
				Ball curBal = balls.get( j );
				
			
				//wykrywanie kolzji w mom�cie kiedy wsp�rz�dne pocisku pokryj� si�  
				// z kszta�tem wroga ( prostok�tem )
				// pierwszy if sprawdzenie w osi X, drugi sprawdzenie w osi Y 
				
				if( curEn.getX() < curBal.getX() &&  curEn.getX()+curEn.getWidth() > curBal.getX())
					if(  ( curEn.getY() + curEn.getHeight() ) > curBal.getY() && curEn.getY()  < curBal.getY())
					{
						// wyslosowanie obra�e� 
						int damage = (int) Math.floor( Math.random()*50 );
						
						//je�li sko�czy�o si� �ycie wrogowi usuwamy go z planszy
						if( curEn.damage( damage ) < 0 )
							enemys.remove( i );
						
						// usuwamy pocisk po ka�dym trafieniu w wroga
						balls.remove( j );
						
					}
				}		
		}
	}

	// Scalenie funkcji do g��wnej p�tli
	public void doGame()
	{
	
		moveBalls();
		moveEnemys();
		checkColisions();
	}
	
	// Tworzenie nowego wroga
	public void spownEnemy(){	enemys.add( new Enemy() );   }

	
	// nowa prywatna klasa rozszerzaj�ca TimerTask wykonuj�c� funkcje w p�tli g��wnej 
	
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
   
        // je�li gra nie zosta�a przrwana  
        	if( isRunning )
        	{
        	
        		// poruszanie przeciwnikami, pociskami, sprawdzanie kolizji
        			doGame();
        			
        		//rysowanie element�w gry po zmianie pozycji
        			repaint();
        			
        		// zmiana w interface wy�wietlanie aktualnej pr�dko�ci pocisku 	
        			makeInterface();
        	}
        	else 
        	{
        		// wy��czenie timera
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

