package br.unicamp.ic.anubisdefaultviewer.ui.columnViewer;



import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;
import br.unicamp.ic.anubisdefaultviewer.dataconnector.AlignmentDataConnector;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_DATA_CONNECTOR_FEATURE;

public class DefaultColumnLayer implements ITextLayer {

	private static final UUID ALIGNMENT_DATA_REPOSITORY_ID = UUID
			.fromString("a663f847-7aa8-4714-9d91-810ac40a7fbd");

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

			ILayerSet layerSet = uimanager.getLayerSet(LayerSetPosition.COLUMNS);
			Insets columnBorders = layerSet.getBorders();

			Color fontColor = uimanager.getFontColor();

			int columnWidth = fontWidth + borders.left + borders.right;

			int columns = width / columnWidth;

			int firstColumn = alignmentManager
					.getCurrentFirstColumn(alignmentIndex);

			AlignmentDataConnector connector = (AlignmentDataConnector) resourceManager
					.getResource(ALIGNMENT_DATA_CONNECTOR_FEATURE);
			Integer[] data = (Integer[]) connector.getColumns(alignmentIndex,
					firstColumn, columns);

			if (data != null) {
				g.setColor(fontColor);
				int lastI = -1 ;
				//Draw visible columns
				for (int i = 0; i < data.length; i++) {
					int x = i * fontWidth + (i + 1) * borders.left + i
							* borders.right;
					int y = fontHeight + borders.top;
					if ((data[i] + 1) % 10 == 0 && (lastI ==-1 ||  i-lastI >= 10)) {
						String label = "" + (data[i] + 1);
						int totalWidth = metrics.stringWidth(label);
						x = (x + fontWidth / 2) - (totalWidth / 2);
						g.drawString(label, x, y);
						lastI = i;
					}
				}

				for (int i = 0; i < data.length; i++) {
					int x = i * fontWidth + (i + 1) * borders.left + i
							* borders.right;
					int y = 2 * (fontHeight + columnBorders.top)
							+ columnBorders.bottom;
					g.drawString(getColumnReference(data[i] + 1), x, y);
				}
			}

		}

		return image;
	}

	private String getColumnReference(int i) {
		String returnValue = null;
		if (i % 5 == 0) {
			returnValue = "|";
		} else {
			returnValue = ".";
		}
		return returnValue;
	}

	@Override
	public boolean IsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLabel() {
		return "Columns";
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

	@Override
	public Insets getBorders(int alignmentIndex) {
		return new Insets(2,2,2,2);
	}

	@Override
	public int getTextRows(int alignmentIndex) {
		return 2;
	}

	@Override
	public int getTextColumns(int alignmentIndex) {
		return 0;
	}

}
