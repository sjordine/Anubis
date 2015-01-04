package br.unicamp.ic.anubis.mechanism.alignment;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.event.AlignmentScoreChangeEvent;
import br.unicamp.ic.anubis.event.AlignmentScoreMethodsChanged;
import br.unicamp.ic.anubis.event.RedrawAlignmentEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.score.IScoreMethod;

public class AnubisAlignmentManager implements IAlignmentManager {

	private static AnubisAlignmentManager instance = new AnubisAlignmentManager();

	private final int COLUMN_POS = 0;
	private final int ROW_POS = 1;

	private int[][] firstPosition;
	private int[][] fullSize;

	private ViewBlockList[][] blockList;

	private int maxSequenceLength[];
	private int maxSequenceDisplayLength;

	private List<IScoreMethod> scoreMethods;
	private int selectedScoreMethod;

	private AnubisAlignmentManager() {
		firstPosition = new int[2][2];
		fullSize = new int[2][2];
		blockList = new ViewBlockList[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				blockList[i][j] = new ViewBlockList();
			}
		}
		maxSequenceLength = new int[2];
		maxSequenceDisplayLength = -1;

		scoreMethods = new ArrayList<IScoreMethod>();
		selectedScoreMethod = 0;
	}

	@Override
	public synchronized int getCurrentFirstColumn(int alignment) {
		int returnValue = -1;

		if (alignment >= 0 && alignment <= 1) {
			returnValue = firstPosition[alignment][COLUMN_POS];
		}

		return returnValue;
	}

	@Override
	public synchronized int getCurrentFirstRow(int alignment) {
		int returnValue = -1;

		if (alignment >= 0 && alignment <= 1) {
			returnValue = firstPosition[alignment][ROW_POS];
		}

		return returnValue;
	}

	@Override
	public synchronized void setCurrentFirstColumn(int alignment, int position) {
		if (alignment >= 0 && alignment <= 1) {
			firstPosition[alignment][COLUMN_POS] = position;
		}
	}

	@Override
	public synchronized void setCurrentFirstRow(int alignment, int position) {
		if (alignment >= 0 && alignment <= 1) {
			firstPosition[alignment][ROW_POS] = position;
		}
	}

	@Override
	public synchronized int getFullNumberOfColumns(int alignment) {
		int returnValue = -1;

		if (alignment >= 0 && alignment <= 1) {
			returnValue = fullSize[alignment][COLUMN_POS];
		}

		return returnValue;
	}

	@Override
	public synchronized int getFullNumberOfRows(int alignment) {
		int returnValue = -1;

		if (alignment >= 0 && alignment <= 1) {
			returnValue = fullSize[alignment][ROW_POS];
		}

		return returnValue;
	}

	@Override
	public void setFullNumberOfColumns(int alignment, int size) {
		if (alignment >= 0 && alignment <= 1) {
			fullSize[alignment][COLUMN_POS] = size;
			blockList[alignment][COLUMN_POS].resetBondaries(0, size - 1);

		}
	}

	@Override
	public void setFullNumberOfRows(int alignment, int size) {
		if (alignment >= 0 && alignment <= 1) {
			fullSize[alignment][ROW_POS] = size;
			blockList[alignment][ROW_POS].resetBondaries(0, size - 1);

		}
	}

	@Override
	public synchronized int getNumberOfColumns(int alignment) {
		int returnValue = -1;

		if (alignment >= 0 && alignment <= 1) {
			returnValue = blockList[alignment][COLUMN_POS].getViewSize();
		}

		return returnValue;
	}

	@Override
	public synchronized int getNumberOfRows(int alignment) {
		int returnValue = -1;

		if (alignment >= 0 && alignment <= 1) {
			returnValue = blockList[alignment][ROW_POS].getViewSize();
		}

		return returnValue;
	}

	public static AnubisAlignmentManager getInstance() {
		return instance;
	}

	@Override
	public int getSequenceDisplayLength(int alignment) {
		return (maxSequenceDisplayLength >= 0 && maxSequenceLength[alignment] > 0) ? maxSequenceDisplayLength
				: maxSequenceLength[alignment];
	}

	@Override
	public void setMaxSequenceDisplayLength(int length) {
		maxSequenceDisplayLength = length;
	}

	@Override
	public void setMaxSequenceLength(int alignment, int length) {
		maxSequenceLength[alignment] = length;
	}

	@Override
	public List<ViewBlock> getColumns(int alignment, int startColumn,
			int endColumn) {
		List<ViewBlock> returnValue = null;

		if (alignment >= AlignmentUtil.BENCHMARK_ALIGNMENT
				&& alignment <= AlignmentUtil.TARGET_ALIGNMENT) {
			returnValue = blockList[alignment][COLUMN_POS].getRange(
					startColumn, endColumn);
		}

		return returnValue;
	}

	@Override
	public List<ViewBlock> getRows(int alignment, int startRow, int endRow) {
		List<ViewBlock> returnValue = null;

		if (alignment >= AlignmentUtil.BENCHMARK_ALIGNMENT
				&& alignment <= AlignmentUtil.TARGET_ALIGNMENT) {
			returnValue = blockList[alignment][ROW_POS].getRange(startRow,
					endRow);
		}

		return returnValue;
	}

	@Override
	public int getAlignmentColumnFromView(int alignment, int viewColumn) {
		int column = -1;

		ViewBlockList columnBlocks = blockList[alignment][COLUMN_POS];

		column = columnBlocks.getValue(viewColumn);

		return column;
	}

	@Override
	public int getAlignmentRowFromView(int alignment, int viewRow) {
		int row = -1;

		ViewBlockList columnBlocks = blockList[alignment][ROW_POS];

		row = columnBlocks.getValue(viewRow);

		return row;
	}

	@Override
	public int getViewColumnFromAlignment(int alignment, int alignmentColumn) {
		int column = -1;

		ViewBlockList columnBlocks = blockList[alignment][COLUMN_POS];

		column = columnBlocks.getViewPosition(alignmentColumn);

		return column;
	}

	@Override
	public int getViewRowFromAlignment(int alignment, int alignmentRow) {
		int row = -1;

		ViewBlockList columnBlocks = blockList[alignment][ROW_POS];

		row = columnBlocks.getViewPosition(alignmentRow);

		return row;
	}

	@Override
	public void hideColumn(int alignment, int viewColumn) {
		ViewBlockList columnBlocks = blockList[alignment][COLUMN_POS];
		columnBlocks.hideViewPositions(viewColumn, viewColumn);
		raiseRedrawEvent(alignment);
	}

	@Override
	public void hideRow(int alignment, int viewRow) {
		ViewBlockList rowBlocks = blockList[alignment][ROW_POS];
		rowBlocks.hideViewPositions(viewRow, viewRow);
		raiseRedrawEvent(alignment);
	}

	@Override
	public void unhideAll() {
		ViewBlockList columnBlocks = blockList[AlignmentUtil.BENCHMARK_ALIGNMENT][COLUMN_POS];
		columnBlocks.unhideAllPositions();
		columnBlocks = blockList[AlignmentUtil.TARGET_ALIGNMENT][COLUMN_POS];
		columnBlocks.unhideAllPositions();
		ViewBlockList rowBlocks = blockList[AlignmentUtil.BENCHMARK_ALIGNMENT][ROW_POS];
		rowBlocks.unhideAllPositions();
		rowBlocks = blockList[AlignmentUtil.TARGET_ALIGNMENT][ROW_POS];
		rowBlocks.unhideAllPositions();
		raiseRedrawEvent(AlignmentUtil.BOTH_ALIGNMENTS);
	}

	@Override
	public void moveRow(int alignment, int currentPosition, int finalPosition) {


		ViewBlockList rowBlocks = blockList[alignment][ROW_POS];
		int size = getNumberOfRows(alignment);

		if (currentPosition >= 0 && currentPosition < size
				&& finalPosition >= 0 && finalPosition < size) {

			rowBlocks.move(currentPosition, finalPosition);
		}
		raiseRedrawEvent(alignment);
	}



	@Override
	public List<IScoreMethod> getScoreMethods() {
		return scoreMethods;
	}

	@Override
	public void setScoreMethod(int index) {
		if (index!=selectedScoreMethod){
			this.selectedScoreMethod = index;
			raiseScoreChange();
		}
	}



	@Override
	public IScoreMethod getScoreMethod() {
		IScoreMethod returnValue = null;

		if (scoreMethods.size() > selectedScoreMethod) {
			returnValue = scoreMethods.get(selectedScoreMethod);
		}

		return returnValue;
	}

	@Override
	public void addScoreMethod(IScoreMethod scoreMethod) {		
		if (scoreMethod != null) {
			scoreMethods.add(scoreMethod);
			raiseScoreMethodsChanged();
		}
	}
	
	private void raiseRedrawEvent(int alignmentId) {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {

			RedrawAlignmentEvent redrawEvent;

			redrawEvent = new RedrawAlignmentEvent(alignmentId);

			eventManager.raise(redrawEvent);
		}
	}
	
	private void raiseScoreChange() {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {

			AlignmentScoreChangeEvent scoreChangedEvent;

			scoreChangedEvent = new AlignmentScoreChangeEvent(AlignmentUtil.BOTH_ALIGNMENTS);

			eventManager.raise(scoreChangedEvent);
		}		
	}

	private void raiseScoreMethodsChanged() {
		AnubisManager manager = AnubisManager.getInstance();
		IEventManager eventManager = manager.getEventManager();

		if (eventManager != null) {

			AlignmentScoreMethodsChanged scoreMethodsChangedEvent;

			scoreMethodsChangedEvent = new AlignmentScoreMethodsChanged();

			eventManager.raise(scoreMethodsChangedEvent);
		}	
	}

}
