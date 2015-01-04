package br.unicamp.ic.anubis.ui.settingsdialog.fields.integerfield;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.ui.settingsdialog.IFieldDefinition;

public class IntegerField implements IFieldDefinition {
	
	private int priority;
	private String propertyName;
	private String fieldName;
	
	public IntegerField(String fieldName, String propertyName) {
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
		
		return returnValue;
	}
	
	@Override
	public void load(Container container) {
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();
		if (settingsManager != null && propertyName != null) {
			Object property = settingsManager.getProperty(propertyName);
			if (property instanceof Integer) {
				JTextField txtFolder = retrieveTextField(container);
				if (txtFolder != null) {
					Integer value = (Integer) property;
					txtFolder.setText(value.toString());
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
				String value = txtFolder.getText();
				Integer convertedValue = Integer.parseInt(value);
				settingsManager.setProperty(propertyName, convertedValue);
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
