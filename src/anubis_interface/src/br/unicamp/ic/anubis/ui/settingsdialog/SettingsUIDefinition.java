package br.unicamp.ic.anubis.ui.settingsdialog;

import java.util.ArrayList;
import java.util.List;

public class SettingsUIDefinition {
	
	List<SettingsTab> tabs;
	
	public SettingsUIDefinition(){
		tabs = new ArrayList<SettingsTab>();
	}
	
	public List<SettingsTab> getTabs(){
		return tabs;
	}
	
	public void addTab(SettingsTab tab){
		tabs.add(tab);
	}

}
