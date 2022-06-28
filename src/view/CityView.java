package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import engine.City;
import engine.Game;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.MaxCapacityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class CityView extends JFrame implements ActionListener {
	private City city;
	private boolean intiating;
	private Game game;
	private JFrame cityview;
	private ArrayList<Army> actualarmy;
	private ArrayList<JButton> armylist;
	private ArrayList<JButton> unitlist;
	private JLabel levelbarracks;
	private JLabel levelarchery;
	private JLabel levelstable;
	private JLabel levelfarm;
	private JLabel levelmarket;
	private JPanel buildings;
	private JButton butbarracks;
	private JButton butarchery;
	private JButton butstable;
	private JButton butfarm;
	private JButton butmarket;
	private JPanel buildinfo;
	private JButton upbutton;
	private JButton recbutton;
	private JButton endturn;
	private JLabel extra;
	private JButton buildingchoice;
	private JButton tomapview;
	private JButton armychoice;
	private JPanel armies;
	private JButton buy;
	private JComboBox<String> combo;
	private JButton defending;
	private JPanel armyinfo2;
	private JList list;
	private JLabel nocombo;
	private ArrayList<Army> refrence;
	private JOptionPane buying;
	private JButton lastclicked;
	private JLabel pgold;
	private JLabel pfood;
	private JLabel pturn;
	private Market market;
	private Farm farm;
	private Barracks barracks;
	private Stable stable;
	private ArcheryRange archeryrange;
	private MapView mapview;
	private JButton initiatebut;
	private JFrame done;

	public JButton getInitiatebut() {
		return initiatebut;
	}

	private JScrollPane scrollableList;
	private JPanel com;
	private Unit chosenunit;
	private Army chosenarmy;
	private JLabel comtext;

	public JButton getbuy() {
		return this.buy;
	}

	public JFrame getcityview() {
		return this.cityview;
	}

	public CityView(City c, Game g, MapView w) {
		armylist = new ArrayList<JButton>();
		actualarmy = new ArrayList<Army>();
		initiatebut = new JButton("Initiate");
		initiatebut.addActionListener(this);
		unitlist = new ArrayList<JButton>();
		initiatebut.setBounds(50, 400, 260, 50);
		initiatebut.setEnabled(false);
		mapview = w;

		farm = null;
		market = null;
		barracks = null;
		stable = null;
		archeryrange = null;

		city = c;
		lastclicked = new JButton();
		game = g;

		buying = new JOptionPane();
		cityview = new JFrame();
		cityview.setUndecorated(true);
		cityview.setContentPane(new JLabel(new ImageIcon("cityView.jpg")));
		buy = new JButton("Buy");
		buy.addActionListener(this);
		buildingchoice = new JButton("Buildings");
		tomapview = new JButton("Map View");
		armies = new JPanel();
		armies.setLayout(null);
		refrence = new ArrayList<Army>();

		buildingchoice.setEnabled(false);
		armychoice = new JButton("Armies");
		armychoice.addActionListener(this);
		tomapview.addActionListener(this);
		buildingchoice.setBounds(420, 20, 200, 40);
		armychoice.setBounds(670, 20, 200, 40);
		tomapview.setBounds(985, 20, 200, 40);
		buildingchoice.setFocusable(false);
		armychoice.setFocusable(false);
		tomapview.setFocusable(false);
		buildingchoice.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
		buildingchoice.addActionListener(this);

		armychoice.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
		tomapview.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));

		armyinfo2 = new JPanel();
		armyinfo2.setLayout(new GridLayout(0, 1));
		armyinfo2.setBounds(390, 30, 500, 347);
		armyinfo2.setBackground(Color.orange);

		armyinfo2.setOpaque(true);
		scrollableList = new JScrollPane(armyinfo2);
		scrollableList.setBounds(370, 30, 500, 347);

		armies.add(scrollableList);
		armies.add(initiatebut);

		JLabel defendinglab = new JLabel("The Defending Army of " + c.getName());
		defendinglab.setBounds(10, 350, 340, 25);
		defendinglab.setOpaque(true);
		defendinglab.setBackground(Color.ORANGE);
		defendinglab.setHorizontalAlignment(JLabel.CENTER);
		defendinglab.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		armies.add(defendinglab);
		defending = new JButton();
		defending.setIcon(new ImageIcon("rsz_defendingarmy.jpg"));
		defending.setBounds(30, 30, 300, 300);
		defending.addActionListener(this);
		armies.add(defending);
		levelbarracks = new JLabel();
		buildings = new JPanel();
		buildinfo = new JPanel();
		upbutton = new JButton("Upgrade");
		recbutton = new JButton("Recruit");
		endturn = new JButton("End Turn");

		buy.setBounds(500, 300, 250, 50);
		upbutton.setBounds(500, 360, 250, 50);
		recbutton.setBounds(500, 420, 250, 50);
		endturn.setBounds(500, 480, 250, 50);
		endturn.setFocusable(false);

		buildings.add(buy);
		buildings.add(upbutton);
		buildings.add(recbutton);
		buildings.add(endturn);
		upbutton.setEnabled(false);
		recbutton.setEnabled(false);
		buy.setEnabled(false);

		comtext = new JLabel("Current Armies in " + c.getName());
		comtext.setBounds(920, 30, 300, 30);
		comtext.setBackground(Color.ORANGE);
		comtext.setHorizontalAlignment(JLabel.CENTER);
		comtext.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
		comtext.setOpaque(true);
		com = new JPanel();
		com.setLayout(new GridLayout(0, 1));
		com.setBounds(920, 60, 350, 317);
		com.setBackground(Color.ORANGE);
		com.setOpaque(true);
		JScrollPane tarek = new JScrollPane(com);
		tarek.setBounds(920, 60, 300, 317);

		combo = new JComboBox();
		combo.setBounds(920, 30, 300, 30);
		nocombo = new JLabel("There are currently no armies in this city");
		nocombo.setBounds(920, 30, 300, 40);
		nocombo.setVisible(false);
