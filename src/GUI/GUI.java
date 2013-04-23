package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Panel;
import javax.swing.JEditorPane;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.text.BoxView;
import javax.swing.text.Element;
//import org.eclipse.ui.internal.progress.GroupInfo;
import java.awt.Toolkit;
import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;

import webserver.WebServer;
import webserver.Wrapper;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class GUI {

	private JFrame frmVvsWebServer;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private boolean stopped=true;
	private boolean maintenance=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmVvsWebServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private boolean isStopped()
	{
		if(stopped==true)
			stopped=false;
		else
			stopped=true;
		return !stopped;
	}
	
	private boolean isMaintenanced()
	{
		if(maintenance==true)
			maintenance=false;
		else
			maintenance=true;
		return !maintenance;
	}
	
	private void initialize() {
		
		final WebServer webserver=new WebServer();
		final Wrapper wp=new Wrapper(webserver);
		final Thread mytr=new Thread(wp);
		
		frmVvsWebServer = new JFrame();
		//frmVvsWebServer.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Proguri\\Eclipse\\Workspace\\Juno EE\\webserver\\arrows.png"));
		frmVvsWebServer.setTitle("VVS Web Server [Not Running]");
		frmVvsWebServer.setBounds(100, 100, 800, 400);
		frmVvsWebServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblServerStatus = new JLabel("Server status");
		
		JLabel lblNewLabel = new JLabel("Server address:");
		
		JLabel lblServerListeningPort = new JLabel("Server listening port:");
		
		final JLabel lblNewLabel_1 = new JLabel("not running");
		
		final JLabel lblNewLabel_2 = new JLabel("not running");
		
		final JLabel lblNewLabel_3 = new JLabel("not running");
		
		JLabel lblServerListeningPort_1 = new JLabel("Server listening on port");
		
		JLabel lblWebRootDirectory = new JLabel("Web root directory");
		
		JLabel lblMaintenanceDirectory = new JLabel("Maintenance directory");
		
		final JButton btnNewButton = new JButton("Start server");
		
		btnNewButton.setIcon(new ImageIcon(".\\b.png"));
		
		final JCheckBox chckbxNewCheckBox = new JCheckBox("Switch to maintenance code");
		
		chckbxNewCheckBox.setEnabled(false);
		
		final JButton btnNewButton_1 = new JButton("");
		final JButton btnNewButton_2 = new JButton("");
		
		btnNewButton_1.setIcon(new ImageIcon(".\\ok.png"));
		btnNewButton_2.setIcon(new ImageIcon(".\\ok.png"));
		
		textField = new JTextField();
		textField.setText("8080");
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("C:\\MyServer");
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setText("C:\\MyServer\\Maintenance");
		textField_2.setColumns(10);
		
		JButton button = new JButton("...");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final JFileChooser fc = new JFileChooser();
				//fc.showOpenDialog(null);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setCurrentDirectory(new java.io.File("C:\\MyServer\\"));
				//textField_1.setText(fc.getSelectedFile().getAbsolutePath());
				//fc.getCurrentDirectory()
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{ 
					String path=fc.getSelectedFile().getPath();
					textField_1.setText(path);
				}

			}
		});

		JButton button_1 = new JButton("...");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final JFileChooser fc = new JFileChooser();
				//fc.showOpenDialog(null);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setCurrentDirectory(new java.io.File("C:\\MyServer\\Maintenance"));
				//textField_1.setText(fc.getSelectedFile().getAbsolutePath());
				//fc.getCurrentDirectory()
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{ 
					//String path=fc.getCurrentDirectory().getAbsolutePath();
					String path=fc.getSelectedFile().getPath();
					textField_2.setText(path);
				}

			}
		});
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isStopped()) 
				{
					//aici e pornit, deci dau stop sa il opresc
					//aici sunt in starea running
					//mytr.stop();
					String port_nr=textField.getText();
					String root_path=textField_1.getText();
					String maintenance_path=textField_2.getText();
					
					if (!(port_nr.matches("[0-9]+") && port_nr.length() >= 2))
					{
						JOptionPane.showMessageDialog(new JFrame(), "Invalid Port", "Dialog",JOptionPane.ERROR_MESSAGE);
						isStopped();
					}
					else
					{
						File f =new File(root_path); 
						if(!f.isDirectory())
						{
							//JOptionPane.showMessageDialog(new JFrame(), "Invalid root path", "Dialog",JOptionPane.ERROR_MESSAGE);
							btnNewButton_1.setIcon(new ImageIcon(".\\x.png"));
							isStopped();
						}
						else
						{
							File f2=new File(maintenance_path);
							FilenameFilter filter = new FilenameFilter() {
								public boolean accept(File dir, String name) {
									return (name.endsWith(".html") || name.endsWith(".htm") );
								}
							};
							File[] files=f2.listFiles(filter);
							System.out.println(maintenance_path);
							System.out.println(files.length);
							if(files.length==0)
							{
								//JOptionPane.showMessageDialog(new JFrame(), "Invalid maintenance path", "Dialog",JOptionPane.ERROR_MESSAGE);
								btnNewButton_2.setIcon(new ImageIcon(".\\x.png"));
								isStopped();
							}
							else
							{
								//aici sunt in starea running
								WebServer.setPort(textField.getText());
								
								
								btnNewButton.setIcon(new ImageIcon(".\\arrows.png"));
								btnNewButton.setText("Stop server");
								
								lblNewLabel_1.setText("running...");
								lblNewLabel_2.setText(WebServer.getServerAddr());
								lblNewLabel_3.setText(WebServer.getPort());
								
								textField.setEditable(false);
								textField_1.setEditable(false);
								
								frmVvsWebServer.setTitle("VVS Web Server [Running]");
								chckbxNewCheckBox.setEnabled(true);
								chckbxNewCheckBox.setSelected(false);
								
								btnNewButton_1.setIcon(new ImageIcon(".\\ok.png"));
								btnNewButton_2.setIcon(new ImageIcon(".\\ok.png"));
								
								mytr.start();
								
								chckbxNewCheckBox.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent e) {
									if(!isMaintenanced())
									{
										frmVvsWebServer.setTitle("VVS Web Server [Maintenance]");
										lblNewLabel_1.setText("maintenance");
										
										webserver.setMaintenanced(true);
										
										textField.setEditable(false);
										textField_1.setEditable(true);
										textField_2.setEditable(false);
										
									}
									else
									{
										webserver.setMaintenanced(false);
										lblNewLabel_1.setText("running...");
										textField.setEditable(false);
										textField_1.setEditable(false);
										textField_2.setEditable(true);
										frmVvsWebServer.setTitle("VVS Web Server [Running]");
									}
								}
								});
								
							}
						}
					}

				}
				else
				{ 
					//aici e oprit,deci dau start sa il pornesc
					//aici sunt in starea stopped
	
					btnNewButton.setIcon(new ImageIcon(".\\b.png"));
					btnNewButton.setText("Start server");
					
					lblNewLabel_1.setText("not running");
					lblNewLabel_2.setText("not running");
					lblNewLabel_3.setText("not running");
					
					textField.setEditable(true);
					textField_1.setEditable(true);
					textField_2.setEditable(true);
					
					frmVvsWebServer.setTitle("VVS Web Server [Not Running]");
					chckbxNewCheckBox.setEnabled(false);
					chckbxNewCheckBox.setSelected(false);
					
					mytr.interrupt();	
					
					chckbxNewCheckBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						mytr.start();
						if(!isMaintenanced())
						{
							frmVvsWebServer.setTitle("VVS Web Server [Maintenance]");
							lblNewLabel_1.setText("maintenance");
							
							textField.setEditable(false);
							textField_1.setEditable(true);
							textField_2.setEditable(false);
							webserver.setMaintenanced(true);
							
						}
						else
						{
							lblNewLabel_1.setText("running...");
							textField.setEditable(false);
							textField_1.setEditable(false);
							textField_2.setEditable(true);
							frmVvsWebServer.setTitle("VVS Web Server [Running]");
							webserver.setMaintenanced(false);
						}
					}
					});
					
				}
			}
		});


		GroupLayout groupLayout = new GroupLayout(frmVvsWebServer.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMaintenanceDirectory)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblServerStatus)
								.addComponent(lblWebRootDirectory)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNewLabel)
									.addComponent(lblServerListeningPort)
									.addComponent(lblServerListeningPort_1, Alignment.TRAILING)))
							.addGap(14)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblNewLabel_3)
								.addComponent(lblNewLabel_2)
								.addComponent(lblNewLabel_1)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
								.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
								.addComponent(textField_1))))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(31)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.addComponent(chckbxNewCheckBox)))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(button, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnNewButton_2, 0, 0, Short.MAX_VALUE)))))
					.addGap(91))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblServerStatus)
								.addComponent(lblNewLabel_1))
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_2))
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblServerListeningPort)
								.addComponent(lblNewLabel_3))
							.addGap(55)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblServerListeningPort_1)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWebRootDirectory)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(button)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxNewCheckBox)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnNewButton_1)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaintenanceDirectory)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_1)
						.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		frmVvsWebServer.getContentPane().setLayout(groupLayout);
	}
}
