package br.unicamp.ic.anubis.ui.settingsdialog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SettingsTab {
	
	private UUID id;
	private String name;
	private int priority;

	private List<IFieldDefinition> fields;

	
	public SettingsTab(){
		fields = new ArrayList<IFieldDefinition>();
	}
	
	
	public List<IFieldDefinition> getFields(){
		return fields;
	}
	
	public void addField(IFieldDefinition field){
		fields.add(field);
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}
}
