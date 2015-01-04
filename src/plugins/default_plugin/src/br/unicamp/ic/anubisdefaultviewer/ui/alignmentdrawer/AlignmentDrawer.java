package br.unicamp.ic.anubisdefaultviewer.ui.alignmentdrawer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentClickedEvent;
import br.unicamp.ic.anubis.event.AlignmentColumnPopUpMenuEvent;
import br.unicamp.ic.anubis.event.AlignmentRowPopUpMenuEvent;
import br.unicamp.ic.anubis.event.ColumnSelectedEvent;
import br.unicamp.ic.anubis.event.RowSelectedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;

import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.ui.ILayerSet;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.ui.LayerSetPosition;
import br.unicamp.ic.anubis.ui.viewer.IAlignmentContainer;
import br.unicamp.ic.anubis.ui.viewer.IAlignmentDrawingStrategy;
import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;

public class AlignmentDrawer implements IAlignmentDrawingStrategy {

	// This enumeration indicates which borders are relevant
	// to the final border sizing
	private enum BorderRelevance {
		ALL, HORIZONTAL, VERTICAL
	}

	@Override
	public void draw(IAlignmentContainer container, Graphics2D g) {

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IUIManager uimanager = manager.getUIManager();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (container != null) {

			int width = container.getDrawingAreaWidth();
			int height = container.getDrawingAreaHeight();

			int alignmentWidth = width;
			int alignmentHeight = height;

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);

			if (resourceManager != null && uimanager != null
					&& alignmentManager != null) {

				int alignmentIndex = container.getAlignmentId();

				// Get font size
				Font currentFont = uimanager.getFont();
				g.setFont(currentFont);
				FontMetrics metrics = g.getFontMetrics();

				int fontHeight = metrics.getHeight();
				int fontWidth = metrics.stringWidth("_");

				// Calculate borders
				Insets borders = calculateBorder(uimanager, alignmentIndex);

				// Build top borders and top layer
				ILayerSet columnsLayerSet = uimanager
						.getLayerSet(LayerSetPosition.COLUMNS);
				Insets topBorders = columnsLayerSet
						.getMiminumBorder(alignmentIndex);
				topBorders.left = borders.left;
				topBorders.right = borders.right;

				int alignmentOffsetY = 0;
				int columnBarHeight = 0;

				// Get top border size considering number of text rows
				columnBarHeight = columnsLayerSet
						.getNumberOfTextRows(alignmentIndex)
						* (fontHeight + topBorders.top + topBorders.bottom);
				// Calculate alignment view vertical offset and height
				alignmentHeight = alignmentHeight - columnBarHeight;
				alignmentOffsetY = height - alignmentHeight;

				// Build bottom layer
				int bottomBarHeight = 0;
				int bottomBarOffsetY = 0;
				ILayerSet bottomLayerSet = uimanager
						.getLayerSet(LayerSetPosition.BOTTOM);
				Insets bottomBorders = columnsLayerSet
						.getMiminumBorder(alignmentIndex);
				bottomBorders.left = borders.left;
				bottomBorders.right = borders.right;
				bottomBarHeight = bottomLayerSet
						.getNumberOfTextRows(alignmentIndex)
						* (fontHeight + bottomBorders.top + bottomBorders.bottom);
				alignmentHeight = alignmentHeight - bottomBarHeight;
				bottomBarOffsetY = alignmentOffsetY + alignmentHeight;

				// Adjust Sequence viewer
				ILayerSet sequenceLayerSet = uimanager
						.getLayerSet(LayerSetPosition.SEQUENCES);

				// int sequenceSize =
				// alignmentManager.getSequenceDisplayLength(alignmentIndex);
				int sequenceBorderWidth = 0;
				int alignmentOffsetX = 0;

				int sequenceSize = sequenceLayerSet
						.getNumberOfTextColumns(alignmentIndex);

				if (sequenceSize > 0) {
					Insets sequenceBorders = sequenceLayerSet
							.getMiminumBorder(alignmentIndex);

					char[] chars = new char[sequenceSize];
					Arrays.fill(chars, '_');
					int sequenceWidth = metrics.stringWidth(String
							.valueOf(chars));
					sequenceBorderWidth = sequenceWidth + sequenceBorders.left
							+ sequenceBorders.right;
					alignmentWidth = alignmentWidth - sequenceBorderWidth;
					alignmentOffsetX = width - alignmentWidth;
				}

				// Set the scroll values
				int elementWidth = fontWidth + borders.left + borders.right;
				int elementHeight = fontHeight + borders.top + borders.bottom;

				int totalColumns = alignmentManager
						.getNumberOfColumns(alignmentIndex);
				int totalRows = alignmentManager
						.getNumberOfRows(alignmentIndex);
				int numberOfColumns = alignmentWidth / elementWidth;
				int numberOfRows = alignmentHeight / elementHeight;

				int lastColumn = totalColumns - numberOfColumns;
				int lastRow = totalRows - numberOfRows;

				container.setHorizontalScrollMax(Math.max(lastColumn, 0));
				container.setVerticalScrollMax(Math.max(lastRow, 0));

				//Draw left layers (sequences)
				if (sequenceBorderWidth > 0 && alignmentHeight > 0) {
					ITextLayer sequenceTextLayer = sequenceLayerSet
							.getCurrentTextLayer();
					if (sequenceTextLayer != null) {
						// draw layer
						Image image = sequenceTextLayer.GetImage(
								alignmentIndex, sequenceBorderWidth,
								alignmentHeight, borders);
						g.drawImage(image, 0, alignmentOffsetY, null);
					}
				}

				//Draw top layers (columns)
				if (alignmentWidth > 0 && columnBarHeight > 0) {
					ITextLayer columnTextLayer = columnsLayerSet
							.getCurrentTextLayer();
					if (columnTextLayer != null) {
						// draw layer
						Image image = columnTextLayer.GetImage(alignmentIndex,
								alignmentWidth, columnBarHeight, topBorders);
						g.drawImage(image, alignmentOffsetX, 0, null);
					}
				}
				
				//Draw bottom layers
				if (alignmentWidth > 0 && bottomBarHeight > 0) {
					ITextLayer columnTextLayer = bottomLayerSet
							.getCurrentTextLayer();
					if (columnTextLayer != null) {
						// draw layer
						Image image = columnTextLayer.GetImage(alignmentIndex,
								alignmentWidth, columnBarHeight, topBorders);
						g.drawImage(image, alignmentOffsetX, bottomBarOffsetY, null);
					}
				}

				//Draw alignment layers
				ILayerSet alignmentLayerSet = uimanager
						.getLayerSet(LayerSetPosition.ALIGNMENT);

				if (alignmentWidth > 0 && alignmentHeight > 0) {
					ITextLayer textLayer = alignmentLayerSet
							.getCurrentTextLayer();
					if (textLayer != null && textLayer.IsEnabled()) {
						// draw layer
						Image image = textLayer.GetImage(alignmentIndex,
								alignmentWidth, alignmentHeight, borders);
						g.drawImage(image, alignmentOffsetX, alignmentOffsetY,
								null);
					}

					List<ILayer> layers = alignmentLayerSet.getDrawingLayers();

					if (layers != null) {
						for (ILayer layer : layers) {
							if (layer.IsEnabled()) {
								// draw layers
								Image image = layer.GetImage(alignmentIndex,
										alignmentWidth, alignmentHeight,
										borders);
								g.drawImage(image, alignmentOffsetX,
										alignmentOffsetY, null);
							}
						}
					}

					ILayer selectionLayer = alignmentLayerSet
							.getSelectionLayer();

					if (selectionLayer != null) {
						Image image = selectionLayer.GetImage(alignmentIndex,
								alignmentWidth, alignmentHeight, borders);
						g.drawImage(image, alignmentOffsetX, alignmentOffsetY,
								null);
					}

				}
			}
		}
	}

	public Insets calculateBorder(IUIManager uimanager, int alignmentIndex) {
		Insets borders = new Insets(0, 0, 0, 0);

		borders = getMinimumBorders(
				uimanager.getLayerSet(LayerSetPosition.ALIGNMENT),
				alignmentIndex, borders, BorderRelevance.ALL);

		borders = getMinimumBorders(
				uimanager.getLayerSet(LayerSetPosition.COLUMNS),
				alignmentIndex, borders, BorderRelevance.HORIZONTAL);
		
		borders = getMinimumBorders(
				uimanager.getLayerSet(LayerSetPosition.BOTTOM),
				alignmentIndex, borders, BorderRelevance.HORIZONTAL);

		borders = getMinimumBorders(
				uimanager.getLayerSet(LayerSetPosition.SEQUENCES),
				alignmentIndex, borders, BorderRelevance.VERTICAL);
		


		return borders;
	}

	public Insets getMinimumBorders(ILayerSet layerSet, int alignmentIndex,
			Insets borders, BorderRelevance relevance) {
		Insets queryBorder = layerSet.getMiminumBorder(alignmentIndex);

		queryBorder = calculateMinimumBorder(borders, queryBorder, relevance);

		return queryBorder;
	}

	public Insets calculateMinimumBorder(Insets borders, Insets queryBorder,
			BorderRelevance relevance) {
		Insets returnValue = new Insets(borders.top, borders.left,
				borders.bottom, borders.right);

		if (relevance == BorderRelevance.ALL
				|| relevance == BorderRelevance.VERTICAL) {
			returnValue.top = Math.max(borders.top, queryBorder.top);
			returnValue.bottom = Math.max(borders.bottom, queryBorder.bottom);
		}
		if (relevance == BorderRelevance.ALL
				|| relevance == BorderRelevance.HORIZONTAL) {
			returnValue.left = Math.max(borders.left, queryBorder.left);
			returnValue.right = Math.max(borders.right, queryBorder.right);
		}

		return returnValue;
	}

	@Override
	public IEvent handleClick(AlignmentClickedEvent message, Graphics g,
			IAlignmentContainer container) {

		IEvent returnValue = null;

		if (message != null && container != null
				&& message.getAlignmentId() == container.getAlignmentId()) {

			AnubisManager manager = AnubisManager.getInstance();
			IUIManager uimanager = manager.getUIManager();
			IAlignmentManager alignmentManager = manager.getAlignmentManager();

			if (g != null && uimanager != null && alignmentManager != null) {

				int alignmentId = message.getAlignmentId();

				Font currentFont = uimanager.getFont();
				g.setFont(currentFont);
				FontMetrics metrics = g.getFontMetrics();

				int fontHeight = metrics.getHeight();
				int fontWidth = metrics.stringWidth("_");

				int width = container.getDrawingAreaWidth();
				int height = container.getDrawingAreaHeight();

				int alignmentWidth = width;
				int alignmentHeight = height;

				int startColumn = alignmentManager
						.getCurrentFirstColumn(alignmentId);
				int startRow = alignmentManager.getCurrentFirstRow(alignmentId);
				int totalColumns = alignmentManager
						.getFullNumberOfColumns(alignmentId);
				int totalRows = alignmentManager
						.getFullNumberOfRows(alignmentId);

				Insets borders = getMinimumBorders(
						uimanager.getLayerSet(LayerSetPosition.ALIGNMENT),
						alignmentId, new Insets(0, 0, 0, 0),
						BorderRelevance.ALL);
				// Insets borders = uimanager.getBorders();
				int elementWidth = fontWidth + borders.left + borders.right;
				int elementHeight = fontHeight + borders.top + borders.bottom;

				Insets topBorders = getMinimumBorders(
						uimanager.getLayerSet(LayerSetPosition.COLUMNS),
						alignmentId, new Insets(0, 0, 0, 0),
						BorderRelevance.ALL);

				int columnBarHeight = 2 * (fontHeight + topBorders.top + topBorders.bottom);

				int sequenceSize = alignmentManager
						.getSequenceDisplayLength(container.getAlignmentId());

				int sequenceBorderWidth = 0;
				int alignmentOffsetX = 0;

				if (sequenceSize > 0) {
					Insets sequenceBorders = getMinimumBorders(
							uimanager.getLayerSet(LayerSetPosition.SEQUENCES),
							alignmentId, new Insets(0, 0, 0, 0),
							BorderRelevance.ALL);

					char[] chars = new char[sequenceSize];
					Arrays.fill(chars, '_');
					int sequenceWidth = metrics.stringWidth(String
							.valueOf(chars));
					sequenceBorderWidth = sequenceWidth + sequenceBorders.left
							+ sequenceBorders.right;
					alignmentWidth = alignmentWidth - sequenceBorderWidth;
					alignmentOffsetX = width - alignmentWidth;
				}

				Point point = message.getPoint();

				if (point != null) {
					int x = point.x;
					int y = point.y;

					if (y <= columnBarHeight) {
						int selectedColumn = (x - sequenceBorderWidth)
								/ elementWidth;
						selectedColumn += startColumn;

						if (selectedColumn < totalColumns) {

							switch (message.getButton()) {
							case AlignmentClickedEvent.LEFT_MOUSE_BUTTON:
								returnValue = new ColumnSelectedEvent(
										alignmentId,
										alignmentManager
												.getAlignmentColumnFromView(
														alignmentId,
														selectedColumn));
								break;
							case AlignmentClickedEvent.RIGHT_MOUSE_BUTTON:
								returnValue = new AlignmentColumnPopUpMenuEvent(
										alignmentId, selectedColumn,
										message.getPoint(),
										message.getContainer());
								break;
							default:
								break;
							}

						}
					}

					if (x <= sequenceBorderWidth) {
						int selectedRow = (y - columnBarHeight) / elementHeight;
						selectedRow += startRow;

						if (selectedRow < totalRows) {

							switch (message.getButton()) {
							case AlignmentClickedEvent.LEFT_MOUSE_BUTTON:
								returnValue = new RowSelectedEvent(alignmentId,
										alignmentManager
												.getAlignmentRowFromView(
														alignmentId,
														selectedRow));
								break;
							case AlignmentClickedEvent.RIGHT_MOUSE_BUTTON:
								returnValue = new AlignmentRowPopUpMenuEvent(
										alignmentId, selectedRow,
										message.getPoint(),
										message.getContainer());
								break;
							default:
								break;
							}

						}
					}

				}

			}
		}

		return returnValue;

	}

}