//		updatecomb();

		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));

		butbarracks = new JButton();
		butbarracks.addActionListener(this);
		butarchery = new JButton();
		butarchery.addActionListener(this);
		butstable = new JButton();
		butstable.addActionListener(this);
		butfarm = new JButton();
		butfarm.addActionListener(this);
		butmarket = new JButton();
		butmarket.addActionListener(this);

		recbutton.addActionListener(this);
		endturn.addActionListener(this);

		JLabel labbarracks = new JLabel();
		JLabel labarchery = new JLabel();
		JLabel labstable = new JLabel();
		JLabel labfarm = new JLabel();
		JLabel labmarket = new JLabel();

		labbarracks.setBounds(50, 230, 250, 20);
		labbarracks.setText("Barracks");
		labbarracks.setBackground(Color.orange);
		labbarracks.setHorizontalAlignment(JLabel.CENTER);
		labbarracks.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		labbarracks.setOpaque(true);

		labarchery.setBounds(500, 230, 250, 20);
		labarchery.setText("Archery");
		labarchery.setBackground(Color.orange);
		labarchery.setHorizontalAlignment(JLabel.CENTER);
		labarchery.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		labarchery.setOpaque(true);

		labstable.setBounds(940, 230, 250, 20);
		labstable.setText("Stable");
		labstable.setBackground(Color.orange);
		labstable.setHorizontalAlignment(JLabel.CENTER);
		labstable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		labstable.setOpaque(true);

		labfarm.setBounds(50, 510, 250, 20);
		labfarm.setText("Farm");
		labfarm.setBackground(Color.orange);
		labfarm.setHorizontalAlignment(JLabel.CENTER);
		labfarm.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		labfarm.setOpaque(true);

		labmarket.setBounds(940, 510, 250, 20);
		labmarket.setText("Market");
		labmarket.setBackground(Color.orange);
		labmarket.setHorizontalAlignment(JLabel.CENTER);
		labmarket.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		labmarket.setOpaque(true);

		butbarracks.setBounds(50, 20, 250, 200);
		butbarracks.setIcon(new ImageIcon("rsz_barracks.jpg"));
		butarchery.setBounds(500, 20, 250, 200);
		butarchery.setIcon(new ImageIcon("rsz_archers.jpg"));
		butstable.setBounds(940, 20, 250, 200);
		butstable.setIcon(new ImageIcon("rsz_2stablebig.jpg"));
		butfarm.setBounds(50, 300, 250, 200);
		butmarket.setBounds(940, 300, 250, 200);
		butmarket.setIcon(new ImageIcon("rsz_1marketbig.jpg"));
		butfarm.setIcon(new ImageIcon("rsz_market.jpg"));

		levelbarracks.setBounds(50, 250, 250, 20);
		levelbarracks.setHorizontalAlignment(JLabel.CENTER);
		levelbarracks.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		levelbarracks.setBackground(Color.orange);
		levelbarracks.setOpaque(true);

		levelarchery = new JLabel();
		levelarchery.setBounds(500, 250, 250, 20);
		levelarchery.setHorizontalAlignment(JLabel.CENTER);
		levelarchery.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		levelarchery.setBackground(Color.orange);
		levelarchery.setOpaque(true);

		levelstable = new JLabel();
		levelstable.setBounds(940, 250, 250, 20);
		levelstable.setHorizontalAlignment(JLabel.CENTER);
		levelstable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		levelstable.setBackground(Color.orange);
		levelstable.setOpaque(true);

		levelfarm = new JLabel();
		levelfarm.setBounds(50, 530, 250, 20);
		levelfarm.setHorizontalAlignment(JLabel.CENTER);
		levelfarm.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		levelfarm.setBackground(Color.orange);
		levelfarm.setOpaque(true);

		levelmarket = new JLabel();
		levelmarket.setBounds(940, 530, 250, 20);
		levelmarket.setHorizontalAlignment(JLabel.CENTER);
		levelmarket.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		levelmarket.setBackground(Color.orange);
		levelmarket.setOpaque(true);

		JLabel pname = new JLabel();
		pname.setBounds(20, 20, 300, 20);
		pname.setVerticalAlignment(JLabel.TOP);
		pname.setText("Name: " + game.getPlayer().getName());
		pname.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		pname.setBackground(Color.ORANGE);
		pname.setOpaque(true);

		pturn = new JLabel();
		pturn.setBounds(20, 40, 300, 20);
		pturn.setVerticalAlignment(JLabel.TOP);
		pturn.setText("Current Turn: " + game.getCurrentTurnCount());
		pturn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		pturn.setBackground(Color.ORANGE);
		pturn.setOpaque(true);

		pfood = new JLabel();
		pfood.setBounds(20, 60, 300, 20);
		pfood.setVerticalAlignment(JLabel.TOP);
		pfood.setText("Food: " + game.getPlayer().getFood());
		pfood.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		pfood.setBackground(Color.ORANGE);
		pfood.setOpaque(true);

		pgold = new JLabel();
		pgold.setBounds(20, 80, 300, 20);
		pgold.setVerticalAlignment(JLabel.TOP);
		pgold.setText("Gold: " + game.getPlayer().getTreasury());
		pgold.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		pgold.setBackground(Color.ORANGE);
		pgold.setOpaque(true);

		cityview.setLayout(null);
		armies.setOpaque(false);
