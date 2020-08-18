/*
 * Michael Palermo
 * Object Oriented Programming
 * 11/12/2018
 * 
 * This program will create a user interface that will enable the user to press keys on a keyboard
to compose messages.
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// We just need one class plus the public class to implement this design.
// No model classes necessary.
class TextPadFrame extends JFrame implements ActionListener{
	private JTextField tField;
	
	public TextPadFrame() {
		configureUI();
	}
	public void configureUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100,100,500,450);
		setTitle("TextPad v0.1");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		JPanel panMid = new JPanel();
		panMid.setLayout(new GridLayout(5,6));
		JButton[] btns = new JButton[30]; // creating an array to hold 30 JButtons.
		// filling the array with JButtons, add each one to the middle panel variable, and add action listeners to each.
		for (int i = 0; i<26; i++) {
			btns[i] = new JButton(""+(char)(65+i));
			panMid.add(btns[i]);
			btns[i].addActionListener(this);
		}
		// adding the more complicated buttons with their individual action listeners.
		JButton period = new JButton(".");
		period.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tField.setText(tField.getText()+".");
						Font f = new Font(null,Font.BOLD,24);	// setting the font larger.
						tField.setFont(f);
					}
				}
				);
		JButton exclamation = new JButton("!");
		exclamation.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tField.setText(tField.getText()+"!");
						Font f = new Font(null,Font.BOLD,24);
						tField.setFont(f);
					}
				}
				);
		JButton space = new JButton("SP");
		space.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tField.setText(tField.getText()+" ");
					}
				}
				);
		JButton enter = new JButton("ENT");
		enter.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(null,
								String.format("You typed \"%s\"", tField.getText()));
					}
				}
				);
		panMid.add(period);
		panMid.add(exclamation);
		panMid.add(space);
		panMid.add(enter);
		c.add(panMid,BorderLayout.CENTER);
		tField = new JTextField("");
		Dimension dim1 = new Dimension(100,50);	//setting the dimensions to a size we want.
		tField.setPreferredSize(dim1);
		c.add(tField,BorderLayout.NORTH);
		JButton clear = new JButton("Clear");
		Dimension dim2 = new Dimension(50,35);
		clear.setPreferredSize(dim2);
		clear.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tField.setText("");
					}
				}
				);
		c.add(clear,BorderLayout.SOUTH);
	}
	// the action listener to handle the GridLayout JButtons.
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)(e.getSource());
		String btnText = source.getText();
		String prevText = tField.getText();
		tField.setText(prevText+btnText);
		Font f = new Font(null,Font.BOLD,24);
		tField.setFont(f);
		repaint();
		
	}
}
public class PalermoTextPad {

	public static void main(String[] args) {
		TextPadFrame frm = new TextPadFrame();
		frm.setVisible(true);

	}

}
