package br.unicamp.ic.anubisdefaultviewer.score.matrixloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.anubis.admin.AnubisManager;
import br.unicamp.ic.anubis.mechanism.resources.IResourceManager;
import br.unicamp.ic.anubis.mechanism.settings.ISettingsManager;
import br.unicamp.ic.jbio.matrix.DistanceMatrix;
import br.unicamp.ic.jbio.matrix.MatrixLoader;

import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.MATRIX_PATH_PROPERTY;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SCORE_LIST_ID;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SCORE_MATRIX_ID;
import static br.unicamp.ic.anubisdefaultviewer.common.CommonConstants.SELECTED_MATRIX_INDEX_PROPERTY;

public class MatrixBatchLoader {

	public static void loadMatrices() {
		AnubisManager manager = AnubisManager.getInstance();
		ISettingsManager settingsManager = manager.getSettingsManager();
		IResourceManager resourceManager = manager.getResourceManager();
		if (settingsManager != null && resourceManager != null) {
			// Get path
			String matrixPath = (String) settingsManager
					.getProperty(MATRIX_PATH_PROPERTY);
			if (matrixPath != null) {
				List<DistanceMatrix> matrices = loadMatrices(matrixPath);
				// Save resource				
				resourceManager.setResource(SCORE_LIST_ID, matrices);
				if (matrices!=null && matrices.size()>0){
					DistanceMatrix matrix = matrices.get(0);
					settingsManager.setProperty(SELECTED_MATRIX_INDEX_PROPERTY, 0);
					resourceManager.setResource(SCORE_MATRIX_ID, matrix);
				}
			}
		}
	}
	
	private static List<DistanceMatrix> loadMatrices(String path){
		List<DistanceMatrix> returnValue = new ArrayList<DistanceMatrix>();
		
		//Check files
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		if (fileList!=null && fileList.length>0){
			for (File file:fileList){				
				//Get matrix name
				String matrixName = file.getName();
				matrixName = stripExtension(matrixName);
				DistanceMatrix matrix;
				try {
					matrix = MatrixLoader.read(matrixName, file.getAbsolutePath());
					returnValue.add(matrix);
				} catch (FileNotFoundException e) {
					// TODO handle errors
				} catch (IOException e) {
					// TODO handle errors
				}				
			}
		}
		
		return returnValue;
	}
	
	private static String stripExtension (String str) {
        // Handle null case specially.
        if (str == null) return null;

        // Get position of last '.'.
        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.
        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.
        return str.substring(0, pos);
    }

}
