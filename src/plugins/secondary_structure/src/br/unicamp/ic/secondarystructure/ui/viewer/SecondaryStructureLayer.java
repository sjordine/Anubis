package br.unicamp.ic.secondarystructure.ui.viewer;

import static br.unicamp.ic.secondarystructure.common.CommonConstants.SECONDARY_STRUCTURE_DATA_CONNECTOR_ID;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.jbio.secondarystructure.ResidueStructure;
import br.unicamp.ic.secondarystructure.dataconnector.SecondaryStructureDataConnector;

public class SecondaryStructureLayer implements ILayer {

	private boolean enabled;
	private int transparency = 100;

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

			int columnWidth = fontWidth + borders.left + borders.right;
			int columnHeight = fontHeight + borders.top + borders.bottom;

			int columns = width / columnWidth;
			int rows = height / columnHeight;

			int startColumn = alignmentManager
					.getCurrentFirstColumn(alignmentIndex);
			int startRow = alignmentManager.getCurrentFirstRow(alignmentIndex);

			SecondaryStructureDataConnector connector = (SecondaryStructureDataConnector) resourceManager
					.getResource(SECONDARY_STRUCTURE_DATA_CONNECTOR_ID);
			Character[][] data = (Character[][]) connector.getData(
					alignmentIndex, startColumn, startRow, columns, rows);

			if (data != null) {

				BufferedImage helixLeft = createHelixLeftBorder(borders,
						columnHeight);
				BufferedImage helixRight = createHelixRightBorder(borders,
						columnHeight);

				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						int x = j * fontWidth + j * borders.left + j
								* borders.right;
						int y = i * fontHeight + i * borders.top + i
								* borders.bottom;
						char value = data[i][j];

						if (value == ResidueStructure.HELIX) {
							Color color = new Color(0, 150, 0, transparency);
							g.setColor(color);
							// Draw left border
							if (j == 0
									|| data[i][j - 1] != ResidueStructure.HELIX) {
								// First element (or first visible on this
								// structure)
								g.drawImage(helixLeft, null, x, y);
							} else {
								g.fillRect(x, y, borders.left, columnHeight);
							}

							// Draw right border
							if (j == data[i].length - 1
									|| data[i][j + 1] != ResidueStructure.HELIX) {
								// Last element (or last visible on this
								// structure)
								g.drawImage(helixRight, null, x + columnWidth
										- borders.right, y);
							} else {
								g.fillRect(x + columnWidth - borders.right, y,
										borders.right, columnHeight);
							}

							// Core part
							g.fillRect(x + borders.left, y, columnWidth
									- (borders.left + borders.right),
									columnHeight);

						}

						if (value == ResidueStructure.STRAND) {
							Color color = new Color(255, 215, 0, transparency);
							g.setColor(color);
							g.fillRect(x, y + borders.top, columnWidth
									- borders.right, columnHeight
									- (borders.bottom + borders.top));
							// Draw right border
							if (j == data[i].length - 1
									|| data[i][j + 1] != ResidueStructure.STRAND) {
								Polygon arrowTop = new Polygon();
								arrowTop.addPoint(x + columnWidth
										- borders.right, y);
								arrowTop.addPoint(x + columnWidth, y
										+ columnHeight / 2);
								arrowTop.addPoint(x + columnWidth
										- borders.right, y + columnHeight);
								g.fillPolygon(arrowTop);
							} else {
								g.fillRect(
										x + columnWidth - borders.right,
										y + borders.top,
										borders.right,
										columnHeight
												- (borders.bottom + borders.top));
							}
						}

					}
				}

			}
		}

		return image;
	}

	public BufferedImage createHelixRightBorder(Insets borders, int columnHeight) {
		Color color;
		BufferedImage helixRight = new BufferedImage(borders.right,
				columnHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = helixRight.createGraphics();
		color = new Color(0, 150, 0, transparency);
		imageGraphics.setColor(color);
		imageGraphics.fillRect(0, 0, borders.right / 2, columnHeight);
		color = new Color(0, 150, 0, transparency);
		imageGraphics.setColor(color);
		imageGraphics.fillArc(0, 0, borders.right, columnHeight, 270, 180);
		return helixRight;
	}

	public BufferedImage createHelixLeftBorder(Insets borders, int columnHeight) {
		Color color;
		BufferedImage helixLeft = new BufferedImage(borders.left, columnHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = helixLeft.createGraphics();
		AlphaComposite composite = AlphaComposite.getInstance(
				AlphaComposite.CLEAR, 1.0f);
		color = new Color(0, 150, 0, transparency);
		imageGraphics.setColor(color);
		imageGraphics.fillRect(borders.left / 2, 0, borders.left / 2,
				columnHeight);
		imageGraphics.setComposite(composite);
		color = new Color(0, 255, 0, transparency);
		imageGraphics.setColor(color);
		imageGraphics.fillOval(0, 0, borders.left, columnHeight);
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		imageGraphics.setComposite(composite);
		color = new Color(0, 255, 0, transparency);
		imageGraphics.setColor(color);
		imageGraphics.fillOval(0, 0, borders.left, columnHeight);
		color = new Color(0, 0, 0, transparency);
		imageGraphics.setColor(color);
		imageGraphics.drawOval(0, 0, borders.left, columnHeight);
		return helixLeft;
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
		return "Secondary Structure";
	}

	@Override
	public int getTransparency() {
		double factor = ((double) transparency) / 255.0;
		return (int) (factor * 100);
	}

	@Override
	public void setTransparency(int transparency) {
		double factor = ((double) transparency) / 100.0;
		this.transparency = (int) (255 * factor);
		notifyRedraw();
	}

	@Override
	public Insets getBorders(int alignmentIndex) {
		return new Insets(5, 8, 5, 8);
	}

	@Override
	public int getTextRows(int alignmentIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTextColumns(int alignmentIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void notifyRedraw() {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {

			RedrawAlignmentEvent redrawEvent = new RedrawAlignmentEvent(
					AlignmentUtil.BOTH_ALIGNMENTS);
			eventManager.raise(redrawEvent);
		}
	}

}
