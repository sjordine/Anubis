package br.unicamp.ic.anubis.ui.viewer;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.BoxLayout;

public class test extends JPanel {

	/**
	 * Create the panel.
	 */
	public test() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JButton btnNewButton = new JButton("New button");
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setVerticalAlignment(SwingConstants.BOTTOM);
		add(btnNewButton_2);

	}

}
