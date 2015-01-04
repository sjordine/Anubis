package br.unicamp.ic.anubis.consensus.ui.viewer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.consensus.dataconnector.ConsensusDataConnector;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;

import static br.unicamp.ic.anubis.consensus.common.CommonConstants.CONSENSUS_DATACONNECTOR_FEATURE;

public class ConsensusLayer implements ITextLayer {

	private boolean isEnabled;

	@Override
	public Image GetImage(int alignmentIndex, int width, int height,
			Insets borders) {
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

			int columns = width / columnWidth;

			g.setColor(fontColor);

			int firstColumn = alignmentManager
					.getCurrentFirstColumn(alignmentIndex);

			ConsensusDataConnector connector = (ConsensusDataConnector) resourceManager
					.getResource(CONSENSUS_DATACONNECTOR_FEATURE);
			Character[] sequence = connector.getSequence(alignmentIndex,
					firstColumn, columns);
			
			if (sequence != null) {				

				for (int i = 0; i < sequence.length; i++) {
					int x = i * fontWidth + (i + 1) * borders.left + i
							* borders.right;
					int y = fontHeight + borders.top;
					g.drawString(sequence[i].toString(), x, y);
				}
			}

		}

		return image;
	}

	@Override
	public String getLabel() {
		return "Consensus Sequence";
	}

	@Override
	public boolean IsEnabled() {
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}

	@Override
	public int getTransparency() {
		return 0;
	}

	@Override
	public void setTransparency(int transparency) {
		// Text Layers shall not adjust transparency
	}

	@Override
	public Insets getBorders(int alignmentIndex) {
		return new Insets(2, 2, 2, 2);
	}

	@Override
	public int getTextRows(int alignmentIndex) {
		return 1;
	}

	@Override
	public int getTextColumns(int alignmentIndex) {
		return 0;
	}

}
