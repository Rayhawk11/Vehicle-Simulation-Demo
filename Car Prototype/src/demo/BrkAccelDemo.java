package demo;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ProgressBarUI;

import java.awt.GridBagLayout;
import javax.swing.JProgressBar;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;

public class BrkAccelDemo extends JFrame {
	private int tick = 5;
	private JPanel contentPane;
	private JTextField textField;
	private JProgressBar progressBar;
	private boolean running = false;
	private Timer timer;
	private JButton btnReset;
	private double accel = 0;
	private int time = 0;
	private static double initvel = 0;
	private boolean stopped = false;
	private double lastpos = 0;
	private double maxvel = 100;
	private JTextField textField_1;
	private JLabel lblAccelms;
	private JLabel lblMaxVelms;
	private JTextField textField_2;
	private JLabel lblInitVelms;
	private JMenuBar menuBar;
	private JMenu mnDebug;
	private JMenuItem mntmSetTickRate;
	private JCheckBoxMenuItem chckbxmntmStopSimulationAt;
	private JMenu mnHelp;
	private JMenuItem mntmDisplayHelpDialog;
	private JLabel lblCurrentVel;
	private JTextField textField_3;
	private double vel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length > 0)
			initvel = Double.parseDouble(args[0]);
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
		setBounds(100, 100, 450, 230);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnDebug = new JMenu("Debug");
		menuBar.add(mnDebug);

		mntmSetTickRate = new JMenuItem("Set Tick Rate");
		mntmSetTickRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = (String) JOptionPane.showInputDialog(null, "Set tick rate", "Tick Rate",
						JOptionPane.PLAIN_MESSAGE, null, null, tick);
				tick = (int) Math.round(Double.parseDouble(s));
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
			}
		});
		mnHelp.add(mntmDisplayHelpDialog);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 214, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
				timer.stop();
				progressBar.setValue(0);
				progressBar.setString("0m");
				time = 0;
				lastpos = 0;
				stopped = false;
			}
		});

		JButton btnStartstop = new JButton("Start/Stop");
		btnStartstop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Button received");

				if (running) {
					System.out.println("Stopping");
					timer.stop();
					running = false;
					return;
				}
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

		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.weightx = 12.0;
		gbc_progressBar.gridwidth = 3;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 4;
		contentPane.add(progressBar, gbc_progressBar);
		progressBar.setStringPainted(true);
		progressBar.setString("0m");
		progressBar.setMaximum(1000);
		
		lblCurrentVel = new JLabel("Current Vel");
		GridBagConstraints gbc_lblCurrentVel = new GridBagConstraints();
		gbc_lblCurrentVel.anchor = GridBagConstraints.EAST;
		gbc_lblCurrentVel.insets = new Insets(0, 0, 0, 5);
		gbc_lblCurrentVel.gridx = 0;
		gbc_lblCurrentVel.gridy = 5;
		contentPane.add(lblCurrentVel, gbc_lblCurrentVel);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 0, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 5;
		contentPane.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	private double position(int t) {
		vel=(initvel) + (accel * ((double) t / 1000));
		if (vel >= maxvel) {
			textField_3.setText(String.valueOf(maxvel));
			System.out.println("Max vel reached: " + time);
			return lastpos + (maxvel * (double) timer.getDelay() / 1000);
		}
		double i = (0.5 * (double) accel * (Math.pow(((double) t) / 1000, 2))) + (initvel * t / 1000);
		System.out.println(i);
		return i;
	}

	class MyTimerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			time = time + timer.getDelay();
			double pos = position(time);
			if (lastpos > pos && chckbxmntmStopSimulationAt.isSelected()) {
				timer.stop();
				stopped = false;
				running = false;

				lastpos = 0;

				JOptionPane.showMessageDialog(null,
						"Done!  Time taken: " + ((double) time / 1000 - ((double) timer.getDelay() / 1000)) + "s");
				time = 0;
			}
			progressBar.setValue((int) Math.round(pos));
			progressBar.setString(String.valueOf(progressBar.getValue()) + "m");
			lastpos = pos;
			if (progressBar.getValue() == 1000) {
				timer.stop();
				stopped = false;
				running = false;
				lastpos = 0;
				JOptionPane.showMessageDialog(null,
						"Done!  Time taken: " + ((double) time / 1000 - ((double) timer.getDelay() / 1000)) + "s");
				time = 0;
			}
		}
	}
}
