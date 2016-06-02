package interp;

import java.util.Arrays;

public class BoardDriver {
	public static void main(String[] args){
		Board b = new Board(new int[] {1,1,5});
		b.board[0][0] = new char[] {'x','1','2','+','@'};//test +
		b.sprint();
		System.out.println(b);

		b = new Board(new int[] {1,1,5});
		b.board[0][0] = new char[] {'x','4','2','-','@'};//test -
		b.sprint();
		System.out.println(b);
		b = new Board(new int[] {1,1,5});
		b.board[0][0] = new char[] {'x','3','1','/','@'};//test /
		b.sprint();
		System.out.println(b);
		

		b = new Board(new int[] {1,1,5});
		b.board[0][0] = new char[] {'x','4','2','*','@'};//test *
		b.sprint();
		System.out.println(b);

		b = new Board(new int[] {1,1,20});
		b.board[0][0] = "x\"emem\"x:#,i@".toCharArray();//test *
		b.sprint();
		System.out.println(b.output);
	}
}
