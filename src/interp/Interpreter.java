package interp;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Interpreter {
	enum Direction {
		UP, DOWN, RIGHT, LEFT
	};
	
	public static void main(String[] args) throws InterruptedException, StackErrorException{

		Stack<Integer> stack = new Stack<Integer>();
		ArrayList<ArrayList<Character>> input = new ArrayList<ArrayList<Character>>();

		Scanner scan = null;
		try {
			scan = new Scanner(new File("src/program.bf"));
			
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				ArrayList<Character> row = new ArrayList<Character>();
				for(char c : line.toCharArray()) {
					row.add(c);
				}
				//System.out.println(row);
				input.add(row);
			}
			scan.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Please add a file");
		}

		
		
		Direction direction = Direction.RIGHT;
		boolean stringmode = false;
		
		int x = 0, y = 0; 
		int a, b, v, xS, yS;
		outerloop:
		for(;;) {
			
			y %= input.size();
			x %= input.get(0).size();
			if(y < 0) {
				y += input.size();
			}
			if(x < 0) {
				x += input.get(0).size();
			}
			//System.out.println("Current stack:" + stack.toString());
			//System.out.println("Command: " + input.get(y).get(x) );
			//Thread.sleep(100);
			
			if(stringmode) {
				if(input.get(y).get(x) == '"') {
					stringmode ^= true;
				}
				else {
					stack.push((int)input.get(y).get(x));	
				}
			}
			else if (Character.isDigit(input.get(y).get(x))) {
				stack.push(Integer.parseInt(input.get(y).get(x)+""));
			}
			else {
				switch (input.get(y).get(x)) {
				case '+':
					a = stack.pop();
					b = stack.pop();
					stack.push(a + b);
					break;
					
				case '-':	
					a = stack.pop();
					b = stack.pop();
					stack.push(b - a);
					break;
					
				case '*': 
					a = stack.pop();
					b = stack.pop();
					stack.push(a * b);
					break;
					
				case '/':	
					a = stack.pop();
					b = stack.pop();
					stack.push((int)Math.floor(b / a));
					break;
					
				case '%':	
					a = stack.pop();
					b = stack.pop();
					stack.push(b % a);
					break;
					
				case '!':
					//Pop a value. If the value is zero, push 1; otherwise, push zero.
					a = stack.pop();
					stack.push(a == 0 ? 1 : 0);
					break;
					
				case '`':	
					a = stack.pop();
					b = stack.pop();
					stack.push((b > a) ? 1 : 0);
					break;
					
				case 'v':
					direction = Direction.DOWN;
					break;
					
				case '^':
					direction = Direction.UP;
					break;
					
				case '>':
					direction = Direction.RIGHT;
					break;
					
				case '<':
					direction = Direction.LEFT;
					break;
					
				case '?':
					int random = (int)(Math.random() * 4);
					direction = Direction.values()[random];
				case '_':
					a = stack.pop();
					direction = (a == 0) ? Direction.RIGHT : Direction.LEFT;
					break;
					
				case '|':
					a = stack.pop();
					direction = (a == 0) ? Direction.DOWN : Direction.UP;
					break;
					
				case '"':
					stringmode ^= true;
					break;
					
				case ':':
					stack.duplicate();
					break;
					
				case '\\':
					stack.swap();
					break;
					
				case '$':
					stack.pop();
					break;
					
				case '.':
					System.out.print(stack.pop());
					break;
					
				case ',':
					System.out.print((char)(int)stack.pop());
					break;
					
				case '#':
					switch(direction) {
					case UP:
						y--;
						break;
					case DOWN:
						y++;
						break;
					case LEFT:
						x--;
						break;
					case RIGHT:
						x++;
						break;
					}
					break;
					
				case '@':
					break outerloop;
					
				case 'g':
					yS = stack.pop();
					xS = stack.pop();
					try {
						stack.push((int)input.get(yS).get(xS));
					}
					catch(IndexOutOfBoundsException e) {
						stack.push(0);
					}
					break;
					
				case 'p':
					yS = stack.pop();
					xS = stack.pop();
					v = stack.pop();
					//System.out.println("x: " + a + ", y: " + b + ", v: " + v);
					input.get(yS).set(xS, (char)v);
					break;
				}
			}
				
			switch(direction) {
			case UP:
				y--;
				break;
			case DOWN:
				y++;
				break;
			case LEFT:
				x--;
				break;
			case RIGHT:
				x++;
				break;
			}
		}
	}
}