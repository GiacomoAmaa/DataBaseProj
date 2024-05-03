package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.ImageIcon;

import util.Pair;
import util.UserType;

public class LoginForm extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final int MAX_LENGTH = 30;
	private static final int FONT_SIZE = 36;
	private static final JPanel panel = new JPanel(new BorderLayout());
	private static final JLabel title = new JLabel("Chess Org");
	private static final JTextField username = new JTextField(LoginForm.MAX_LENGTH),
			cardNumber = new JTextField(LoginForm.MAX_LENGTH);
	private static final JComboBox<UserType> role = new JComboBox<>(UserType.values());
	private static final JButton confirmType = new JButton("OK"),
			login = new JButton("LOG IN"),
			register = new JButton("REGISTER");
	private static final JPasswordField password = new JPasswordField(LoginForm.MAX_LENGTH);
	private static final ImageIcon logo = new ImageIcon(LoginForm.class.getResource("/icons/logo.png"));
	
	public LoginForm() {
		super("Chess Organization");
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(LoginForm.panel);
		setIconImage(LoginForm.logo.getImage());
		initialize();
		setLocationRelativeTo(null);
		updateType();
		setVisible(true);
	}
	
	public void setRegisterHandler(Consumer<Map<String, String>> handler) {
		LoginForm.register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handler.accept(getUserData());
			}
			
		});
	}
	
	public void setLogInHandler(Consumer<Pair<UserType, Map<String, String>>> handler) {
		LoginForm.login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handler.accept(new Pair<UserType, Map<String, String>>((UserType)LoginForm.role.getSelectedItem(), getUserData()));
			}
			
		});
	}
	
	/**
	 * shows an error and resets fields when log in trial goes wrong
	 */
	public void Error() {
		LoginForm.username.setText("");
		LoginForm.password.setText("");
		updateType();
		JOptionPane.showMessageDialog(null, "Username or password are not valid, please try again.");
	}

	/**
	 * Sets up the frame.
	 */
	private void initialize() {
		// TODO import font for labels
		// initializing components
		LoginForm.password.setEchoChar('*');
		LoginForm.confirmType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateType();
			}
			
		});
		// initializing northern panel
		LoginForm.title.setFont(new Font("Serif", Font.BOLD, LoginForm.FONT_SIZE));
		LoginForm.title.setHorizontalAlignment(JLabel.CENTER);
		LoginForm.panel.add(wrap(List.of(LoginForm.title, new JLabel(LoginForm.logo))), BorderLayout.NORTH);
		// initializing center panel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(wrap(List.of(new JLabel("submit as\t"), role, confirmType)));
		centerPanel.add(wrap(List.of(new JLabel("username\t"), LoginForm.username)));
		centerPanel.add(wrap(List.of(new JLabel("password\t"), LoginForm.password)));
		centerPanel.add(wrap(List.of(new JLabel("card num.\t"), LoginForm.cardNumber)));
		LoginForm.panel.add(centerPanel, BorderLayout.CENTER);
		// initializing southern panel
		LoginForm.panel.add(wrap(List.of(login, register)), BorderLayout.SOUTH);
		// packing frame
		pack();
	}
	
	/** 
	 * Wraps components into panels containing them.
	 */
	private JComponent wrap(Collection<JComponent> elements) {
		var wrapper = new JPanel();
		elements.stream().forEach(e -> wrapper.add(e));
		return wrapper;
	}
	
	/**
	 * Updates the login form based on the type of user chosen.
	 */
	private void updateType() {
		var selectedType = (UserType)LoginForm.role.getSelectedItem();
		LoginForm.cardNumber.setEnabled(selectedType.equals(UserType.REFEREE));
		LoginForm.cardNumber.setText(selectedType.equals(UserType.REFEREE) ? "" : "----");
		LoginForm.register.setEnabled(selectedType.equals(UserType.PLAYER));
	}
	
	private Map<String, String> getUserData() {
		if(((UserType)LoginForm.role.getSelectedItem()).equals(UserType.REFEREE)) {
			return Map.of("username", LoginForm.username.getText(),
					"password", String.valueOf(LoginForm.password.getPassword()),
					"cardNumber", LoginForm.cardNumber.getText());
		}
		else {
			return Map.of("username", LoginForm.username.getText(),
					"password", String.valueOf(LoginForm.password.getPassword()));
		}
	}
	
}
