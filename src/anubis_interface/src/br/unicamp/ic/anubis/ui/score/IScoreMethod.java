package br.unicamp.ic.anubis.ui.score;

import java.util.UUID;

public interface IScoreMethod {
	
	enum State{
		NOT_LOADED,
		CALCULATING,
		LOADED
	}
	
	
	public UUID getId();
	public String getName();
	
	public double getScore(int alignmentId);
	public State getState(int alignmentId);	

}
