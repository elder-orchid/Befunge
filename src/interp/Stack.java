package interp;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Stack<Integer> extends ArrayList<Integer> {

	public int pop() throws StackErrorException {
		if(size() > 0) {
			return (int)remove(size()-1);
		}
		//throw new StackErrorException("POP(): empty stack");
		return 0;
	}
	
	public void push(Integer newVal) {
		add(newVal);
	}
	
	public void swap() throws StackErrorException {
		if(size() > 1) {
			Integer tmp = get(size()-1);
			set(size()-1, get(size()-2));
			set(size()-2, tmp);
		}
		else {
			throw new StackErrorException("SWAP(): too few values on stack");
		}
	}
	
	public void duplicate() throws StackErrorException {
		if(size() > 0) {
			push(get(size()-1));
		}
		else {
			// Do nothing
		}
	}
}
