package br.unicamp.ic.anubisdefaultviewer.ui.statusbar.messaging;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.statusbar.StatusBarSection;
import br.unicamp.ic.anubis.ui.statusbar.StatusBarSection.Orientation;

public class MessagingStatusBar {
	
	private JLabel lblMessage;
	
	public MessagingStatusBar(){
		lblMessage = new JLabel("message");
	}

	public void create(){
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();
		
		if (uiManager!=null){
			//Section - selection method
			JPanel sectionPane = new JPanel();
			sectionPane.setLayout(new FlowLayout());
			sectionPane.add(lblMessage);
			
			StatusBarSection section = new StatusBarSection();
			section.setColumnWeight(3.0);
			section.setSectionPanel(sectionPane);
			section.setOrientation(Orientation.LEFT);
			section.setPriority(20);
			uiManager.addStatusBarSection(section);
		}
	}
}
