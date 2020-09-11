package bl;

import java.util.ArrayList;

public class Group {
	private ArrayList<Field> fields;
	private String type;
	private ArrayList<Integer> possibleValues;
	
	public Group(String type) {
		this.type = type;
		for (int i = 1; i < 10; i++){
			getPossibleValues().add(i);
		}
	}
	
	public ArrayList<Field> getFields() {
		if (fields == null) fields = new ArrayList<Field>();
		return fields;
	}
	public void addField(Field field) {
		getFields().add(field);
		field.addGroup(this);
	}
	
	public boolean hasValue(int value){
		for (Field field : fields){
			Integer val = field.getValue();
			if (val == null) continue;
			if (val.equals(value)) return true;
		}
		return false;
	}
	
	public String getType() {
		return type;
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
		boolean changed = false;
		for (Field field : getFields()){
			if (field.getValue()!= null) {
				if (removePossibleValue(field.getValue())) changed = true;
			}
		}
		for (Integer value : getPossibleValues()){
			boolean foundMultiple = false;
			Field matchedField = null;
			for (Field field : getFields()){
				if (field.getValue()!= null) continue;
				if (field.getPossibleValues().contains(value)) {
					if (matchedField == null) {
						matchedField = field;
					} else {
						foundMultiple = true;
						matchedField = null;
						break;
					}
				}
			}
			if (matchedField != null){
				changed = true;
				matchedField.setValue(value);
			}
		}
		return changed;
	}
	
	public boolean check(){
		boolean [] array = {false, false, false, false, false, false, false, false, false};
		for (Field field : getFields()){
			if (field.getValue()== null) continue;
			if (array[field.getValue() - 1]) return false;
			array[field.getValue() - 1] = true;
		}
		return true;
	}
}
