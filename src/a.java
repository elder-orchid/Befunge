import java.util.Scanner;
import java.io.*;
public class a {
	public static void main(String[] args) throws InterruptedException{
		int boundsx = 20;
		int boundsy = 20;
		char input [] [] = new char [boundsx] [boundsy];
		Scanner in = new Scanner(System.in);
		int stack [] = new int[100];
		int iteration = 0;
		int delay = 0;
		
		int stackcount = 0;
		System.out.println("Enter speed " );
		String speed = in.next();
		
		if(speed.equals("crawl")){
			delay = 1000;
		}else{
			delay = 10;
		}
		int x = 0;
		int y = 0;
		int i = 0;
		String imp = null;
	
		Scanner scan = null;
			try {
				scan = new Scanner(new File("src/program.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("put a fille in");
			}
		
		while ( i < boundsy && scan.hasNextLine()){
			imp = scan.nextLine();	
			System.out.println(imp);
				for (int j = 0; j < imp.length(); j++){
					input [j] [i] = imp.charAt(j);
					
		}
		i++;
	}
		boolean goup = false;
		boolean godown = false;
		boolean goright = true;
		boolean goleft = false;
		boolean stringmode = false;
			for (; ;){
				Thread.sleep(delay);
				
				if (stringmode == false){
					
				if (/*47 > input[x][y] && */input[x][y] > 58 ){
					
					switch (input [x] [y]){
					case (0): 
						stack[stackcount] = 0;
						//break?
					case (1): 
						stack[stackcount] = 1;
						
					case (2): 
						stack[stackcount] = 2;
						
					case (3): 
						stack[stackcount] = 3;
					case (4): 
						stack[stackcount] = 4;
					case (5): 
						stack[stackcount] = 5;
					case (6): 
						stack[stackcount] = 6;
					case (7): 
						stack[stackcount] = 7;
					case (8): 
						stack[stackcount] = 8;
					case (9): 
						stack[stackcount] = 9;
					stackcount ++;
					}
					
					}else{

					System.out.println("memes");
					switch (input [x] [y]){
					case ('+'): 
						stack[stackcount -1] = stack[stackcount -2] + stack[stackcount -1] ;
						stackcount ++;
						break;
					case ('-'):	
						stack[stackcount -1] = stack[stackcount -2] - stack[stackcount -1] ;
					stackcount ++;
						break;
					case ('*'): 
						stack[stackcount -1] = stack[stackcount -2] * stack[stackcount -1] ;
						stackcount ++;
						break;
					case ('/'):	
						stack[stackcount -1] = stack[stackcount -2] / stack[stackcount -1] ;
					stackcount ++;
						break;
					case ('%'):	
						stack[stackcount -1] = stack[stackcount -2] % stack[stackcount -1] ;
					stackcount ++;
						break;
					case ('`'):	
						if(stack[stackcount -2] > stack[stackcount -1]){
							stack[stackcount] = 1;
						}else{
							stack[stackcount] = 0;
						}
						break;
					case ('?'):
						double random = Math.random();
						random = random * 4;
						if ((int)random >= 3){
							goup = false;
							godown = true;
							goright = false;
							goleft = false;
							break;
						}
						if ((int)random >= 2){
							goup = true;
							godown = false;
							goright = false;
							goleft = false;
							break;
						}
						if ((int)random >= 1){
							goup = false;
							godown = false;
							goright = true;
							goleft = false;
							break;
						}
						if ((int)random >= 0){
							goup = false;
							godown = false;
							goright = false;
							goleft = true;
							break;
						}
					case ('_'):
						if (stack[stackcount-1] == 0){
							goup = false;
							godown = false;
							goright = true;
							goleft = false;
							break;
						}else{
							goup = false;
							godown = false;
							goright = false;
							goleft = true;
							break;
						}
					case ('|'):
						if (stack[stackcount-1] == 0){
							goup = false;
							godown = true;
							goright = false;
							goleft = false;
							break;
						}else{
							goup = true;
							godown = false;
							goright = false;
							goleft = false;
							break;
						}
					case ('"'):
						
						stringmode = !stringmode;
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
						if (goright){
							x++;
						}
						if (goleft){
							x--;
						}
						if (goup){
							y++;
						}
						if (godown){
							y--;
						}
					case ('@'):
						continue;
						
					case ('p'):
						input [stack[stackcount - 1]] [stack[stackcount - 2]] = (char)stack[stackcount -3];
					case ('g'):
						if (stack[stackcount -1] <= boundsx && stack[stackcount - 2] <= boundsy){
						stack[stackcount] = input [stack[stackcount -1]] [stack[stackcount - 2]];
						}else{
							stack[stackcount] = 0;
						}
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
				}else{
					stack[stackcount] = (int)input [x] [y];
				}
				if (goright){
					x++;
				}
				if (goleft){
					x--;
				}
				if (goup){
					y--;
				}
				if (godown){
					y++;
				}
				System.out.println(input [x] [y]);
				iteration ++;
				System.out.println(iteration + " iteration");
				System.out.println(stack[stackcount]);
				if (iteration >= 200){
					System.out.println("fin");
					break;
				}
			}
			
		}
	}// to do: highlight current char, assigning x and y cords with their graphical representations
