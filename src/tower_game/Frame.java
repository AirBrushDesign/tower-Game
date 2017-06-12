package tower_game;

import javax.swing.JFrame; 

public class Frame extends JFrame 
	{
	
	static GameBoard board; 
	static Frame frame;
	
	// Konstruktor klasy Frame
		public Frame()										
		{	
			
			// nazwa okienka
			super("Hello");   									
		
			// inicjalizacja operacji po zamkniêciu 
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			
			// inicjalizacja rozmiaru okienka
			setSize(1200,400);				
			
			setResizable(false);
			
			//ustawienie widzialnoœci okienka
			setVisible(true);									
		}
		
		
		public static void main(String[] args)
		{
			
		// Stworzenie nowej "planszy gry"	
		 board = new GameBoard( frame );	
		 
		// stworzenie nowego okienka
		  frame = new Frame();		
		 
		//dodanie "planszy" do okienka
		 frame.add( board );								
	  
		}
		
	
	
}

	
