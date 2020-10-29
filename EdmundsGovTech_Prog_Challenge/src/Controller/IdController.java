package Controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import Model.IdModel;
import View.IdUI;

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

public class IdController {
	
	private static final int NAME_LENGTH_LIMIT = 20;
	private static final int INITIAL_ID_NUM = 5; 	// ie 5 = 005
	private static final int ID_NUM_INCREMENT = 5;
	private static final int ID_SUBSTRING_SIZE = 3; // ie 3 = ABC
	private static final int ID_NUM_LENGTH = 3; 	// ie 3 = 000

	// Maximum number for IDs with the same initial 3 characters
	// ie for an increment of 5 and a length of 3 MAX_ID_NUM would be 995
	private static final int MAX_ID_NUM = ((int) Math.pow(10, ID_NUM_LENGTH)) - ID_NUM_INCREMENT;

	// Small format string to precede ID numbers with the correct amount of 0s
	private static final String ID_NUM_FORMAT = "%0" + ID_NUM_LENGTH + "d";
	
	// Possible returns when testing validity of an input
	public enum Validity {VALID, BAD_LENGTH, BAD_CHARS};
	
	static IdUI window = new IdUI();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.getInputTextField().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							String input = window.getInputTextField().getText();

							if (IdController.validateInput(input) == Validity.VALID) {
								String newId = IdController.createAndAddUserId(input);

								if (newId.equals("-1")) { // If there are too many IDs with that substring
									window.displayIdOverflowError();
									window.clearInputText();
								}
								else {
									window.displayIdAddedSuccess(newId);
									window.updateIdListDisplay(formatIdListDisplay(IdModel.getKeySetAsArray()));
									window.clearInputText();
									
								}
							}
							else {
								if (IdController.validateInput(input) == Validity.BAD_CHARS) {
									window.displayIdAllowedCharsError();
								}
								else if (IdController.validateInput(input) == Validity.BAD_LENGTH) {
									window.displayIdLengthLimitationsError(ID_SUBSTRING_SIZE, NAME_LENGTH_LIMIT);
								}
								
							}
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Validate the input string for proper length & limitations
	 * 
	 * @param Input to validate for correct length and characters
	 * @return Validity value of input. VALID for a valid string, 
	 * or BAD_LENGTH for an input shorter than the ID_SUBSTRING_SIZE,
	 * or BAD_CHARS for an empty input or one containing a non letter
	 */
	public static Validity validateInput(String input) {
		Validity validState;

		// Valid characters (includes not empty input)
		if (!input.matches("[a-zA-Z]+")) {
			validState = Validity.BAD_CHARS;
		}

		// Valid length
		else if (input.length() > NAME_LENGTH_LIMIT || input.length() < ID_SUBSTRING_SIZE) {
			validState = Validity.BAD_LENGTH;
		}
		
		else {
			validState = Validity.VALID;
		}

		return validState;
	}
	
	/**
	 * Return a valid ID for the given name, if one can be made, and place it into
	 * the idMap.
	 * 
	 * @param Name to create ID for, add to idMap, and return
	 * @return valid ID inserted into idMap or -1 if the max ID number is reached
	 */
	public static String createAndAddUserId(String name) {
		Map<String, Object> IdHashMap = IdModel.getIdHashMap();
		String idSubstring = name.substring(0, ID_SUBSTRING_SIZE).toUpperCase();
		String id = "-1";
		boolean idAdded = false;
		int x = 0;

		// Search for the next available ID number for that substring until one is added
		// or the maximum ID number is reached
		do {
			String tryId = idSubstring + String.format(ID_NUM_FORMAT, INITIAL_ID_NUM + (ID_NUM_INCREMENT * x));
			if (!IdHashMap.containsKey(tryId)) { // O(1)
				id = tryId;
				IdHashMap.put(id, null);
				idAdded = true;
			} else {
				x++;
			}

		} while (!idAdded && x < (MAX_ID_NUM / ID_NUM_INCREMENT));

		return id;
	}
	
	/** Formats the given ArrayList<String> by dividing its elements by new lines.
	 * @param idList ArrayList who's elements to divide by new lines; for the IdListDisplay
	 */
	public static String formatIdListDisplay(ArrayList<String> idList) {
//		Collections.sort(idList); // This could be used to sort for display purposes
		String idDisplayText = "";
		for (int i = 0; i < idList.size(); i++) {
			idDisplayText = idDisplayText + idList.get(i) + "\n";
		}
		return idDisplayText;
	}

}
