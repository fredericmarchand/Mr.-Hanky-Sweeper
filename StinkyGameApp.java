/**Assignment#2: StinkyGameApp
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

class StinkyGameApp extends JFrame {
	private StinkyGame 			newGame;//represents the game being played
	private JButton 			playButton;//represents the play/quit button
	private JButton				gameButton;//represents all the buttons on the gameboard
	private JLabel 				scoreLabel;//represents the label displaying the score
	private ArrayList<Integer>	buttonRowPosition = new ArrayList<Integer>();//used to store the row position of the button
	private ArrayList<Integer>	buttonColPosition = new ArrayList<Integer>();//used to store the column position of the button
	private ArrayList<JButton>	allButtons = new ArrayList<JButton>();//this arraylist stores all the buttons on the gameboard
	private int					currentScore = 0;//represents the current score
	private boolean 			gameFlow = true;//this is used when the game is lost

	StinkyGameApp(String title){//constructor
		super(title);
		newGame = new StinkyGame();
		buildWindow();//builds the window

		playButton.addActionListener(new ActionListener() {//action listener for the play/quit button
			public void actionPerformed(ActionEvent event) {
				handlePlayButtonPress();
			}
		});

		for (JButton b: allButtons){
			b.addActionListener(new ActionListener() {//an action listener is created for each button on the gameboard
				public void actionPerformed(ActionEvent event) {
					handleGameButtonPress((JButton)event.getSource());
				}
			});
		}

		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        disableButtons();//the buttons are disabled to start the game, which means that the play button needs to be pressed in order to start the first game
	}

	private void buildWindow(){//method used to construct the window
		GridBagLayout 		layout = new GridBagLayout();
		GridBagConstraints  constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5); // default spacing
		setLayout(layout);

		JPanel centerPanel = new JPanel(new GridLayout(10,10,0,0));
		for (int row=0; row<newGame.getBoard().ROWS; row++){
			for (int col=0; col<newGame.getBoard().COLUMNS; col++){
				centerPanel.add(gameButton = new JButton());//a new button is added in each of the cases of the grid
				allButtons.add(gameButton);//each button is added to the arraylist of buttons
				buttonRowPosition.add(row);//the row position of each is added to the buttonRowPosition arraylist
				buttonColPosition.add(col);//the row position of each is added to the buttonColPosition arraylist
			}
		}
		constraints.gridx = 0;
		constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        layout.setConstraints(centerPanel, constraints);
		add(centerPanel);

       	playButton = new JButton("Start");
       	playButton.setBackground(new Color(0, 100, 0));
       	playButton.setForeground(Color.white);
        constraints.gridx = 0;
		constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0;
        constraints.weighty = 0;
        layout.setConstraints(playButton, constraints);
        add(playButton);

        JLabel label1 = new JLabel("Score: ");
        label1.setFont(new Font("Impact", Font.BOLD, 24));
		constraints.gridx = 1;
		constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.EAST;
        layout.setConstraints(label1, constraints);
        add(label1);

		scoreLabel = new JLabel("0");
		scoreLabel.setFont(new Font("Impact", Font.BOLD, 28));
		scoreLabel.setForeground(new Color(0,0,139));
		constraints.gridx = 2;
		constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 0;
        layout.setConstraints(scoreLabel, constraints);
        add(scoreLabel);
	}

	public void disableButtons(){//this method disables all the buttons on the gameboard
		for (JButton b: allButtons)
			b.setEnabled(false);
	}

	public void enableButtons(){//this method enables all the buttons on the gameboard
		for (JButton b: allButtons)
			b.setEnabled(true);
	}

	//the startNewGame starts a new game by making a new board, resetting all the buttons to their default configurations and enabling all the buttons
	public void startNewGame(){
		newGame.startNextBoard();
		playButton.setText("Stop");
		for(JButton b: allButtons){
			b.setText("");
			b.setIcon(new ImageIcon(""));
		}
		enableButtons();
	}

	//the updateButtons method basicly changes the look of the board after a button has been pressed
	public void updateButtons(JButton source){
		if(newGame.getBoard().stinkyAt(buttonRowPosition.get(allButtons.indexOf(source)), buttonColPosition.get(allButtons.indexOf(source)))){
			gameFlow = false;
			for(JButton j: allButtons){
				if(newGame.getBoard().stinkyAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j)))){
					j.setIcon(new ImageIcon("hank.jpg"));
				}
				else{
					if (newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))) == 0){
						j.setEnabled(false);
					}
					else if (newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))) == 1){
						j.setForeground(Color.blue);
						j.setText("" + newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))));
					}
					else if (newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))) == 2){
						j.setForeground(new Color(0, 100, 0));
						j.setText("" + newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))));
					}
					else if (newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))) == 3){
						j.setForeground(Color.red);
						j.setText("" + newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))));
					}
					else if(newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))) >= 4){
						j.setForeground(Color.black);
						j.setText("" + newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))));
					}
				}
			}
		}
		else{
			for(JButton j: allButtons){
				if(!(newGame.getBoard().coveredAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))))){
					if (newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))) == 0){
						j.setEnabled(false);
					}
					else if (newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))) == 1){
						j.setForeground(Color.blue);
						j.setText("" + newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))));
					}
					else if (newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))) == 2){
						j.setForeground(new Color(0, 100, 0));
						j.setText("" + newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))));
					}
					else if (newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))) == 3){
						j.setForeground(Color.red);
						j.setText("" + newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))));
					}
					else if(newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))) >= 4){
						j.setForeground(Color.black);
						j.setText("" + newGame.getBoard().smellStrengthAt(buttonRowPosition.get(allButtons.indexOf(j)), buttonColPosition.get(allButtons.indexOf(j))));
					}
				}
			}
		}
	}

	//the updateScore method basicly changes the score that we see on the window after an action has been performed
	public void updateScore(JButton source){
		scoreLabel.setText(""+ (newGame.getUnflipped()+currentScore));
		if(newGame.getUnflipped() == 900){
			newGame.finalScore();
			currentScore = newGame.getScore() + currentScore;
			scoreLabel.setText(""+currentScore);
			JOptionPane.showMessageDialog(null, "Board completed in: " + newGame.getCompletionTime() + " seconds \n" + newGame.getBonus() + " bonus points");
			playButton.setText("Start");
			for(JButton j: allButtons){
				j.setEnabled(false);
			}
		}
		else if(newGame.getUnflipped() < 900){
			if(newGame.getBoard().stinkyAt(buttonRowPosition.get(allButtons.indexOf(source)), buttonColPosition.get(allButtons.indexOf(source)))){
				JOptionPane.showMessageDialog(null, "Final score: " + (newGame.getUnflipped()+currentScore));
				currentScore = 0;
				playButton.setText("Start");
				for(JButton j: allButtons){
					j.setEnabled(false);
				}
			}
		}
}

	//the update method basicly groups both the updateScore and updateButtons method together so that both don't have to be called each time
	public void update(JButton source){
		updateButtons(source);
		updateScore(source);
	}

	//the handlePlayButtonPress is called when the play/stop button is pressed
	public void handlePlayButtonPress(){
		if(playButton.getText().equals("Stop"))
			System.exit(0);
		else{
			startNewGame();
			if(!gameFlow){
				scoreLabel.setText("0");
				gameFlow = true;
				currentScore = 0;
			}
			else
				scoreLabel.setText(""+currentScore);
		}
	}

	//the handleGameButtonPress is called when a button on the gameboard is pressed
	public void handleGameButtonPress(JButton source){
		newGame.handleSelection(buttonRowPosition.get(allButtons.indexOf(source)), buttonColPosition.get(allButtons.indexOf(source)));
		update(source);
	}

	//this is the main method of the StinkyGameApp
	public static void main(String args[]){
		new StinkyGameApp("Stinky Game").setVisible(true);
	}
}