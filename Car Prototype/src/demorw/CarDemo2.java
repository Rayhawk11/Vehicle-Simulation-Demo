package demorw;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CarDemo2 extends JFrame {

	private JPanel contentPane;
	private JTextField accelField;
	private JTextField brkField;
	private JTextField initVelField;
	private JTextField maxForSpField;
	private JTextField maxRevSpField;
	private JLabel txtReverseAccelerationms;
	private JTextField textField;
	private JPanel panel;
	private JButton accelerateBtn;
	private JButton brkRevBtn;
	private JButton pauseBtn;
	private JButton resetBtn;
	private JPanel panel_1;
	private JLabel lblCurrentVelocity;
	private JTextField txtMs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CarDemo2 frame = new CarDemo2();
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
	public CarDemo2() {
		setTitle("Car Demo: Revamped");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 443, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 221, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblAcceleration = new JLabel("Acceleration (m/s^2)");
		lblAcceleration.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblAcceleration = new GridBagConstraints();
		gbc_lblAcceleration.anchor = GridBagConstraints.WEST;
		gbc_lblAcceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcceleration.gridx = 0;
		gbc_lblAcceleration.gridy = 0;
		contentPane.add(lblAcceleration, gbc_lblAcceleration);

		accelField = new JTextField();
		accelField.setText("5");
		GridBagConstraints gbc_accelField = new GridBagConstraints();
		gbc_accelField.insets = new Insets(0, 0, 5, 0);
		gbc_accelField.fill = GridBagConstraints.HORIZONTAL;
		gbc_accelField.gridx = 1;
		gbc_accelField.gridy = 0;
		contentPane.add(accelField, gbc_accelField);
		accelField.setColumns(10);

		JLabel lblBrakingDeceleration = new JLabel("Braking Magnitude (m/s^2)");
		lblBrakingDeceleration.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblBrakingDeceleration = new GridBagConstraints();
		gbc_lblBrakingDeceleration.anchor = GridBagConstraints.WEST;
		gbc_lblBrakingDeceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblBrakingDeceleration.gridx = 0;
		gbc_lblBrakingDeceleration.gridy = 1;
		contentPane.add(lblBrakingDeceleration, gbc_lblBrakingDeceleration);

		brkField = new JTextField();
		brkField.setText("10");
		GridBagConstraints gbc_brkField = new GridBagConstraints();
		gbc_brkField.anchor = GridBagConstraints.SOUTH;
		gbc_brkField.insets = new Insets(0, 0, 5, 0);
		gbc_brkField.fill = GridBagConstraints.HORIZONTAL;
		gbc_brkField.gridx = 1;
		gbc_brkField.gridy = 1;
		contentPane.add(brkField, gbc_brkField);
		brkField.setColumns(10);

		txtReverseAccelerationms = new JLabel();
		txtReverseAccelerationms.setText("Reverse Acceleration Magnitude (m/s^2)");
		GridBagConstraints gbc_txtReverseAccelerationms = new GridBagConstraints();
		gbc_txtReverseAccelerationms.anchor = GridBagConstraints.WEST;
		gbc_txtReverseAccelerationms.insets = new Insets(0, 0, 5, 5);
		gbc_txtReverseAccelerationms.gridx = 0;
		gbc_txtReverseAccelerationms.gridy = 2;
		contentPane.add(txtReverseAccelerationms, gbc_txtReverseAccelerationms);

		textField = new JTextField();
		textField.setText("2");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);

		JLabel lblInitialVelocityms = new JLabel("Initial Velocity (m/s)");
		lblInitialVelocityms.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblInitialVelocityms = new GridBagConstraints();
		gbc_lblInitialVelocityms.anchor = GridBagConstraints.WEST;
		gbc_lblInitialVelocityms.insets = new Insets(0, 0, 5, 5);
		gbc_lblInitialVelocityms.gridx = 0;
		gbc_lblInitialVelocityms.gridy = 3;
		contentPane.add(lblInitialVelocityms, gbc_lblInitialVelocityms);

		initVelField = new JTextField();
		initVelField.setText("0");
		GridBagConstraints gbc_initVelField = new GridBagConstraints();
		gbc_initVelField.insets = new Insets(0, 0, 5, 0);
		gbc_initVelField.fill = GridBagConstraints.HORIZONTAL;
		gbc_initVelField.gridx = 1;
		gbc_initVelField.gridy = 3;
		contentPane.add(initVelField, gbc_initVelField);
		initVelField.setColumns(10);

		JLabel lblMaximumForwardSpeed = new JLabel("Maximum Forward Speed (m/s)");
		lblMaximumForwardSpeed.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblMaximumForwardSpeed = new GridBagConstraints();
		gbc_lblMaximumForwardSpeed.anchor = GridBagConstraints.WEST;
		gbc_lblMaximumForwardSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximumForwardSpeed.gridx = 0;
		gbc_lblMaximumForwardSpeed.gridy = 4;
		contentPane.add(lblMaximumForwardSpeed, gbc_lblMaximumForwardSpeed);

		maxForSpField = new JTextField();
		maxForSpField.setText("90");
		GridBagConstraints gbc_maxForSpField = new GridBagConstraints();
		gbc_maxForSpField.insets = new Insets(0, 0, 5, 0);
		gbc_maxForSpField.fill = GridBagConstraints.HORIZONTAL;
		gbc_maxForSpField.gridx = 1;
		gbc_maxForSpField.gridy = 4;
		contentPane.add(maxForSpField, gbc_maxForSpField);
		maxForSpField.setColumns(10);

		JLabel lblMaximumReverseSpeed = new JLabel("Maximum Reverse Speed (m/s)");
		lblMaximumReverseSpeed.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblMaximumReverseSpeed = new GridBagConstraints();
		gbc_lblMaximumReverseSpeed.anchor = GridBagConstraints.WEST;
		gbc_lblMaximumReverseSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximumReverseSpeed.gridx = 0;
		gbc_lblMaximumReverseSpeed.gridy = 5;
		contentPane.add(lblMaximumReverseSpeed, gbc_lblMaximumReverseSpeed);

		maxRevSpField = new JTextField();
		maxRevSpField.setText("27");
		GridBagConstraints gbc_maxRevSpField = new GridBagConstraints();
		gbc_maxRevSpField.insets = new Insets(0, 0, 5, 0);
		gbc_maxRevSpField.fill = GridBagConstraints.HORIZONTAL;
		gbc_maxRevSpField.gridx = 1;
		gbc_maxRevSpField.gridy = 5;
		contentPane.add(maxRevSpField, gbc_maxRevSpField);
		maxRevSpField.setColumns(10);
		
		lblCurrentVelocity = new JLabel("Current Velocity");
		GridBagConstraints gbc_lblCurrentVelocity = new GridBagConstraints();
		gbc_lblCurrentVelocity.anchor = GridBagConstraints.WEST;
		gbc_lblCurrentVelocity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentVelocity.gridx = 0;
		gbc_lblCurrentVelocity.gridy = 6;
		contentPane.add(lblCurrentVelocity, gbc_lblCurrentVelocity);
		
		txtMs = new JTextField();
		txtMs.setEditable(false);
		txtMs.setText("0 m/s");
		txtMs.setColumns(10);
		GridBagConstraints gbc_txtMs = new GridBagConstraints();
		gbc_txtMs.insets = new Insets(0, 0, 5, 0);
		gbc_txtMs.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMs.gridx = 1;
		gbc_txtMs.gridy = 6;
		contentPane.add(txtMs, gbc_txtMs);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 2;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 7;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		accelerateBtn = new JButton("Accelerate");
		accelerateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_accelerateBtn = new GridBagConstraints();
		gbc_accelerateBtn.insets = new Insets(0, 0, 0, 5);
		gbc_accelerateBtn.gridx = 0;
		gbc_accelerateBtn.gridy = 0;
		panel.add(accelerateBtn, gbc_accelerateBtn);

		brkRevBtn = new JButton("Brake/Reverse");
		GridBagConstraints gbc_brkRevBtn = new GridBagConstraints();
		gbc_brkRevBtn.insets = new Insets(0, 0, 0, 5);
		gbc_brkRevBtn.gridx = 1;
		gbc_brkRevBtn.gridy = 0;
		panel.add(brkRevBtn, gbc_brkRevBtn);

		pauseBtn = new JButton("Pause/Resume");
		GridBagConstraints gbc_pauseBtn = new GridBagConstraints();
		gbc_pauseBtn.insets = new Insets(0, 0, 0, 5);
		gbc_pauseBtn.gridx = 2;
		gbc_pauseBtn.gridy = 0;
		panel.add(pauseBtn, gbc_pauseBtn);

		resetBtn = new JButton("Reset");
		GridBagConstraints gbc_resetBtn = new GridBagConstraints();
		gbc_resetBtn.gridx = 3;
		gbc_resetBtn.gridy = 0;
		panel.add(resetBtn, gbc_resetBtn);

		panel_1 = new JPanel() {
			@Override
			public void paint(Graphics g) {

				panel_1.removeAll();
				Graphics2D g2d = (Graphics2D)g;
				//g2d.drawOval((int) Math.round((lastpos/1000)*((double) panel.getWidth()-13)), 5, 12, 12);
				g2d.drawLine(0, 0, panel.getWidth(), 0);
				g2d.drawLine(0, 22, panel.getWidth(), 22);
			
			}
		};
		panel_1.repaint();
		repaint();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 8;
		contentPane.add(panel_1, gbc_panel_1);
	}
	public static void drawCircle(Graphics g, int x, int y, int radius) {

		  int diameter = radius * 2;

		  //shift x and y by the radius of the circle in order to correctly center it
		  g.drawOval(x - radius, y - radius, diameter, diameter); 
	}
	public void centerString(Graphics g, Rectangle r, String s, 
	        Font font) {
	    FontRenderContext frc = 
	            new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rX = (int) Math.round(r2D.getX());
	    int rY = (int) Math.round(r2D.getY());

	    int a = (r.width / 2) - (rWidth / 2) - rX;
	    int b = (r.height / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, r.x + a, r.y + b);
	}
}
