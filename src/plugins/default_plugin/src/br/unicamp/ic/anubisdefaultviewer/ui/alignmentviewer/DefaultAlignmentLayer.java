package br.unicamp.ic.anubisdefaultviewer.ui.alignmentviewer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;
import br.unicamp.ic.anubisdefaultviewer.dataconnector.AlignmentDataConnector;

public class DefaultAlignmentLayer implements ITextLayer {
	
	private boolean enabled = false;
	

	private static UUID alignmentDataConnectorInterfaceID = UUID
			.fromString("d13127e4-8a75-4b7a-9066-29ebd6551076");


	@Override
	public Image GetImage(int alignmentIndex, int width, int height, Insets borders) {

		Image image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IUIManager uimanager = manager.getUIManager();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (resourceManager != null && uimanager != null
				&& alignmentManager != null) {

			Graphics2D g = (Graphics2D) image.getGraphics();

			g.setFont(uimanager.getFont());
			FontMetrics metrics = g.getFontMetrics();

			int fontWidth = metrics.stringWidth("_");
			int fontHeight = metrics.getHeight();

			Color fontColor = uimanager.getFontColor();

			int columnWidth = fontWidth + borders.left + borders.right;
			int columnHeight = fontHeight + borders.top + borders.bottom;

			int columns = width / columnWidth;
			int rows = height / columnHeight;

			g.setColor(fontColor);

			// TODO: get valid columns
			int startColumn = alignmentManager
					.getCurrentFirstColumn(alignmentIndex);
			int startRow = alignmentManager.getCurrentFirstRow(alignmentIndex);

			AlignmentDataConnector connector = (AlignmentDataConnector) resourceManager
					.getResource(alignmentDataConnectorInterfaceID);
			Character[][] data = (Character[][]) connector.getData(
					alignmentIndex, startColumn, startRow, columns, rows);

			if (data != null) {

				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						int x = j * fontWidth + (j + 1) * borders.left + j
								* borders.right;
						int y = (i + 1) * fontHeight + (i + 1) * borders.top
								+ i * borders.bottom;
						g.drawString(data[i][j].toString(), x, y);
					}
				}
			}
		}

		return image;
	}

	@Override
	public boolean IsEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		notifyRedraw();
	}

	@Override
	public String getLabel() {
		return "Default";
	}

	@Override
	public int getTransparency() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTransparency(int transparency) {
		// TODO Auto-generated method stub
		
	}


	public void notifyRedraw(){
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {					
			
			RedrawAlignmentEvent redrawEvent = new RedrawAlignmentEvent(
					AlignmentUtil.BOTH_ALIGNMENTS);
			eventManager.raise(redrawEvent);
		}
	}

	@Override
	public Insets getBorders(int alignmentIndex) {
		return new Insets(2,2,2,2);
	}

	@Override
	public int getTextRows(int alignmentIndex) {
		return 0;
	}

	@Override
	public int getTextColumns(int alignmentIndex) {
		return 0;
	}
	

}
