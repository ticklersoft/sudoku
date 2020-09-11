package bl;

import java.util.ArrayList;

public class Engine {
	private ArrayList<Group> rows;
	private ArrayList<Group> columns;
	private ArrayList<Group> cells;
	private ArrayList<Field> fields;
	private int passes;
	
	public void init(){
		passes = 0;
		int size = 9;
		fields = new ArrayList<Field>();
		rows = new ArrayList<Group>();
		columns = new ArrayList<Group>();
		cells = new ArrayList<Group>();
		
		Group currentColumn;
		Group currentCell;
		Group currentRow;
		
		for (int i = 0; i < size; i++){
			currentRow = getRow(i);
			
			for (int j = 0; j < size; j++){
				currentColumn = getColumn(j);
				currentCell = getCell(i, j);
				
				Field field = new Field();
				fields.add(field);
				currentCell.addField(field);
				currentRow.addField(field);
				currentColumn.addField(field);
			}
		}
	}
	
	public void setValues(int [] values){
		for (int i = 0; i < values.length; i++){
			int val = values[i];
			if (val > 0) getField(i).setValue(val);
		}
		boolean changed = true;
		while (changed) {
			passes++;
			changed = false;
			for (int i = 0; i < fields.size(); i++){
				if (getField(i).initPossibleValues()) changed = true;
			}
			for (Group g : rows){
				if (g.initPossibleValues()) changed = true;
			}
			for (Group g : columns){
				if (g.initPossibleValues()) changed = true;
			}
			for (Group g : cells){
				if (g.initPossibleValues()) changed = true;
			}
		}
	}
	
	public Group getColumn(int pos){
		if (columns.size() < pos + 1) columns.add(new Group("Column")); 
		return columns.get(pos);
	}
	
	public Group getRow(int pos){
		if (rows.size() < pos + 1) rows.add(new Group("Row")); 
		return rows.get(pos);
	}
	
	public Group getCell(int pos){
		if (cells.size() < pos + 1) cells.add(new Group("Cell")); 
		return cells.get(pos);
	}
	
	public Group getCell(int x, int y){
		int i = x / 3;
		int j = y / 3;
		return getCell(i * 3 + j);
	}
	
	public Field getField(int x, int y){
		int i = x % 9;
		int j = y % 9;
		return getField(i * 9 + j);
	}
	
	public Field getField(int pos){
		return fields.get(pos);
	}

	
	@Override
	public String toString() {
		String output = "";
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				output += getField(i, j).toString() + ", ";
			}
			output += "\n";
		}
		return output;
	}

	public int getPasses() {
		return passes;
	}
	
	public boolean check(){
		for (Group g : rows){
			if (!g.check()) return false;
		}
		for (Group g : columns){
			if (!g.check()) return false;
		}
		for (Group g : cells){
			if (!g.check()) return false;
		}
		return true;
	}
}
