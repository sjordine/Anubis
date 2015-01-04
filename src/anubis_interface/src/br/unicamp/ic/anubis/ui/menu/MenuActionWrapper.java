package br.unicamp.ic.anubis.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


class MenuActionWrapper implements ActionListener {
	
	private MenuAction associatedCommand;
	
	
	public MenuActionWrapper(JMenuItem source, MenuAction command){
		associatedCommand = command;
		command.setAssociatedMenu(source);
	}
	

	@Override
	public void actionPerformed(ActionEvent event) {
		if (associatedCommand!=null){
			associatedCommand.execute(event);
		}
	}

}