//		armies.setBackground(Color.blue);
		armies.add(tarek);
		armies.add(comtext);
		armies.setBounds(20, 100, 1240, 570);
		buildings.setLayout(null);
		buildings.setBounds(20, 100, 1240, 570);
		buildings.setOpaque(false);
//		buildings.setBackground(Color.yellow);
		buildings.add(labbarracks);
		buildings.add(labarchery);
		buildings.add(labstable);
		buildings.add(labfarm);
		buildings.add(labmarket);

		upbutton.addActionListener(this);

		buildings.add(butbarracks);
		buildings.add(butarchery);
		buildings.add(butstable);
		buildings.add(butfarm);
		buildings.add(butmarket);

		buildings.add(levelbarracks);
		buildings.add(levelarchery);
		buildings.add(levelstable);
		buildings.add(levelfarm);
		buildings.add(levelmarket);
		upgradelevels();
		handlearmies();

		cityview.setDefaultCloseOperation(EXIT_ON_CLOSE);
		cityview.setTitle("The Conqueror");
		cityview.setExtendedState(JFrame.MAXIMIZED_BOTH);
		cityview.add(armychoice);
		cityview.add(tomapview);
		cityview.add(buildingchoice);
		cityview.add(armies);
		cityview.add(buildings);
		cityview.add(pname);
		cityview.add(pturn);
		cityview.add(pfood);
		cityview.add(pgold);
