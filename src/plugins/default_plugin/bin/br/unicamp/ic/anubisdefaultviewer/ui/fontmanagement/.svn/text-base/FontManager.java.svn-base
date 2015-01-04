package br.unicamp.ic.anubisdefaultviewer.ui.fontmanagement;

import static br.unicamp.ic.anubis.mechanism.resources.DefaultResources.ANUBIS_VIEW;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.Session;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.ui.IUIManager;

public class FontManager {

	public enum Direction {
		DECREASE, INCREASE
	}

	public static int[] sizes = { 8, 9, 10, 11, 12, 14, 16, 18, 21, 24, 36, 48};

	public static void changeFontSize(Direction direction) {
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();
		IEventManager eventManager = manager.getEventManager();

		if (uiManager != null && eventManager != null) {
			int currentSize = uiManager.getFontSize();
			int currentIndex = getCurrentIndex(currentSize);

			switch (direction) {
			case DECREASE:
				if (currentIndex > 0){
					currentIndex--;
					uiManager.setFontSize(sizes[currentIndex]);
					redrawInterface(eventManager);
				}
				break;
			case INCREASE:
				if (currentIndex < sizes.length -1){
					currentIndex++;
					uiManager.setFontSize(sizes[currentIndex]);
					redrawInterface(eventManager);
				}
				break;
			}
		}
	}
	
	public static void changeFontColor(){
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();
		IEventManager eventManager = manager.getEventManager();
		Session session = manager.getSession();

		if (session != null && uiManager != null && eventManager!=null) {
			JFrame view = (JFrame) session.get(ANUBIS_VIEW);
			Color currentColor = uiManager.getFontColor();

			Color newColor = JColorChooser.showDialog(view,
					"Font Color", currentColor);
			
			if (newColor!=null){
				uiManager.setFontColor(newColor);
				redrawInterface(eventManager);
			}
		}
	}
	

	private static void redrawInterface(IEventManager eventManager) {
		RedrawAlignmentEvent redrawEvent = new RedrawAlignmentEvent(-1);
		eventManager.raise(redrawEvent);
	}

	private static int getCurrentIndex(int fontSize) {

		int size = 0;

		for (int i = 0; i < (sizes.length - 1); i++) {
			size = (fontSize >= sizes[i] && fontSize < sizes[i + 1]) ? i
					: size;
		}

		if (fontSize >= sizes[sizes.length - 1]) {
			size = sizes.length - 1;
		}

		return size;

	}

}
