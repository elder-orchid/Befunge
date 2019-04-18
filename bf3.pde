import java.util.ArrayList;

@SuppressWarnings("serial")
public class bf3 extends ArrayList<ArrayList<ArrayList<Character>>> {
  
  public int[] getDimensions() {
    return new int[] {get(0).get(0).size(), this.get(0).size(), size()};
  }
  
  public void pset(int z, int y, int x, char in) {
    get(z,y,x);
    get(z).get(y).set(x, in);
  }
  
  public char get(int z, int y, int x) {
    while(z > size()-1) {
      add(new ArrayList<ArrayList<Character>>());
    }
    while(y > get(z).size()-1) {
      get(z).add(new ArrayList<Character>());
    }
    while(x > get(z).get(y).size()-1) {
      get(z).get(y).add(' ');
    }
    
    return get(z).get(y).get(x);
  }

}
