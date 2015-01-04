package br.unicamp.ic.anubis.ui.settingsdialog.fields.folderfield;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FolderFieldBrowseAction implements ActionListener {

	private JPanel field;

	public FolderFieldBrowseAction(JPanel folderField) {
		this.field = folderField;
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		// Show dialog
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(field);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// Set field value
			if (field != null) {
				JTextField txtFolder = FolderField.retrieveTextField(field);
				if (txtFolder != null) {
					txtFolder.setText(chooser.getSelectedFile()
							.getAbsolutePath());
				}
			}
		}
	}

}
