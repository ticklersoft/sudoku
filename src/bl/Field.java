package bl;

import java.util.ArrayList;

public class Field {
	private Integer value;
	private ArrayList<Group> groups;
	private ArrayList<Integer> possibleValues;

	public Field() {
		for (int i = 1; i < 10; i++){
			getPossibleValues().add(i);
		}
	}
	
	public ArrayList<Group> getGroups() {
		if (groups == null) groups = new ArrayList<Group>();
		return groups;
	}
	public void addGroup(Group group) {
		getGroups().add(group);
	}
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
		if (value != null) getPossibleValues().clear();
	}
	
	public ArrayList<Integer> getPossibleValues() {
		if (possibleValues == null) possibleValues = new ArrayList<Integer>();
		return possibleValues;
	}
	
	public void addPossibleValue(Integer possibleValue) {
		getPossibleValues().add(possibleValue);
	}
	public boolean removePossibleValue(Integer possibleValue) {
		if (!getPossibleValues().contains(possibleValue)) return false;
		getPossibleValues().remove(possibleValue);
		return true;
	}
	
	public boolean initPossibleValues(){
		if (getValue() != null) {
			getPossibleValues().clear();
			return false;
		}
		boolean changed = false;
		for (int i = 1; i < 10; i++){
			for (Group group : getGroups()){
				if (group.hasValue(i)) {
					if (removePossibleValue(i)) changed = true;
				}
			}
		}
		if (getPossibleValues().size() == 1) setValue(getPossibleValues().get(0));
		return changed;
	}
	
	@Override
	public String toString() {
		String output = "";
		if (getValue() == null) output = " ";
		else output = getValue().toString();
		return output;
	}
}
