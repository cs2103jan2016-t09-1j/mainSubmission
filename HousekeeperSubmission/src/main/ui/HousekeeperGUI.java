package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import main.logic.Logic;
import main.logic.Task;
import main.parser.DateGUIparser;

/**
 * Create a Good GUI
 * @@author A0116137M
 */

public class HousekeeperGUI extends JLabel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String MESSAGE_WELCOME = "Welcome!";
	private static String MESSAGE_HELP = "1. add a task:\nadd [task]\nadd [task] by [deadline time]\nadd [task] from[start time] to [end time]\n\n"
    + "2. edit a task:\nedit [index] -des [description]\nedit [index] -dea [deadline]\nedit  [index] -sta [start date]\nedit  [index] -end [end date]\n\n"
    + "3. search:\nsearch [key word]\n\n" + "4. check\ncheck [index of task in the current view]\n\n"
    + "5. delete a task:\ndelete [index of task in the current view]\n\n"
    + "6. check a task:\ncheck [index of certain task]\n\n" + "7. open: open [directory]\n\n"
    + "8. save: save [directory]\n\n" + "9. other command:\nclear | displayall | undo | redo";
	private Logic logic;
    
	// GUI basic component
	private JFrame frame;
	private JLabel FBLabel;//feedback label
	private JPanel taskPanel, timePanel, floatingPanel;
	private JScrollPane scrollPane;
	private JButton undoButton,helpButton;
	
	// state flag to decide output format
	private int DCFlag = 1;//doubleColumn FLAG
	private final int NormalFlag = 1;// Normal form
	private final int SearchFlag = 2;// Search form
	private final int ShowFlag = 3;// Display form
	private final int SortFlag = 4;// Sorted form
	private int current_status = NormalFlag;// set current to normal form
    
	// constructor
	public HousekeeperGUI() throws IOException {
		logic = Logic.getInstance();
		logic.commandExecute("open");
		buildUI();
	}
    
	/**
	 * @@author A0124719A
	 */
	private void buildUI() throws IOException {
		frame = new JFrame("HouseKeeper");
		frame.setBounds(300, 100, 700, 700);
		frame.setMinimumSize(new Dimension(700, 700));
		frame.setMaximumSize(new Dimension(700, 700));
        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(buildTopPanel(), BorderLayout.NORTH);
		frame.add(buildCenterPanel(), BorderLayout.CENTER);
		frame.add(buildBottomPanel(), BorderLayout.SOUTH);
		frame.setVisible(true);
        
		// save data when click the exit button
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Logic.saveFile(Logic.getFilePath(), Logic.getFileName());
			}
		});
		
		// close the frame when the user presses escape		
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action escapeAction = new AbstractAction() {
		   
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
		        frame.dispose();
		    }
		};
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
		frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
		
		//Hides the window when the user presses F1
		KeyStroke hideKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false);
		Action hideAction = new AbstractAction() {
		    
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				frame.setState(Frame.ICONIFIED);
		    }
		};
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(hideKeyStroke, "HIDE");
		frame.getRootPane().getActionMap().put("HIDE", hideAction);
		
		//Maximise the window screen.
		KeyStroke maximiseKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false);
		Action maximiseAction = new AbstractAction() {
		    
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		    }
		};
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(maximiseKeyStroke, "MAXIMISE");
		frame.getRootPane().getActionMap().put("MAXIMISE", maximiseAction);
		
		//Restores window to normal size.
		KeyStroke normalWindowKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, false);
		Action normalWindowAction = new AbstractAction() {
		    
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.NORMAL);
		    }
		};
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(normalWindowKeyStroke, "NORMALWINDOW");
		frame.getRootPane().getActionMap().put("NORMALWINDOW", normalWindowAction);
		
		/*
		// restores the frame when the user presses F2
		KeyStroke restoreKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false);
		Action restoreAction = new AbstractAction() {
		    
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				frame.setState(Frame.NORMAL);
		    }
		};
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(restoreKeyStroke, "SHOW");
		frame.getRootPane().getActionMap().put("SHOW", restoreAction);*/
	} 
	
	/**
	 * use as a feedback section any input command will reflect in here
	 * @@author A0116137M
	 */
	private Component buildTopPanel() {
		JPanel panel = new JPanel();
		FBLabel = new JLabel(MESSAGE_WELCOME);
		FBLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		panel.add(FBLabel, BorderLayout.CENTER);
		return panel;
	}
    
	/**
	 * Display central tasks
	 * @throws IOException 
	 * @@author A0116137M
	 */
	private Component buildCenterPanel() throws IOException {
        
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		ArrayList<Task> tasks = logic.getTasks();
		taskPanel = new JPanel();
		timePanel = new JPanel();
		floatingPanel = new JPanel();
		Color background1 = new Color(34, 34, 35);
		Color background2 = new Color(0, 176, 139);
		Color background3 = new Color(34, 34, 35);
		taskPanel.setBackground(background3);
		timePanel.setBackground(background1);
		floatingPanel.setBackground(background2);
		
		//Welcome picture message when Housekeeper executes.
		URL url = HousekeeperGUI.class.getResource("/Housekeeperlogo.png");
		JLabel picLabel = new JLabel(new ImageIcon(url));
		JOptionPane.showMessageDialog(null, picLabel);
		
		
		/*//Experiment with welcome picture on panel
		ImageIcon image = newJOptionPane.showMessageDialog ImageIcon(IMG_PATH);
		JLabel labelPic = new JLabel("", image, JLabel.CENTER);
		JPanel taskPanel = new JPanel(new BorderLayout());
		taskPanel.add( labelPic, BorderLayout.CENTER );*/
		
		updateTasks(tasks);
		
		scrollPane = new JScrollPane(taskPanel, v, h);
		
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		InputMap vert = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
		scrollPane.getHorizontalScrollBar().setUnitIncrement(30);
		InputMap hori = horizontal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		//Moves the scrollbar up and down.
		vert.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
		vert.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
		
		//ActionEvent.CTRL_MASK does not work for Left and Right keyboard buttons.
		//Moves the scrollbar left and right.
		hori.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, false), "positiveUnitIncrement");
		hori.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, false), "negativeUnitIncrement");
		return scrollPane;
	}
    
	/**
	 * input commands with help and undo button.
	 * @@author A0116137M
	 */
	private Component buildBottomPanel() {
        
		JPanel bottomPanel = new JPanel();
		JLabel inputLable = new JLabel("Command: ");
		final JTextField inputBox = new JTextField("", 35);
		@SuppressWarnings("serial")
		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Task> tasks = new ArrayList<>();
				String userInput = inputBox.getText();
				// "input should not be null";
				String feedback = logic.commandExecute(userInput);
				current_status = logic.getCurrentStatus();
				DCFlag = 1;
				if (feedback.equalsIgnoreCase("exit")) {
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				} else if (feedback.equalsIgnoreCase("help")) {
					JOptionPane.showMessageDialog(frame, MESSAGE_HELP);
					if (current_status == SearchFlag) {
						tasks = logic.getTaskSearch();
						DCFlag = 0;
					}else if (current_status == ShowFlag) {
						tasks = logic.getTaskShow();
						DCFlag = 0;
					}else if (current_status == SortFlag) {
						tasks = logic.getTasksSorted();
						DCFlag = 0;
					}else{
						tasks = logic.getTasks();
					}
				} else if (feedback.equalsIgnoreCase("This task has been marked")) {
					buildDeleteDialog(userInput);
					tasks = logic.getTasks();
				} else if (current_status == SearchFlag && feedback.equalsIgnoreCase("Task found")) {
					tasks = logic.getTaskSearch();
					DCFlag = 0;
				} else if (current_status == ShowFlag && feedback.equalsIgnoreCase("Tasks shown")) {
					tasks = logic.getTaskShow();
					DCFlag = 0;
				} else if (current_status == SortFlag && feedback.equalsIgnoreCase("Tasks sorted")) {
					tasks = logic.getTasksSorted();
					System.out.println(tasks);
					DCFlag = 0;
				} else if (feedback.equalsIgnoreCase("Here are your tasks")) {
					tasks = logic.getTasks();
					Logic.taskHistory.pop();
				} else {
					tasks = logic.getTasks();
				}
				assert feedback != null : "feed back should not be null";
				assert tasks != null : "tasks arrayList should not be null";
				updateHousekeeperGUI(feedback, tasks);
				inputBox.setText("");
			}
            
			/**
			 * build a pop up dialog for user to decide delete the task after
			 * the task is been marked
			 * @@author A0116137M
			 */
			private void buildDeleteDialog(String userInput) {
				final JOptionPane optionPane = new JOptionPane("Do you want to delete the marked item?\n",
                                                               JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
				final JDialog dialog = new JDialog(frame, "Deleting", true);
				dialog.setContentPane(optionPane);
				dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				dialog.setLocation(400, 300);
				optionPane.addPropertyChangeListener(new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						String prop = e.getPropertyName();
						if (dialog.isVisible() && (e.getSource() == optionPane)
                            && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
							dialog.setVisible(false);
						}
					}
				});
				dialog.pack();
				dialog.setVisible(true);
				int value = ((Integer) optionPane.getValue()).intValue();
				if (value == JOptionPane.YES_OPTION) {
					logic.commandExecute(DateGUIparser.checkToDelete(userInput));
				} else {
					logic.commandExecute("displayall");
					Logic.taskHistory.pop();
				}
			}
		};
		inputBox.addActionListener(action);
		helpButton = new JButton ("Help");
		helpButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, MESSAGE_HELP);
			}
		});
        
		undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String feedback = logic.commandExecute("undo");
				ArrayList<Task> tasks = logic.getTasks();
				updateHousekeeperGUI(feedback, tasks);
			}
		});
		bottomPanel.setLayout(new BorderLayout());
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		p1.add(inputLable);
		p1.add(inputBox);
		p2.add(undoButton);
		p2.add(helpButton);
		bottomPanel.add(p1, BorderLayout.WEST);
		bottomPanel.add(p2, BorderLayout.EAST);
		if (Logic.taskHistory.isEmpty()) {
			undoButton.setEnabled(false);
		}
		return bottomPanel;
	}
    
	/**
	 * update GUI for every user input based on the feedback and tasks
	 *
	 * @param feedback
	 * @param tasks
	 */
	private void updateHousekeeperGUI(String feedback, ArrayList<Task> tasks) {
        
		FBLabel.setText("<html><p>" + feedback + "</p></html>");
		if (Logic.taskHistory.isEmpty()) {
			undoButton.setEnabled(false);
		} else {
			undoButton.setEnabled(true);
		}
		updateTasks(tasks);
		scrollPane.validate();
		scrollPane.repaint();
		scrollPane.updateUI();
	}
    
	public static void main(String[] args) throws IOException {
		new HousekeeperGUI();
        
	}
    
	/**
	 * update main view with the updated tasks list from logic
	 *
	 * @param tasks
	 */
	private void updateTasks(ArrayList<Task> tasks) {
		taskPanel.removeAll();
		timePanel.removeAll();
		floatingPanel.removeAll();
		int a = 0;
		if (tasks == null) {
			taskPanel.add(noTaskLabel());
		} else if (DCFlag == 1) {
			buildTwoColumnView(tasks, a);
		} else {
			buildOneColumnView(tasks);
            
		}
		taskPanel.revalidate();
		taskPanel.repaint();
	}
    
	/**
	 * for search, show and sort, it should only be displayed in one column
	 *
	 * @param tasks
	 */
	private void buildOneColumnView(ArrayList<Task> tasks) {
		taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
		if (current_status == SearchFlag) {
			for (int i = 0; i < tasks.size(); i++) {
				taskPanel.add(makeTaskLabel(i, tasks.get(i)));
				Color backgroundSearch = new Color(34, 34, 35);
				taskPanel.setBackground(backgroundSearch);
			}
		} else {
			String strTempDate = null;
			if (DateGUIparser.getDate(tasks.get(0)) != null) {
				taskPanel.add(makeDateLabel(tasks.get(0)));
				taskPanel.add(new JLabel(" "));
				strTempDate = DateGUIparser.Date(DateGUIparser.getDate(tasks.get(0)));
			}
			for (int i = 0; i < tasks.size(); i++) {
				if (strTempDate != null) {
					if (!strTempDate.equals(DateGUIparser.Date(DateGUIparser.getDate(tasks.get(i))))) {
						taskPanel.add(new JLabel(" "));
						taskPanel.add(makeDateLabel(tasks.get(i)));
						taskPanel.add(new JLabel(" "));
						strTempDate = DateGUIparser.Date(DateGUIparser.getDate(tasks.get(i)));
					}
				}
				taskPanel.add(makeTaskLabel(i, tasks.get(i)));
				Color backgroundSearch = new Color(34, 34, 35);
				taskPanel.setBackground(backgroundSearch);
			}
		}
	}
    
	/**
	 * for the rest command, it should only be displayed in two column
	 *
	 * @param tasks
	 */
	private void buildTwoColumnView(ArrayList<Task> tasks, int a) {
		taskPanel.setLayout(new GridLayout(1, 2));
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
		floatingPanel.setLayout(new BoxLayout(floatingPanel, BoxLayout.Y_AXIS));
		ArrayList<Task> timeTasks = DateGUIparser.getTimeTasks(tasks);
		ArrayList<Task> floatingTasks = DateGUIparser.getFloatingTasks(tasks);
		if (!timeTasks.isEmpty()) {
			timePanel.add(makeCategoryLabelLeft("Priority Tasks"));
			timePanel.add(new JLabel(" "));
			timePanel.add(makeDateLabel(timeTasks.get(0)));
			String strTempDate = DateGUIparser.Date(DateGUIparser.getDate(timeTasks.get(0)));
			for (int i = 0; i < timeTasks.size(); i++) {
				if (!strTempDate.equals(DateGUIparser.Date(DateGUIparser.getDate(timeTasks.get(i))))) {
					timePanel.add(new JLabel(" "));
					timePanel.add(makeDateLabel(timeTasks.get(i)));
					strTempDate = DateGUIparser.Date(DateGUIparser.getDate(timeTasks.get(i)));
				}
				timePanel.add(makeTaskLabel(a, timeTasks.get(i)));
				a++;
			}
			taskPanel.add(timePanel);
		}
		if (!floatingTasks.isEmpty()) {
			floatingPanel.add(makeCategoryLabelRight("Daily Notes"));
			floatingPanel.add(new JLabel(" "));
			for (int i = 0; i < floatingTasks.size(); i++) {
				floatingPanel.add(makeTaskLabel(a, floatingTasks.get(i)));
				floatingPanel.add(new JLabel(" "));
				a++;
			}
			taskPanel.add(floatingPanel);
		}
		// recombine 2 floating tasks and timeTasks into one ArrayList
		ArrayList<Task> recombineList = new ArrayList<Task>(timeTasks);
		recombineList.addAll(floatingTasks);
		logic.setTasks(recombineList);
	}	
    
	/**
	 *
	 *
	 * @param index
	 * @param task
	 * @return JLabel with task details
	 */
	private JLabel makeTaskLabel(int index, Task task) {
		String firstLine = "";
		String description = "<font color = 'white'>[Task&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]&nbsp;" + task.getTaskDescription();
		String done = "<font color = 'white'>[Status&nbsp;&nbsp;&nbsp;&nbsp;]&nbsp;";
		Color borderColor = null;
		if (task.getTaskDeadline() != null) {
			if (DCFlag == 0) {
				firstLine = "<font color = 'red'>[Deadline]</font>&nbsp;" + "<font color = 'red'>" + task.getTaskDeadlineInString() + "<br>";
			} else {
				String deadlineTime = DateGUIparser.Time(task.getTaskDeadline());
				firstLine = "<font color = 'red'>[Deadline]</font>&nbsp;"
                + "<font color = 'red'>" + deadlineTime + "<br>";
			}
			borderColor = Color.RED;    //Right side short color notification bar for deadline.
		} else if (task.getStartDate() != null) {
			String displayEndDate;
			if (DateGUIparser.Date(task.getStartDate())
                .equals(DateGUIparser.Date(task.getEndDate()))) {
				displayEndDate = DateGUIparser.Time(task.getEndDate());
			} else {
				displayEndDate = task.getEndDateInString();
			}
			if (DCFlag == 0) {
				firstLine = "<font color ='orange'>[Duration]</font>&nbsp;" + "<font color ='orange'>" + task.getStartDateInString() + " to "
                + "<font color ='orange'>" + task.getEndDateInString() + "<br>";
			} else {
				firstLine = "<font color ='orange'>[Duration]</font>&nbsp;"
                + "<font color ='orange'>" + DateGUIparser.Time(task.getStartDate()) + " to " + "<font color ='orange'>" + displayEndDate + "<br>";
			}
			borderColor = Color.ORANGE;
		} else {
			borderColor = Color.GRAY;
		}
		if (task.getFlag()) {
			done += "complete";
		} else {
			done += "uncompleted";
		}
		assert firstLine != null : "firstLine should not be null";
		assert description != null : "description should not be null";
		assert done != null : "done should not be null";
		JLabel temp = new JLabel("<html>" + firstLine + description + "<br>" + done + "<br>" + "</html>");
		MatteBorder matte = new MatteBorder(0, 0, 0, 4, borderColor);
		TitledBorder titled = new TitledBorder(Integer.toString(index + 1));
		titled.setTitleColor(Color.white);
		CompoundBorder compound = new CompoundBorder(titled, matte);
		temp.setBorder(compound);
		temp.setBackground(Color.white);
		return temp;
        
	}
    
	/**
	 * Label for display certain date
	 */
	private JLabel makeDateLabel(Task t) {
		Color borderColor = Color.WHITE;
		Date taskDate = DateGUIparser.getDate(t);
		String specialStr = "";
		Date today = new Date();
		Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
		if (DateGUIparser.Date(today).equals(DateGUIparser.Date(DateGUIparser.getDate(t)))) {
			specialStr += "Today: ";
			borderColor = Color.YELLOW;
		} else if (DateGUIparser.Date(tomorrow)
                   .equals(DateGUIparser.Date(DateGUIparser.getDate(t)))) {
			specialStr += "Tomorrow: ";
			borderColor = Color.MAGENTA;
		}
        
		JLabel dateLabel = new JLabel(" [  " + specialStr + DateGUIparser.Date(taskDate) + "  ] ");
		dateLabel.setForeground (Color.white);    //Sets the Label text to a specific color.
		MatteBorder matte = new MatteBorder(2, 0, 2, 0, borderColor);
		dateLabel.setBorder(matte);
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		return dateLabel;
	}
    
	/**
	 * JLabel for different category
	 * @@author A0116137M
	 */
	private JLabel makeCategoryLabelLeft(String str) {
		JLabel categoryLabel = new JLabel("                 " + str + "                 ");
		categoryLabel.setFont(new Font("Trebuchet MS", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		categoryLabel.setForeground(Color.yellow);
		MatteBorder matte = new MatteBorder(0, 2, 3, 2, Color.YELLOW);
		categoryLabel.setBorder(matte);
		return categoryLabel;
	}
	
	private JLabel makeCategoryLabelRight(String str) {
		JLabel categoryLabel = new JLabel("                 " + str + "                 ");
		categoryLabel.setFont(new Font("Trebuchet MS", Font.LAYOUT_NO_LIMIT_CONTEXT, 20));
		categoryLabel.setForeground(Color.pink);
		MatteBorder matte = new MatteBorder(0, 2, 3, 2, Color.pink);
		categoryLabel.setBorder(matte);
		return categoryLabel;
	}
    
	/**
	 * @@author A0116137M
	 */
	private JLabel noTaskLabel() {
		return new JLabel("There are currently no tasks!");
	}
    
}
