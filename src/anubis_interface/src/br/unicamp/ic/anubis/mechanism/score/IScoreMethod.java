package br.unicamp.ic.anubis.mechanism.score;

import java.util.UUID;

public interface IScoreMethod {
	
	public double getScore(int alignmentId);
	public String getName();
	public UUID getSettingsId();

}
