import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

enum Direction {
    UP, DOWN, RIGHT, LEFT, IN, OUT
};
  
public class Interpreter {
  
  int x = 0, y = 0, z = 0; 
  private int a, b, v, xS, yS, zS;
  private char cmd;
  public Stack stack = new Stack();
  private bf3 input = new bf3();
  private Scanner userInput = new Scanner(System.in);
  private Direction direction = Direction.RIGHT;
  private boolean stringmode = false;
  private String fileName;
  private boolean debug = false;
  private boolean exit;
  Interpreter(String fileName_, boolean debug_) {
    fileName = fileName_;
    debug = debug_;
  }
  
  
  public void run() throws InterruptedException{
    reset();
    exit = false;
    while(!exit) {
      exit = step();
    }
    userInput.close();
  }
  
  public void stop() {
    exit = true;
    crawl = false;
  }
  public int[] loadFile() {
    
    //fileReader = new Scanner(new File("src/program.bf3"));
    int page = -1;
    String[] fileContents = loadStrings(fileName);
    for(String line : fileContents) {
        if(line.equals("/page")) {
        input.add(new ArrayList<ArrayList<Character>>());
        page++;
      }
      else {
        ArrayList<Character> row = new ArrayList<Character>();
        for(char c : line.toCharArray()) {
          row.add(c);
        }
        input.get(page).add(row);
      }
    }
    return input.getDimensions();
  }
  
  public char getChar(int x, int y, int z) {
    return input.get(z, y, x);
  }
  
  public void reset() {
    x = 0;
    y = 0;
    z = 0;
    stack.clear();
    output = "";
    crawl = false;
  }
  
  public boolean step() {
    z %= input.size();
    y %= input.get(0).size();
    x %= input.get(0).get(0).size();
    
    if(z < 0) {
      z += input.size();
    }
    if(y < 0) {
      y += input.get(0).size();
    }
    if(x < 0) {
      x += input.get(0).get(0).size();
    }
    cmd = input.get(z).get(y).get(x);
    
    if(debug) {
      System.out.println(z + ":" + y + ":" + x);
      System.out.println("Current stack:" + stack.toString());
      System.out.println("Command: " + cmd);
      delay(100);
    }
    
    if(stringmode) {
      if(cmd == '"') {
        stringmode ^= true;
      }
      else {
        stack.push((int)cmd);  
      }
    }
    else if (Character.isDigit(cmd)) {
      stack.push(Integer.parseInt(cmd+""));
    }
    else {
      switch (cmd) {
      case ' ':
        break;
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
      
      case 'o':
        direction = Direction.OUT;
        break;
        
      case 'x':
        direction = Direction.IN;
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
        
      case '\"':
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
        output += stack.pop();
        break;
        
      case ',':
        output += (char)(int)stack.pop();
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
        case IN:
          break;
        case OUT:
          break;
        }
        break;
        
      case '@':
        String[] lines = split(output, '\n');
        saveStrings("output.txt", lines);
        return true;
        
      case 'g':
        yS = stack.pop();
        xS = stack.pop();
        try {
          stack.push((int)input.get(z).get(yS).get(xS));
        }
        catch(IndexOutOfBoundsException e) {
          stack.push(0);
        }
        break;
        
      case 'p':
        yS = stack.pop();
        xS = stack.pop();
        v = stack.pop();
        //System.out.println("z: " + z + ", y: " + yS + ", x: " + xS + ", v: " + v);
        //input.get(z).get(yS).set(xS, (char)v);
        input.pset(z, yS, xS, (char)v);
        break;
        
      case '&':
        System.out.println("(Please input a single int)");
        stack.push(3);
        break;
        
      case '~':
        System.out.println("(Please input a single char)");
        stack.push((int)userInput.nextLine().charAt(0));
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
    case OUT:
      z--;
      break;
    case IN:
      z++;
      break;
    }
    
    return false;
  }
}
