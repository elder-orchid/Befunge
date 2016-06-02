package interp;

import java.util.Arrays;

public class Board {
	public int[] pos;
	public char[][][] board;
	public Direction dir;
	public byte[] stack;
	public int stackIndex;
	public boolean stringIn;
	public String output;
	public boolean finished;
	
	public enum Direction{//uses mathematical orientation:z is up and down, y is depth
		Left,		//x--
		Right,		//x++
		Backward,	//z--
		Foreward,	//z++
		Up,			//y--
		Down;		//y++

		private static Direction[] all=null;
		
		private int mod(int a, int b){
			if(a>=0){
				return a%b;
			}else{
				return b+a%b;
			}
		}
		
		private int[] addValue(int[] in, char[][][] board){
			switch(this){
			case Left:
				return new int[] {mod(-1+in[0],board.length),in[1],in[2]};//x is first index
			case Right:
				return new int[] {mod(1+in[0],board.length),in[1],in[2]};
			case Backward:
				return new int[] {in[0],in[1],mod(-1+in[2],board[0][0].length)};//z is last index
			case Foreward:
				return new int[] {in[0],in[1],mod(1+in[2],board[0][0].length)};
			case Up:
				return new int[] {in[0],mod(-1+in[1],board[0].length),in[2]};//y is middle index
			case Down:
				return new int[] {in[0],mod(1+in[1],board[0].length),in[2]};
			}
			return new int[] {0,0,0};//catch all
		}
		
		public static Direction random(){
			if(all==null){
				all = new Direction[] {Direction.Left, Direction.Right, Direction.Backward, Direction.Foreward, Direction.Up, Direction.Down};
			}
			return all[(int)(Math.random()*6)];
		}
	}
	
	public Board(int[] bounds){
		this.board = new char[bounds[0]][bounds[1]][bounds[2]];

		this.dir = Direction.Right;
		this.pos = new int[] {0,0,0};
		this.stackIndex = 0;
		this.stringIn = false;
		this.output = "";
		this.finished = false;
		this.stack = new byte[1024];
	}
	
	public Board(int[] bounds, int stacksize){
		this.board = new char[bounds[0]][bounds[1]][bounds[2]];

		this.dir = Direction.Right;
		this.pos = new int[] {0,0,0};
		this.stackIndex = 0;
		this.stringIn = false;
		this.output = "";
		this.finished = false;
		this.stack = new byte[stacksize];
	}
	
