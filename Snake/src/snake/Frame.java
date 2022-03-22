package snake;
import javax.swing.*;


public class Frame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Frame(){
		
			 	this.add(new GUI());
		        this.setTitle("Snake");
		        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        this.setResizable(false);
		        this.pack(); // take our J frame and fit it around all of the component that we add
		        this.setVisible(true);
		        this.setLocationRelativeTo(null); // appear in the middle of the computer screen
		
	}
}
