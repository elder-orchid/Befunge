import java.util.ArrayList;

@SuppressWarnings("serial")
public class bf3 extends ArrayList<ArrayList<ArrayList<Character>>> {
  
  public void pset(int z, int y, int x, char in) {
    while(z > size()-1) {
      add(new ArrayList<ArrayList<Character>>());
    }
    while(y > get(z).size()-1) {
      get(z).add(new ArrayList<Character>());
    }
    while(x > get(z).get(y).size()-1) {
      get(z).get(y).add(' ');
    }
    
    get(z).get(y).set(x, in);
  }

}
