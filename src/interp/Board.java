package interp;

public class Board {
	public int[] pos;
	public char[][][] board;
	public Direction d;
	public int[] stack;
	public int stackIndex;
	
	public enum Direction{
		Left,		//x--
		Right,		//x++
		Backward,	//y--
		Foreward,	//y++
		Up,			//z--
		Down		//z++
	}
	
	public Board(int[] bounds){
		this.board = new char[bounds[0]][bounds[1]][bounds[2]];

		this.d = Direction.Right;
		this.pos = new int[] {0,0,0};
		this.stackIndex = 0;
		this.stack = new int[1024];
	}
	
	public Board(int[] bounds, int stacksize){
		this.board = new char[bounds[0]][bounds[1]][bounds[2]];

		this.d = Direction.Right;
		this.pos = new int[] {0,0,0};
		this.stackIndex = 0;
		this.stack = new int[stacksize];
	}
	
	public void step(){//pos is length 3
		char curVal = board[pos[0]][pos[1]][pos[2]];
		if(){
		if (curVal - (int)'0' < 10 ) {
			stack[stackIndex] = curVal;
			stackIndex++;
		}else{
			switch(curVal){
			case ('+'): 
				stack[stackcount - 1] = stack[stackcount - 2] / stack[stackcount - 1];
				stackcount ++;
				break;
			case ('-'):	
				stack[stackcount - 1] = stack[stackcount - 2] / stack[stackcount - 1];
				stackcount ++;
				break;
			case ('*'): 
				stack[stackcount - 1] = stack[stackcount - 2] / stack[stackcount - 1];
				stackcount ++;
				break;
			case ('/'):	
				stack[stackcount - 1] = stack[stackcount - 2] / stack[stackcount - 1];
				stackcount ++;
				break;
			case ('%'):	
				stack[stackcount - 1] = stack[stackcount - 2] / stack[stackcount - 1];
				stackcount ++;
				break;
			case ('`'):	
				if(stack[stackcount - 2] > stack[stackcount - 1]) {
					stack[stackcount] = 1;
				}
				else {
					stack[stackcount] = 0;
				}
				break;
			case ('?'):
				double random = Math.random() * 4;
				if ((int)random >= 3) {
					goup = false;
					godown = true;
					goright = false;
					goleft = false;
					break;
				}
				if ((int)random >= 2) {
					goup = true;
					godown = false;
					goright = false;
					goleft = false;
					break;
				}
				if ((int)random >= 1) {
					goup = false;
					godown = false;
					goright = true;
					goleft = false;
					break;
				}
				if ((int)random >= 0) {
					goup = false;
					godown = false;
					goright = false;
					goleft = true;
					break;
				}
			case ('_'):
				if (stack[stackcount-1] == 0) {
					goup = false;
					godown = false;
					goright = true;
					goleft = false;
					break;
				}
				else {
					goup = false;
					godown = false;
					goright = false;
					goleft = true;
					break;
				}
			case ('|'):
				if (stack[stackcount-1] == 0) {
					goup = false;
					godown = true;
					goright = false;
					goleft = false;
					break;
				}
				else {
					goup = true;
					godown = false;
					goright = false;
					goleft = false;
					break;
				}
			case ('"'):
				stringmode ^= true;
			break;
			case (':'):
				stack[stackcount] = stack[stackcount - 1];
			break;
			case ('\\'):
				stack[stackcount -1] = stack[stackcount - 2];
			stack[stackcount -2] = stack[stackcount - 1];
			break;
			case ('$'):
				stack[stackcount - 1] = 0;
			break;
			case ('.'):
				System.out.print((int)stack[stackcount -1]);
			break;
			case (','):
				System.out.print((char)stack[stackcount -1]);
			break;
			case ('#'):
				if (goright) {
					x++;
				}
			if (goleft) {
				x--;
			}
			if (goup) {
				y++;
			}
			if (godown) {
				y--;
			}
			case ('@'):
				continue;
			case ('p'):
				input [stack[stackcount - 1]] [stack[stackcount - 2]] = (char)stack[stackcount -3];
				break;
			case ('g'):
				if (stack[stackcount -1] <= boundsx && stack[stackcount - 2] <= boundsy){
					stack[stackcount] = input [stack[stackcount -1]] [stack[stackcount - 2]];
				}else{
					stack[stackcount] = 0;
				}
				break;
			case ('v'):
				goup = false;
				godown = true;
				goright = false;
				goleft = false;
				System.out.println("ayylmao");
				break;
			case ('^'):
				goup = true;
				godown = false;
				goright = false;
				goleft = false;
				break;
			case ('>'):
				goup = false;
				godown = false;
				goright = true;
				goleft = false;
				break;
			case ('<'):
				goup = false;
				godown = false;
				goright = false;
				goleft = true;
				break;
			}
		}else{
			stack[stackcount] = (int)input[y][x];
		}
		if (goright) {
			x++;
		}
		if (goleft) {
			x--;
		}
		if (goup) {
			y--;
		}
		if (godown) {
			y++;
		}
		System.out.println(input[y][x]);
		iteration ++;
		System.out.println(iteration + " iteration");
		System.out.println(stack[stackcount]);
		if (iteration >= 200) {
			System.out.println("fin");
			break;
		}
	}
}