package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import engine.City;
import engine.Game;
import exceptions.FriendlyFireException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Unit;

public class BattleView extends JFrame implements ActionListener {
	private Game game;
	private JFrame battleView;
	private JPanel attpanel;
	private JPanel defpanel;
	private JTextArea chosenUnitsArea;
	private JTextArea battleLog;
	private JPanel chooseUnitPan;
	private JLabel defendinglab;
	private JLabel attackinglab;
	private JLabel pname;
	private JLabel pturn;
	private JLabel pfood;
	private JLabel pgold;
	private JTextArea unitInfo;
	private Army attackingArmy;

	public Army getAttackingArmy() {
		return attackingArmy;
	}

	public Army getDefendingArmy() {
		return defendingArmy;
	}

	private Army defendingArmy;
	private ArrayList<JButton> attackingButtons;
	private ArrayList<JButton> defendingButtons;
	private JButton startBattle;
	private JButton unitInfoChoose;
	private JButton unitInfoCancel;
	private JButton simBattle;
	private JButton toMapView;
	private Unit attackingUnit;
	private Unit defendingUnit;
	private String logMessage;
	private JScrollPane battleLogScroll;
	private MapView mapo;

	public BattleView(Game game, Army attacking, Army defending, MapView m) {
		mapo = m;
		this.game = game;
		logMessage = "";
		attackingUnit = null;
		defendingUnit = null;
		this.attackingArmy = attacking;
		this.defendingArmy = defending;
		this.attackingButtons = new ArrayList<JButton>();
		this.defendingButtons = new ArrayList<JButton>();

		battleView = new JFrame();
		battleView.setUndecorated(true);
		battleView.setContentPane(new JLabel(new ImageIcon("rsz_2battleview11.jpg")));
		battleView.setLayout(null);
		battleView.setDefaultCloseOperation(EXIT_ON_CLOSE);
		battleView.setVisible(true);
		battleView.setExtendedState(JFrame.MAXIMIZED_BOTH);

		JLabel photo1 = new JLabel();
		photo1.setIcon(new ImageIcon("rsz_attackingarmy.jpg"));
		photo1.setOpaque(true);
		photo1.setBounds(270, 150, 300, 200);

		JLabel photo2 = new JLabel();
		photo2.setIcon(new ImageIcon("rsz_defendingarmy (1).jpg"));
		photo2.setOpaque(true);
		photo2.setBounds(270, 400, 300, 200);

		pname = new JLabel();
		pname.setBounds(20, 20, 300, 20);
		pname.setVerticalAlignment(JLabel.TOP);
		pname.setText("Name: " + game.getPlayer().getName());
		pname.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		pname.setBackground(Color.decode("#155ABA"));
		pname.setForeground(Color.white);
		pname.setOpaque(true);

		battleView.add(pname);

		pturn = new JLabel();
		pturn.setBounds(20, 40, 300, 20);
		pturn.setVerticalAlignment(JLabel.TOP);
		pturn.setText("Current Turn: " + game.getCurrentTurnCount());
		pturn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		pturn.setBackground(Color.decode("#155ABA"));
		pturn.setForeground(Color.white);

		pturn.setOpaque(true);

		pfood = new JLabel();
		pfood.setBounds(20, 60, 300, 20);
		pfood.setVerticalAlignment(JLabel.TOP);
		pfood.setText("Food: " + game.getPlayer().getFood());
		pfood.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		pfood.setBackground(Color.decode("#155ABA"));
		pfood.setForeground(Color.white);

		pfood.setOpaque(true);

		pgold = new JLabel();
		pgold.setBounds(20, 80, 300, 20);
		pgold.setVerticalAlignment(JLabel.TOP);

		pgold.setText("Gold: " + game.getPlayer().getTreasury());
		pgold.setBackground(Color.decode("#155ABA"));
		pgold.setForeground(Color.white);
		pgold.setOpaque(true);

		pgold.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

		battleView.add(pfood);
		battleView.add(pgold);
		battleView.add(pturn);

		attackinglab = new JLabel("Attacking Army");

		attackinglab.setBounds(20, 115, 550, 30);
		attackinglab.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
		attackinglab.setHorizontalAlignment(JLabel.CENTER);
		attackinglab.setOpaque(true);
		attackinglab.setBackground(Color.decode("#155ABA"));
		attackinglab.setForeground(Color.white);
		battleView.add(attackinglab);

		attpanel = new JPanel();
		attpanel.setLayout(new GridLayout(0, 1));
		attpanel.setBounds(20, 150, 200, 200);
		attpanel.setBackground(Color.cyan);
		attpanel.setOpaque(true);
		JScrollPane attpan = new JScrollPane(attpanel);
		attpan.setBounds(20, 150, 200, 200);
		battleView.add(attpan);

		defendinglab = new JLabel("Defending Army");

		defendinglab.setBounds(20, 365, 550, 30);
		defendinglab.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
		defendinglab.setHorizontalAlignment(JLabel.CENTER);
		defendinglab.setOpaque(true);

		defendinglab.setBackground(Color.decode("#155ABA"));
		defendinglab.setForeground(Color.white);
		battleView.add(defendinglab);

		defpanel = new JPanel();
		defpanel.setLayout(new GridLayout(0, 1));
		defpanel.setBounds(20, 400, 200, 200);
		defpanel.setBackground(Color.cyan);
		defpanel.setOpaque(true);
		JScrollPane defpan = new JScrollPane(defpanel);
		defpan.setBounds(20, 400, 200, 200);
		battleView.add(defpan);

		for (int i = 0; i < attackingArmy.getUnits().size(); i++) {
			Unit unit = attackingArmy.getUnits().get(i);
			String type = "";
			if (unit instanceof Archer) {
				type = "Archer";
			} else if (unit instanceof Infantry) {
				type = "Infantry";
			} else if (unit instanceof Cavalry) {
				type = "Cavalry";
			}
			String butText = type + " Level:" + unit.getLevel();
			JButton unitButton = new JButton(butText);
			unitButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			unitButton.addActionListener(this);
			attackingButtons.add(unitButton);
			attpanel.add(unitButton);
		}

		for (int i = 0; i < defendingArmy.getUnits().size(); i++) {
			Unit unit = defendingArmy.getUnits().get(i);
			String type = "";
			if (unit instanceof Archer) {
				type = "Archer";
			} else if (unit instanceof Infantry) {
				type = "Infantry";
			} else if (unit instanceof Cavalry) {
				type = "Cavalry";
			}
			String butText = type + " Level:" + unit.getLevel();
			JButton unitButton = new JButton(butText);
			unitButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			unitButton.addActionListener(this);
			defendingButtons.add(unitButton);
			defpanel.add(unitButton);
		}

		chosenUnitsArea = new JTextArea();
		chosenUnitsArea.setBounds(650, 20, 600, 50);
		chosenUnitsArea.setBackground(Color.pink);
		chosenUnitsArea.setOpaque(true);
		chosenUnitsArea.setVisible(true);
		chosenUnitsArea.setEditable(false);
		chosenUnitsArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		chosenUnitsArea.setText(this.ChosenUnitText(attackingUnit, defendingUnit));
		battleView.add(chosenUnitsArea);

		battleLog = new JTextArea();
//		battleLog.setBounds(700,200,800,475);
		battleLog.setBounds(650, 100, 600, 675);
		battleLog.setLayout(new FlowLayout());
		battleLog.setBackground(Color.BLACK);
		battleLog.setForeground(Color.white);
		battleLog.setOpaque(true);
		battleLog.setVisible(true);
		battleLog.setEditable(false);
		battleLog.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		battleLogScroll = new JScrollPane(battleLog);
		battleLogScroll.setBounds(650, 80, 600, 450);
		battleView.add(battleLogScroll);

		chooseUnitPan = new JPanel();
		chooseUnitPan.setBounds(650, 10, 600, 110);
		chooseUnitPan.setLayout(null);
		chooseUnitPan.setBackground(Color.decode("#155ABA"));
		chooseUnitPan.setForeground(Color.white);
		chooseUnitPan.setOpaque(true);
		chooseUnitPan.setVisible(false);
		battleView.add(chooseUnitPan);

		unitInfo = new JTextArea("Type: ***** \nLevel: * \nSoldier Count: ** \nMax Soldier Count: **");
		unitInfo.setBounds(10, 10, 300, 100);
		unitInfo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
		unitInfo.setBackground(Color.decode("#155ABA"));
		unitInfo.setForeground(Color.white);

		chooseUnitPan.add(unitInfo);

		unitInfoChoose = new JButton("Choose");
		unitInfoChoose.setBounds(400, 10, 150, 40);
		unitInfoChoose.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		unitInfoChoose.setVisible(false);
		unitInfoChoose.addActionListener(this);
		chooseUnitPan.add(unitInfoChoose);

		unitInfoCancel = new JButton("Cancel");
		unitInfoCancel.setBackground(Color.white);
		unitInfoCancel.setBounds(400, 60, 150, 40);
		unitInfoCancel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		unitInfoCancel.addActionListener(this);
		// Don't forget to set the visibility of the button according to the user
		// action.
		unitInfoCancel.setVisible(false);
		chooseUnitPan.add(unitInfoCancel);

		startBattle = new JButton("Start Battle");
		startBattle.setBackground(Color.white);
		startBattle.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				startBattle.setText("");
				startBattle.setIcon(new ImageIcon("rsz_waricon.jpg"));
				;
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				startBattle.setIcon(null);
				;
				startBattle.setText("Start Battle");
			}
		});

		startBattle.setBounds(650, 550, 150, 50);
		startBattle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		startBattle.addActionListener(this);
		battleView.add(startBattle);

		simBattle = new JButton("SIM Battle");
		simBattle.setBackground(Color.white);
		simBattle.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				simBattle.setText("");
				simBattle.setIcon(new ImageIcon("rsz_waricon.jpg"));
				;
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				simBattle.setIcon(null);
				;
				simBattle.setText("SIM Battle");
			}
		});

		simBattle.setBounds(875, 550, 150, 50);
		simBattle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		simBattle.addActionListener(this);
		battleView.add(simBattle);
		battleView.add(photo1);
		battleView.add(photo2);

		toMapView = new JButton("To Map View");
		toMapView.setBackground(Color.white);
		toMapView.setBounds(1100, 550, 150, 50);
		toMapView.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		toMapView.addActionListener(this);
		toMapView.setEnabled(false);
		battleView.add(toMapView);

		battleView.revalidate();
		battleView.repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		Unit chosenUnit = null;

		if (e.getSource() == toMapView) {
			mapo.getWorldview().setVisible(true);
			battleView.setVisible(false);
			mapo.reloadarmies();
			mapo.handlearmies();
			battleView.dispose();

		}

		if (attackingButtons.contains(clickedButton)) {
			chosenUnitsArea.setVisible(false);
			for (int i = 0; i < attackingButtons.size(); i++) {
				if (!attackingButtons.get(i).equals(clickedButton)) {
					attackingButtons.get(i).setEnabled(true);
				} else {
					attackingButtons.get(i).setEnabled(false);
				}
			}
			for (int i = 0; i < defendingButtons.size(); i++) {
				defendingButtons.get(i).setEnabled(true);
			}
			unitInfoChoose.setBackground(Color.green);
			unitInfoChoose.setForeground(Color.black);
			unitInfoChoose.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			chooseUnitPan.setVisible(true);
			battleLogScroll.setBounds(650, 150, 600, 380);
			unitInfo.setVisible(true);
			unitInfoChoose.setVisible(true);
			unitInfoCancel.setVisible(true);
			clickedButton.setEnabled(false);
			int unitNumber = attackingButtons.indexOf(clickedButton);
			chosenUnit = attackingArmy.getUnits().get(unitNumber);
			String type = "";
			if (chosenUnit instanceof Archer) {
				type = "Archer";
			} else if (chosenUnit instanceof Infantry) {
				type = "Infantry";
			} else if (chosenUnit instanceof Cavalry) {
				type = "Cavalry";
			}
			unitInfo.setText("Type: " + type + " \nLevel: " + chosenUnit.getLevel() + "\nSoldier Count: "
					+ chosenUnit.getCurrentSoldierCount() + "\nMax Soldier Count: " + chosenUnit.getMaxSoldierCount());

		} else if (defendingButtons.contains(clickedButton)) {
			chosenUnitsArea.setVisible(false);
			chooseUnitPan.setVisible(true);
			for (int i = 0; i < defendingButtons.size(); i++) {
				if (!defendingButtons.get(i).equals(clickedButton)) {
					defendingButtons.get(i).setEnabled(true);
				} else {
					defendingButtons.get(i).setEnabled(false);
				}
			}
			for (int i = 0; i < attackingButtons.size(); i++) {
				attackingButtons.get(i).setEnabled(true);
			}

			unitInfoChoose.setBackground(Color.red);
			unitInfoChoose.setForeground(Color.white);
			unitInfoChoose.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
			battleLogScroll.setBounds(650, 150, 600, 380);
			unitInfo.setVisible(true);
			unitInfoChoose.setVisible(true);
			unitInfoCancel.setVisible(true);
			clickedButton.setEnabled(false);
			int unitNumber = defendingButtons.indexOf(clickedButton);
			chosenUnit = defendingArmy.getUnits().get(unitNumber);
			String type = "";
			if (chosenUnit instanceof Archer) {
				type = "Archer";
			} else if (chosenUnit instanceof Infantry) {
				type = "Infantry";
			} else if (chosenUnit instanceof Cavalry) {
				type = "Cavalry";
			}
			unitInfo.setText("Type: " + type + " \nLevel: " + chosenUnit.getLevel() + "\nSoldier Count: "
					+ chosenUnit.getCurrentSoldierCount() + "\nMax Soldier Count: " + chosenUnit.getMaxSoldierCount());
		} else if (clickedButton.equals(unitInfoCancel)) {
			chooseUnitPan.setVisible(false);
			for (int i = 0; i < attackingButtons.size(); i++) {
				attackingButtons.get(i).setEnabled(true);
			}
			for (int i = 0; i < defendingButtons.size(); i++) {
				defendingButtons.get(i).setEnabled(true);
			}
			chosenUnitsArea.setVisible(true);
			battleLogScroll.setBounds(650, 80, 600, 450);
			unitInfo.setVisible(false);
			unitInfoChoose.setVisible(false);
			unitInfoCancel.setVisible(false);
		} else if (clickedButton.equals(simBattle)) {
			try {
				game.autoResolve(attackingArmy, defendingArmy);
				this.determineWinner(attackingArmy, defendingArmy);
				this.updateArmyList(attackingArmy, defendingArmy);
				chosenUnitsArea.setVisible(false);
				simBattle.setEnabled(false);
			} catch (FriendlyFireException v) {
				JOptionPane.showMessageDialog(null, v.getMessage(), "The Conqueror", JOptionPane.ERROR_MESSAGE);
			}
		} else if (clickedButton.equals(unitInfoChoose)) {
			chooseUnitPan.setVisible(false);
			for (int i = 0; i < attackingButtons.size(); i++) {
				if (!attackingButtons.get(i).isEnabled()) {
					attackingUnit = attackingArmy.getUnits().get(i);
				}
			}
			for (int i = 0; i < defendingButtons.size(); i++) {
				if (!defendingButtons.get(i).isEnabled()) {
					defendingUnit = defendingArmy.getUnits().get(i);
				}
			}
			chosenUnitsArea.setVisible(true);
			chosenUnitsArea.setText(this.ChosenUnitText(attackingUnit, defendingUnit));
			battleLogScroll.setBounds(650, 80, 600, 450);
			unitInfo.setVisible(false);
			unitInfoChoose.setVisible(false);
			unitInfoCancel.setVisible(false);
		} else if (clickedButton.equals(startBattle)) {
			if (attackingUnit == null) {
				JOptionPane.showMessageDialog(null, "Please choose an attacking unit first from your army.",
						"Choose Unit", JOptionPane.WARNING_MESSAGE);
			} else if (defendingUnit == null) {
				JOptionPane.showMessageDialog(null, "Please choose a unit to attack from the defending army.",
						"Choose Unit", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					logMessage += "-> " + game.getPlayer().getName() + ": " + getType(attackingUnit) + " Level "
							+ attackingUnit.getLevel() + " is preparing for Battle.\n";
					attackingUnit.attack(defendingUnit);
					if (defendingUnit.getCurrentSoldierCount() == 0) {
						logMessage += "-> " + game.getPlayer().getName() + ": " + getType(attackingUnit) + " Level "
								+ attackingUnit.getLevel() + getRandomCommentary() + getType(defendingUnit) + " Level "
								+ defendingUnit.getLevel() + "\n";
						logMessage += "-> " + game.getPlayer().getName() + ": "
								+ " All the unit soldiers were Killed...\n"
								+ "----------------------------------------------------------------------------------"
								+ "\n";
					} else {
						logMessage += "-> " + game.getPlayer().getName() + ": " + getType(attackingUnit) + " Level "
								+ attackingUnit.getLevel() + getRandomCommentary() + getType(defendingUnit) + " Level "
								+ defendingUnit.getLevel() + " " + "with the soldier count of the latter being "
								+ defendingUnit.getCurrentSoldierCount() + "\n"
								+ "----------------------------------------------------------------------------------"
								+ "\n";
					}
					battleLog.setText(logMessage);
					this.updateArmyList(attackingArmy, defendingArmy);
					Random rand = new Random();
					int n = rand.nextInt(attackingArmy.getUnits().size());
					Unit playerUnit = attackingArmy.getUnits().get(n);
					n = rand.nextInt(defendingArmy.getUnits().size());
					Unit bot = defendingArmy.getUnits().get(n);
					logMessage += "-> Bot: " + getType(bot) + " Level " + bot.getLevel()
							+ " is preparing for Battle.\n";
					bot.attack(playerUnit);
					if (playerUnit.getCurrentSoldierCount() == 0) {
						logMessage += "-> Bot: " + getType(bot) + " Level " + bot.getLevel() + getRandomCommentary()
								+ getType(playerUnit) + " Level " + playerUnit.getLevel() + "\n";
						logMessage += "-> Bot: All the unit soldiers were Killed...\n"
								+ "----------------------------------------------------------------------------------"
								+ "\n";
					} else {
						logMessage += "-> Bot: " + getType(bot) + " Level " + bot.getLevel() + getRandomCommentary()
								+ getType(playerUnit) + " Level " + playerUnit.getLevel() + " "
								+ "with the soldier count of the latter being " + playerUnit.getCurrentSoldierCount()
								+ "\n"
								+ "----------------------------------------------------------------------------------"
								+ "\n";
					}
					battleLog.setText(logMessage);
					this.updateArmyList(attackingArmy, defendingArmy);
					this.attackingUnit = null;
					this.defendingUnit = null;
					chosenUnitsArea.setText(this.ChosenUnitText(attackingUnit, defendingUnit));
					this.determineWinner(attackingArmy, defendingArmy);
					toMapView.setEnabled(false);
					if (attackingArmy.getUnits().size() != 0 && defendingArmy.getUnits().size() == 0) {
						game.occupy(attackingArmy, defendingArmy.getCurrentLocation());
					}
				} catch (FriendlyFireException v1) {
					JOptionPane.showMessageDialog(null, v1.getMessage(), "The Conqueror", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		battleView.revalidate();
		battleView.repaint();

	}

	public String getType(Unit chosenUnit) {
		if (chosenUnit instanceof Archer) {
			return "Archer";
		} else if (chosenUnit instanceof Infantry) {
			return "Infantry";
		} else if (chosenUnit instanceof Cavalry) {
			return "Cavalry";
		} else {
			return "";
		}
	}

	public static String getRandomCommentary() {
		String[] messages = { " attacked brutally ", " came victorious over ", " faught hard against ", " annihilated ",
				" attacked resourcefully " };
		Random rand = new Random();
		return messages[rand.nextInt(messages.length)];
	}

	public String ChosenUnitText(Unit aUnit, Unit dUnit) {
		if (aUnit == null && dUnit == null) {
			return "Please choose a unit to attack with.\nPlease choose a unit to attack.";
		} else if (aUnit != null && dUnit == null) {
			return "Attacking Unit: " + this.getType(aUnit) + ", Level: " + aUnit.getLevel() + ", Soldier Count: "
					+ aUnit.getCurrentSoldierCount() + ", Max Soldier Count: " + aUnit.getMaxSoldierCount() + ".\n"
					+ "Please choose a unit to attack";

		} else if (aUnit == null && dUnit != null) {
			return "Please choose a unit to attack with.\n" + "Defending Unit: " + this.getType(dUnit) + ", Level: "
					+ dUnit.getLevel() + ", Soldier Count: " + dUnit.getCurrentSoldierCount() + ", Max Soldier Count: "
					+ dUnit.getMaxSoldierCount();

		} else if (aUnit != null && dUnit != null) {
			return "Attacking Unit: " + this.getType(aUnit) + ", Level: " + aUnit.getLevel() + ", Soldier Count: "
					+ aUnit.getCurrentSoldierCount() + ", Max Soldier Count: " + aUnit.getMaxSoldierCount() + ".\n"
					+ "Defending Unit: " + this.getType(dUnit) + ", Level: " + dUnit.getLevel() + ", Soldier Count: "
					+ dUnit.getCurrentSoldierCount() + ", Max Soldier Count: " + dUnit.getMaxSoldierCount();
		} else {
			return "";
		}
	}

	public void determineWinner(Army attackingArmy, Army defendingArmy) {
		toMapView.setEnabled(true);
		if (attackingArmy.getUnits().size() == 0) {
			updateArmies(game);
			startBattle.setEnabled(false);
			logMessage += "-> Unfortunately,The Defending Army succeeded in defending their city, Come back stronger!";
			battleLog.setText(logMessage);
		} else if (defendingArmy.getUnits().size() == 0) {
			updateArmies(game);
			startBattle.setEnabled(false);
			logMessage += "-> Congratulations, You have succeeded in conquering " + defendingArmy.getCurrentLocation()
					+ ". Well Done !";
			battleLog.setText(logMessage);
		}
	}

	public void updateArmyList(Army attackingArmy, Army defendingArmy) {
		attpanel.removeAll();
		defpanel.removeAll();
		attackingButtons.clear();
		defendingButtons.clear();
		for (int i = 0; i < attackingArmy.getUnits().size(); i++) {
			Unit unit = attackingArmy.getUnits().get(i);
			String type = "";
			if (unit instanceof Archer) {
				type = "Archer";
			} else if (unit instanceof Infantry) {
				type = "Infantry";
			} else if (unit instanceof Cavalry) {
				type = "Cavalry";
			}
			String butText = type + " Level:" + unit.getLevel();
			JButton unitButton = new JButton(butText);
			unitButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			unitButton.addActionListener(this);
			attackingButtons.add(unitButton);
			attpanel.add(unitButton);
		}

		for (int i = 0; i < defendingArmy.getUnits().size(); i++) {
			Unit unit = defendingArmy.getUnits().get(i);
			String type = "";
			if (unit instanceof Archer) {
				type = "Archer";
			} else if (unit instanceof Infantry) {
				type = "Infantry";
			} else if (unit instanceof Cavalry) {
				type = "Cavalry";
			}
			String butText = type + " Level:" + unit.getLevel();
			JButton unitButton = new JButton(butText);
			unitButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			unitButton.addActionListener(this);
			defendingButtons.add(unitButton);
			defpanel.add(unitButton);
		}
	}

	public void updateArmies(Game game) {
		ArrayList<Army> playerArmies = game.getPlayer().getControlledArmies();
		for (int i = 0; i < playerArmies.size(); i++) {
			if (playerArmies.get(i).getUnits().size() == 0) {
				playerArmies.remove(playerArmies.get(i));
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Game g = new Game("Kayed", "Rome");
		BattleView b = new BattleView(g, g.getAvailableCities().get(1).getDefendingArmy(),
				g.getAvailableCities().get(0).getDefendingArmy(), new MapView(g));
	}

}
