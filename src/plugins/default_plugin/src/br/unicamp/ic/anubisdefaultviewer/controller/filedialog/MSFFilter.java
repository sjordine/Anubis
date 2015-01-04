package br.unicamp.ic.anubisdefaultviewer.controller.filedialog;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MSFFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		
		String fileName = file.getName();
		
		return (file.isDirectory() || fileName.endsWith(".msf"));
	}

	@Override
	public String getDescription() {
		return ("MSF Files");
	}

}
