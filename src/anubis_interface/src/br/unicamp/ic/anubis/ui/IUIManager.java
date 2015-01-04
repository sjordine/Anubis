package br.unicamp.ic.anubis.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.List;
import java.util.UUID;

import br.unicamp.ic.anubis.ui.score.IScoreMethod;
import br.unicamp.ic.anubis.ui.statusbar.StatusBarSection;
import br.unicamp.ic.anubis.ui.viewer.IAlignmentDrawingStrategy;
import br.unicamp.ic.anubis.ui.viewer.ILayer;
import br.unicamp.ic.anubis.ui.viewer.ISelectionLayer;
import br.unicamp.ic.anubis.ui.viewer.ITextLayer;

public interface IUIManager {
	
	public IAlignmentDrawingStrategy getDrawingStrategy();
	public void setDrawingStrategy(IAlignmentDrawingStrategy drawingStrategy);;
		
	public int getFontSize();
	public void setFontSize(int size);
	public Font getFont();
	public void setFontColor(Color color);
	public Color getFontColor();
	
	public ILayerSet getLayerSet(LayerSetPosition position);
	
	
	public List<IScoreMethod> getScoreMethods();
	public IScoreMethod getScoreMethod(UUID id);
	public void addScoreMethod(IScoreMethod scoreMethod);
	
	public List<StatusBarSection> getStatusBarSections();
	public void addStatusBarSection(StatusBarSection section);
	
	
	void resetBorders();
	void setBorders(int leftBorder, int topBorder, int rightBorder,
			int bottomBorder);
	void setBorders(Insets dimension);
	void resetDrawingLayers();
	
}