	public void step(){//pos is length 3
		final char curVal = board[pos[0]][pos[1]][pos[2]];
		if(!finished){
			if(stringIn && curVal!='\"'){// currently a string
				stack[stackIndex] = (byte)curVal;
				stackIndex++;
			}else if (((int)curVal - (int)'0' < 10 && (int)curVal - (int)'0'>=0) || ((int)curVal - (int)'A' < 6 && (int)curVal - (int)'A'>=0) || ((int)curVal - (int)'a' < 6 && (int)curVal - (int)'a'>=0)) {// if it's a hex input
				stack[stackIndex] = (byte) Integer.parseInt(""+curVal,16);
				stackIndex++;
			}else{// special char
				switch(curVal){//in the order of esolang.org/wiki/Befunge
				
				//operators
				case ('+'):
					if(stackIndex >= 2){
						stack[stackIndex - 2] = (byte) (stack[stackIndex - 2] + stack[stackIndex - 1]);//add two previous vals into the value two indecies earlier
						stack[stackIndex - 1] = 0;//set used slot to 0
						stackIndex--;//go to 0 slot
					}
					break;
				case ('-'):	
					if(stackIndex >= 2){
						stack[stackIndex - 2] = (byte) (stack[stackIndex - 2] - stack[stackIndex - 1]);//subtract stack value of previous from stack value two previous and set that in the stack 2 previous
						stack[stackIndex - 1] = 0;//set used slot to 0
						stackIndex--;//go to 0 slot
					}
					break;
				case ('*'):
					if(stackIndex >= 2){
						stack[stackIndex - 2] = (byte) (stack[stackIndex - 2] * stack[stackIndex - 1]);//multiply stack value of previous with stack value two previous and set that in the stack 2 previous
						stack[stackIndex - 1] = 0;//set used slot to 0
						stackIndex--;//go to 0 slot
					}
					break;
				case ('/'):	
					if(stackIndex >= 2){
						stack[stackIndex - 2] = (byte) (stack[stackIndex - 2] / stack[stackIndex - 1]);//divide stack value of previous from stack value two previous and set that in the stack 2 previous
						stack[stackIndex - 1] = 0;//set used slot to 0
						stackIndex--;//go to 0 slot
					}
					break;
				case ('%'):	
					if(stackIndex >= 2){
						stack[stackIndex - 2] = (byte) (stack[stackIndex - 2] % stack[stackIndex - 1]);//subtract stack value of previous from stack value two previous and set the remainder in the stack 2 previous
						stack[stackIndex - 1] = 0;//set used slot to 0
						stackIndex--;//go to 0 slot
					}
					break;
				case ('!'):
					if(stackIndex >= 1){
						stack[stackIndex - 1] = (byte) (stack[stackIndex - 1] == 0?1:0);
					}
					break;
				case ('`'):	
					if(stackIndex >= 1){
						stack[stackIndex - 1] = (byte) (stack[stackIndex - 2] > stack[stackIndex - 1]?1:0);
					}
					break;
				
				//directions
				case ('v')://x dimensions
					this.dir = Direction.Down;
					break;
				case ('^'):
					this.dir = Direction.Up;
					break;
				case ('>')://z dimensions
					this.dir = Direction.Right;
					break;
				case ('<'):
					this.dir = Direction.Left;
					break;
				case ('x')://y dimensions
					this.dir = Direction.Foreward;				
					break;
				case ('o'):
					this.dir = Direction.Backward;			
					break;
				case ('?'):
					this.dir = Direction.random();
	
				//if statements
				case ('_')://x axis
					if(stackIndex >= 1){
						this.dir = stack[stackIndex - 1]==0?Direction.Right:Direction.Left;
						stack[stackIndex - 1] = 0;
						stackIndex--;
					}
					break;
				case ('|')://z axis
					if(stackIndex >= 1){
						this.dir = stack[stackIndex - 1]==0?Direction.Down:Direction.Up;
						stack[stackIndex - 1] = 0;
						stackIndex--;
					}
					break;
				case ('i')://y axis
					if(stackIndex >= 1){
						this.dir = stack[stackIndex - 1]==0?Direction.Foreward:Direction.Backward;
						stack[stackIndex - 1] = 0;
						stackIndex--;
					}
					break;
	
				case ('"')://toggle string input
					stringIn = !stringIn;
					break;
				case (':')://duplicate top stack value
					if(stackIndex >= 1){
						stack[stackIndex] = stack[stackIndex - 1];
						stackIndex++;
					}
					break;
				case ('\\')://swap top stack values
					if(stackIndex >= 2){
						byte swap = stack[stackIndex - 1];
						stack[stackIndex - 1] = stack[stackIndex - 2];
						stack[stackIndex - 2] = swap;
					}
					break;
				case ('$')://remove top stack value
					if(stackIndex >= 1){
						stack[stackIndex - 1] = 0;
						stackIndex--;
					}
					break;
					
				//outputs
				case ('.')://pop and print as int
					if(stackIndex >= 1){
						output+=stack[stackIndex - 1]+" ";
						stackIndex--;
					}
					break;
				case (',')://pop and print as char
					if(stackIndex >= 1){
						output += (char)stack[stackIndex - 1];
						stackIndex--;
					}
					break;
				
				case ('#')://hop
					this.pos = this.dir.addValue(this.pos,board);
					break;
				case ('@'):
					this.finished = true;
					break;
				case ('g')://get
					if (stackIndex >= 4 && stack[stackIndex - 3] < this.board.length && stack[stackIndex - 2] <= this.board[0].length && stack[stackIndex - 1] <= this.board[0][0].length){
						this.board[stack[stackIndex - 3]][stack[stackIndex - 2]][stack[stackIndex - 1]] = (char) stack[stackIndex - 4];

						for(int i=0;i<4;i++){//set to 0
							stack[stackIndex - i - 1] = 0;
						}
						stackIndex-=4;
					}
					break;
				case ('p')://put
					if (stackIndex >= 3 && stack[stackIndex - 3] < this.board.length && stack[stackIndex - 2] <= this.board[0].length && stack[stackIndex - 1] <= this.board[0][0].length){
						stack[stackIndex - 3] = (byte) this.board[stack[stackIndex - 3]][stack[stackIndex - 2]][stack[stackIndex - 1]];//set value

						for(int i=0;i<2;i++){//remove used values
							stack[stackIndex - i - 1] = 0;
						}
					}
					break;
				}
			}			
			this.pos = this.dir.addValue(this.pos, board);//move in direction
		}
	}
	
	public void sprint(){
		while(!finished){
			this.step();
		}
	}
	
	private byte[] trimStack(){
		int i=stack.length - 1;
		while(i>0 && stack[i]==0){
			i--;
		}
		return Arrays.copyOfRange(stack, 0, i+1);
	}
	
	public String toString(){
		String s = "";
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board[i].length;j++){
				for(int k=0;k<board[i][j].length;k++){
					s+=board[i][j][k]+" ";
				}
				s+="\n";
			}
			s+="\n";
		}
		s+="stack: "+Arrays.toString(trimStack());
		return s;
	}
}