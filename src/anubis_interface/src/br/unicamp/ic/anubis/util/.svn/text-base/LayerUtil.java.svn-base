package br.unicamp.ic.anubis.util;

import java.awt.Insets;

public class LayerUtil {
	
	/**
	 * Get the minimum acceptable border considering two sets of borders
	 * 
	 * @param border1 First border set
	 * @param border2 Second border set
	 * @return minimum acceptable border, the maximum value for each dimension
	 */
	public static Insets getMinimumAcceptableBorder(Insets border1, Insets border2){
		Insets returnValue = new Insets(0,0,0,0);
		
		returnValue.top = Math.max(border1.top, border2.top);
		returnValue.bottom = Math.max(border1.bottom, border2.bottom);
		returnValue.left = Math.max(border1.left, border2.left);
		returnValue.right = Math.max(border1.right, border2.right);
		
		return returnValue;
	}

}
