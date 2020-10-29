package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


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

public class IdUI{

	// Fields for UI elements
	private static final String FRAME_TITLE = "User ID Creator";
	private static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 12);
	private static final Font FONT = new Font("SansSerif", Font.PLAIN, 12);
	private final static Image EDMUNDS_ICON = Toolkit.getDefaultToolkit()
			.getImage(IdUI.class.getResource("/images/edmunds_icon.png"));
	private static final Color EDMUNDS_BLUE = new Color(0, 123, 255);
	private static final Color EDMUNDS_GREEN = new Color(40, 167, 69);
	
	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

	private JTextField inputTextField;
	private JTextArea idTextArea;

	public JTextField getInputTextField() {
		return inputTextField;
	}

	public JTextArea getIdTextArea() {
		return idTextArea;
	}

	public IdUI() {
		JFrame frame = new JFrame(FRAME_TITLE);
        frame.setIconImage(EDMUNDS_ICON);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initialize(frame.getContentPane());
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

	}

	public void initialize(Container mainFrame) {
		if (RIGHT_TO_LEFT) {
			mainFrame.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
		mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
        c.fill = GridBagConstraints.HORIZONTAL;
        }
        
		// Name Input Frame
		JPanel panelInputFrame = new JPanel();
		panelInputFrame.setSize(panelInputFrame.getPreferredSize());
		panelInputFrame.setBackground(EDMUNDS_GREEN);		
		
			// Label
		JLabel lblEnterName = new JLabel("Enter Name:");
		lblEnterName.setForeground(Color.WHITE);
		lblEnterName.setFont(FONT_BOLD);
		panelInputFrame.add(lblEnterName);
		
			// Text Input
		inputTextField = new JTextField(15); //15 Allows just enough room for 20 characters
		inputTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		inputTextField.setFont(FONT);
		panelInputFrame.add(inputTextField);
        
			//GridConstraints
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.ipady = 5;
		c.ipadx = 15;
		c.anchor = GridBagConstraints.NORTH;
		
		mainFrame.add(panelInputFrame,c);
		
		// ID Display Frame
		JPanel panelIdFrame = new JPanel();
		panelIdFrame.setSize(panelIdFrame.getPreferredSize());
		Border borderLine = BorderFactory.createLineBorder(EDMUNDS_GREEN, 2);
		panelIdFrame.setBorder(BorderFactory.createTitledBorder(borderLine, "Existing IDs"));
		panelIdFrame.setBackground(Color.WHITE);
		panelIdFrame.setLayout(new BorderLayout());

			// Text Output Area
		idTextArea = new JTextArea(5,25);
		idTextArea.setEnabled(false);
		idTextArea.setDisabledTextColor(Color.BLACK);
		idTextArea.setFont(FONT);
		JScrollPane scrollArea = new JScrollPane(idTextArea);
		scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollArea.setBorder(BorderFactory.createLineBorder(EDMUNDS_BLUE, 2));
		panelIdFrame.add(scrollArea, BorderLayout.CENTER);
		
			//GridConstraints
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		c.ipadx = 0;
		c.anchor = GridBagConstraints.CENTER;
		mainFrame.add(panelIdFrame, c);

	}
	
	/**Empties the current input string in the frame's inputTextDField*/
	public void clearInputText() {
		this.getInputTextField().setText("");
	}
	
	/** Updates the Text Area of the window with the given String (Display area can fit up to 28 characters per row)
	 * @param displayString to display in the Text Area window (normally the ID's)
	 */
	public void updateIdListDisplay(String displayString) {
		this.getIdTextArea().setText(displayString);
	}
	
	
	//Dialog display methods 
	
	/** Displays an error dialog that the maximum IDs with a particular substring is met */
	public void displayIdOverflowError() {
		JLabel label = new JLabel("Maximum users with that name reached.", SwingConstants.CENTER);
		JOptionPane.showMessageDialog(null, label, "Alert", JOptionPane.WARNING_MESSAGE);
	}

	/** Displays a message dialog that the user was successfully added
	 * @param newId The ID which was successfully added
	 */
	public void displayIdAddedSuccess(String newId) {
		JLabel label = new JLabel("<html><center>User Added!<br>" + newId, SwingConstants.CENTER);
		JOptionPane.showMessageDialog(null, label, "Success", JOptionPane.PLAIN_MESSAGE);
	}
	
	/** Displays a message dialog for what the allowed chars are*/
	public void displayIdAllowedCharsError() {
		JLabel label = new JLabel("Allowed characters are A-Z a-z.", SwingConstants.CENTER);
		JOptionPane.showMessageDialog(null, label, "Alert", JOptionPane.WARNING_MESSAGE);	
	}
	
	/** Displays a message dialog if the input was too short or too long
	 * @param substringSize The size of the substring for IDs and the minimum length for name inputs
	 * @param inputMaxLength The maximum size of input text allowed
	 */
	public void displayIdLengthLimitationsError(int substringSize, int nameMaxLength) {
		JLabel label = new JLabel("Name must be between " + substringSize + " and " + nameMaxLength + " characters long.");
		JOptionPane.showMessageDialog(null, label, "Alert", JOptionPane.WARNING_MESSAGE);
	}
}
