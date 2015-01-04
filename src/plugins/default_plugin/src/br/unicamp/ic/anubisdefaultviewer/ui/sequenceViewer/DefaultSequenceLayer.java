package br.unicamp.ic.anubisdefaultviewer.ui.sequenceViewer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;
import br.unicamp.ic.anubisdefaultviewer.dataconnector.AlignmentDataConnector;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.ALIGNMENT_DATA_CONNECTOR_FEATURE;

public class DefaultSequenceLayer implements ITextLayer {

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

			int fontHeight = metrics.getHeight();

			ILayerSet layerSet = uimanager.getLayerSet(LayerSetPosition.SEQUENCES);
			Insets sequenceBorders = layerSet.getBorders();

			int numberOfCharacters = alignmentManager
					.getSequenceDisplayLength(alignmentIndex);

			Color fontColor = uimanager.getFontColor();

			int columnHeight = fontHeight + borders.top + borders.bottom;
			int rows = height / columnHeight;

			g.setColor(fontColor);

			// TODO: get valid rows
			int startRow = alignmentManager.getCurrentFirstRow(alignmentIndex);

			AlignmentDataConnector connector = (AlignmentDataConnector) resourceManager
					.getResource(ALIGNMENT_DATA_CONNECTOR_FEATURE);
			String[] data = (String[]) connector.getSequences(alignmentIndex,
					startRow, rows);

			if (data != null) {
				for (int i = 0; i < data.length; i++) {
					int x = sequenceBorders.left;
					int y = (i + 1) * fontHeight + (i + 1) * borders.top + i
							* borders.bottom;

					String sequenceName = data[i];
					if (sequenceName != null
							&& sequenceName.length() > numberOfCharacters) {
						sequenceName = sequenceName.substring(0,
								numberOfCharacters);
					}

					g.drawString(sequenceName, x, y);
				}
			}
		}

		return image;
	}

	@Override
	public boolean IsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getLabel() {
		return "Sequences";
	}

	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub

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
		return new Insets(2, 2, 2, 2);
	}


	@Override
	public int getTextRows(int alignmentIndex) {
		return 0;
	}

	@Override
	public int getTextColumns(int alignmentIndex) {
		int returnValue = 0;

		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (alignmentManager != null) {

			returnValue = alignmentManager
					.getSequenceDisplayLength(alignmentIndex);
		}
		return returnValue;
	}

}
