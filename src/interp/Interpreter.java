package interp;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Interpreter {
	enum Direction {
		UP, DOWN, RIGHT, LEFT
	};
	
	public static void main(String[] args) throws InterruptedException{

		ArrayList<Integer> stack = new ArrayList<Integer>();
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
		int a, b, v;
		outerloop:
		for(;;) {
			
			if(stringmode) {
				if(input.get(y).get(x) == '"') {
					stringmode ^= true;
				}
				else {
					stack.add((int)input.get(y).get(x));	
				}
			}
			else {
				switch (input.get(y).get(x)) {
				case '+':
					a = stack.remove(stack.size()-1);
					b = stack.remove(stack.size()-1);
					stack.add(a + b);
					break;
					
				case '-':	
					a = stack.remove(stack.size()-1);
					b = stack.remove(stack.size()-1);
					stack.add(a - b);
					break;
					
				case '*': 
					a = stack.remove(stack.size()-1);
					b = stack.remove(stack.size()-1);
					stack.add(a * b);
					break;
					
				case '/':	
					a = stack.remove(stack.size()-1);
					b = stack.remove(stack.size()-1);
					stack.add(a / b);
					break;
					
				case '%':	
					a = stack.remove(stack.size()-1);
					b = stack.remove(stack.size()-1);
					stack.add(a % b);
					break;
					
				case '`':	
					a = stack.remove(stack.size()-1);
					b = stack.remove(stack.size()-1);
					stack.add((b > a) ? 1 : 0);
					break;
				case '?':
					int random = (int)(Math.random() * 4);
					direction = Direction.values()[random];
				case '_':
					if(stack.size() == 0 ) {
						direction = Direction.RIGHT;
					}
					else {
						a = stack.remove(stack.size()-1);
						direction = (a == 0) ? Direction.RIGHT : Direction.LEFT;
					}
					break;
					
				case '|':
					a = stack.remove(stack.size()-1);
					direction = (a == 0) ? Direction.DOWN : Direction.UP;
					break;
					
				case '"':
					stringmode ^= true;
					break;
					
				case ':':
					if(stack.size() != 0 ) { 
						stack.add(stack.get(stack.size()-1));
					}
					break;
					
				case '\\':
					//TODO
					break;
					
				case '$':
					stack.remove(stack.size()-1);
					break;
					
				case '.':
					System.out.print(stack.remove(stack.size()-1));
					break;
					
				case ',':
					System.out.print((char)(int)stack.remove(stack.size()-1));
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
					a = stack.remove(stack.size()-1);
					b = stack.remove(stack.size()-1);
					stack.add((int)input.get(a).get(b));
					break;
					
				case 'p':
					a = stack.remove(stack.size()-1);
					b = stack.remove(stack.size()-1);
					v = stack.remove(stack.size()-1);
					ArrayList<Character> row = input.get(y);
					row.set(x, (char)v);
					input.set(y, row);
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