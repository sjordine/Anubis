package br.unicamp.ic.anubis.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.unicamp.ic.anubis.ui.statusbar.StatusBarSection;
import br.unicamp.ic.anubis.ui.statusbar.StatusBarSectionComparator;

public class AnubisUIStatusBarManager {
	
	List<StatusBarSection> sections;
	
	AnubisUIStatusBarManager(){
		sections = new ArrayList<StatusBarSection>();
	}

	public List<StatusBarSection> getStatusBarSections() {
		return sections;
	}

	public void addStatusBarSection(StatusBarSection section) {
		if (section!=null){
			sections.add(section);
			Collections.sort(sections,new StatusBarSectionComparator());
		}
		
	}

}
