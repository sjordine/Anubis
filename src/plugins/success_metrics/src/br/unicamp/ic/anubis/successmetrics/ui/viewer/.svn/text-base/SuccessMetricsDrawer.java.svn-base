package br.unicamp.ic.anubis.successmetrics.ui.viewer;

import static br.unicamp.ic.anubis.successmetrics.common.CommonConstants.RESIDUE_SUCCESS_DATACONNECTOR_ID;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.successmetrics.common.metricsstrategy.ISuccessMetricsStrategy;
import br.unicamp.ic.anubis.successmetrics.residue.dataconnector.ResidueSuccessDataConnector;
import br.unicamp.ic.anubis.ui.IUIManager;
import br.unicamp.ic.anubis.util.ColorUtil;

public class SuccessMetricsDrawer {
	
	private static final Color START_COLOR = new Color(255, 0, 0);
	private static final Color INTERMEDIATE_COLOR = new Color(255, 255, 0);
	private static final Color FINAL_COLOR = new Color(0, 255, 0);
	
	public static Image GetImage( int alignmentIndex, ISuccessMetricsStrategy dataProvider, int width, int height, int transparency, Insets borders){
		Image image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();
		IUIManager uimanager = manager.getUIManager();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (resourceManager != null && uimanager != null
				&& alignmentManager != null && dataProvider != null) {

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
			
			Integer[][] data = dataProvider.getData(alignmentIndex,	startColumn, startRow, columns, rows);

			if (data != null) {
				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						int x = j * fontWidth + j * borders.left + j
								* borders.right;
						int y = i * fontHeight + i * borders.top + i
								* borders.bottom;
						int value = (data[i][j]) != null ? data[i][j] : -1;
						if (value >= 0) {
							Color color = makeGradient(value, transparency);
							if (color != null) {
								g.setColor(color);
								g.fillRect(x, y, columnWidth, columnHeight);
							}
						}
					}
				}
			}
		}

		return image;
	}
	
	private static Color makeGradient(int value, int transparency) {
		Color color = null;
		if (value <= 50) {
			color = ColorUtil.getGradientColor(value, 0, 50, START_COLOR,
					INTERMEDIATE_COLOR, transparency);
		} else {
			color = ColorUtil.getGradientColor(value, 50, 100,
					INTERMEDIATE_COLOR, FINAL_COLOR, transparency);
		}
		return color;
	}

}
