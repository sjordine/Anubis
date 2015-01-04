package br.unicamp.ic.anubis.ui.statusbar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.ui.IUIManager;

public class StatusBar extends JPanel {

	public StatusBar() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		this.setBorder(BorderFactory.createLoweredBevelBorder());

	}

	public void create() {
		// Get sections
		AnubisManager manager = AnubisManager.getInstance();
		IUIManager uiManager = manager.getUIManager();

		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();

		if (uiManager != null && gridBagLayout != null) {
			List<StatusBarSection> sections = uiManager.getStatusBarSections();
			if (sections != null && sections.size() > 0) {
				configureLayout(gridBagLayout, sections);
				createSections(sections);
				invalidate();
			}
		}
	}

	public void createSections(List<StatusBarSection> sections) {
		int xPos = 0;
		for (int i = 0; i < sections.size(); i++) {

			StatusBarSection section = sections.get(i);
			int numberOfColumns = section.getNumberOfColumns();

			GridBagConstraints gbc_panel_2 = new GridBagConstraints();
			switch (section.getOrientation()) {
			case LEFT:
				gbc_panel_2.anchor = GridBagConstraints.WEST;
				break;
			case RIGHT:
				gbc_panel_2.anchor = GridBagConstraints.EAST;
				break;
			default:
				gbc_panel_2.anchor = GridBagConstraints.WEST;
				break;
			}

			gbc_panel_2.insets = new Insets(0, 0, 5, 5);
			gbc_panel_2.fill = GridBagConstraints.VERTICAL;
			gbc_panel_2.gridx = xPos;
			gbc_panel_2.gridy = 0;
			add(section.getSectionPanel(), gbc_panel_2);

			xPos += numberOfColumns;
		}
	}

	public void configureLayout(GridBagLayout gridBagLayout,
			List<StatusBarSection> sections) {
		int[] columnWidths = new int[sections.size()];
		for (int i = 0; i < sections.size(); i++) {
			columnWidths[i] = 0;
		}
		gridBagLayout.columnWidths = columnWidths;
		gridBagLayout.rowHeights = new int[] { 0 };
		double[] columnWeigths = new double[sections.size()];
		for (int i = 0; i < sections.size(); i++) {
			StatusBarSection section = sections.get(i);
			columnWeigths[i] = section.getColumnWeight();
		}
		
		gridBagLayout.columnWeights = columnWeigths;
		gridBagLayout.rowWeights = new double[] { 0.0 };
	}

}
