package demo;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JProgressBar;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class BrkAccelDemo extends JFrame {
	private int tick = 5; // Milliseconds between each simulation update
	private JPanel contentPane; // GUI Panel
	private JTextField textField; // Acceleration input
	private JProgressBar progressBar; // Progress bar of position
	private boolean running = false; // Used to determine whether start/stop
										// should reset or start a new
										// simulation
	private Timer timer; // The timer which dictates simulation speed
	private JButton btnReset; // Reset button
	private double accel = 0; // Value of acceleration (m/s^2)
	private double time = 0; // Value of time (ms)
	private double initvel = 0; // Initial velocity (m/s)
	private double lastpos = 0; // Position during last tick (m)
	private double maxvel = 100; // Max velocity (m/s)
	private JTextField textField_1; // Max velocity entry
	private JLabel lblAccelms; // Text on the GUI
	private JLabel lblMaxVelms; // Text on the GUI
	private JTextField textField_2; // Initial velocity field
	private JLabel lblInitVelms; // Text on the GUI
	private JMenuBar menuBar; // Menu bar
	private JMenu mnDebug; // Debug menu
	private JMenuItem mntmSetTickRate; // Option to set tick rate
	private JCheckBoxMenuItem chckbxmntmStopSimulationAt; // Option to disable
															// autostop
	private JMenu mnHelp; // Help menu
	private JMenuItem mntmDisplayHelpDialog; // Opens the help dialog
	private JLabel lblCurrentVel; // Text on the GUI
	private JTextField textField_3; // Displays current velocity
	private double vel; // Value of current velocity
	private boolean max = false; // Whether or not the vehicle is at max speed
	private JLabel lblTime; // Text on the GUI
	private JTextField textField_4; // Displays current simulation time
	private JPanel panel;
	private JSpinner spinner;
	private JLabel lblSelectedCar;
	private JButton btnSubmitCarSettings;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrkAccelDemo frame = new BrkAccelDemo();
					frame.setOpacity(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BrkAccelDemo() {
		setTitle("Acceleration Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 489, 319);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnDebug = new JMenu("Debug");
		menuBar.add(mnDebug);

		mntmSetTickRate = new JMenuItem("Set Tick Rate");
		mntmSetTickRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = (String) JOptionPane.showInputDialog(null, "Set tick rate", "Tick Rate",
						JOptionPane.PLAIN_MESSAGE, null, null, tick);
				tick = (int) Math.round(Double.parseDouble(s)); // Parse user
																// input and set
																// as tick rate
			}
		});
		mnDebug.add(mntmSetTickRate);

		chckbxmntmStopSimulationAt = new JCheckBoxMenuItem("Stop Simulation at 0 vel");
		chckbxmntmStopSimulationAt.setSelected(true);
		mnDebug.add(chckbxmntmStopSimulationAt);

		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		mntmDisplayHelpDialog = new JMenuItem("Display Help Dialog");
		mntmDisplayHelpDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"Input values into text boxes and then hit start.  Pressing stop while the simulation is running causes it to pause.\nUse the debug menu to set tick rate (ms between each calculation) and set if the simulation will stop at V=0.\nFailure to enter values results in a silent error.");
				// Help dialog
			}

		});
		mnHelp.add(mntmDisplayHelpDialog);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 214, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		lblInitVelms = new JLabel("Init Vel (m/s)");
		GridBagConstraints gbc_lblInitVelms = new GridBagConstraints();
		gbc_lblInitVelms.insets = new Insets(0, 0, 5, 5);
		gbc_lblInitVelms.anchor = GridBagConstraints.EAST;
		gbc_lblInitVelms.gridx = 0;
		gbc_lblInitVelms.gridy = 0;
		contentPane.add(lblInitVelms, gbc_lblInitVelms);

		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 2;
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 0;
		contentPane.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);

		lblAccelms = new JLabel("Accel (m/s)");
		GridBagConstraints gbc_lblAccelms = new GridBagConstraints();
		gbc_lblAccelms.insets = new Insets(0, 0, 5, 5);
		gbc_lblAccelms.anchor = GridBagConstraints.EAST;
		gbc_lblAccelms.gridx = 0;
		gbc_lblAccelms.gridy = 1;
		contentPane.add(lblAccelms, gbc_lblAccelms);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);

		lblMaxVelms = new JLabel("Max vel (m/s)");
		GridBagConstraints gbc_lblMaxVelms = new GridBagConstraints();
		gbc_lblMaxVelms.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxVelms.anchor = GridBagConstraints.EAST;
		gbc_lblMaxVelms.gridx = 0;
		gbc_lblMaxVelms.gridy = 2;
		contentPane.add(lblMaxVelms, gbc_lblMaxVelms);

		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		contentPane.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		btnReset = new JButton("Reset");
		btnReset.setHorizontalAlignment(SwingConstants.RIGHT);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				timer.stop(); // Stop the simulation
				progressBar.setValue(0); // Reset progress bar
				progressBar.setString("0m");
				time = 0; // Set time to 0
				lastpos = 0; // Reset last position to 0
				max = false; // Reset max speed indicator
			}
		});

		JButton btnStartstop = new JButton("Start/Stop");
		btnStartstop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			

				if (running) {
					
					timer.stop();
					running = false;
					// Stop/pause the simulation without resetting it
					return;
				} else {
					running = true;
					String s = textField_1.getText();
					if (s.equalsIgnoreCase(""))
						maxvel = Double.MAX_VALUE;
					else
						maxvel = Double.parseDouble(textField_1.getText());
					accel = Double.parseDouble(textField.getText());
					initvel = Double.parseDouble(textField_2.getText());
					timer = new Timer(tick, new MyTimerActionListener());
					timer.start();
					// Parse values and begin simulation if it is not running
				}

			}

		});
		GridBagConstraints gbc_btnStartstop = new GridBagConstraints();
		gbc_btnStartstop.anchor = GridBagConstraints.WEST;
		gbc_btnStartstop.insets = new Insets(0, 0, 5, 5);
		gbc_btnStartstop.gridx = 0;
		gbc_btnStartstop.gridy = 3;
		contentPane.add(btnStartstop, gbc_btnStartstop);
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.anchor = GridBagConstraints.EAST;
		gbc_btnReset.insets = new Insets(0, 0, 5, 0);
		gbc_btnReset.gridx = 2;
		gbc_btnReset.gridy = 3;
		contentPane.add(btnReset, gbc_btnReset);
		
		lblSelectedCar = new JLabel("Selected Car");
		GridBagConstraints gbc_lblSelectedCar = new GridBagConstraints();
		gbc_lblSelectedCar.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectedCar.gridx = 0;
		gbc_lblSelectedCar.gridy = 4;
		contentPane.add(lblSelectedCar, gbc_lblSelectedCar);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 4;
		contentPane.add(spinner, gbc_spinner);
		
		btnSubmitCarSettings = new JButton("Submit Car Settings");
		GridBagConstraints gbc_btnSubmitCarSettings = new GridBagConstraints();
		gbc_btnSubmitCarSettings.insets = new Insets(0, 0, 5, 0);
		gbc_btnSubmitCarSettings.gridx = 2;
		gbc_btnSubmitCarSettings.gridy = 4;
		contentPane.add(btnSubmitCarSettings, gbc_btnSubmitCarSettings);

		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.weightx = 12.0;
		gbc_progressBar.gridwidth = 3;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 5;
		contentPane.add(progressBar, gbc_progressBar);
		progressBar.setStringPainted(true);
		progressBar.setString("0m");
		progressBar.setMaximum(1000);

		lblCurrentVel = new JLabel("Current Vel");
		GridBagConstraints gbc_lblCurrentVel = new GridBagConstraints();
		gbc_lblCurrentVel.anchor = GridBagConstraints.EAST;
		gbc_lblCurrentVel.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentVel.gridx = 0;
		gbc_lblCurrentVel.gridy = 6;
		contentPane.add(lblCurrentVel, gbc_lblCurrentVel);

		textField_3 = new JTextField();
		textField_3.setEditable(false);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 6;
		contentPane.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);

		lblTime = new JLabel("Time");
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.anchor = GridBagConstraints.EAST;
		gbc_lblTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblTime.gridx = 0;
		gbc_lblTime.gridy = 7;
		contentPane.add(lblTime, gbc_lblTime);

		textField_4 = new JTextField();
		textField_4.setEditable(false);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 7;
		contentPane.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);

		panel = new JPanel() {
			@Override public void paint(Graphics g) {
				panel.removeAll();
				Graphics2D g2d = (Graphics2D)g;
				g2d.drawOval((int) Math.round((lastpos/1000)*((double) panel.getWidth()-13)), 5, 12, 12);
				g2d.drawLine(0, 0, panel.getWidth(), 0);
				g2d.drawLine(0, 22, panel.getWidth(), 22);
			}
		};
		contentPane.setOpaque(true);
		
		panel.setOpaque(true);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 8;
		contentPane.add(panel, gbc_panel);
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	private double position(double t) {
		vel = velocity(t / 1000);
		if (!max) { // If vehicle is not at max speed...
			if (vel >= maxvel) { // ..check if it is
				textField_3.setText(String.valueOf(maxvel));
				max = true;
				return lastpos + (maxvel * (double) timer.getDelay() / 1000);

			} else { // If it isn't, then return as usual
				double i = (0.5 * (double) accel * (Math.pow(((double) t) / 1000, 2))) + (initvel * t / 1000);
				return i;
			}
		} else { // If vehicle IS at max speed...
			textField_3.setText(String.valueOf(maxvel));
			max = true;
			return lastpos + (maxvel * (double) timer.getDelay() / 1000);
			// ...then return the previous position + the max velocity * time
		}

	}

	private double velocity(double t) {
		return initvel + (accel * t);
		// Return velocity
	}

	class MyTimerActionListener implements ActionListener { // Simulation timer
		public void actionPerformed(ActionEvent e) {
			time = time + timer.getDelay(); // Increment time by the tick rate
			double pos = position(time); // Get current position of the object
			if (lastpos > pos && chckbxmntmStopSimulationAt.isSelected()) { // If
																			// vehicle
																			// is
																			// in
																			// reverse
																			// and
																			// the
																			// setting
																			// says
																			// to
																			// stop...
				timer.stop(); // Stop simulation
				running = false;

				lastpos = 0; // Reset last position

				JOptionPane.showMessageDialog(null,
						"Done!  Time taken: " + ((double) time / 1000 - ((double) timer.getDelay() / 1000)) + "s");
				// Show time taken to stop vehicle
				time = 0; // Reset time
				max = false; // Reset condition of max speed
			} else if (progressBar.getValue() == 1000) { // If course is
															// complete...
				timer.stop(); // Stop simulation
				running = false;
				lastpos = 0; // Reset last position
				JOptionPane.showMessageDialog(null,
						"Done!  Time taken: " + ((double) time / 1000 - ((double) timer.getDelay() / 1000)) + "s");
				// Show time to complete
				time = 0;
				max = false;
				return;
			}
			DecimalFormat df = new DecimalFormat("#.###");
			df.setMinimumFractionDigits(3);
			df.setMaximumFractionDigits(3);
			df.setRoundingMode(RoundingMode.HALF_UP);
			if (!max) {
				// Display velocity if not maxed
				textField_3.setText(String.valueOf(df.format(velocity(time / 1000))) + " m/s");
			} else
				textField_3.setText(maxvel + " m/s"); // Display velocity if
														// maxed
			textField_4.setText(df.format(time / 1000) + "s"); // Display time
			progressBar.setValue((int) Math.round(pos)); // Update position
															// display
			progressBar.setString(String.valueOf(progressBar.getValue()) + "m");
			lastpos = pos; // Update last position value
			panel.repaint();
			repaint();
			System.out.println(time+"  "+String.valueOf(lastpos));
		}
	}
}
