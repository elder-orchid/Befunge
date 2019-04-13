import java.util.ArrayList;

public class Stack extends ArrayList<Integer> {
  
  public int pop() {
    if(size() > 0) {
      return (int)remove(size()-1);
    }
    return 0;
  }
  
  public void push(Integer newVal) {
    add(newVal);
  }
  
  public void swap() {
    if(size() > 1) {
      Integer tmp = get(size()-1);
      set(size()-1, get(size()-2));
      set(size()-2, tmp);
    }
  }
  
  public void duplicate() {
    if(size() > 0) {
      push(get(size()-1));
    }
  }
}
