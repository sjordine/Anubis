package br.unicamp.ic.anubis.ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuDocking implements IMenuDocking {
	
	private JMenu menu;
	
	public MenuDocking(JMenu menu){
		this.menu = menu;
	}
	

	@Override
	public void add(JMenuItem item) {
		menu.add(item);
	}

	@Override
	public void addSeparator() {
		menu.addSeparator();
	}

}
