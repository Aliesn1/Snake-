package snake;
import java.util.Random;
public class Snake {

	static final int MAX= 100000000;
	int coda;
	Cordinate[] a;
	Random random = new Random();
	int SCREEN_WIDTH = 600;
	int SCREEN_HEIGHT = 600;
	int UNIT_SIZE=50;
	
	Snake(){
		a = new Cordinate[MAX];	//istanzio un vettore per contenere il numero massimo di elementi
		coda=0;	//inizializzo la coda a 0
		
	}
	
	void muovi(Cordinate c){
		for(int i = coda; i >= 1; i--) {	//sposto gli elementi del vettore al posto successivo
			a[i] = a[i-1];
		}
		
		a[0] = c;  //aggiungo le cordinate al primo spazio dell'array
	}
	
	void AggiungiCoda(Cordinate c) {
		
		a[coda] = c;
		coda++;
		muovi(c);
		
	}
	
	public void Randomizer (Cordinate a, Cordinate b) {
		
		a.x = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        a.y = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        do {
        	b.x = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
            b.y = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        }while(a==b);
      
	}
	
	void clear() {
		coda=0;
	}
	
	
	
}
