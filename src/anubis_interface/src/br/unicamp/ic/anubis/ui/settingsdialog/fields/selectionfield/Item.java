package br.unicamp.ic.anubis.ui.settingsdialog.fields.selectionfield;

public class Item {
	
	private String name;
	private Object value;
	
	public Item(String name, Object value){
		this.name = name;
		this.value = value;
	}	
	
	public String getName() {
		return name;
	}
	public Object getValue() {
		return value;
	}

    public String toString(){
        return this.name;
    }
	

}
