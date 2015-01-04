package br.unicamp.ic.anubisdefaultviewer.controller.contextmenu;

import java.awt.event.ActionEvent;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.ui.menu.AlignmentContextMenuAction;

public class MoveRowDownAction extends AlignmentContextMenuAction {

	@Override
	public boolean evaluate(int alignmentId, int column, int row) {
		boolean active = false;
		
		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();
		
		if (alignmentManager != null) {
			int size = alignmentManager.getNumberOfRows(alignmentId);
			active = row < size -1;
		}

		

		return active;
	}

	@Override
	public void execute(ActionEvent event) {
		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if (alignmentManager != null) {

			Integer alignmentId = (Integer) getParameter("ALIGNMENT_ID");
			Integer row = (Integer) getParameter("ROW");

			if (alignmentId != null && row != null) {
				alignmentManager.moveRow(alignmentId, row, row+1);
			}

		}
	}

}
