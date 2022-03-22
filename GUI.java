package snake;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;
import java.awt.*;


public class GUI extends JPanel implements KeyListener, ActionListener{

	
	
		Snake snake = new Snake();
		Cordinate testa = new Cordinate(0,0);
		Cordinate coda = new Cordinate (0,0);
	 	static int SCREEN_WIDTH = 600;
	    static int SCREEN_HEIGHT = 600;
	    static int UNIT_SIZE = 25;
	    Cordinate apple = new Cordinate(200,200);
	    Cordinate appleb = new Cordinate (300,250);
	    boolean running;
	    int score=0;
	    //final int X[]= new int[GAME_UNITS];
	    final int Y[]=new int[GAME_UNITS];
	    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	    private boolean left;
		private boolean right;
		private boolean up;
		private boolean down;
	    private Image apple_im;
	    int time,j=0;
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		GUI(){
			
			this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT)); //dimensioni dello schermo
	        this.setBackground(Color.black); //colore background
	        this.setFocusable(true);
	        addKeyListener(this); //key listener per recepire i tasti premuti
	       
			startGame(); //avvio del gioco
		}
		
		 public void startGame () {
			 	reset(); //inizializzazione dei valori
		        running=true;
		        snake.AggiungiCoda(testa); //aggiungo il primo elemento
				snake.AggiungiCoda(coda); //aggiungo una coda
		       // loadImages();
		        gioca(); 
		        
		    }
		 
		 private void loadImages() {

			
		        ImageIcon iia = new ImageIcon("src/resources/apple.png");
		        apple_im = iia.getImage();

		        
		    }
		
		
		public void paintComponent (Graphics g) {
	        super.paintComponent(g);
	        draw(g); //chiama il metodo draw
	        
	        
	    }
	    public void draw(Graphics g){ //funzione che disegna il campo, lo snake e la sua coda
	        if(running){
	        
	            //draw the grid line on the screen
	            for(int i = 0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){ //in base alla grandezza del campo disegno le linee
	                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT); //disegno la linea verticale 
	                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE); //disegno la linea orizzontale
	            }
	            gioca(); //chiamo la funzione gioca
	            //disegno le mele sul campo
	            g.setColor(Color.red);
	            g.fillOval(apple.x,apple.y,UNIT_SIZE,UNIT_SIZE);
	            g.setColor(Color.blue);
	            g.fillOval(appleb.x,appleb.y,UNIT_SIZE,UNIT_SIZE);
	            //disegno il corpo e la coda dello snake
	            for(int i =0;i<snake.coda;i++){
	                if(i==0){
	                    g.setColor(Color.green);             //for the head
	                }else {
	                    g.setColor(new Color(45,180,0)); //for the body

	                }
	                g.fillRect(snake.a[i].x,snake.a[i].y,UNIT_SIZE,UNIT_SIZE);
	                //score
	                g.setColor(Color.red);
	                g.setFont(new Font("Helvetica",Font.BOLD,40));
	                FontMetrics metrics = getFontMetrics(g.getFont());
	                g.drawString("Score: "+score,(metrics.stringWidth("Score: "+score))*0,g.getFont().getSize()); //mostra lo score a schermo
	                g.drawString("Time: "+time,(metrics.stringWidth("Time: "+time))+250,g.getFont().getSize()); //mostra il tempo rimanente a schermo
	                
	                
	                
	            }
	            
	        }
	        
	        else {
				gameover(g);
			}
	        
	       
	    }
	    
	    void gioca() {
			 
			if(running) { //eseguito solo se il gioco è in esecuzione
				
				 direzione(); //controllo cambi di direzione
	             check_collision(); //controllo se ci sono collisioni
	             check_mela(); //controllo se vegono mangiate le mele
	             
				
				
				pausa(120); //rallento l'esecuzione di 120 ms
				if (time==0) { //se il tempo si azzera game over
					running=false; 
				}
				
			}
			repaint(); //chiamo il repaint di draw
			
			
		 }
	    
	    void pausa(int ms) {	//rllento il gioco tramite il thread sleep
	    	try {
				Thread.sleep(ms);
				j++;
				
		 } catch (InterruptedException e) {
				e.printStackTrace();
		 }
	    
	    	if (j==8) {//decremento il timer
	    		j=0;
	    		time--;
	    	}
	    }
	    
	    
	    void check_collision() { //controllo che il muro non venga colpitoo la testa colpisca la coda
	    	
		    	for (int i=snake.coda; i>0;i--) { //ciclo for che scorre tutti gli elmenti della coda e controlla se coincidono con le coordinate della testa
		    		if (snake.a[0].x == snake.a[i].x && snake.a[0].y == snake.a[i].y) {
		    			running = false; //se coincidono running a false e game over
		    		}
		    	}
		    	
		    	
		    	if (snake.a[0].x < 0 || snake.a[0].x > SCREEN_WIDTH) { //controllo che la testa non esca dal campo
		    		running=false;
		    	}
		    	if (snake.a[0].y < 0 || snake.a[0].y > SCREEN_HEIGHT) { 
		    		running=false;
		    	}
		    	
	    		
	    }
	    
	    public void gameover(Graphics g){ 	//grafica del gameover quando il running viene impostato a 0
	    	
	        g.setColor(Color.red);
	        g.setFont( new Font("Helvetica",Font.BOLD, 40));
	        FontMetrics metrics1 = getFontMetrics(g.getFont());
	        g.drawString("Score: "+score, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+score))/2, g.getFont().getSize());
	        //Game over text
	        g.setColor(Color.red);
	        g.setFont(new Font("Helvetica",Font.BOLD,75));
	        FontMetrics metrics = getFontMetrics(g.getFont());
	        g.drawString("Game Over",(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
	        g.setColor(Color.green);
	        g.setFont( new Font("Helvetica",Font.BOLD, 25));
	        FontMetrics metrics2 = getFontMetrics(g.getFont());
	        g.drawString("E per uscire \n", (SCREEN_WIDTH - metrics2.stringWidth("E per uscire"))/2, (SCREEN_HEIGHT/2)+UNIT_SIZE*4);
	        g.drawString("R per rigiocare ", (SCREEN_WIDTH - metrics2.stringWidth("R per rigiocare"))/2, (SCREEN_HEIGHT/2)+UNIT_SIZE*3);
	    }
	    
	    
	    
	    
	    void check_mela() {
	    	
	    	if(apple.x == snake.a[0].x && apple.y== snake.a[0].y) {	//controlla se la mela rossa viene mangiata
	    		snake.Randomizer(apple, appleb); //randomizzo la mela rossa e blu quando viene mangiata
	    		snake.AggiungiCoda(testa); //aggiungo un valore in coda
	    		score++; //incremento il puntaggio
	    		time=time+5; //aumento il tempo a disposizione di 5 secondi
	    	}
	    	if(appleb.x == snake.a[0].x && appleb.y== snake.a[0].y) { //controlla se la mela blu viene mangiata
	    		snake.Randomizer(apple, appleb);	//randomizzo la mela rossa e blu quando viene mangiata
	    		for (int i=0;i<5;i++) {
	    			snake.AggiungiCoda(testa);	//aggiungo 5 valori in coda come penalità per aver mangiato la mela blu
	    		}
	    		
	    		score--; //abbasso di 1 il puntaggio
	    		
	    		
	    	}
	    	if (score<0) {//se lo score scende sotto lo 0 la partita va in game over
	    		running=false;
	    	}
	    	
	    	
	    }
	    
	    
	    void direzione() {	//modifica i valori delle cordinate della testa del serpente in base alla direzione
			if (left == true ) 
					{
				testa= new Cordinate(snake.a[0].x - UNIT_SIZE, snake.a[0].y);
			} 
			else if (right == true) {
				testa = new Cordinate(snake.a[0].x + UNIT_SIZE, snake.a[0].y);
			} 
			else if (up == true) {
				testa = new Cordinate(snake.a[0].x, snake.a[0].y - UNIT_SIZE);
			} 
			else if (down== true) {
				testa = new Cordinate(snake.a[0].x, snake.a[0].y + UNIT_SIZE);
			}
			snake.muovi(testa);
			
		}
	
	    
	    public void keyTyped(KeyEvent e) {
	    	// TODO Auto-generated method stub
			
		}
	    
	    public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
	    	
		}

		@Override
		public void keyPressed(KeyEvent e) { //keyEvent associato a key listener che, in base a quale tasto viene premuto, 
											// cambia i valori booleani di direzione
			// TODO Auto-generated method stub
			
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT && right != true)
			{
				
				left = true;
				right = false;
				up = false;
				down = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_RIGHT && left != true) 
			{
				left = false;
				right = true;
				up = false;
				down = false;
				
				
			}

			if (e.getKeyCode() == KeyEvent.VK_UP && down != true) 
			{
				left = false;
				right = false;
				up = true;
				down = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_DOWN && up != true)
			{
				left = false;
				right = false;
				up = false;
				down = true;
			}
			
			if (running == false) {	//comandi attivi solo quando il gioco è in "game over"
				if (e.getKeyCode() == KeyEvent.VK_E) {
					System.exit(1); //esco se viene premuto il tasto "e"
					
				}
				if (e.getKeyCode() == KeyEvent.VK_R) {
					reset();
					startGame(); //reset dei valori e riavvio della partita se viene premuto R
					
					
				}
			}
			
			
		}

		void reset() {	//inizializzo tutti i valori
			left=false;
			right=true;
			up=false;
			down=false;
			testa.x=25;
			testa.y=25;
			coda.x=0;
			coda.y=0;
			snake.clear();
			score=0;
			time=30;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}

	
		
		
	
       
		
		
}
