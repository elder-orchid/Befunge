package interp;

import java.util.Scanner;
import java.io.*;

public class a {
	public static void main(String[] args) throws InterruptedException{
		int boundsx = 21, boundsy = 2, iteration = 0, delay = 0, stackcount = 0;
		int stack [] = new int[100];
		char input [] [] = new char [boundsy][boundsx];
		Scanner in = new Scanner(System.in);

		System.out.println("Enter speed ");
		String speed = in.next();

		if(speed.equals("crawl")) {
			delay = 1000;
		}
		else {
			delay = 10;
		}
		int x = 0;
		int y = 0;
		int i = 0;
		String imp = null;

		Scanner scan = null;
		try {
			scan = new Scanner(new File("src/program.bf"));
		} catch (FileNotFoundException e) {
			System.out.println("Please add a file");
		}

		while (i < boundsy && scan.hasNextLine()) {
			imp = scan.nextLine();	
			System.out.println(imp);
			for (int j = 0; j < boundsx-1; j++) {
				input[i][j] = imp.charAt(j);
			}
			i++;
		}
		boolean goup = false;
		boolean godown = false;
		boolean goright = true;
		boolean goleft = false;
		boolean stringmode = false;
		for(;;) {
			Thread.sleep(delay);

			if (stringmode == false) {

				if (/*47 > input[x][y] && */input[y][x] > 58 ) {
					stack[stackcount] = input[y][x];
					stackcount ++;
				}
				else {

					System.out.println("memes");
					switch (input[y][x]) {
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

				}
			}
			else {
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
}// to do: highlight current char, assigning x and y cords with their graphical representations
