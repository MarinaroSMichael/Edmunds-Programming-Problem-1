package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Question 1 solution for Edmunds GovTech Programming Challenge
 * 
 * @author Michael Marinaro
 * @version 10/27/2020
**/

/**
 * This program takes a name as an input and outputs a String ID based on the
 * 	name. The ID takes the first three letters of the input name and appends a
 * 	three digit number to the end, which begins at 005 and increments by 5s for
 *	all names which would output the same initial 3 letters.
 *
 * Additionally: 
 * - Deleted IDs are reused. Example: If MIC005 is removed from the list, the 
 * 		next Michael may be given MIC005 as an ID. 
 * - There is a limited number of users with the same initial letters in their ID. 
 * 		If more IDs are attempted to be made an error statement will be returned 
 * 		in place of an ID. 
 * - Regex and length limitations are in place to avoid bad or malicious input.
 * 		This may not be necessary in all situations, and in which it could be
 * 		removed. 
 * - A simple Swing interface is used for input and output. And dialog boxes
 * 		for errors and alerts.
 */

public class IdModel {
	
	/*
	 * Using a HashMap here as IDs are normally connected with additional
	 * 	information, and HashMap's search is in most cases O(1) which is excellent
	 * 	for constant search operations which assure there are no duplicate IDs and
	 * 	what ID's are available
	 */
	private static HashMap<String, Object> idHashMap = new HashMap<String, Object>();

	
	/**
	 * @return the idHashMap
	 */
	public static HashMap<String, Object> getIdHashMap() {
		return idHashMap;
	}

	/**
	 * @param idHashMap the idHashMap to set
	 */
	public static void setIdHashMap(HashMap<String, Object> idHashMap) {
		IdModel.idHashMap = idHashMap;
	}
	
	/**
	 * @return the KeySet of the HashMap as an ArrayList
	 */
	public static ArrayList<String> getKeySetAsArray() {
		return new ArrayList<String>(getIdHashMap().keySet());
	}
}
