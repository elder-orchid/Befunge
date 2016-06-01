package interp;

public class Board {
	public int[] pos;
	public char[][][] board;
	public Direction dir;
	public int[] stack;
	public int stackIndex;
	public boolean stringIn;
	public String output;
	public boolean finished;
	
	public enum Direction{//uses mathematical orientation:z is up and down, y is depth
		Left,		//x--
		Right,		//x++
		Backward,	//y--
		Foreward,	//y++
		Up,			//z--
		Down;		//z++

		private static Direction[] all=null;
		
		private int[] addValue(int[] in){
			switch(this){
			case Left:
				return new int[] {-1+in[0],in[1],in[2]};
			case Right:
				return new int[] {1+in[0],in[1],in[2]};
			case Backward:
				return new int[] {in[0],-1+in[1],in[2]};
			case Foreward:
				return new int[] {in[0],1+in[1],in[2]};
			case Up:
				return new int[] {in[0],in[1],-1+in[2]};
			case Down:
				return new int[] {in[0],in[1],1+in[2]};
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
		this.stack = new int[1024];
	}
	
	public Board(int[] bounds, int stacksize){
		this.board = new char[bounds[0]][bounds[1]][bounds[2]];

		this.dir = Direction.Right;
		this.pos = new int[] {0,0,0};
		this.stackIndex = 0;
		this.stringIn = false;
		this.output = "";
		this.finished = false;
		this.stack = new int[stacksize];
	}
	
	public void step(){//pos is length 3
		final char curVal = board[pos[0]][pos[1]][pos[2]];
		if(!finished){
			if(stringIn){// currently a string
				stack[stackIndex] = (int)curVal;
				stackIndex++;
			}else if (curVal - (int)'0' < 10 || curVal-(int)'A' < 6 || curVal-(int)'a' < 6) {// if it's a hex input
				stack[stackIndex] = curVal;
				stackIndex++;
			}else{// special char
				switch(curVal){//in the order of esolang.org/wiki/Befunge
				
				//operators
				case ('+'):
					stack[stackIndex - 2] = stack[stackIndex - 2] + stack[stackIndex - 1];//add two previous vals into the value two indecies earlier
					stack[stackIndex - 1] = 0;//set used slot to 0
					stackIndex--;//go to 0 slot
					break;
				case ('-'):	
					stack[stackIndex - 2] = stack[stackIndex - 2] - stack[stackIndex - 1];//subtract stack value of previous from stack value two previous and set that in the stack 2 previous
					stack[stackIndex - 1] = 0;//set used slot to 0
					stackIndex--;//go to 0 slot
					break;
				case ('*'): 
					stack[stackIndex - 2] = stack[stackIndex - 2] * stack[stackIndex - 1];//multiply stack value of previous with stack value two previous and set that in the stack 2 previous
					stack[stackIndex - 1] = 0;//set used slot to 0
					stackIndex--;//go to 0 slot
					break;
				case ('/'):	
					stack[stackIndex - 2] = stack[stackIndex - 2] / stack[stackIndex - 1];//divide stack value of previous from stack value two previous and set that in the stack 2 previous
					stack[stackIndex - 1] = 0;//set used slot to 0
					stackIndex--;//go to 0 slot
				case ('%'):	
					stack[stackIndex - 2] = stack[stackIndex - 2] % stack[stackIndex - 1];//subtract stack value of previous from stack value two previous and set the remainder in the stack 2 previous
					stack[stackIndex - 1] = 0;//set used slot to 0
					stackIndex--;//go to 0 slot
				case ('!'):	
					stack[stackIndex - 1] = stack[stackIndex - 1] == 0?1:0;
					break;
				case ('`'):	
					stack[stackIndex - 1] = stack[stackIndex - 2] > stack[stackIndex - 1]?1:0;
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
					this.dir = stack[stackIndex - 1]==0?Direction.Right:Direction.Left;
					stack[stackIndex - 1] = 0;
					stackIndex--;
				case ('|')://z axis
					this.dir = stack[stackIndex - 1]==0?Direction.Down:Direction.Up;
					stack[stackIndex - 1] = 0;
					stackIndex--;
				case ('i')://y axis
					this.dir = stack[stackIndex - 1]==0?Direction.Foreward:Direction.Backward;
					stack[stackIndex - 1] = 0;
					stackIndex--;
					break;
	
				case ('"')://toggle string input
					stringIn = !stringIn;
					break;
				case (':')://duplicate top stack value
					stack[stackIndex] = stack[stackIndex - 1];
					stackIndex++;
					break;
				case ('\\')://swap top stack values
					int swap = stack[stackIndex - 1];
					stack[stackIndex - 1] = stack[stackIndex - 2];
					stack[stackIndex - 2] = swap;
					break;
				case ('$')://remove top stack value
					stack[stackIndex - 1] = 0;
					stackIndex--;
					break;
					
				//outputs
				case ('.')://pop and print as int
					output+=stack[stackIndex - 1]+" ";
					stackIndex--;
					break;
				case (',')://pop and print as char
					output += (char)stack[stackIndex - 1];
					stackIndex--;
					break;
				
				case ('#')://hop
					this.pos = this.dir.addValue(this.pos);
					break;
				case ('@'):
					this.finished = true;
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
						stack[stackIndex - 3] = this.board[stack[stackIndex - 3]][stack[stackIndex - 2]][stack[stackIndex - 1]];//set value

						for(int i=0;i<2;i++){//remove used values
							stack[stackIndex - i - 1] = 0;
						}
					}
					break;
				}
			}
			
			this.pos = this.dir.addValue(this.pos);//move in direction
		}
	}
}