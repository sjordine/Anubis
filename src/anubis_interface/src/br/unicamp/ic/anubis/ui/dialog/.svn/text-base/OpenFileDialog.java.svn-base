package br.unicamp.ic.anubis.ui.dialog;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ANUBIS_VIEW;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.Session;
import br.unicamp.ic.anubis.plugin.IParameterizedContext;

public class OpenFileDialog implements IParameterizedContext {

	private HashMap<String,Object> parameters;
	private HashMap<FileFilter, FileAction> actions;
	private List<FileFilter> filters;
	private IParameterizedContext parentContext = null;

	public OpenFileDialog() {
		actions = new HashMap<FileFilter, FileAction>();
		filters = new ArrayList<FileFilter>();
		parameters = new HashMap<String, Object>();
	}

	public void addFileHandler(FileFilter filter, FileAction handler) {
		actions.put(filter, handler);
		handler.setParentContext(this);
		filters.add(filter);
	}

	public void showDialog() {
		AnubisManager manager = AnubisManager.getInstance();
		Session session = manager.getSession();

		JFrame view = (JFrame) session.get(ANUBIS_VIEW);
		JFileChooser choser = new JFileChooser();
		
		choser.removeChoosableFileFilter(choser.getFileFilter());

		for (FileFilter filter : filters) {
			choser.addChoosableFileFilter(filter);
		}

		int result = choser.showOpenDialog(view);

		if (result == JFileChooser.APPROVE_OPTION) {
			FileFilter selectedFilter = choser.getFileFilter();
			File selectedFile = choser.getSelectedFile();
			if (actions.containsKey(selectedFilter)) {
				FileAction action = actions.get(selectedFilter);
				if (action != null) {
					action.execute(selectedFile);
				}
			}
		}

	}

	@Override
	public void setParameter(String parameterName, Object parameterValue) {
		parameters.put(parameterName, parameterValue);
		
	}

	@Override
	public Object getParameter(String parameterName) {
		Object returnValue = null;
		
		if (parameters.containsKey(parameterName)){
			returnValue = parameters.get(parameterName);
		} else {
			if (parentContext!=null){
				returnValue = parentContext.getParameter(parameterName);
			}
		}
		
		return returnValue;
		
	}

	@Override
	public void setParentContext(IParameterizedContext parentContext) {
		this.parentContext = parentContext;
	}

}
