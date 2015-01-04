package br.unicamp.ic.anubis.ui.settingsdialog.fields.folderfield;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.ui.settingsdialog.IFieldDefinition;

public class FolderField implements IFieldDefinition {

	private int priority;
	private String propertyName;
	private String fieldName;

	public FolderField(String fieldName, String propertyName) {
		this.fieldName = fieldName;
		this.propertyName = propertyName;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public Container render() {
		JPanel returnValue = new JPanel();
		returnValue.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel(getFieldName());
		returnValue.add(label);
		JTextField txtFolder = new JTextField(20);
		returnValue.add(txtFolder);
		JButton btnBrowse = new JButton();
		btnBrowse.setPreferredSize(new Dimension(16, 16));
		Image btnIcon = Toolkit
				.getDefaultToolkit()
				.getImage(
						getClass()
								.getResource(
										"/br/unicamp/ic/anubis/resources/folder_icon_16x16.png"));
		if (btnIcon != null) {
			btnBrowse.setIcon(new ImageIcon(btnIcon));
		}
		btnBrowse.addActionListener(new FolderFieldBrowseAction(returnValue));
		returnValue.add(btnBrowse);
		return returnValue;
	}

	@Override
	public void load(Container container) {
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();
		if (settingsManager != null && propertyName != null) {
			Object property = settingsManager.getProperty(propertyName);
			if (property instanceof String) {
				JTextField txtFolder = retrieveTextField(container);
				if (txtFolder != null) {
					String folderName = (String) property;
					txtFolder.setText(folderName);
				}

			}
		}
	}

	@Override
	public void save(Container container) {
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();
		if (settingsManager != null && propertyName != null) {
			JTextField txtFolder = retrieveTextField(container);
			if (txtFolder != null) {
				String folderName = txtFolder.getText();
				settingsManager.setProperty(propertyName, folderName);
			}

		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public static JTextField retrieveTextField(Container container) {
		JTextField txtFolder = null;
		Component[] components = container.getComponents();
		if (components != null && components.length > 0) {
			for (int i = 0; i < components.length && txtFolder == null; i++) {
				if (components[i] instanceof JTextField) {
					txtFolder = (JTextField) components[i];
				}
			}
		}
		return txtFolder;
	}
}
