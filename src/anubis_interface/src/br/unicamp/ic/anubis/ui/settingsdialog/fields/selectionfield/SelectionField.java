package br.unicamp.ic.anubis.ui.settingsdialog.fields.selectionfield;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.anubis.ui.settingsdialog.IFieldDefinition;

public class SelectionField implements IFieldDefinition {
	
	private int priority;
	private String propertyName;
	private String fieldName;
	private ISelectionFieldDataProvider provider;
	
	public SelectionField(String fieldName, String propertyName, ISelectionFieldDataProvider provider) {
		this.fieldName = fieldName;
		this.propertyName = propertyName;
		this.provider = provider;
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
		JComboBox cbValues = new JComboBox();
		returnValue.add(cbValues);
		
		return returnValue;
	}

	@Override
	public void load(Container container) {
		JComboBox comboBox = retrieveComboBox(container);
		Item selectedItem = null;
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settings = manager.getSettingsManager();
		List<Item> items = provider.getItems();
		Object currentValue = null;
		if (settings!=null){
			currentValue = settings.getProperty(propertyName);
		}
		
		if (comboBox!=null && items!=null){
			comboBox.removeAllItems();
			for (Item item:items){
				comboBox.addItem(item);
				if (currentValue!=null && currentValue.equals(item.getValue())){
					selectedItem = item;
				}
			}
			//set current item
			if (selectedItem!=null){
				comboBox.setSelectedItem(selectedItem);
			}
		}
	}

	@Override
	public void save(Container container) {
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settings = manager.getSettingsManager();
		JComboBox comboBox = retrieveComboBox(container);
		if (settings!=null && comboBox!=null){
			Item selectedItem = (Item) comboBox.getSelectedItem();
			if (selectedItem!=null){
				settings.setProperty(propertyName, selectedItem.getValue());
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
	
	private static JComboBox retrieveComboBox(Container container) {
		JComboBox cbValues = null;
		Component[] components = container.getComponents();
		if (components != null && components.length > 0) {
			for (int i = 0; i < components.length && cbValues == null; i++) {
				if (components[i] instanceof JComboBox) {
					cbValues = (JComboBox) components[i];
				}
			}
		}
		return cbValues;
	}

}
