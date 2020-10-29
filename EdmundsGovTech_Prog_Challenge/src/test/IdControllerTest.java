package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import Controller.IdController;
import Controller.IdController.Validity;
import Model.IdModel;

class IdControllerTest {

	//Test for proper input validation
	@Test
	void testValidateInput() {
		assertEquals(Validity.BAD_CHARS, IdController.validateInput("Duck4"), "Numbers should not be accepted");
		assertEquals(Validity.BAD_CHARS, IdController.validateInput("\\[-|-]//"), "Alternate characters should not be accepted");
		assertEquals(Validity.BAD_CHARS, IdController.validateInput(""), "Empty input should not be accepted");
		assertEquals(Validity.BAD_LENGTH, IdController.validateInput("X"), "One character input should not be accepted");
		assertEquals(Validity.BAD_LENGTH, IdController.validateInput("AReallyLongNameThatIsGreaterThanTwentyChars"), "21+ Character inputs should not be accepted");
		assertEquals(Validity.VALID, IdController.validateInput("Michael"), "Michael should be accepted");
	}
	
	//Test for proper addition of IDs up to and when beyond maximum ID number
	@Test
	void testCreateAndAddUserId() {
		IdModel.setIdHashMap(new HashMap<String, Object>());
		String testString = "Test";
		String fooString = "Foo";
		String testAnswer;
		String fooAnswer;
		for (int i = 5; i < 1015; i=i+5) {
			testAnswer = "TES" + String.format("%03d", i);
			fooAnswer = "FOO" + String.format("%03d", i);
			if (i < 1000) {	//While below maximum ID number
				assertEquals(testAnswer, IdController.createAndAddUserId(testString), "Bad ID below maximum ID number");
				assertEquals(fooAnswer, IdController.createAndAddUserId(fooString), "Bad ID below maximum ID number");
			}
			else {	//When at or above maximum ID number
				assertEquals("-1", IdController.createAndAddUserId(testString), "Bad ID at or above maximum ID number");
				assertEquals("-1", IdController.createAndAddUserId(fooString), "Bad ID at or above maximum ID number");
			}
		}
	}
	
	//Test for proper reuse of removed IDs
	@Test
	void testRefillInput() {
		IdModel.setIdHashMap(new HashMap<String, Object>());
		String testString = "Test";
		String testAnswer = "TES005";
		IdController.createAndAddUserId(testString);	//Added TES005
		IdController.createAndAddUserId(testString);	//Added TES010
		IdModel.getIdHashMap().remove(testAnswer);		//Removed TES005
		//Should refill TES005
		assertEquals(testAnswer, IdController.createAndAddUserId(testString), "Removed ID not properly reused");
		//Next available ID should be TES015
		assertEquals("TES015", IdController.createAndAddUserId(testString), "Refilled ID, and next available ID not properly added");
	}
	
	//Test for proper formatting of list of ID elements
	@Test
	void testFormatListDisplay() {
		//There should be a new line character between each element
		String testAnswer = "TES005\nFOO005\nDUC005\nTES010\n";
		ArrayList<String> testArray = new ArrayList<String>();
		testArray.add("TES005");
		testArray.add("FOO005");
		testArray.add("DUC005");
		testArray.add("TES010");
		assertEquals(testAnswer, IdController.formatIdListDisplay(testArray), "Bad format output");
	}
}
