package gui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {
	ProgramGrid grid;
	
	public KeyHandler(ProgramGrid grid) {
		this.grid = grid;
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		if(key.isControlDown()) {
			if(key.getKeyCode() == 38) {
				grid.moveBox(-2);
			}
			else if(key.getKeyCode() == 40) {
				grid.moveBox(-1);
			}
		}
		else {
			if(key.getKeyCode() > 36 && key.getKeyCode() < 41){
				grid.moveBox(key.getKeyCode());
			}else{
				if(key.getKeyChar() == '\b'){
					// Backspace
					grid.b.board[(int) grid.boxLoc.x][(int) grid.boxLoc.y][(int) grid.boxLoc.z] = (char)0;
				}else if(key.getKeyCode() == 13){
					// Enter:step the board
					// TODO
				}else if(key.getKeyCode() == 27){
					// Escape: reset
					// TODO
				}else{
					// Write to the board
					grid.b.board[(int) grid.boxLoc.x][(int) grid.boxLoc.y][(int) grid.boxLoc.z] = key.getKeyChar();
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
