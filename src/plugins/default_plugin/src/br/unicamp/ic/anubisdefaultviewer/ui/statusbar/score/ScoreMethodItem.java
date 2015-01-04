package br.unicamp.ic.anubisdefaultviewer.ui.statusbar.score;

public class ScoreMethodItem {
	
	private String name;
	private int index;
	
	public ScoreMethodItem(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	public String getName(){
		return name;
	}
	
	public int getIndex(){
		return index;
	}

	@Override
	public String toString(){
		return name;
	}

}