//		armies.add(combo);
		armies.setVisible(false);
		armies.add(nocombo);
		cityview.setVisible(true);

	}

	public JPanel getArmyinfo2() {
		return armyinfo2;
	}

	public void actionPerformed(ActionEvent e) {
		recbutton.setEnabled(false);
		upbutton.setEnabled(false);
		buy.setEnabled(false);
		if (game.isGameOver()) {
			if (game.getPlayer().getControlledCities().size() == game.getAvailableCities().size()) {
				displayEndWindow("Congratulations! You have succeeded in conquering the world.");
			} else {
//				fass----------------------------------------------------------------------------
				displayEndWindow("You have failed to conquer all the cities! Try again later");

			}
			cityview.setVisible(false);

		}


		if (intiating)
			initiatebut.setEnabled(true);
		else
			initiatebut.setEnabled(false);

		JButton pressed = (JButton) e.getSource();

//		if (e.getSource() == endturn) {
//
//			int resp = JOptionPane.showConfirmDialog(null, "Would you like to end the turn?", "Ending Turn",
//					JOptionPane.YES_NO_OPTION);
//			if (resp == JOptionPane.YES_OPTION) {
//				game.endTurn();
//				upgradelevels();
//				armyinfo2.removeAll();
//				handlearmies();
//				refreshprog();
//			}
//		}

		if (e.getSource() == endturn) {
			endturn.setVisible(true);
			boolean flag = false;
			ArrayList<City> citiesToFight = mapview.shouldFight();
			for (int k = 0; k < citiesToFight.size(); k++) {
				for (int i = 0; i < game.getPlayer().getControlledArmies().size(); i++) {
					if (game.getPlayer().getControlledArmies().get(i).getCurrentLocation()
							.equalsIgnoreCase(citiesToFight.get(k).getName())) {
						JOptionPane.showMessageDialog(null,
								"The number of turn exceeded the maximum number, The army has to attack now!",
								"The Conqueror", JOptionPane.ERROR_MESSAGE);
						new BattleView(game, game.getPlayer().getControlledArmies().get(i),
								citiesToFight.get(k).getDefendingArmy(), mapview);
						return;
					}
				}

			}

			int resp = JOptionPane.showConfirmDialog(null, "Would you like to end the turn?", "Ending Turn",
					JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION) {

				game.endTurn();

				refreshprog();
				handlearmies();

				for (int i = 0; i < game.getPlayer().getControlledArmies().size(); i++) {
					Army a = game.getPlayer().getControlledArmies().get(i);

					if (a.getDistancetoTarget() == 0 && a.getCurrentStatus() == Status.IDLE
							&& a.getTarget().equalsIgnoreCase("")) {

						int reply = JOptionPane.showConfirmDialog(null, "Do You want to lay seiege?", "Ending Turn",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							City x = null;
							for (int j = 0; j < game.getAvailableCities().size(); j++) {
								if (game.getAvailableCities().get(j).getName().equalsIgnoreCase(a.getCurrentLocation()))
									x = game.getAvailableCities().get(j);
							}

							try {
								game.getPlayer().laySiege(a, x);
								handlearmies();

							} catch (TargetNotReachedException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage(), "The Conqueror",
										JOptionPane.ERROR_MESSAGE);
							} catch (FriendlyCityException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage(), "The Conqueror",
										JOptionPane.ERROR_MESSAGE);
							}

						} else {
							City c = null;
							for (int m = 0; m < game.getAvailableCities().size(); m++) {
								if (mapview.getChoice1().getCurrentLocation()
										.equalsIgnoreCase(game.getAvailableCities().get(m).getName())) {
									c = game.getAvailableCities().get(m);
								}
							}

							mapview.setVisible(false);

							new BattleView(game, mapview.getChoice1(), c.getDefendingArmy(), mapview);

							handlearmies();

						}

					}
				}

			}
		}

		if (unitlist.contains(pressed)) {

			if (intiating)
				initiatebut.setEnabled(true);
			else
				initiatebut.setEnabled(false);

			for (int i = 0; i < unitlist.size(); i++) {
				unitlist.get(i).setBackground(Color.white);
				unitlist.get(i).setEnabled(true);
			}
			pressed.setEnabled(false);
			pressed.setBackground(Color.green);
			int unitnumber = unitlist.indexOf(pressed);
			chosenunit = chosenarmy.getUnits().get(unitnumber);

		}

		if (e.getSource() == initiatebut) {
			initiatebut.setVisible(true);
			int resp = JOptionPane.showConfirmDialog(null, "Would you relocate this unit to the chose Army ?",
					"Initiate new Army", JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION) {
				game.getPlayer().initiateArmy(city, chosenunit);
				reloadunits(city.getDefendingArmy());
				handlearmies();
				armyinfo2.removeAll();

			}
			initiatebut.setEnabled(false);

		}

		if (e.getSource() == butbarracks || e.getSource() == butarchery || e.getSource() == butstable
				|| e.getSource() == butfarm || e.getSource() == butmarket) {
			if ((pressed == butbarracks && levelbarracks.getText().equals("Not bought yet"))
					|| (pressed == butarchery && levelarchery.getText().equals("Not bought yet"))
					|| (pressed == butstable && levelstable.getText().equals("Not bought yet"))
					|| (pressed == butfarm && levelfarm.getText().equals("Not bought yet"))
					|| (pressed == butmarket && levelmarket.getText().equals("Not bought yet"))) {
				buy.setEnabled(true);
			} else {
				upbutton.setEnabled(true);
				if (e.getSource() == butbarracks || e.getSource() == butarchery || e.getSource() == butstable) {
					recbutton.setEnabled(true);
				}

			}
			lastclicked = (JButton) e.getSource();
		}
		if (e.getSource() == armychoice) {
			buildingchoice.setEnabled(true);
			armyinfo2.removeAll();
			initiatebut.setEnabled(false);
			armychoice.setEnabled(false);
			buildings.setVisible(false);
			armies.setVisible(true);

		}
		if (e.getSource() == buildingchoice) {
			buildingchoice.setEnabled(false);
			armychoice.setEnabled(true);
			buildings.setVisible(true);
			armies.setVisible(false);
		}

		if (e.getSource() == defending || armylist.contains(pressed)) {
			Army a = null;
			if (armylist.contains(pressed)) {
				int armynumber = armylist.indexOf(pressed);
				a = actualarmy.get(armynumber);
				intiating = false;
				initiatebut.setEnabled(false);
			} else {
				a = city.getDefendingArmy();
				intiating = true;
				initiatebut.setEnabled(false);
			}
			armyinfo2.removeAll();

			JLabel test = new JLabel();
			reloadunits(a);
			test.setHorizontalAlignment(JLabel.CENTER);

		}

		armyinfo2.repaint();
		armyinfo2.revalidate();

		if (e.getSource() == buy) {
			int x = -1;
			String s = "";
			if (lastclicked == butbarracks) {
				barracks = new Barracks();
				x = 2000;
				s = "Barracks";
			}
			if (lastclicked == butarchery) {
				archeryrange = new ArcheryRange();
				x = 1500;
				s = "ArcheryRange";

			}
			if (lastclicked == butstable) {
				stable = new Stable();
				x = 2500;
				s = "Stable";

			}
			if (lastclicked == butfarm) {
				farm = new Farm();
				x = 1000;
				s = "Farm";

			}
			if (lastclicked == butmarket) {
				market = new Market();
				x = 1500;
				s = "Market";
			}
			int resp = JOptionPane.showConfirmDialog(null, "Would you like to buy the " + s + " for " + x + " gold ?",
					"Buying", JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION) {
				try {
					game.getPlayer().build(s, city.getName());
					pgold.setText("Gold: " + (game.getPlayer().getTreasury()));
					upgradelevels();
					for (int i = 0; i < city.getEconomicalBuildings().size(); i++) {
						Building b = city.getEconomicalBuildings().get(i);
						if (b instanceof Farm)
							farm = (Farm) b;
						if (b instanceof Market)
							market = (Market) b;
					}
					for (int i = 0; i < city.getMilitaryBuildings().size(); i++) {
						Building b = city.getMilitaryBuildings().get(i);
						if (b instanceof Barracks)
							barracks = (Barracks) b;
						if (b instanceof Stable)
							stable = (Stable) b;
						if (b instanceof ArcheryRange)
							archeryrange = (ArcheryRange) b;
					}

				} catch (NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "The Conqueror", JOptionPane.ERROR_MESSAGE);

				}

			}

		}

		if (e.getSource() == upbutton) {
			String s = "";
			Building b = null;
			if (lastclicked == butbarracks) {
				b = barracks;
				s = "Barracks";
			}
			if (lastclicked == butarchery) {
				b = archeryrange;
				s = "ArcheryRange";
			}
			if (lastclicked == butstable) {
				b = stable;
				s = "Stable";
			}
			if (lastclicked == butfarm) {
				b = farm;
				s = "Farm";
			}
			if (lastclicked == butmarket) {
				b = market;
				s = "Market";
			}
			int resp = JOptionPane.showConfirmDialog(null,
					"Would you like to upgrade the " + s + " for " + b.getUpgradeCost() + " gold ?", "Upgrading",
					JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION) {
				try {
					game.getPlayer().upgradeBuilding(b);
					pgold.setText("Gold: " + (game.getPlayer().getTreasury()));
					upgradelevels();
				} catch (BuildingInCoolDownException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "The Conqueror", JOptionPane.ERROR_MESSAGE);
				} catch (MaxLevelException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "The Conqueror", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "The Conqueror", JOptionPane.ERROR_MESSAGE);
				}

			}
		}

		if (e.getSource() == recbutton) {
			String s = "";
			String x = "";
			MilitaryBuilding m = null;
			if (lastclicked == butbarracks) {
				m = barracks;
				s = "Barracks";
				x = "infantry";
			}
			if (lastclicked == butarchery) {
				m = archeryrange;
				s = "ArcheryRange";
				x = "archer";
			}
			if (lastclicked == butstable) {
				m = stable;
				s = "Stable";
				x = "cavalry";
			}
			int resp = JOptionPane.showConfirmDialog(null,
					"Would you like to rceruit a unit from the " + s + " for " + m.getRecruitmentCost() + " gold ?",
					"Recruiting", JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION) {
				try {
					game.getPlayer().recruitUnit(x, city.getName());
					pgold.setText("Gold: " + (game.getPlayer().getTreasury()));

				} catch (BuildingInCoolDownException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "The Conqueror", JOptionPane.ERROR_MESSAGE);
				} catch (MaxRecruitedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "The Conqueror", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "The Conqueror", JOptionPane.ERROR_MESSAGE);
				}
			}

		}

		if (e.getSource() == tomapview) {
			mapview.getWorldview().setVisible(true);
			mapview.refreshprog();
			mapview.handlearmies();
			mapview.reset();
			cityview.setVisible(false);

		}

	}

	public void refreshprog() {
		pgold.setText("Gold: " + game.getPlayer().getTreasury());
		pfood.setText("Food: " + game.getPlayer().getFood());
		pturn.setText("Current Turn: " + game.getCurrentTurnCount());

	}

	public void upgradelevels() {
		levelbarracks.setText("Not bought yet");
		levelarchery.setText("Not bought yet");
		levelstable.setText("Not bought yet");
		levelfarm.setText("Not bought yet");
		levelmarket.setText("Not bought yet");
		for (int i = 0; i < city.getMilitaryBuildings().size(); i++) {
			MilitaryBuilding m = city.getMilitaryBuildings().get(i);
			if (m instanceof Barracks)
				levelbarracks.setText("Level :" + m.getLevel());
			if (m instanceof ArcheryRange)
				levelarchery.setText("Level :" + m.getLevel());
			if (m instanceof Stable)
				levelstable.setText("Level :" + m.getLevel());
		}
		for (int i = 0; i < city.getEconomicalBuildings().size(); i++) {
			EconomicBuilding m = city.getEconomicalBuildings().get(i);
			if (m instanceof Farm)
				levelfarm.setText("Level :" + m.getLevel());
			if (m instanceof Market)
				levelmarket.setText("Level :" + m.getLevel());
		}
		buildings.revalidate();
		buildings.repaint();
	}

	public void handlearmies() {
		com.removeAll();
		armylist.clear();
		actualarmy.clear();
		int counter = 1;
		for (int i = 0; i < this.game.getPlayer().getControlledArmies().size(); i++) {
			JButton idles = new JButton();
			idles.setFocusable(false);
			idles.setText("Army " + counter);
			idles.setPreferredSize(new Dimension(90, 60));
			idles.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			idles.addActionListener(this);
			if (this.game.getPlayer().getControlledArmies().get(i).getCurrentLocation()
					.equalsIgnoreCase(city.getName())) {
				com.add(idles);
				counter++;
				armylist.add(idles);
				actualarmy.add(game.getPlayer().getControlledArmies().get(i));
			}

		}
		armies.revalidate();
		armies.repaint();
	}

	public void reloadunits(Army a) {
		chosenarmy = a;
		unitlist.clear();
		for (int i = 0; i < a.getUnits().size(); i++) {
			Unit currunit = a.getUnits().get(i);
			JPanel infoholder = new JPanel();
			JButton unitcounter = new JButton("Unit " + (i + 1) + ":");
			infoholder.setLayout(new GridLayout(1, 0));
			unitcounter.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
			unitcounter.setHorizontalAlignment(JLabel.CENTER);
			infoholder.add(unitcounter);
			unitcounter.addActionListener(this);
			unitcounter.setFocusable(false);
			JLabel unittype = new JLabel();
			unitlist.add(unitcounter);
			if (currunit instanceof Cavalry)
				unittype.setText("Type : Cavalry");
			if (currunit instanceof Archer)
				unittype.setText("Type : Archer");
			if (currunit instanceof Infantry)
				unittype.setText("Type : Infantry");
			unittype.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
			unittype.setHorizontalAlignment(JLabel.CENTER);
			infoholder.add(unittype);
			JLabel unitlevel = new JLabel("Level: " + currunit.getLevel());
			unitlevel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
			unitlevel.setHorizontalAlignment(JLabel.CENTER);
			infoholder.add(unitlevel);
			JLabel unitcurrcount = new JLabel("Current Count : " + currunit.getCurrentSoldierCount());
			unitcurrcount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
			unitcurrcount.setHorizontalAlignment(JLabel.CENTER);
			infoholder.add(unitcurrcount);
			JLabel unitmaxcount = new JLabel("Max Count : " + currunit.getMaxSoldierCount());
			unitmaxcount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
			unitmaxcount.setHorizontalAlignment(JLabel.CENTER);
			infoholder.add(unitmaxcount);
			infoholder.setBorder(BorderFactory.createLineBorder(Color.black, 1));

			armyinfo2.add(infoholder);
		}
	}
	
	public void displayEndWindow(String message) {
		done = new JFrame();
		done.setDefaultCloseOperation(EXIT_ON_CLOSE);
		done.setTitle("The Conqueror");
		done.setExtendedState(JFrame.MAXIMIZED_BOTH);

		done.setLayout(null);
		JLabel result = new JLabel(message);
		result.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 27));
		result.setBounds(0,300,1500,100);
		done.add(result);
		done.revalidate();
		done.repaint();
		done.setVisible(true);
	}

	public static void main(String[] args) {

	}

}
