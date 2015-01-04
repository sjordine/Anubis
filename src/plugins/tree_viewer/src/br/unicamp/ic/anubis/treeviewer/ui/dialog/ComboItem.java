package br.unicamp.ic.anubis.treeviewer.ui.dialog;

public class ComboItem {
	
	private String name;
	private int index;

	public ComboItem(String name, int index){
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
