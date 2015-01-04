package br.unicamp.ic.anubis.ui.settingsdialog;

import java.util.Comparator;

public class FieldComparator implements Comparator<IFieldDefinition> {

	@Override
	public int compare(IFieldDefinition field1, IFieldDefinition field2) {
	int returnValue = 0;
		
		if (field1==null){
			if (field2==null){
				returnValue = 0;
			} else {
				returnValue = 1;
			}
		} else {
			if (field2==null){
				returnValue = -1;
			} else {
				returnValue = ((Integer)field1.getPriority()).compareTo(field2.getPriority());
			}
		}
		
		return returnValue;
	}

}
