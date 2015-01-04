package br.unicamp.ic.anubis.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class AnubisSplash extends JWindow {

	public AnubisSplash() {
		super();

		JLabel l = new JLabel("Anubis");

		try {
			Image backgroundImg = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource(
							"/br/unicamp/ic/anubis/ui/resources/splash.png"));

			l = new JLabel(new ImageIcon(backgroundImg));
		} catch (Exception ex) {
			// Use default textual label if image could not be loaded
		}
		getContentPane().add(l, BorderLayout.CENTER);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = l.getPreferredSize();
		setLocation(screenSize.width / 2 - (labelSize.width / 2),
				screenSize.height / 2 - (labelSize.height / 2));
		setVisible(true);
		screenSize = null;
		labelSize = null;
	}

}
