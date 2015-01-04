package br.unicamp.ic.anubis.mechanism.alignment;

import java.util.ArrayList;
import java.util.List;

public class ViewBlockList {

	private List<ViewBlock> viewBlocks;

	public ViewBlockList() {
		viewBlocks = new ArrayList<ViewBlock>();
	}

	public int getValue(int position) {
		int value = -1;

		int startPos = 0;
		for (int i = 0; i < viewBlocks.size() && value == -1; i++) {
			ViewBlock block = viewBlocks.get(i);
			if (block.isVisible()) {
				int lastPos = startPos + block.getSize();
				if (startPos <= position) {
					if (position < lastPos) {
						value = block.getStart() + (position - startPos);
					} else {
						startPos = lastPos;
					}
				}
			}
		}

		return value;
	}

	public int getViewPosition(int alignmentPosition) {
		int viewPosition = -1;
		int blockNumber = -1;
		boolean found = false;
		for (int i = 0; i < viewBlocks.size() && !found; i++) {
			ViewBlock block = viewBlocks.get(i);
			if (block.isVisible()) {
				found = (alignmentPosition >= block.getStart())
						&& (alignmentPosition <= block.getEnd());
				if (found) {
					blockNumber = i;
				}
			}
		}

		if (found) {
			viewPosition = 0;
			for (int i = 0; i <= blockNumber; i++) {
				ViewBlock block = viewBlocks.get(i);
				if (block.isVisible()) {
					if (i != blockNumber) {
						viewPosition += block.getSize();
					} else {
						viewPosition += (alignmentPosition - block.getStart());
					}
				}
			}
		}

		return viewPosition;
	}

	public List<ViewBlock> getRange(int startPos, int endPos) {
		List<ViewBlock> returnValue = new ArrayList<ViewBlock>();
		int currentStartPos = 0;
		int currentEndPos = 0;
		int currentSize = 0;
		int totalSize = endPos - startPos;

		for (ViewBlock block : viewBlocks) {
			if (block.isVisible()) {
				int size = block.getSize();
				currentEndPos = currentStartPos + size;
				// Check if this block (or part of it) must be returned
				if (currentSize <= totalSize && currentEndPos > startPos) {
					int startIndex = block.getStart();
					int endIndex = block.getEnd();
					startIndex = (currentStartPos > startPos) ? startIndex
							: startIndex + (startPos - currentStartPos);
					// Return block will be the remaining requested size
					// if current block exceeds it.
					endIndex = Math.min(endIndex, startIndex
							+ (totalSize - currentSize));
					ViewBlock newBlock = new ViewBlock(startIndex, endIndex,
							true);
					returnValue.add(newBlock);
					currentSize += newBlock.getSize();
				}
			}

			currentStartPos = currentEndPos;
		}

		return returnValue;
	}

	public void resetBondaries(int startPos, int endPos) {
		viewBlocks.clear();
		ViewBlock startBlock = new ViewBlock(startPos, endPos, true);
		viewBlocks.add(startBlock);
	}

	public int getViewSize() {
		int size = 0;

		for (ViewBlock block : viewBlocks) {
			if (block.isVisible()) {
				size += block.getSize();
			}
		}

		return size;
	}

	public void hideViewPositions(int startPos, int endPos) {
		List<ViewBlock> blocks = getRange(startPos, endPos);
		if (blocks != null && blocks.size() > 0) {
			for (int i = 0; i < blocks.size(); i++) {
				ViewBlock block = blocks.get(i);
				hideAlignmentPositions(block.getStart(), block.getEnd());
			}
		}
	}

	public void hideAlignmentPositions(int startPos, int endPos) {
		splitByVisibility(startPos, endPos, true);
	}

	public void unhideAlignmentPositions(int startPos, int endPos) {
		splitByVisibility(startPos, endPos, false);
	}

	public void unhideAllPositions() {
		for (ViewBlock block : viewBlocks) {
			if (!block.isVisible()) {
				block.setVisible(true);
			}
		}
		// merge blocks
		for (int i = 0; i < viewBlocks.size(); i++) {
			mergeBlocks(i);
		}
	}

	public void move(int originalPos, int finalPos) {

		// split origin block
		int originalBlockIndex = getContainingBlock(originalPos);
		ViewBlock originalBlock = viewBlocks.get(originalBlockIndex);
		List<ViewBlock> splittedBlocks = splitBlock(originalBlock, originalPos,
				originalPos);
		replacePositions(originalBlockIndex, splittedBlocks);

		// split final block
		int finalBlockIndex = getContainingBlock(finalPos);
		ViewBlock finalBlock = viewBlocks.get(finalBlockIndex);
		splittedBlocks = splitBlock(finalBlock, finalPos, finalPos);
		replacePositions(finalBlockIndex, splittedBlocks);

		// move block to its position
		originalBlockIndex = getContainingBlock(originalPos);
		finalBlockIndex = getContainingBlock(finalPos);
		originalBlock = viewBlocks.get(originalBlockIndex);
		
			viewBlocks.remove(originalBlockIndex);
		
		if (originalBlockIndex != finalBlockIndex) {			
			viewBlocks.add(finalBlockIndex, originalBlock);
		}
		


		// merge blocks, if needed
		for (int i = 0; i < viewBlocks.size(); i++) {
			mergeBlocks(i);
		}


	}

