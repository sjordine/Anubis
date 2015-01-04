package br.unicamp.ic.anubis.ui.menu;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JMenuItem;

import br.unicamp.ic.anubis.plugin.IParameterizedContext;

public abstract class MenuAction implements IParameterizedContext, Cloneable{

	private HashMap<String, Object> parameters;
	private IParameterizedContext parentContext = null;
	
	private JMenuItem associatedMenu;

	public MenuAction() {
		parameters = new HashMap<String, Object>();
	}

	public abstract void execute(ActionEvent event);

	public void setParameter(String parameterName, Object value) {
		parameters.put(parameterName, value);
	}

	public Object getParameter(String parameterName) {
		Object returnValue = null;

		if (parameters.containsKey(parameterName)) {
			returnValue = parameters.get(parameterName);
		} else {
			if (parentContext!=null){
				returnValue = parentContext.getParameter(parameterName);
			}
		}

		return returnValue;
	}
	
	public void setParentContext(IParameterizedContext parentContext){
		this.parentContext = parentContext;
	}

	public void setAssociatedMenu(JMenuItem source) {
		associatedMenu = source;
	}

	public JMenuItem getAssociatedMenu() {
		return associatedMenu;
	}


	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


}
