package br.unicamp.ic.selectionlayer.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.selection.SelectionArea;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.viewer.ISelectionLayer;
import br.unicamp.ic.selectionlayer.selectionconnector.SelectionDataConnector;

import static br.unicamp.ic.selectionlayer.common.CommonConstants.SELECTION_DATA_CONNECTOR_ID;

public class DefaultSelectionLayer implements ISelectionLayer {

	private boolean enabled = false;
	
	private Color SELECTION_COLOR = new Color(200,30,30,150);

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


			// TODO: get valid columns
			int startColumn = alignmentManager
					.getCurrentFirstColumn(alignmentIndex);
			int startRow = alignmentManager.getCurrentFirstRow(alignmentIndex);

			SelectionDataConnector connector = (SelectionDataConnector) resourceManager
					.getResource(SELECTION_DATA_CONNECTOR_ID);
			List<SelectionArea> areas = (List<SelectionArea>) connector
					.getData(alignmentIndex, startColumn, startRow, columns,
							rows);
			
			g.setColor(SELECTION_COLOR);

			
			if (areas!=null && areas.size()>0){
				for (SelectionArea area:areas){
					int x1 = area.getStartColumn();
					int y1 = area.getStartRow();
					int x2 = area.getEndColumn();					
					int y2 = area.getEndRow();		
								
					x1 = x1 * fontWidth + x1 * borders.left + x1
							* borders.right;
					y1 = y1 * fontHeight + y1 * borders.top + y1
							* borders.bottom;
					x2 = (x2+1) * fontWidth + (x2+1) * borders.left + (x2+1)
							* borders.right;
					y2 = (y2+1) * fontHeight + (y2+1) * borders.top + (y2+1)
							* borders.bottom;
					
				
					g.fillRect(x1, y1, x2-x1, y2-y1);
					
				}
			}	
			
			
			
		}

		return image;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
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
	public int getTransparency() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTransparency(int transparency) {
		// TODO Auto-generated method stub
	}

	public void notifyRedraw() {
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