	private void splitByVisibility(int startPos, int endPos, boolean visible) {
		List<Integer> blocksToHide = new ArrayList<Integer>();

		if (startPos <= endPos) {
			// check all blocks that must be splitted
			for (int i = 0; i < viewBlocks.size(); i++) {
				ViewBlock block = viewBlocks.get(i);
				if (block.intersects(startPos, endPos)
						&& block.isVisible() == visible) {
					blocksToHide.add(i);
				}
			}
			// split and hide blocks
			for (int i = blocksToHide.size() - 1; i >= 0; i--) {
				int blockIndex = blocksToHide.get(i);
				splitBlockVisibility(blockIndex, startPos, endPos, visible);
			}
			// check for merged blocks
			for (int i = 0; i < viewBlocks.size(); i++) {
				mergeBlocks(i);
			}
		}
	}

	private void splitBlockVisibility(int blockIndex, int startPos, int endPos,
			boolean visible) {
		if (blockIndex >= 0 && blockIndex < viewBlocks.size()) {
			ViewBlock block = viewBlocks.get(blockIndex);
			List<ViewBlock> splittedBlocks = splitBlock(block, startPos, endPos);
			if (splittedBlocks != null) {
				for (ViewBlock currentBlock : splittedBlocks) {
					if (currentBlock.intersects(startPos, endPos)) {
						currentBlock.setVisible(!visible);
					}
				}
				replacePositions(blockIndex, splittedBlocks);

			}
		}
	}

	/**
	 * Merge the given block to the adjacent positions, if possible
	 * 
	 * @param blockPos
	 *            block that may be merged
	 */
	private void mergeBlocks(int blockPos) {

		boolean removeNext = false;
		boolean removeBlock = false;

		if (blockPos >= 0 && blockPos < viewBlocks.size()) {
			ViewBlock block = viewBlocks.get(blockPos);
			if (blockPos > 0) {
				ViewBlock previousBlock = viewBlocks.get(blockPos - 1);
				if (previousBlock.intersects(block.getStart() - 1,
						block.getEnd())
						&& previousBlock.isVisible() == block.isVisible()
						&& previousBlock.getStart() <= block.getStart()) {
					previousBlock.resize(Math.min(previousBlock.getStart(),
							block.getStart()), Math.max(previousBlock.getEnd(),
							block.getEnd()), previousBlock.isVisible());
					block = previousBlock;
					removeBlock = true;
				}
			}
			if (blockPos < viewBlocks.size() - 1) {
				ViewBlock nextBlock = viewBlocks.get(blockPos + 1);
				if (nextBlock.intersects(block.getStart(), block.getEnd() + 1)
						&& nextBlock.isVisible() == block.isVisible()
						&& block.getStart() <= nextBlock.getStart()) {
					block.resize(
							Math.min(nextBlock.getStart(), block.getStart()),
							Math.max(nextBlock.getEnd(), block.getEnd()),
							nextBlock.isVisible());
					removeNext = true;
				}
			}

			if (removeNext) {
				viewBlocks.remove(blockPos + 1);
			}

			if (removeBlock) {
				viewBlocks.remove(blockPos);
			}

		}
	}

	/**
	 * Splits a block given a set of positions
	 * 
	 * @param block
	 *            block that may be splitted
	 * @param startPos
	 *            start index to split
	 * @param endPos
	 *            end index to split
	 */
	private List<ViewBlock> splitBlock(ViewBlock block, int startPos, int endPos) {

		List<ViewBlock> splitBlocks = null;

		if (block != null && startPos <= endPos) {

			int startBlock = -1;
			int targetBlockStart = -1;
			int targetBlockEnd = -1;
			int endBlock = -1;

			boolean visible = block.isVisible();

			if (block.intersects(startPos, endPos)) {
				splitBlocks = new ArrayList<ViewBlock>();

				int blockStart = block.getStart();
				int blockEnd = block.getEnd();

				startBlock = (blockStart < startPos) ? blockStart : -1;
				endBlock = (endPos < blockEnd) ? endPos + 1 : -1;
				targetBlockStart = Math.max(startPos, blockStart);
				targetBlockEnd = Math.min(endPos, blockEnd);

				ViewBlock newBlock = null;

				if (startBlock != -1) {
					newBlock = new ViewBlock(startBlock, targetBlockStart - 1,
							visible);
					splitBlocks.add(newBlock);
				}

				newBlock = new ViewBlock(targetBlockStart, targetBlockEnd,
						visible);
				splitBlocks.add(newBlock);

				if (endBlock != -1) {
					newBlock = new ViewBlock(targetBlockEnd + 1, blockEnd,
							visible);
					splitBlocks.add(newBlock);
				}
			}

		}

		return splitBlocks;
	}

	private void replacePositions(int blockPos, List<ViewBlock> splitBlocks) {
		if (blockPos < viewBlocks.size()) {
			if (splitBlocks != null && splitBlocks.size() > 0) {
				// Resize first block
				ViewBlock block = splitBlocks.get(0);
				ViewBlock currentBlock = viewBlocks.get(blockPos);
				currentBlock.resize(block.getStart(), block.getEnd(),
						block.isVisible());
				splitBlocks.remove(0);
				// Add all others (if any) after given position
				if (splitBlocks.size() > 0) {
					viewBlocks.addAll(blockPos + 1, splitBlocks);
				}
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		if (viewBlocks != null) {
			for (ViewBlock block : viewBlocks) {
				buffer.append(block);
			}
		}

		return buffer.toString();
	}

	private int getContainingBlock(int viewPosition) {
		int returnValue = -1;

		int startPos = 0;
		for (int i = 0; i < viewBlocks.size() && returnValue == -1; i++) {
			ViewBlock block = viewBlocks.get(i);
			if (block.isVisible()) {
				int lastPos = startPos + block.getSize();
				if (startPos <= viewPosition) {
					if (viewPosition < lastPos) {
						returnValue = i;
					} else {
						startPos = lastPos;
					}
				}
			}
		}

		return returnValue;
	}

}
