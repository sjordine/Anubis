package br.unicamp.ic.anubis.ui.viewer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentClickedEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.ui.IUIManager;

public class AlignmentViewerPanel extends JPanel implements IEventHandler {

	IAlignmentContainer container = null;

	public AlignmentViewerPanel(IAlignmentContainer container) {
		super();
		this.container = container;
		AnubisManager anubisManager = AnubisManager.getInstance();
		IEventManager eventManager = anubisManager.getEventManager();
		if (eventManager!=null){
			eventManager.register(AlignmentClickedEvent.class, this);
		}
	}

	@Override
	public void paint(Graphics g) {

		// super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		AnubisManager anubisManager = AnubisManager.getInstance();
		IUIManager uiManager = anubisManager.getUIManager();

		if (uiManager != null) {
			IAlignmentDrawingStrategy strategy = uiManager.getDrawingStrategy();
			if (strategy != null) {
				strategy.draw(container, g2);
			}

		}

	}

	@Override
	public void eventRaised(IEvent message) {
		if (message instanceof AlignmentClickedEvent) {
			AlignmentClickedEvent event = (AlignmentClickedEvent)message;
			AnubisManager anubisManager = AnubisManager.getInstance();
			IUIManager uiManager = anubisManager.getUIManager();
			IEventManager eventManager = anubisManager.getEventManager();
			
			if (uiManager != null) {
				IAlignmentDrawingStrategy strategy = uiManager
						.getDrawingStrategy();
				if (strategy != null) {
					Image image = new BufferedImage(1, 1,
							BufferedImage.TYPE_INT_ARGB);
					// Check if it is a column, row or point selection
					IEvent highLevelEvent = strategy.handleClick(event,image.getGraphics(),container);
					// Raise proper event
					if (highLevelEvent!=null){
						eventManager.raise(highLevelEvent);
					}
				}
			}
		}
	}

}
