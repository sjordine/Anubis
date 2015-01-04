package br.unicamp.ic.anubis.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.ui.score.IScoreMethod;
import br.unicamp.ic.anubis.ui.statusbar.StatusBarSection;
import br.unicamp.ic.anubis.ui.viewer.IAlignmentDrawingStrategy;
import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.anubis.ui.viewer.ISelectionLayer;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;

public class AnubisUIManager implements IUIManager {

	private static AnubisUIManager instance = new AnubisUIManager();
	private IAlignmentDrawingStrategy drawingStrategy = null;

	AnubisUIAlignmentManager alignmentManager = null;
	AnubisUILayersManager layerManager = null;
	AnubisScoreMethodManager scoreManager = null;
	AnubisUIStatusBarManager statusBarManager = null;

	private ILayerSet alignmentLayerSet;
	private ILayerSet bottomLayerSet;
	private ILayerSet columnsLayerSet;
	private ILayerSet rightLayerSet;
	private ILayerSet sequencesLayerSet;

	private AnubisUIManager() {

		alignmentManager = new AnubisUIAlignmentManager();
		layerManager = new AnubisUILayersManager();
		scoreManager = new AnubisScoreMethodManager();
		statusBarManager = new AnubisUIStatusBarManager();

		//Initialize Layer Sets
		alignmentLayerSet = new AnubisLayerSet();
		bottomLayerSet = new AnubisLayerSet();
		columnsLayerSet = new AnubisLayerSet();
		rightLayerSet = new AnubisLayerSet();
		sequencesLayerSet = new AnubisLayerSet();
	}

	@Override
	public IAlignmentDrawingStrategy getDrawingStrategy() {
		return drawingStrategy;
	}

	@Override
	public void setDrawingStrategy(IAlignmentDrawingStrategy drawingStrategy) {
		this.drawingStrategy = drawingStrategy;
	}

	public static AnubisUIManager getInstance() {
		return instance;
	}


	@Override
	public void resetDrawingLayers() {
		alignmentLayerSet.resetDrawingLayers();
		columnsLayerSet.resetDrawingLayers();
		bottomLayerSet.resetDrawingLayers();
		sequencesLayerSet.resetDrawingLayers();
		rightLayerSet.resetDrawingLayers();
		alignmentLayerSet.resetDrawingLayers();
	}


	@Override
	public int getFontSize() {
		return alignmentManager.getFontSize();
	}

	@Override
	public void setFontSize(int size) {
		alignmentManager.setFontSize(size);
	}

	@Override
	public Font getFont() {
		return alignmentManager.getFont();
	}

	@Override
	public void setFontColor(Color color) {
		alignmentManager.setFontColor(color);
	}

	@Override
	public Color getFontColor() {
		return alignmentManager.getFontColor();
	}

	@Override
	public void setBorders(Insets dimension) {
		//SET ALL LAYER BORDERS
		alignmentLayerSet.setBorders(dimension);
		
		columnsLayerSet.setBorders(dimension);
		bottomLayerSet.setBorders(dimension);

		sequencesLayerSet.setBorders(dimension);
		rightLayerSet.setBorders(dimension);

	}

	@Override
	public void setBorders(int leftBorder, int topBorder, int rightBorder,
			int bottomBorder) {
		//Set all layer set borders		
		alignmentLayerSet.setBorders(leftBorder, topBorder, rightBorder,
				bottomBorder);
		columnsLayerSet.setBorders(leftBorder, topBorder, rightBorder,
				bottomBorder);
		bottomLayerSet.setBorders(leftBorder, topBorder, rightBorder,
				bottomBorder);
		sequencesLayerSet.setBorders(leftBorder, topBorder, rightBorder,
				bottomBorder);
		rightLayerSet.setBorders(leftBorder, topBorder, rightBorder,
				bottomBorder);
	}


	@Override
	public void resetBorders() {

		alignmentLayerSet.resetBorders();
		columnsLayerSet.resetBorders();
		bottomLayerSet.resetBorders();
		sequencesLayerSet.resetBorders();
		rightLayerSet.resetBorders();
	}

	@Override
	public List<IScoreMethod> getScoreMethods() {
		return scoreManager.getScoreMethods();
	}

	@Override
	public IScoreMethod getScoreMethod(UUID id) {
		return scoreManager.getScoreMethod(id);
	}

	@Override
	public void addScoreMethod(IScoreMethod scoreMethod) {
		scoreManager.addScoreMethod(scoreMethod);
	}

	@Override
	public List<StatusBarSection> getStatusBarSections() {
		return statusBarManager.getStatusBarSections();
	}

	@Override
	public void addStatusBarSection(StatusBarSection section) {
		statusBarManager.addStatusBarSection(section);
	}

	@Override
	public ILayerSet getLayerSet(LayerSetPosition position) {
		ILayerSet layerSet = null;

		switch (position) {
		case ALIGNMENT:
			layerSet = alignmentLayerSet;
			break;
		case BOTTOM:
			layerSet = bottomLayerSet;
			break;
		case COLUMNS:
			layerSet = columnsLayerSet;
			break;
		case RIGHT:
			layerSet = rightLayerSet;
			break;
		case SEQUENCES:
			layerSet = sequencesLayerSet;
			break;
		}

		return layerSet;
	}

}
