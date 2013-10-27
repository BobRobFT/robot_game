/**
Program: Assignment 2: Robot Bomb Clearance Application
Filename: CBombRobot.java
@author: © Bob Roberts (12407404)
Course: BSc/HND Computing Year 1
Module: CSY1020 Problem Solving & Programming
Tutor: Gary Hill
@version: 0.7.6
Last update: 27/10/13
*/

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CBombRobot extends JFrame implements ActionListener, ChangeListener, KeyListener {
	private JPanel panel, panela, panelOption, panelb, panelRight, panelaaaa, panelImage; //all of the panels for the gui
	private JTextField textFieldOption, textFieldSquare, textFieldDirection;
	private JButton button1, button2, button3, button4, buttonRun, buttonAct, buttonRestart, buttonOption1, buttonOption2, buttonOption3, buttonExit, buttonImage;
	JMenuBar topMenuBar;          //declare Menu Bar
	JMenu fileMenu, editMenu, searchMenu, helpMenu, scenario;  //declare sub-Menus
	JMenuItem exitItem, fontItem, foreColor, backColor, helpItem, aboutItem, scenario1, scenario2, scenario3, mainEdit, partyMode, controls, controlsEdit, controlsInfo;
    private javax.swing.Timer timer; //creates the timer so robot can move on its own
    private static JSlider slider; //slider is to control the speed for the robot
    private JTextField gapField; //gapField contains the value of the slider
    private JLabel gapLabel; //gapLabel contains text saying "speed"

	static int intRobotLocation; //moving green around
    static int intRobotArrayLocation; //putting color back into place 
    static int intRobotDirection = 2; //check what way the robot is facing 
    static int intBombLocation; // where the bomb is
    int intRockTemp; //helps to find where all the rocks are on the map
    boolean runIsRunning = false, runIsStop = false, run1IsFinished = false, actIsFinished = false; // check if it is currently being run
    int intScenario = 1; //only used for reset button 
    
	URL north_location1 = this.getClass().getResource("/Resources/north.jpg");
	URL east_location = this.getClass().getResource("/Resources/east.jpg");
	URL south_location = this.getClass().getResource("/Resources/south.jpg");
	URL west_location = this.getClass().getResource("/Resources/west.jpg");
	URL act_location = this.getClass().getResource("/Resources/Act.jpg");
	URL run_location = this.getClass().getResource("/Resources/Run.jpg");
	URL restart_location = this.getClass().getResource("/Resources/Reset.jpg");
	URL paper_location = this.getClass().getResource("/Resources/crumpled-paper32x32.jpg");
	URL brick_location = this.getClass().getResource("/Resources/brick32x32.png");
	URL rock_location = this.getClass().getResource("/Resources/rock32x32.png");
	URL bomb_location = this.getClass().getResource("/Resources/bomb32x32.png");
	URL robotNorth_location = this.getClass().getResource("/Resources/robot32x32Up.png");
	URL robotEast_location = this.getClass().getResource("/Resources/robot32x32Right.png");
	URL robotSouth_location = this.getClass().getResource("/Resources/robot32x32Down.png");
	URL robotWest1 = this.getClass().getResource("/Resources/robot32x32Left.png");
	//URL icon_location = this.getClass().getResource("/Resources/greenfoot.jpg"); //not used, static images dont work 
	URL explode_location = this.getClass().getResource("/Resources/explode.png");

    ImageIcon north	      = new ImageIcon(north_location1);
    ImageIcon east        = new ImageIcon(east_location);
    ImageIcon south       = new ImageIcon(south_location);
    ImageIcon west        = new ImageIcon(west_location);
    ImageIcon act         = new ImageIcon(act_location);
    ImageIcon run         = new ImageIcon(run_location);
    ImageIcon restart     = new ImageIcon(restart_location);
    ImageIcon paper       = new ImageIcon(paper_location);
    ImageIcon brick       = new ImageIcon(brick_location);
    ImageIcon rock        = new ImageIcon(rock_location);
    ImageIcon bomb		  = new ImageIcon(bomb_location);
    ImageIcon robotNorth  = new ImageIcon(robotNorth_location);
    ImageIcon robotEast   = new ImageIcon(robotEast_location);
    ImageIcon robotSouth  = new ImageIcon(robotSouth_location);
    ImageIcon robotWest   = new ImageIcon(robotWest1);
    //static ImageIcon icon = new ImageIcon(icon_location);
    ImageIcon explode     = new ImageIcon(explode_location);
    /* all of the imageIcon are located in the Resources folder */

    // 0 == paper  1 == brick  2 == rock(run to go up)  3 == bomb 4==robot 5==rock(run to go down) 6== explode
    static int[] allArray1 = 
    { 
	    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  
	    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0,   
	    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0,   
	    0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0,   
	    0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 1, 1, 1, 1, 1, 0,   
	    0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 3, 5, 0, 0, 1, 0, 0, 0, 1, 0,  
	    0, 0, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 1, 0, 0, 0, 1, 0,   
	    0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 1, 1, 1, 1, 1, 0, 
	    0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0,  
	    0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0,  
	    0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  
	    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   
	    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };
    static int[] allArray1Copy = (int[])allArray1.clone(); //back up, used for reset
    static int[] allArray1CopyZ = (int[])allArray1.clone(); //used as default array, used for switching scenerio and reset 
    
    static int[] allArray2 = 
    { 
	    0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  
	    0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   
	    0, 2, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   
	    0, 2, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0,   
	    0, 2, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 2, 0, 1, 1, 1, 1, 1, 0,   
	    0, 2, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 2, 0, 1, 0, 0, 0, 1, 0,  
	    0, 2, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 2, 0, 1, 0, 0, 0, 1, 0,   
	    0, 2, 0, 0, 5, 0, 3, 2, 0, 0, 5, 0, 0, 2, 0, 1, 1, 1, 1, 1, 0, 
	    0, 2, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0,  
	    4, 2, 0, 0, 5, 0, 0, 2, 0, 0, 5, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0,  
	    0, 2, 0, 0, 5, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0,  
	    0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0,   
	    0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0,
    };
    static int[] allArray2Copy = (int[])allArray2.clone(); //back up used as reset
    
    static int[] allArray3 =
    { 
	    0, 0, 0, 5, 0, 5, 5, 5, 5, 5, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0,  
	    0, 4, 0, 5, 0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0,   
	    0, 0, 0, 5, 0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0,   
	    0, 0, 0, 5, 0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0,   
	    0, 0, 3, 5, 0, 5, 0, 0, 2, 2, 2, 0, 5, 0, 0, 1, 1, 1, 1, 1, 0,   
	    0, 0, 0, 5, 0, 5, 0, 0, 2, 0, 0, 0, 5, 0, 0, 1, 0, 0, 0, 1, 0,  
	    0, 0, 0, 5, 0, 5, 0, 0, 2, 0, 0, 0, 5, 0, 0, 1, 0, 0, 0, 1, 0,   
	    0, 0, 0, 5, 0, 5, 0, 0, 2, 0, 0, 0, 5, 0, 0, 1, 1, 1, 1, 1, 0, 
	    0, 0, 0, 5, 5, 5, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  
	    0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,  
	    0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,  
	    0, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,   
	    0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,
    };
    static int[] allArray3Copy = (int[])allArray3.clone(); //back up used for reset 
	
    public static void main(String[] args)
    {	
    	CBombRobot frame = new CBombRobot(); //makes the frame so everything can go on
    	frame.setSize(875, 550); //Default size for assignment, do not change
        frame.createGUI(); //method for creating all of the gui
        frame.setVisible(true); //makes the frame visible
        frame.setTitle("CBombRobot Application"); //sets the title what the application is called
        //frame.setIconImage(icon.getImage()); //sets the icon for the application //not working fix later
        frame.setResizable(false); //makes sure no one change the size of the application 
        }
    
    private void createGUI()
    {
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout() );
        
        panel = new JPanel(); //left main panel for all 273 buttons 
        panel.setPreferredSize(new Dimension(650, 440));
        panel.setBackground(Color.white); 
        panel.setLayout(new GridLayout(13,23) ); //default grid layout for the assignment
        window.add(panel);
        panel.setFocusable(true);
        panel.addKeyListener(this);
        
        panelRight = new JPanel(); // right panel container, containing panels 
        panelRight.setPreferredSize(new Dimension(190, 440)); //thin and long
        window.add(panelRight);
        
        panelaaaa = new JPanel(); // top right panel 1 contains option, square, direction
        panelaaaa.setPreferredSize(new Dimension(190, 100)); 
        panelRight.add(panelaaaa);
        
        panela = new JPanel(); // top right panel 2, contains arrow
        panela.setPreferredSize(new Dimension(190, 100));
        panelRight.add(panela);
        
        panelOption = new JPanel(); // top right panel 3, option buttons
        panelOption.setPreferredSize(new Dimension(190, 100)); 
        panelRight.add(panelOption);
                
        panelImage = new JPanel(); // top right panel 4, image with n,e,s,w
        panelImage.setPreferredSize(new Dimension(125, 125));
        panelRight.add(panelImage);
      
        panelb = new JPanel(); // bottom panel containing run, act restart
        panelb.setPreferredSize(new Dimension(855, 46));
        window.add(panelb);
        
        JButton buttonBlank1 = new JButton(""); //buttonBlank1,2,3,4,5 are there to make the arrow buttons in correct position 
        buttonBlank1.setEnabled(false); //set the enabled to false so no one can use it 
        buttonBlank1.setPreferredSize(new Dimension(55, 26)); //default size of the blank buttons
        
        JButton buttonBlank2 = new JButton("");
        buttonBlank2.setEnabled(false);
        buttonBlank2.setPreferredSize(new Dimension(55, 26));
        
        JButton buttonBlank3 = new JButton("");
        buttonBlank3.setEnabled(false);
        buttonBlank3.setPreferredSize(new Dimension(55, 26));
        
        JButton buttonBlank4 = new JButton("");
        buttonBlank4.setEnabled(false);
        buttonBlank4.setPreferredSize(new Dimension(55, 26));
        
        JButton buttonBlank5 = new JButton("");
        buttonBlank5.setEnabled(false);
        buttonBlank5.setPreferredSize(new Dimension(55, 26));
        
        buttonAct = new JButton("Act"); // the act button moves the robot one place in what ever direction it needs to go
        buttonAct.setIcon(act); //sets the image icon for the act button
        panelb.add(buttonAct); //adds the act button to the bottom panel
        buttonAct.addActionListener(this); //if I press the button the actionlistener will do what it is meant to do 
        buttonAct.addKeyListener(this); //i added this so i can continue to control the robot with the keyboard after press it 
        
        buttonRun = new JButton("Run");//run button
        buttonRun.setIcon(run);
        panelb.add(buttonRun);
        buttonRun.addActionListener(this);
        buttonRun.addKeyListener(this);
        
        buttonRestart = new JButton("Restart"); //restart button
        buttonRestart.setIcon(restart);
        panelb.add(buttonRestart);
        buttonRestart.addActionListener(this);
        buttonRestart.addKeyListener(this);
        
        button1 = new JButton("^");// move 1 up north button
        button1.setPreferredSize(new Dimension(55, 26));
        button1.setMargin(new Insets(0,0,0,0)); //i set the margin as 0,0,0,0 so that it will show the whole arrow 
        panela.add(buttonBlank1);
        panela.add(button1);
        panela.add(buttonBlank2);
        button1.addActionListener(this);
        button1.addKeyListener(this);
        
        button4 = new JButton("<");//move 1 west button
        button4.setPreferredSize(new Dimension(55, 26));
        button4.setMargin(new Insets(0,0,0,0));
        panela.add(button4);
        panela.add(buttonBlank3);
        button4.addActionListener(this);
        button4.addKeyListener(this);

        button2 = new JButton(">");//move 1 east button
        button2.setPreferredSize(new Dimension(55, 26));
        button2.setMargin(new Insets(0,0,0,0));
        panela.add(button2);
        panela.add(buttonBlank4);
        button2.addActionListener(this);
        button2.addKeyListener(this);
        
        button3 = new JButton("v"); //move one south button
        button3.setPreferredSize(new Dimension(55, 26));
        button3.setMargin(new Insets(0,0,0,0));
        panela.add(button3);
        panela.add(buttonBlank5);
        button3.addActionListener(this);
        button3.addKeyListener(this);
        
        buttonOption1 = new JButton("Option 1"); //these do nothing just yet 
        buttonOption1.setPreferredSize(new Dimension(90, 35));
        buttonOption1.setMargin(new Insets(0,0,0,0));
        panelOption.add(buttonOption1);
        buttonOption1.addActionListener(this);
        buttonOption1.addKeyListener(this);
        
        buttonOption2 = new JButton("Option 2");
        buttonOption2.setPreferredSize(new Dimension(90, 35));
        buttonOption2.setMargin(new Insets(0,0,0,0));
        panelOption.add(buttonOption2);
        buttonOption2.addActionListener(this);
        buttonOption2.addKeyListener(this);
        
        buttonOption3 = new JButton("Option 3");
        buttonOption3.setPreferredSize(new Dimension(90, 35));
        buttonOption3.setMargin(new Insets(0,0,0,0));
        panelOption.add(buttonOption3);
        buttonOption3.addActionListener(this);
        buttonOption3.addKeyListener(this);
        
        buttonExit = new JButton("Exit");  //close the programme 
        buttonExit.setPreferredSize(new Dimension(90, 35));
        buttonExit.setMargin(new Insets(0,0,0,0));
        panelOption.add(buttonExit);
        buttonExit.addActionListener(this);
        
        buttonImage = new JButton();  //for the n,e,s,w image position 
        buttonImage.setPreferredSize(new Dimension(85, 85));
    	buttonImage.setIcon(east); //default direction the robot is facing
        buttonImage.setOpaque(true);
        buttonImage.setContentAreaFilled(false);
        buttonImage.setBorderPainted(true);
        buttonImage.setMargin(new Insets(0,0,0,0));
        panelImage.add(buttonImage);
        buttonImage.addActionListener(this);
        
        gapLabel = new JLabel("Speed:  "); //for the user to know what it is used for 
        panelb.add(gapLabel);
        gapField = new JTextField(4);
        panelb.add(gapField);

        slider = new JSlider(JSlider.HORIZONTAL, 20, 2000, 1000); //slider controls how fast the robot moves when you press run
        panelb.add(slider);
        slider.addChangeListener(this);
        gapField.setText(Integer.toString(slider.getValue()));
        timer = new javax.swing.Timer(1000, this); //default starting position. what ever the integer value of the slider is the timer will copy it 

        JLabel textLabelOption = new JLabel("Option:              "); //13 spaces, to make it all look equal
        panelaaaa.add(textLabelOption);
        textFieldOption = new JTextField("Option 1", 6); //what option you have selected
        panelaaaa.add(textFieldOption); 

        JLabel textLabelDirection = new JLabel("Direction:          "); //11 spaces  //
        panelaaaa.add(textLabelDirection);
        textFieldDirection = new JTextField("east", 6); //what direction the robot is facing
        panelaaaa.add(textFieldDirection);
        
		JLabel textLabelSquare = new JLabel("Square:             "); //13 spaces
		panelaaaa.add(textLabelSquare);
		textFieldSquare = new JTextField(6); //what square the robot is in
		panelaaaa.add(textFieldSquare); 
	    
		setLocationRelativeTo(null); //sets the application to the centre when it starts
		menuSetup(); // this makes the menu (top of the application containing things like edit) 
		
		methodsMustRun(); //important methods to find location of objects like bomb and robot
    }
    void methodsMustRun()
    {
    	returnRobotLocation(); //method on line 826. this finds where the value of the robot is
    	returnBombLocation();  //method on line 872. this finds where the bomb is located
        makeEachButton();      //method on line 309. makes all the 274 square buttons
        moveRobotNESW();       //method on line 652. shows what square the robot is on
    }
    
    private void makeEachButton() //has to be run every time something moves 
    {
    	// 0 == paper  1 == brick  2 == rock(run to go up)  3 == bomb 4==robot 5==rock(run to go down) 6== explode 
        JButton buttonPaper[]=new JButton[273]; //273 = default number of buttons to create
		panel.removeAll(); //Deletes what is there so it can be updated
		panel.updateUI();  //updates the screen so you can see things moving 
        for(int i=0;i<allArray1CopyZ.length;i++) //the allArray1CopyZ array is used so that it would be easier switch between scenarios 
        {
	        if (allArray1CopyZ[i] == 0) // if it has a number 0 then the following 2 tasks will be done
	        {
	        	buttonPaper[i] = new JButton(paper);//creates a new button 
	        	panel.add(buttonPaper[i]); //adds the paper to the correction location
	        }
	        if (allArray1CopyZ[i] == 1)
	        {
	        	buttonPaper[i] = new JButton(brick);
	            panel.add(buttonPaper[i]);
	        }
	        if (allArray1CopyZ[i] == 2)
	        {
	            buttonPaper[i] = new JButton(rock);
	            panel.add(buttonPaper[i]);
	        }
	        if (allArray1CopyZ[i] == 3)
	        {
	            buttonPaper[i] = new JButton(bomb);
	            panel.add(buttonPaper[i]);
	        }
	        if (allArray1CopyZ[i] == 4 && intRobotDirection == 1) //this and the next 3 if statements are for the robot and what way the robot is facing 
	        {
	            buttonPaper[i] = new JButton(robotNorth);
	            panel.add(buttonPaper[i]);
	        }	
	        if (allArray1CopyZ[i] == 4 && intRobotDirection == 2)
	        {
	            buttonPaper[i] = new JButton(robotEast);
	            panel.add(buttonPaper[i]);
	        }	
	        if (allArray1CopyZ[i] == 4 && intRobotDirection == 3)
	        {
	            buttonPaper[i] = new JButton(robotSouth);
	            panel.add(buttonPaper[i]);
	        }	
	        if (allArray1CopyZ[i] == 4 && intRobotDirection == 4)
	        {
	            buttonPaper[i] = new JButton(robotWest);
	            panel.add(buttonPaper[i]);
	        }	
	        if (allArray1CopyZ[i] == 5)
	        {
	            buttonPaper[i] = new JButton(rock);
	            panel.add(buttonPaper[i]);
	        }
	        if (allArray1CopyZ[i] == 6)
	        {
	            buttonPaper[i] = new JButton(explode);
	            panel.add(buttonPaper[i]);
	        }
    	}
    }

    public void menuSetup()
    {
        topMenuBar = new JMenuBar();      //create a menu bar
        setJMenuBar(topMenuBar);          //set the menu bar to the JFrame
        
        scenario = new JMenu("Scenario");     // File menu,
        scenario1 = new JMenuItem("scenario 1"); //one of the drop down options 
        scenario.add(scenario1);           
        scenario1.addActionListener(this); 
        scenario2 = new JMenuItem("scenario 2"); 
        scenario.add(scenario2);          
        scenario2.addActionListener(this); 
        scenario3 = new JMenuItem("scenario 3"); 
        scenario.add(scenario3);         
        scenario3.addActionListener(this); 
        topMenuBar.add(scenario);         

        editMenu = new JMenu("Edit");     
        mainEdit = new JMenuItem("Edit"); 
        editMenu.add(mainEdit);           
        mainEdit.addActionListener(this);
        partyMode = new JMenuItem("Party Mode!"); 
        editMenu.add(partyMode);           
        partyMode.addActionListener(this); 
        topMenuBar.add(editMenu );

        controls = new JMenu("Controls");
        controlsInfo = new JMenuItem("controls");
        controls.add(controlsInfo);
        controlsInfo.addActionListener(this);
        controlsEdit = new JMenuItem("Edit Controls");
        controls.add(controlsEdit);
        controlsEdit.addActionListener(this);
        topMenuBar.add(controls);

        helpMenu = new JMenu("Help");   // help menu, with  help topics, about application
        helpItem = new JMenuItem("Help Topics");
        helpMenu.add(helpItem);
        helpItem.addActionListener(this);
        aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        aboutItem.addActionListener(this);
        topMenuBar.add(helpMenu);
    }

	public void actionPerformed(ActionEvent event) //every time a button is pressed this method is played out
	{
        Object source = event.getSource(); //finds out what the button was pressed was 
        
        if (source == button1) //if the button button1 was pressed then 
        { 
        	up(); //method on line 911. checks to see if it can move north, if it can then robot goes up one
        	resetBoolean(); //resets all the booleans. is for so you can re-take control of where the robot travels 
        }
        if (source == button2) 
        {
        	right(); //method on line 925
        	resetBoolean();
        }
        if (source == button3)
        {
        	down(); //method on line 938
        	resetBoolean();
        }
        if (source == button4) 
        {
        	left(); //method on line 952
        	resetBoolean();
        }
        //############################################ other controls
        if(source == buttonExit)
        {
        	System.exit(0); //closes the whole application
        }
        if(source == buttonAct) 
        {
        	if(actIsFinished == false) //if the task of the bomb is in the correct location then it will see which method will be run
        	{
        		act(); //when the bomb is passed a specific point then it will no longer be run
        	}
        	if(actIsFinished == true)
        	{
        		act1(); //once the bomb is passed a specific point then this method will run
        	}
        }
        if(source == buttonRun)
        {
        	booleanRun(); //starts the timer and turns a boolean to true. on line 459 the method moveRobot will run if the run button is pressed 
        }
        if(runIsRunning == true)
        {
        	moveRobot(); //will run this method as long as the runIsRunning boolean is true 
        }
        if(run1IsFinished == true && actIsFinished == false) //if the bomb is in a specific location and the two booleans are correct then the the method wll run
        {
        	moveRobot1(); //a bit like the moveRobot() method but it only pushes the bomb up or down 
        } 
        if(source == buttonRestart)
        {
        	restart(); //method on line 676, will reset the application and put everything back in the default location 
        	restart1(); //method on line 687. make sure it resets the application to the correct scenario 
        }
        //############################################# top top menu bar 
        if (source == mainEdit)
        { 
            JOptionPane.showMessageDialog(null, "Type a number where you want to add a rock on a square.");
        }
        if (source == partyMode)
        { 
            JOptionPane.showMessageDialog(null, "Does nothing, sorry.");
        }
        if (source == controlsInfo)
        { 
            JOptionPane.showMessageDialog(null, "Use mouse to select buttons. Or use arrow keys to move robot");
        }
        if (source == controlsEdit)
        { 
            JOptionPane.showMessageDialog(null, "Does nothing, sorry.");
        }
        if (source == helpItem)
        {
            JOptionPane.showMessageDialog(null, "Press run or act to automatically get the robot to mvoe the bomb around\n or manually move it by clicking the up, left, right and down arrows.");
        }
        if (source == aboutItem)
        {
            JOptionPane.showMessageDialog(null, "Project made by Bob.");
        }
        if (source == scenario1) 
        {
        	intScenario = 1; //changes the scenario if you change it 
        	System.arraycopy(allArray1Copy, 0, allArray1CopyZ, 0, allArray1CopyZ.length);
        	restart(); //reset everything so it will go back to the way it should look like
        }
        if (source == scenario2)
        {
        	intScenario = 2;
        	System.arraycopy(allArray2Copy, 0, allArray1CopyZ, 0, allArray1CopyZ.length);
        	restart();
        }
        if (source == scenario3)
        {
        	intScenario = 3;
        	System.arraycopy(allArray3Copy, 0, allArray1CopyZ, 0, allArray1CopyZ.length);
        	restart();
        }
        if (source == buttonOption1)
        {
        	//does nothing
        }
        if (source == buttonOption2)
        {
        	//does thing
        }
        if (source == buttonOption3)
        {
        	//does nothing
        }
        
    }//end of action performed  buttonOption1
    
	
    public void stateChanged(ChangeEvent e) 
    {
        int timeGap = slider.getValue(); //updates the speed of the timer of the slider changes 
        gapField.setText(Integer.toString(timeGap)); //changes the text of the output so the user knows what the value is
        timer.setDelay(timeGap); //sets the delay of the timer 
    }
	//################################################################## North
	private void moveNorth() 
	{ 
		bombNorth(); //moves the bomb 1 north. on line 596
    	textFieldDirection.setText("north"); //sets the direction to what the robot is facing
    	buttonImage.setIcon(north); //sets the icon so that the arrow points north
    	intRobotDirection = 1; //used so that it can tell what image icon to use
		allArray1CopyZ[intRobotLocation] = intRobotArrayLocation; //when the robot travels over the red bricks it will make sure it wll be replaced by another redbrick
		allArray1CopyZ[intRobotLocation-21] = 4; //moves the robot 1 place north
		intRobotLocation = intRobotLocation - 21; //updates the robot location
		methodTogether(); //this method runs 4 important methods on line 587
	}
	//################################################################## East
	public void moveEast() 
	{ 
		bombEast();
		textFieldDirection.setText("east");
    	buttonImage.setIcon(east);
    	intRobotDirection = 2;
		allArray1CopyZ[intRobotLocation] = intRobotArrayLocation;
		allArray1CopyZ[intRobotLocation+1] = 4;
		intRobotLocation = intRobotLocation + 1;
		methodTogether();
	}
	//################################################################## South
	private void moveSouth() 
	{ 
		bombSouth();
		textFieldDirection.setText("south");
    	buttonImage.setIcon(south);
    	intRobotDirection = 3;
		allArray1CopyZ[intRobotLocation] = intRobotArrayLocation;
		allArray1CopyZ[intRobotLocation+21] = 4;
		intRobotLocation = intRobotLocation + 21;
		methodTogether();
	}
	//################################################################## West
	private void moveWest() 
	{
		bombWest();
		textFieldDirection.setText("west");
    	buttonImage.setIcon(west);
    	intRobotDirection = 4;
		allArray1CopyZ[intRobotLocation] = intRobotArrayLocation;
		allArray1CopyZ[intRobotLocation-1] = 4;
		intRobotLocation = intRobotLocation - 1;
		methodTogether();
	}
	//end of west
	
	void methodTogether()
	{ //makes it easier to see what is happening 
		checkColorArray(); //line 637. checks to see if the robot is over a redbrick 
		checkExplode();    //line 849. checks to see if the bomb should be replaced with an explosion 
		moveRobotNESW();   //line 652. adds the robot location to the panel so the user can see what square it is on 
		makeEachButton();  //line 309. repaints the entire panel of square buttons so when the robot moves north the robot will move 1 place north
	}
	
	//###################################################################### bomb north
	private void bombNorth()
	{
		if(intRobotLocation == (intBombLocation + 21)) //will only be activated if a robot is below the bomb
		{
			//allArray1CopyZ[intRobotLocation] = intRobotArrayLocation; //makes sure a red brick is replaced when a bomb goes over a red brick
			allArray1CopyZ[intBombLocation - 21] = 3; //moves the bomb one place north
			intBombLocation = intBombLocation - 21; //updates the bomb location 
		}
	}
	//###################################################################### bomb east
	private void bombEast()
	{
		if(intRobotLocation == (intBombLocation - 1))
		{
			allArray1CopyZ[intRobotLocation] = intRobotArrayLocation;
			allArray1CopyZ[intBombLocation + 1] = 3;
			intBombLocation = intBombLocation + 1;
		}
	}
	//###################################################################### bomb south
	private void bombSouth()
	{
		if(intRobotLocation == (intBombLocation - 21))
		{
			allArray1CopyZ[intRobotLocation] = intRobotArrayLocation;
			allArray1CopyZ[intBombLocation + 21] = 3;
			intBombLocation = intBombLocation + 21;
		}
	}
	//###################################################################### bomb west
	private void bombWest()
	{
		if(intRobotLocation == (intBombLocation + 1))
		{
			allArray1CopyZ[intRobotLocation] = intRobotArrayLocation;
			allArray1CopyZ[intBombLocation - 1] = 3;
			intBombLocation = intBombLocation - 1;
		}
	}
	//end of bomb west

	void checkColorArray()
	{
    	if(intRobotLocation == 103 || intRobotLocation == 124 || intRobotLocation == 145 || intRobotLocation == 166 
    							   || intRobotLocation == 165 || intRobotLocation == 164 || intRobotLocation == 163 
    							   || intRobotLocation == 162 || intRobotLocation == 141 || intRobotLocation == 120 
    							   || intRobotLocation == 99 || intRobotLocation == 100 || intRobotLocation == 101 
    							   || intRobotLocation == 102) //all of the locations of the red brick
    	{
    		intRobotArrayLocation = 1; //if a robot is over a red brick and if the robot moves to another location the last location of the robot will be replaced with a red brick
    	}
    	else
    	{
    		intRobotArrayLocation = 0; //if the robot is not over a red brick then it will just be repalced with paper
    	}
	}
	private void moveRobotNESW()
	{ //adds the intRobotLocation to the square location 
		String StringRobotLocationNESW = Integer.toString(intRobotLocation); //changes the robotlocation interget to a string so it can be added to the panel 
		textFieldSquare.setText(StringRobotLocationNESW);
		panelaaaa.add(textFieldSquare); 
	}
	//###################################################################### 
	private void act() 
	{
		if(actIsFinished == false)//this if statement will only run if actIsFinished is not complete. once line 667 is complete this if statement wont run
		{
			runIsRunning = true;
			moveRobot(); //moves the robot in what ever direction it is meant to 
			runIsRunning = false;
		}
		if((intBombLocation-17) % 21 == 0 && (intRobotLocation-17) % 21 == 0) //if the robot and bomb are both at a specific part of the map then the next boolean will change
		{
			actIsFinished = true;
		} 
	}
	private void act1()
	{
		moveRobot1();
	}
	private void restart() //makes all the settings back to default setting 
	{
		booleanStop(); //stops the timer 
		returnRobotLocation(); //updates the robot location
		returnBombLocation(); // updates the bomb location
		intRobotDirection = 2; //2 = east = default direction
		moveRobotNESW(); 
		resetBoolean();//resets all booleans 
		textFieldDirection.setText("east"); //sets the default direction text
		makeEachButton(); //updates all the changes 
	}
	private void restart1()
	{
		if(intScenario == 1) //checks to see what scenario is clicked on
		{
			System.arraycopy( allArray1Copy, 0, allArray1CopyZ, 0, allArray1.length );
		}
		if(intScenario == 2)
		{
			System.arraycopy( allArray2Copy, 0, allArray1CopyZ, 0, allArray1.length );
		}
		if(intScenario == 3)
		{
			System.arraycopy( allArray3Copy, 0, allArray1CopyZ, 0, allArray1.length );
		}
		restart();
	}
	//end of act run restart
	boolean booleanRun()
	{
		timer.start(); //starts the timer
		return runIsRunning = true;
	}
	boolean booleanStop()
	{
		timer.stop(); //stops the timer
		return runIsRunning = false;
	}
	void resetBoolean()
	{
		runIsRunning 		= false; //resets the booleans when restart is pressed
		runIsStop		    = false;
		run1IsFinished      = false; 
		actIsFinished       = false;
	}
	void moveRobot()
	{ // 0 == paper  1 == brick  2 == rock(run to go up)  3 == bomb 4==robot 5==rock(run to go down)
		if(runIsRunning == true && intBombLocation != 143 || intBombLocation != 122)
		{
			if(allArray1CopyZ[intRobotLocation +1] != 2) // will always move right
			{
				if(intRobotLocation != 247)
				{
					right();
				}
			} 
			if(intRobotLocation + 1 == intBombLocation && allArray1CopyZ[intRobotLocation +2]==5)//if the bomb is on the right of the robot and a rock is right of the bomb then it will run
			{System.out.println("[C1]");
				if(allArray1CopyZ[intRobotLocation-21] != 4) //first the robot will move 1 north
				{
					up();
				}
				if(allArray1CopyZ[intRobotLocation+1] != 4) //second the robot will move 1 east
				{
					right();
				}
				if(allArray1CopyZ[intRobotLocation+21] != 4) //third the robot will move 1 south
				{
					down();
				}
			}
			if(intRobotLocation + 1 == intBombLocation && allArray1CopyZ[intRobotLocation +2]==2) //if the bomb is right of the robot and left of a rock it will be activated
			{System.out.println("[C2]");
				if(allArray1CopyZ[intRobotLocation+21] != 4) //first the robot will move 1 down
				{
					down();
				}
				if(allArray1CopyZ[intRobotLocation+22] != 4) //second the robot will move right
				{
					right();
				}
			}
			if(allArray1CopyZ[intRobotLocation +1]==2 || allArray1CopyZ[intRobotLocation +1]==5) //if the robot is left to a rock then it will be activated //[c3]
			{
				if(allArray1CopyZ[intRobotLocation +1]==2) //it will either move up or down depending on if the rock is a number 2 or 5. this makes it easy for the robot to make a right choice 
				{
					up();
				}
				if(allArray1CopyZ[intRobotLocation +1]==5)
				{
					down();
				}
			}
			if(intRobotLocation + 21 == intBombLocation && allArray1CopyZ[intRobotLocation +22]==0)//if the robot is north of the bomb and the bomb plus 1 is paper then this if statement will be activated // [c4] 
			{
				if(allArray1CopyZ[intRobotLocation-1] != 4)
				{
					left();
				}
				if(allArray1CopyZ[intRobotLocation+21] != 4)
				{
					down();
				}
			}
			if(intRobotLocation - 21 == intBombLocation && allArray1CopyZ[intRobotLocation -20]==0) //if the robot is south of the bomb and the bomb + 1 is paper then this if statement will be activated 
			{
				if(allArray1CopyZ[intRobotLocation-1] != 4)
				{
					left();
				}
				if(allArray1CopyZ[intRobotLocation-21] != 4)
				{
					up();
				}
			}
			if((intRobotLocation-16) % 21 == 0) //checks to see if the robot is in the correct location 
			{
				if(intRobotLocation < 125) //checks to see if the robot should move down or up
				{
					if(allArray1CopyZ[intRobotLocation+21] != 4)
					{
						up();
					}
					if(allArray1CopyZ[intRobotLocation+21] != 4)
					{
						right();
					}
				}
				if(intRobotLocation > 140){
					if(allArray1CopyZ[intRobotLocation+21] != 4)
					{
						down();
					}
					if(allArray1CopyZ[intRobotLocation+21] != 4)
					{
						right();
					}
				}
				booleanStop(); //stops the timer
				run1IsFinished = true; //this is so moveRobot1 method can run
			}
		} 
	}
	void moveRobot1()
	{ //runs after moveRobot() method is complete 
		timer.start(); //starts the timer again
		if(intRobotLocation < 125) //checks to see if it should move the robot north or south
		{
			if((intRobotLocation-17) % 21 == 0)
			{
				down();
			}
			if(intRobotLocation == 101)
			{
				up();
				run1IsFinished = false;
				booleanStop();
			}
		}
		if(intRobotLocation > 140)
		{
			if((intRobotLocation-17) % 21 == 0)
			{
				up();
			}
			if(intRobotLocation == 164)
			{
				down();
				run1IsFinished = false;
				booleanStop();
			}
		}
	}
	void checkExplode() //checks to see if the bomb is inside the red square and that the robot is not near the bomb
	{
		if(intBombLocation == 121 || intBombLocation == 122 || intBombLocation == 123 || intBombLocation == 142 || intBombLocation == 143 || intBombLocation == 144)
		{
			if(allArray1CopyZ[intRobotLocation + 1] != 1)
			{
				allArray1CopyZ[intBombLocation] = 6;
				makeEachButton();
			}
		}
	}
	
	// 0 == paper  1 == brick  2 == rock(run to go up)  3 == bomb 4==robot 5==rock(run to go down)
	private void returnRobotLocation() //an easy way to find the robot location
	{
		for(int i = 0; i < allArray1CopyZ.length; i++)
		{
    		if (allArray1CopyZ[i] == 4) //it checks every array int until it finds the robot 
    		{
    			intRobotLocation = i;
    		}
		}
	}
	private void returnBombLocation()  //works just the same way as finding the robot location
	{
		for(int i = 0; i < allArray1CopyZ.length; i++)
		{
    		if (allArray1CopyZ[i] == 3)
    		{
    			intBombLocation = i;
    		}
		}
	}

	public void keyPressed(KeyEvent e)  //if the up, down, right, or left arrow keys on the keyboard are pressed then the robot will move
	{ //key controls
	    if (e.getKeyCode() == KeyEvent.VK_UP ) 
	    {
	    	up();
	    } 
	    if (e.getKeyCode() == KeyEvent.VK_RIGHT ) 
	    {
			right();
	    } 
	    if (e.getKeyCode() == KeyEvent.VK_DOWN ) 
	    {
			down();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_LEFT ) 
	    {
			left();
	    } 
	}
	public void keyReleased(KeyEvent e) 
	{
		//does nothing
	}

	public void keyTyped(KeyEvent e) 
	{
		//does nothing
	}
	void up()
	{
        if(intRobotLocation >= 0 && intRobotLocation <= 20 || allArray1CopyZ[intRobotLocation -21]==2 || allArray1CopyZ[intRobotLocation -21]==5) //checks to see if the robot can actually move in that diection
        {
        	textFieldDirection.setText("north");
            buttonImage.setIcon(north); 
            intRobotDirection = 1; //if it can't move in that direction the robot will still change the way it is facing
            makeEachButton();
        }
        else 
        {
            moveNorth(); //if it can move in that direction then it will move north. on line 538
        }
	}
	void right()
	{
        if(intRobotLocation-20 % 21 == 0 || allArray1CopyZ[intRobotLocation +1]==2  || allArray1CopyZ[intRobotLocation +1]==5)
        {
        	textFieldDirection.setText("east");
            buttonImage.setIcon(east);
            intRobotDirection = 2;
            makeEachButton();
        }
        else {
            moveEast();
        }
	}
	void down()
	{
        if(intRobotLocation >= 252 && intRobotLocation <= 272 || allArray1CopyZ[intRobotLocation +21]==2 || allArray1CopyZ[intRobotLocation +21]==5)
        {
        	textFieldDirection.setText("south");
            buttonImage.setIcon(south);
            intRobotDirection = 3;
            makeEachButton();
        }
        else 
        {
            moveSouth();
        }
	}
	void left()
	{
        if(intRobotLocation % 21 == 0 || allArray1CopyZ[intRobotLocation -1]==2 || allArray1CopyZ[intRobotLocation -1]==5)
        {	
        	textFieldDirection.setText("west");
            buttonImage.setIcon(west);
            intRobotDirection = 4;
            makeEachButton();
        }
        else
        {
            moveWest();
        }
	}
}



