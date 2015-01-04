package br.unicamp.ic.selectionlayer.eventhandler;

import java.util.UUID;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.event.ColumnSelectedEvent;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.event.RowSelectedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventHandler;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.selection.ISelectionManager;
import br.unicamp.ic.anubis.mechanism.selection.SelectionArea;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;

import static br.unicamp.ic.anubis.mechanism.settings.CommonSettingKeys.LOCK_ALIGNMENTS_KEY;

public class SelectionWatcher implements IEventHandler {

	private int ALL = -1;

	private static final UUID ALIGNMENT_DATA_REPOSITORY_ID = UUID
			.fromString("a663f847-7aa8-4714-9d91-810ac40a7fbd");

	@Override
	public void eventRaised(IEvent message) {
		// TODO Auto-generated method stub
		if (message instanceof ColumnSelectedEvent) {
			ColumnSelectedEvent event = (ColumnSelectedEvent) message;
			int alignmentId = event.getAlignmentId();
			int selectedColumn = event.getColumn();
			makeSelection(alignmentId, selectedColumn, ALL, false);
		}
		if (message instanceof RowSelectedEvent) {
			RowSelectedEvent event = (RowSelectedEvent) message;
			int alignmentId = event.getAlignmentId();
			int selectedColumn = event.getRow();
			makeSelection(alignmentId, ALL, selectedColumn, false);
		}

		if (message instanceof AlignmentDataLoadedEvent) {
			AlignmentDataLoadedEvent alignmentLoadedMessage = (AlignmentDataLoadedEvent) message;
			if (ALIGNMENT_DATA_REPOSITORY_ID.equals(alignmentLoadedMessage
					.getSource())) {
				AnubisManager manager = AnubisManager.getInstance();
				ISelectionManager selectionManager = manager
						.getSelectionManager();
				selectionManager.clearAllSelection();
				notifyRedraw(AlignmentUtil.BOTH_ALIGNMENTS);
			}
		}
	}

	private void makeSelection(int alignmentId, int column, int row,
			boolean keep) {

		boolean lockAlignments = false;

		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();
		ISelectionManager selectionManager = manager.getSelectionManager();
		ISettingsManager settingsManager = manager.getSettingsManager();

		if (alignmentManager != null && selectionManager != null) {

			if (settingsManager != null) {
				Boolean locked = (Boolean) settingsManager
						.getProperty(LOCK_ALIGNMENTS_KEY);
				lockAlignments = (locked != null) && locked;
			}

			if (!keep) {
				selectionManager.clearSelection(alignmentId);
				if (lockAlignments) {
					selectionManager.clearSelection(AlignmentUtil
							.otherAlignment(alignmentId));
				}
			}

			int totalColumns = alignmentManager
					.getFullNumberOfColumns(alignmentId);
			int totalRows = alignmentManager.getFullNumberOfRows(alignmentId);

			int otherAlignment = AlignmentUtil.otherAlignment(alignmentId);

			int otherAlignmentTotalColumns = alignmentManager
					.getFullNumberOfColumns(otherAlignment);
			int otherAlignmentTotalRows = alignmentManager
					.getFullNumberOfRows(otherAlignment);

			if (column == ALL && row != ALL) {
				if (row >= 0 && row < totalRows) {
					makeSelection(selectionManager, alignmentId, 0, row,
							totalColumns - 1, row);
					if (lockAlignments) {
						if (row < otherAlignmentTotalRows) {
							makeSelection(selectionManager, otherAlignment, 0,
									row, otherAlignmentTotalColumns - 1, row);
						}
					}
				}
			}

			if (row == ALL && column != ALL) {
				if (column >= 0 && column < totalColumns) {
					makeSelection(selectionManager, alignmentId, column, 0,
							column, totalRows - 1);
					if (lockAlignments) {
						if (column < otherAlignmentTotalColumns) {
							makeSelection(selectionManager, otherAlignment,
									column, 0, column,
									otherAlignmentTotalRows - 1);
						}
					}
				}
			}

		}
	}

	private void makeSelection(ISelectionManager selectionManager,
			int alignmentId, int startColumn, int startRow, int endColumn,
			int endRow) {

		SelectionArea selection = new SelectionArea(startColumn, startRow,
				endColumn, endRow);
		selectionManager.addSelection(alignmentId, selection);
		notifyRedraw(alignmentId);
	}

	private void notifyRedraw(int alignmentId) {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {
			RedrawAlignmentEvent event = new RedrawAlignmentEvent(alignmentId);
			eventManager.raise(event);
		}
	}
}
