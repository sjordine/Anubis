package br.unicamp.ic.anubisdefaultviewer.datarepository;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.KeyStroke;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.data.IAlignmentDataRepository;
import br.unicamp.ic.anubis.event.AlignmentDataLoadedEvent;
import br.unicamp.ic.anubis.mechanism.alignment.AlignmentUtil;
import br.unicamp.ic.anubis.mechanism.alignment.IAlignmentManager;
import br.unicamp.ic.anubis.mechanism.messaging.IEvent;
import br.unicamp.ic.anubis.mechanism.messaging.IEventManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.plugin.IFeature;
import br.unicamp.ic.anubis.ui.dialog.OpenFileDialog;
import br.unicamp.ic.anubis.ui.menu.MenuAction;
import br.unicamp.ic.anubis.ui.menu.MenuItemDefinition;

public class AlignmentDataRepository implements IFeature,
		IAlignmentDataRepository {

	public static final UUID ID = UUID
			.fromString("1da87917-7290-4d39-9bff-9bae34cfce63");
	private static final UUID INTERFACE_ID = UUID
			.fromString("a663f847-7aa8-4714-9d91-810ac40a7fbd");

	private static UUID fileMenuId = UUID
			.fromString("41a39630-6778-4f52-b1d6-cd889d130864");
	private static UUID loadAlignmentMenuId = UUID
			.fromString("77a2cb4a-9b2b-4753-b0c7-d609b5e98f91");
	private static UUID loadAlignment1MenuId = UUID
			.fromString("49981f1d-6e9b-494d-960d-885e5f88e9ef");
	private static UUID loadAlignment2MenuId = UUID
			.fromString("6acdf486-51d0-4783-85f9-0ffc1908d726");

	private static UUID loadAlignmentDialogId = UUID
			.fromString("04fa2535-ef1c-4210-be43-bf04de98e948");

	// Alignment Data Structures
	private Character[][][] alignments = new Character[2][][];
	private String[][] sequences = new String[2][];
	public boolean[] loaded = new boolean[2];

	@Override
	public UUID getId() {
		return ID;
	}

	@Override
	public UUID getInterfaceID() {
		return INTERFACE_ID;
	}

	@Override
	public List<UUID> getRequiredInterfaces() {
		return null;
	}

	@Override
	public void enable() {

		// Clean isLoaded flag
		loaded[0] = false;
		loaded[1] = false;

		// Get {menu root
		AnubisManager manager = AnubisManager.getInstance();
		IResourceManager resourceManager = manager.getResourceManager();

		if (resourceManager != null) {

			// Add load alignment menus
			MenuItemDefinition parentMenu = (MenuItemDefinition) resourceManager
					.getResource(fileMenuId);
			MenuItemDefinition menuItem = new MenuItemDefinition();
			menuItem.setText("Load alignment");
			menuItem.setOrder(100);
			resourceManager.setResource(loadAlignmentMenuId, menuItem);
			parentMenu.addChildren(menuItem);

			parentMenu = menuItem;

			menuItem = new MenuItemDefinition();
			menuItem.setText("Load benchmark alignment");
			menuItem.setShortcut(KeyStroke.getKeyStroke('B',InputEvent.CTRL_DOWN_MASK ));
			menuItem.setOrder(100);

			// Create dialog
			OpenFileDialog dialog = new OpenFileDialog();
			resourceManager.setResource(loadAlignmentDialogId, dialog);

			MenuAction action = new LoadBenchmarkAction();
			action.setParameter("ALIGNMENT_INDEX", 0);
			menuItem.setAction(action);

			resourceManager.setResource(loadAlignment1MenuId, menuItem);
			parentMenu.addChildren(menuItem);

			menuItem = new MenuItemDefinition();
			menuItem.setText("Load target alignment");
			menuItem.setShortcut(KeyStroke.getKeyStroke('T',InputEvent.CTRL_DOWN_MASK ));
			menuItem.setOrder(500);

			action = new LoadBenchmarkAction();
			action.setParameter("ALIGNMENT_INDEX", 1);
			menuItem.setAction(action);

			resourceManager.setResource(loadAlignment2MenuId, menuItem);
			parentMenu.addChildren(menuItem);

			resourceManager.setResource(INTERFACE_ID, this);
		}

	}

	@Override
	public void setData(int alignmentId, Object data) {

		AnubisManager manager = AnubisManager.getInstance();
		IAlignmentManager alignmentManager = manager.getAlignmentManager();

		if ((alignmentId == AlignmentUtil.BENCHMARK_ALIGNMENT || alignmentId == AlignmentUtil.TARGET_ALIGNMENT)
				&& alignmentManager != null) {
			storeSequenceList(alignmentId,
					(List<Map.Entry<String, String>>) data);

			// Check if at least one sequence was loaded to indicate that there
			// is data
			loaded[alignmentId] = alignments[alignmentId] != null
					&& alignments[alignmentId].length > 0;

			// Check if the other alignment must be invalidated
			boolean otherAlignmentStillValid = validateOtherAlignment(alignmentId);
			
			int updateIndex = otherAlignmentStillValid? alignmentId : AlignmentUtil.BOTH_ALIGNMENTS;

			notifyDataChanged(updateIndex, manager);
		}
	}

	private boolean  validateOtherAlignment(int alignmentId) {
		boolean stillValid = true;
		
		int otherAlignment = AlignmentUtil.otherAlignment(alignmentId);
		if (loaded[otherAlignment]){
			//check sequences
			String[] currentSequences = sequences[alignmentId];
			String[] otherSequences = sequences[otherAlignment];
			
			List<String> currentSequenceList = new ArrayList<String>(Arrays.asList(currentSequences));
			List<String> otherSequenceList =  new ArrayList<String>(Arrays.asList(otherSequences));
			
			
			
			while (currentSequenceList.size() > 0 && otherSequenceList.size() > 0 && stillValid){
				String firstElement = currentSequenceList.get(0);
				if (otherSequenceList.contains(firstElement)){
					int index = otherSequenceList.indexOf(firstElement);
					currentSequenceList.remove(0);
					otherSequenceList.remove(index);
				} else {
					//lists are different
					stillValid = false;
				}
					
			}
			
			stillValid = stillValid && currentSequenceList.size()==0 && otherSequenceList.size()==0;
			
			if (!stillValid){
				loaded[otherAlignment]=false;
				alignments[otherAlignment]=null;
				sequences[otherAlignment]=null;
			}
			
		}		
		
		return stillValid;
	}

	@Override
	public Object getData(int alignmentId, int startColumn, int startRow,
			int endColumn, int endRow) {
		Character[][] returnValue = null;

		if (validateParameters(alignmentId, startRow, startColumn, endRow,
				endColumn)) {
			int numberOfRows = endRow - startRow;
			int numberOfColumns = endColumn - startColumn;

			returnValue = new Character[numberOfRows + 1][numberOfColumns + 1];

			for (int i = 0; i <= numberOfRows; i++) {
				Character[] sequence = alignments[alignmentId][i + startRow];
				System.arraycopy(sequence, startColumn, returnValue[i], 0,
						numberOfColumns + 1);
			}
		}

		return returnValue;
	}

	private boolean validateParameters(int alignmentId, int startRow,
			int startColumn, int endRow, int endColumn) {

		boolean result = startColumn >= 0 && startColumn <= endColumn
				&& startRow >= 0 && startRow <= endRow;
		result = result && alignmentId >= 0 && alignmentId <= 1;
		result = result && alignments[alignmentId] != null;
		if (result) {
			Character[][] alignment = alignments[alignmentId];
			int numberOfSequences = alignment.length;
			result = result && startRow < numberOfSequences
					&& endRow < numberOfSequences;
			if (result) {
				int alignmentLength = alignment[startRow].length;
				result = result && startColumn < alignmentLength
						&& endColumn < alignmentLength;
			}
		}

		return result;
	}

	private void storeSequenceList(int alignmentNumber,
			List<Map.Entry<String, String>> alignment) {
		if (alignmentNumber < 2 && alignment != null) {

			int alignmentSize = alignment.size();

			alignments[alignmentNumber] = new Character[alignmentSize][];
			sequences[alignmentNumber] = new String[alignmentSize];
			int sequenceCount = 0;

			for (Map.Entry<String, String> sequence : alignment) {
				
				// Add sequence name
				sequences[alignmentNumber][sequenceCount] = sequence.getKey();
				String currentSequence = sequence.getValue();

				alignments[alignmentNumber][sequenceCount] = new Character[currentSequence
						.length()];
				for (int i = 0; i < currentSequence.length(); i++) {
					alignments[alignmentNumber][sequenceCount][i] = currentSequence
							.charAt(i);

				}

				sequenceCount++;
			}
		}

	}

	@Override
	public boolean isLoaded(int aligmentId) {
		return loaded[aligmentId];
	}

	@Override
	public int numberOfSequences(int aligmentId) {

		int size = 0;

		if (isLoaded(aligmentId)) {
			size = alignments[aligmentId].length;
		}

		return size;
	}

	@Override
	public int length(int aligmentId) {
		int length = 0;

		if (isLoaded(aligmentId)) {
			length = alignments[aligmentId][0].length;
		}

		return length;
	}

	@Override
	public Object getSequences(int alignmentId, int startRow, int endRow) {
			
		String[] returnValue = null;

		if (validateParameters(alignmentId, startRow, 0, endRow, 0)) {
			int numberOfRows = endRow - startRow;

			returnValue = new String[numberOfRows + 1];

			System.arraycopy(sequences[alignmentId], startRow, returnValue, 0,
					numberOfRows + 1);

		}
		

		return returnValue;
	}

	private void notifyDataChanged(int alignmentId, AnubisManager manager) {

		// Notify loaded alignment, if successful
		IEventManager eventManager = manager.getEventManager();
		if (eventManager != null) {
			IEvent event = new AlignmentDataLoadedEvent(INTERFACE_ID,
					alignmentId);
			eventManager.raise(event);
		}
	}

}
