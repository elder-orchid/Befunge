package gui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {

	ArrayList<Integer> currentkeys = new ArrayList<Integer>();
	
	@Override
	public void keyPressed(KeyEvent key) {
		currentkeys.add(key.getKeyCode());
		if(currentkeys.contains(17)) {
			if(currentkeys.contains(38)) {
				ProgramGrid.moveBox(-1);
			}
			else if(currentkeys.contains(40)) {
				ProgramGrid.moveBox(-2);
			}
		}
		else {
			ProgramGrid.moveBox(currentkeys.get(currentkeys.size()-1));
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		currentkeys.remove(currentkeys.size()-1);
	}

	@Override
	public void keyTyped(KeyEvent key) {
		
	}

}
