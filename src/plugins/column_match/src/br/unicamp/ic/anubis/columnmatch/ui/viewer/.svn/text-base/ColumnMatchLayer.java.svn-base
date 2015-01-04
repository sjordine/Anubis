package br.unicamp.ic.anubis.columnmatch.ui.viewer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.columnmatch.dataconnector.ColumnMatchDataConnector;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.viewer.ILayer;

public class ColumnMatchLayer implements ILayer {

	private static final byte[] colors = { 0x04, 0x02, 0x01, 0x05, 0x06, 0x03 };
	
    private static final int MIN_VALUE = 10;
    private static final int MAX_VALUE = 250;
    private static final int STEP = 30;
    
    private static final int CYCLE_SIZE = (MAX_VALUE - MIN_VALUE)/STEP;
    
    
    

	private static UUID columnMatchDataConnectorInterfaceID = UUID
			.fromString("d0539b15-600f-4bf5-b7ff-de7cb8e61b6e");

	private boolean enabled;
	private int transparency = 100;

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

			int columnWidth = fontWidth + borders.left + borders.right;
			int columnHeight = fontHeight + borders.top + borders.bottom;

			int columns = width / columnWidth;
			int rows = height / columnHeight;

			// TODO: get valid columns
			int startColumn = alignmentManager
					.getCurrentFirstColumn(alignmentIndex);
			int startRow = alignmentManager.getCurrentFirstRow(alignmentIndex);

			ColumnMatchDataConnector connector = (ColumnMatchDataConnector) resourceManager
					.getResource(columnMatchDataConnectorInterfaceID);
			Integer[][] data = (Integer[][]) connector.getData(alignmentIndex,
					startColumn, startRow, columns, rows);

			if (data != null) {
				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						int x = j * fontWidth + j * borders.left + j
								* borders.right;
						int y = i * fontHeight + i * borders.top + i
								* borders.bottom;
						int value = data[i][j];
						if (value != -1) {
/*							Color color = new Color(
									255 * ((colorBitMap & 0x04) >> 2),
									255 * ((colorBitMap & 0x02) >> 1),
									255 * (colorBitMap & 0x01), 100);*/
							
							int colorPos = value % (CYCLE_SIZE * colors.length);
							byte colorMask = colors[value % colors.length];
							int colorFactor = STEP * (colorPos / colors.length);
							
						
							Color color = new Color(
									colorFactor * ((colorMask & 0x04) >> 2),
									colorFactor * ((colorMask & 0x02) >> 1),
									colorFactor * (colorMask & 0x01), transparency);
							
							
							g.setColor(color);
							g.fillRect(x, y, columnWidth, columnHeight);
						}
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
		return "Column Matches";
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
	public int getTransparency() {
		double factor = ((double)transparency)/255.0;
		return (int)(factor*100);
	}

	@Override
	public void setTransparency(int transparency) {
		double factor = ((double)transparency)/100.0;
		this.transparency = (int)(255 * factor);
		notifyRedraw();
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
