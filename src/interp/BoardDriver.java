package interp;

import java.util.Arrays;

public class BoardDriver {
	public static void main(String[] args){
		Board b = new Board(new int[] {3,3,5});
		/*
		b.board[0][0] = new char[] {'v','1','2','+','@'};//test +
		b.sprint();
		System.out.println(b);

		b = new Board(new int[] {3,3,5});
		b.board[0][0] = new char[] {'v','4','2','-','@'};//test -
		b.sprint();
		System.out.println(b);
		*/
		b = new Board(new int[] {3,3,5});
		b.board[0][0] = new char[] {'v','8','2','/','@'};//test /
		b.sprint();
		System.out.println(b);
		//the fuck?
		//apparently 8/2 = 0
		//needs bugfix
		
		/*
		b = new Board(new int[] {3,3,5});
		b.board[0][0] = new char[] {'v','4','2','*','@'};//test *
		b.sprint();
		System.out.println(b);
		*/
	}
}
