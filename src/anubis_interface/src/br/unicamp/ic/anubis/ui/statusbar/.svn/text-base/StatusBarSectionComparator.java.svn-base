package br.unicamp.ic.anubis.ui.statusbar;

import java.util.Comparator;


public class StatusBarSectionComparator implements
		Comparator<StatusBarSection> {

	@Override
	public int compare(StatusBarSection section1, StatusBarSection section2) {
		int returnValue = 0;
		
		if (section1==null){
			if (section2==null){
				returnValue = 0;
			} else {
				returnValue = 1;
			}
		} else {
			if (section2==null){
				returnValue = -1;
			} else {
				returnValue = ((Integer)section1.getPriority()).compareTo(section2.getPriority());
			}
		}
		
		return returnValue;
	}

}
