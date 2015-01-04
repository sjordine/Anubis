package br.unicamp.ic.anubis.treeviewer.mechanism;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import jebl.evolution.distances.DistanceMatrix;
import jebl.evolution.taxa.Taxon;

public class AnubisDistanceMatrix implements DistanceMatrix {

	private double[][] distances;
	private HashMap<String, Integer> sequenceMap;
	private List<Taxon> taxaList;

	public AnubisDistanceMatrix(String[] sequences, Double[][] distanceMatrix) {
		buildMatrix(distanceMatrix);
		buildSequenceMap(sequences);

	}

	private void buildMatrix(Double[][] originalMatrix) {
		if (originalMatrix.length > 0 && originalMatrix[0] != null
				&& originalMatrix[0].length > 0) {
			int rows = originalMatrix.length;
			int columns = originalMatrix[0].length;
			distances = new double[rows][columns];
			for (int i=0; i < rows; i++){
				for (int j=0; j < columns; j++){
					distances[i][j] = originalMatrix[i][j];
				}
			}
		}
	}

	@Override
	public double getDistance(int row, int column) {
		return distances[row][column];
	}

	@Override
	public double getDistance(Taxon taxonRow, Taxon taxonColumn) {
		// Get index for both taxa
		int row = sequenceMap.get(taxonRow.getName());
		int column = sequenceMap.get(taxonColumn.getName());
		// Get distance based on its indexes
		return getDistance(row, column);
	}

	@Override
	public double[][] getDistances() {
		return distances;
	}

	@Override
	public int getSize() {
		return distances.length;
	}

	@Override
	public DistanceMatrix getSubmatrix(Collection<Taxon> arg0) {
		return null;
	}

	@Override
	public List<Taxon> getTaxa() {
		return taxaList;
	}

	private void buildSequenceMap(String[] sequences) {
		sequenceMap = new HashMap<String, Integer>();
		taxaList = new ArrayList<Taxon>();
		for (int i = 0; i < sequences.length; i++) {
			sequenceMap.put(sequences[i], i);
			Taxon currentTaxon = Taxon.getTaxon(sequences[i]);
			taxaList.add(currentTaxon);
		}
	}

}
