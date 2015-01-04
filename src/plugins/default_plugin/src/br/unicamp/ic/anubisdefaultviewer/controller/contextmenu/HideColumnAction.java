package br.unicamp.ic.anubisdefaultviewer.controller.contextmenu;

import java.awt.event.ActionEvent;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.ui.menu.AlignmentContextMenuAction;

public class HideColumnAction extends AlignmentContextMenuAction {

	@Override
	public boolean evaluate(int alignmentId, int column, int row) {
		boolean active = false;

		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (alignmentManager != null) {
			int viewColumn = alignmentManager.getAlignmentColumnFromView(
					alignmentId, column);
			active = (viewColumn != -1);
		}

		return active;
	}

	@Override
	public void execute(ActionEvent event) {
		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (alignmentManager != null) {

			Integer alignmentId = (Integer) getParameter("ALIGNMENT_ID");
			Integer column = (Integer) getParameter("COLUMN");

			if (alignmentId != null && column != null) {
				alignmentManager.hideColumn(alignmentId, column);
			}

		}

	}

}
