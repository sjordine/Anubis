package br.unicamp.ic.anubis.ui.settingsdialog;

import java.util.Comparator;

public class TabComparator implements Comparator<SettingsTab> {

	@Override
	public int compare(SettingsTab tab1, SettingsTab tab2) {
		int returnValue = 0;
		
		if (tab1==null){
			if (tab2==null){
				returnValue = 0;
			} else {
				returnValue = 1;
			}
		} else {
			if (tab2==null){
				returnValue = -1;
			} else {
				returnValue = ((Integer)tab1.getPriority()).compareTo(tab2.getPriority());
			}
		}
		
		return returnValue;
	}

}
