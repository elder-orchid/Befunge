package interp;

import java.util.Arrays;

public class BoardDriver {
	public static void main(String[] args){
		Board b = new Board(new int[] {1,1,4});
		b.board[0][0] = new char[] {'1','2','+','@'};//test +
		b.sprint();
		System.out.println(b);

		b = new Board(new int[] {1,1,4});
		b.board[0][0] = new char[] {'4','2','-','@'};//test -
		b.sprint();
		System.out.println(b);
		b = new Board(new int[] {1,1,4});
		b.board[0][0] = new char[] {'3','1','/','@'};//test /
		b.sprint();
		System.out.println(b);
		

		b = new Board(new int[] {1,1,4});
		b.board[0][0] = new char[] {'4','2','*','@'};//test *
		b.sprint();
		System.out.println(b);

		b = new Board(new int[] {1,1,20});
		b.board[0][0] = "\"emem\">:#,_@".toCharArray();//test *
		b.sprint();
		System.out.println(b.output);
	}
}
