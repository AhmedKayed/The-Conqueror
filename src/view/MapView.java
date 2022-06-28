package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;

import buildings.ArcheryRange;
import engine.City;
import engine.Game;
import exceptions.FriendlyCityException;
import exceptions.MaxCapacityException;
import exceptions.TargetNotReachedException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class MapView extends JFrame implements ActionListener {
	ArrayList<JButton> infolist;
	ArrayList<JButton> citylist;
	ArrayList<JButton> unitlist;
	Game game;
	private JFrame worldview;

	public JFrame getWorldview() {
		return worldview;
	}

	private JPanel idlers;
	private JPanel marchers;
	private JPanel besiegers;
	private JPanel info;
	private JPanel marchseige;
	private JLabel r;
	private CityView cairoview;
	private CityView romeview;
	private CityView spartaview;
	private JLabel avcities;
	private JLabel army1;
	private JButton settarget;
	private Army choice1;

	public Army getChoice1() {
		return choice1;
	}

	private boolean settingtarget;
	private JScrollPane scrollableList;
	private JButton relocatebut;
	private JButton fightbut;
	private JButton initiatebut;

	public JButton getInitiatebut() {
		return initiatebut;
	}

	private Unit chosenunit;
	private boolean unitaction;
	private boolean relocating;
	private JLabel pname;
	private JLabel pfood;
	private JLabel pgold;
	private JLabel pturn;
	private ArrayList<Boolean> boolarray;
	private JFrame done;

	public JFrame getDone() {
		return done;
	}

	public JLabel getDonetext() {
		return donetext;
	}

	private JLabel donetext;

	public MapView(Game g) {
		boolarray = new ArrayList<Boolean>();
		settingtarget = false;
		unitlist = new ArrayList<JButton>();
		cairoview = null;
		romeview = null;
		spartaview = null;
		infolist = new ArrayList<JButton>();
		citylist = new ArrayList<JButton>();
		game = g;
		worldview = new JFrame();
		worldview.setUndecorated(true);
		worldview.setContentPane(new JLabel(new ImageIcon("worldmap22.jpg")));
		worldview.setLayout(null);
		worldview.setDefaultCloseOperation(EXIT_ON_CLOSE);
		worldview.setTitle("The Conqueror");
		worldview.setExtendedState(JFrame.MAXIMIZED_BOTH);
		done = new JFrame();
		done.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		done.setExtendedState(JFrame.MAXIMIZED_BOTH);
		donetext = new JLabel();

		JPanel targets = new JPanel();
		targets.setBounds(340, 60, 600, 60);
//		targets.setBackground(Color.cyan);
		targets.setOpaque(false);

		army1 = new JLabel();
		army1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		army1.setHorizontalAlignment(JLabel.CENTER);
		army1.setBounds(420, 20, 440, 40);
		army1.setBackground(Color.DARK_GRAY);
		army1.setOpaque(true);
		army1.setForeground(Color.white);
		worldview.add(army1);

		settarget = new JButton("Set Target");
		settarget.setBackground(Color.white);
		settarget.setFocusable(false);
		settarget.setPreferredSize(new Dimension(200, 50));
		targets.add(settarget);
		settarget.setEnabled(false);

		pname = new JLabel();
		pname.setBounds(20, 20, 300, 20);
		pname.setVerticalAlignment(JLabel.TOP);
		pname.setText("Name: " + game.getPlayer().getName());
		pname.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		pname.setBackground(Color.DARK_GRAY);
		pname.setForeground(Color.white);

		pname.setOpaque(true);

		pturn = new JLabel();
		pturn.setBounds(20, 40, 300, 20);
		pturn.setVerticalAlignment(JLabel.TOP);
		pturn.setText("Current Turn: " + game.getCurrentTurnCount());
		pturn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		pturn.setBackground(Color.DARK_GRAY);
		pturn.setForeground(Color.white);

		pturn.setOpaque(true);

		pfood = new JLabel();
		pfood.setBounds(20, 60, 300, 20);
		pfood.setVerticalAlignment(JLabel.TOP);
		pfood.setText("Food: " + game.getPlayer().getFood());
		pfood.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		pfood.setBackground(Color.DARK_GRAY);
		pfood.setForeground(Color.white);

		pfood.setOpaque(true);

		pgold = new JLabel();
		pgold.setBounds(20, 80, 300, 20);
		pgold.setVerticalAlignment(JLabel.TOP);

		pgold.setText("Gold: " + game.getPlayer().getTreasury());
		pgold.setBackground(Color.DARK_GRAY);
		pgold.setForeground(Color.white);
		pgold.setOpaque(true);

		pgold.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

		avcities = new JLabel();
		avcities.setLayout(null);
		avcities.setIcon(new ImageIcon("rsz_fortnitemap.jpg"));
		avcities.setBounds(960, 40, 300, 180);
		avcities.setBackground(Color.pink);
		avcities.setOpaque(true);

		reloadarmies();

//		for (int i = 0; i < game.getAvailableCities().size(); i++) {
//			JButton c = new JButton(game.getAvailableCities().get(i).getName());
//			c.setPreferredSize(new Dimension(90, 50));
//			c.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
//			c.setBackground(Color.DARK_GRAY);
//			c.setFocusable(false);
//			c.setOpaque(true);
//			c.setForeground(Color.white);
//			c.setHorizontalAlignment(JLabel.CENTER);
//			c.addActionListener(this);
//			avcities.add(c);
//			citylist.add(c);
//		}

		JLabel idletitle = new JLabel("Idle Armies");
		idletitle.setForeground(Color.white);
		idletitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		idletitle.setBackground(Color.DARK_GRAY);
		idletitle.setOpaque(true);
		idletitle.setBounds(960, 230, 300, 30);
		idletitle.setHorizontalAlignment(JLabel.CENTER);

		idlers = new JPanel();
		idlers.setLayout(new GridLayout(0, 1));
		idlers.setBounds(960, 260, 300, 350);
		idlers.setBackground(Color.DARK_GRAY);
		idlers.setOpaque(true);

		JScrollPane idlescroll = new JScrollPane(idlers);
		idlescroll.setBounds(960, 260, 300, 350);

		JLabel marchtitle = new JLabel("Marching Armies");
		marchtitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		marchtitle.setBackground(Color.DARK_GRAY);
		marchtitle.setForeground(Color.white);
		marchtitle.setOpaque(true);
		marchtitle.setBounds(20, 130, 300, 30);
		marchtitle.setHorizontalAlignment(JLabel.CENTER);

		marchers = new JPanel();
		marchers.setBounds(20, 160, 300, 200);
		marchers.setBackground(Color.DARK_GRAY);
		marchers.setOpaque(true);
		marchers.setLayout(new GridLayout(0, 1));
		JScrollPane marchscroll = new JScrollPane(marchers);
		marchscroll.setBounds(20, 160, 300, 200);

		JLabel beseigetitle = new JLabel("Besieging Armies");
		beseigetitle.setForeground(Color.white);
		beseigetitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		beseigetitle.setBackground(Color.DARK_GRAY);
		beseigetitle.setOpaque(true);
		beseigetitle.setBounds(20, 380, 300, 30);
		beseigetitle.setHorizontalAlignment(JLabel.CENTER);

		besiegers = new JPanel();
		besiegers.setBounds(20, 410, 300, 200);
		besiegers.setBackground(Color.DARK_GRAY);
		besiegers.setOpaque(true);
		besiegers.setLayout(new GridLayout(0, 1));
		JScrollPane besigescroll = new JScrollPane(besiegers);
		besigescroll.setBounds(20, 160, 300, 200);

		handlearmies();
		settarget.addActionListener(this);

		r = new JLabel();
		marchseige = new JPanel();
		r.setBounds(330, 155, 620, 75);
		r.setBackground(Color.DARK_GRAY);
		r.setForeground(Color.white);
		r.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		r.setOpaque(true);
		r.setHorizontalAlignment(JLabel.CENTER);

		marchseige.setBounds(330, 155, 620, 75);
		marchseige.setBackground(Color.DARK_GRAY);
		marchseige.setForeground(Color.white);
		marchseige.setOpaque(true);

		info = new JPanel();
		info.setLayout(new GridLayout(0, 1));
		info.setBounds(390, 230, 500, 380);
		info.setBackground(Color.DARK_GRAY);
		info.setOpaque(true);
		relocatebut = new JButton("Relocate");
		fightbut = new JButton("Fight");
		fightbut.setBackground(Color.white);
		fightbut.setFocusable(false);

		initiatebut = new JButton("End Turn");
		initiatebut.setBackground(Color.white);
		initiatebut.setFocusable(false);

		relocatebut.addActionListener(this);

		initiatebut.addActionListener(this);
		fightbut.addActionListener(this);

		relocatebut.setBounds(420, 460, 150, 50);
		fightbut.setBounds(700, 460, 150, 50);
		initiatebut.setBounds(562, 530, 150, 50);
		fightbut.setEnabled(false);
		relocatebut.setEnabled(false);
		worldview.add(fightbut);
		worldview.add(initiatebut);
		worldview.add(relocatebut);
		worldview.add(r);
		scrollableList = new JScrollPane(info);
		scrollableList.setBounds(330, 230, 620, 200);
		worldview.add(scrollableList);
		worldview.add(beseigetitle);
		worldview.add(besiegers);
		worldview.add(marchtitle);
		worldview.add(marchers);
		worldview.add(idletitle);
		worldview.add(idlescroll);
		worldview.add(marchscroll);
		worldview.add(besigescroll);
		worldview.add(pname);
		worldview.add(pturn);
		worldview.add(pfood);
		worldview.add(pgold);
		worldview.add(targets);
		worldview.add(avcities);
		worldview.setVisible(true);
	}

	public void handlearmies() {
		idlers.removeAll();
		marchers.removeAll();
		besiegers.removeAll();
		infolist.clear();
		for (int i = 0; i < this.game.getPlayer().getControlledArmies().size(); i++) {
			JButton idles = new JButton();
			idles.setFocusable(false);
			idles.setText("Army " + (i + 1));
			idles.setPreferredSize(new Dimension(90, 60));
			idles.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			idles.addActionListener(this);
			if (this.game.getPlayer().getControlledArmies().get(i).getCurrentStatus() == Status.IDLE)
				idlers.add(idles);
			if (this.game.getPlayer().getControlledArmies().get(i).getCurrentStatus() == Status.MARCHING)
				marchers.add(idles);
			if (this.game.getPlayer().getControlledArmies().get(i).getCurrentStatus() == Status.BESIEGING)
				besiegers.add(idles);
			infolist.add(idles);
			donetext.setHorizontalAlignment(JLabel.CENTER);
			donetext.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
			done.add(donetext, JLabel.CENTER);
		}
		worldview.revalidate();
		worldview.repaint();
	}

	public void actionPerformed(ActionEvent e) {

		if (game.isGameOver()) {
			if (game.getPlayer().getControlledCities().size() == game.getAvailableCities().size()) {
				displayEndWindow("Congratulations! You have succeeded in conquering the world.");
			} else {
//				fass----------------------------------------------------------------------------
				displayEndWindow("You have failed to conquer all the cities! Try again later");

			}
			worldview.setVisible(false);
			return;
		}

//		r.setText("");
		// marchseige.removeAll();
//		fightbut.setEnabled(false);
		relocatebut.setEnabled(false);
		initiatebut.setEnabled(true);

		if (e.getSource() == fightbut) {

			int resp = JOptionPane.showConfirmDialog(null,
					"Would you like to break the seige and attack " + choice1.getCurrentLocation() + "?", "Fighting",
					JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION) {
				City c = null;
				for (int i = 0; i < game.getAvailableCities().size(); i++) {
					if (choice1.getCurrentLocation().equalsIgnoreCase(game.getAvailableCities().get(i).getName())) {
						c = game.getAvailableCities().get(i);
					}
				}

				worldview.setVisible(false);

				new BattleView(game, choice1, c.getDefendingArmy(), this);
				c.setUnderSiege(false);

				for (int i = 0; i < game.getPlayer().getControlledArmies().size(); i++) {
				}
				r.setText("");

				handlearmies();
				army1.setText("");
				r.setText("");
				info.removeAll();

			} else {

			}

		}

//		
		if (e.getSource() == initiatebut) {
			initiatebut.setVisible(true);
			r.setText("");
			army1.setText("");
			relocatebut.setVisible(true);
			boolean flag = false;
			ArrayList<City> citiesToFight = shouldFight();
			for (int k = 0; k < citiesToFight.size(); k++) {
				for (int i = 0; i < game.getPlayer().getControlledArmies().size(); i++) {
					if (game.getPlayer().getControlledArmies().get(i).getCurrentLocation()
							.equalsIgnoreCase(citiesToFight.get(k).getName())) {
						JOptionPane.showMessageDialog(null,
								"The number of turn exceeded the maximum number, The army has to attack now!",
								"The Conqueror", JOptionPane.ERROR_MESSAGE);
						new BattleView(game, game.getPlayer().getControlledArmies().get(i),
								citiesToFight.get(k).getDefendingArmy(), this);
						return;
					}
				}

			}

			int resp = JOptionPane.showConfirmDialog(null, "Would you like to end the turn?", "Ending Turn",
					JOptionPane.YES_NO_OPTION);
			if (resp == JOptionPane.YES_OPTION) {

				game.endTurn();

				relocating = false;
				relocatebut.setBackground(Color.white);
				relocatebut.setEnabled(false);
				settingtarget = false;
				fightbut.setEnabled(false);
				settarget.setBackground(Color.white);
				settarget.setEnabled(false);
				info.removeAll();
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
								if (choice1.getCurrentLocation()
										.equalsIgnoreCase(game.getAvailableCities().get(m).getName())) {
									c = game.getAvailableCities().get(m);
								}
							}

							worldview.setVisible(false);

							new BattleView(game, choice1, c.getDefendingArmy(), this);

							handlearmies();

						}

					}
				}

			}
		}

		if (e.getSource() == relocatebut) {

			initiatebut.setEnabled(true);
			relocatebut.setEnabled(true);
			if (relocating) {
				relocating = false;
				relocatebut.setBackground(Color.white);
			} else {
				initiatebut.setEnabled(true);
				relocatebut.setBackground(Color.green);
				JOptionPane.showMessageDialog(null, "Please chose an army", "Relocate",
						JOptionPane.INFORMATION_MESSAGE);
				relocating = true;
			}
		}

		JButton armybutton = (JButton) e.getSource();
		if (unitlist.contains(armybutton)) {
			settarget.setBackground(Color.white);
			settingtarget = false;

			for (int i = 0; i < unitlist.size(); i++) {
				unitlist.get(i).setBackground(Color.white);
				unitlist.get(i).setEnabled(true);
			}
			armybutton.setEnabled(false);
			armybutton.setBackground(Color.green);
			int unitnumber = unitlist.indexOf(armybutton);
			chosenunit = choice1.getUnits().get(unitnumber);
			relocatebut.setEnabled(true);
			initiatebut.setEnabled(true);
		}

		if (infolist.contains(armybutton)) {
			fightbut.setEnabled(false);
			choice1 = null;

			int armynumber = infolist.indexOf(armybutton);
			Army a = game.getPlayer().getControlledArmies().get(armynumber);
			choice1 = a;
			if (!relocating) {
				for (int i = 0; i < infolist.size(); i++) {
					infolist.get(i).setBackground(Color.white);
					infolist.get(i).setEnabled(true);
				}
			}
			armybutton.setEnabled(false);
			armybutton.setBackground(Color.green);
			if (relocating) {
				int resp = JOptionPane.showConfirmDialog(null,
						"Do you relocate the chosen unit to Army " + (armynumber + 1) + "?", "Relocating",
						JOptionPane.YES_NO_OPTION);
				if (resp == JOptionPane.YES_OPTION) {
					try {
						if (choice1.getCurrentLocation() != chosenunit.getParentArmy().getCurrentLocation()) {
							JOptionPane.showMessageDialog(null,
									"You cant relocate a unit to a new army if they are not in the same city",
									"The Conqueror", JOptionPane.ERROR_MESSAGE);
							relocatebut.setEnabled(false);
							relocatebut.setBackground(Color.white);
							relocating = false;
							handlearmies();
							info.removeAll();
						} else {
							if (chosenunit.getParentArmy().getUnits().size() == 1) {
								Army x = chosenunit.getParentArmy();
								choice1.relocateUnit(chosenunit);
								game.getPlayer().getControlledArmies().remove(x);

							} else {
								choice1.relocateUnit(chosenunit);
							}

							settarget.setBackground(Color.white);
							settarget.setEnabled(false);
							settingtarget = false;
							relocatebut.setEnabled(false);
							relocatebut.setBackground(Color.white);
							relocating = false;
							for (int i = 0; i < infolist.size(); i++) {
								infolist.get(i).setBackground(Color.white);
								infolist.get(i).setEnabled(true);
							}

							armybutton.setEnabled(false);
							armybutton.setBackground(Color.green);

							initiatebut.setEnabled(true);
							reloadunits(choice1);
							info.removeAll();
							handlearmies();

						}
					} catch (MaxCapacityException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "The Conqueror",
								JOptionPane.ERROR_MESSAGE);
						reset();
					}

				} else {
					relocatebut.setBackground(Color.white);
					relocatebut.setEnabled(true);
					relocating = false;
					initiatebut.setEnabled(true);
					for (int i = 0; i < infolist.size(); i++) {
						infolist.get(i).setBackground(Color.white);
						infolist.get(i).setEnabled(true);
					}
				}

			} else {

				info.removeAll();
				r.setText("");
//			marchseige.removeAll();

				army1.setText("Army " + (armynumber + 1) + " is currently in " + a.getCurrentLocation());
				reloadunits(a);
				if (a.getCurrentStatus() == Status.BESIEGING) {
					fightbut.setEnabled(true);
//				JLabel r = new JLabel();
					String x = a.getCurrentLocation();
					City c = null;
					for (int i = 0; i < game.getAvailableCities().size(); i++) {
						if (game.getAvailableCities().get(i).getName().equalsIgnoreCase(x)) {
							c = game.getAvailableCities().get(i);
							break;
						}
					}
					int t = c.getTurnsUnderSiege();
					if (t > 1)
						r.setText("Army " + (1 + armynumber) + " is currently seiging " + x + " for " + t + " turns");
					else
						r.setText("Army " + (1 + armynumber) + " is currently seiging " + x + " for " + t + " turn");
//				r.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
//				r.setForeground(Color.white);
//				marchseige.add(r);

				}

				if (a.getCurrentStatus() == Status.MARCHING) {
					settarget.setEnabled(false);
//				JLabel r = new JLabel();
					if (a.getDistancetoTarget() > 1)
						r.setText("Army " + (1 + armynumber) + " is currently on road to " + a.getTarget()
								+ " and will reach after " + a.getDistancetoTarget() + " turns");
					else
						r.setText("Army " + (1 + armynumber) + " is currently on road to " + a.getTarget()
								+ " and will reach after " + a.getDistancetoTarget() + " turn");
//				r.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
//				marchseige.add(r);
				}

				info.revalidate();
				info.repaint();

			}
		}
		if (citylist.contains(armybutton)) {
			int citynumber = citylist.indexOf(armybutton);
			City c = game.getAvailableCities().get(citynumber);
			if (settingtarget) {
				int resp = JOptionPane.showConfirmDialog(null,
						"Would you like to set the target of this army to " + c.getName(), "Setting Target",
						JOptionPane.YES_NO_OPTION);
				if (resp == JOptionPane.YES_OPTION) {

					game.targetCity(choice1, c.getName());
//					if(game.getPlayer().getControlledCities().contains(c)) {
//						JOptionPane.showMessageDialog(null, "You already control "+c.getName(), "Error", JOptionPane.WARNING_MESSAGE);
//					}
//					else {
//						if(c.isUnderSiege()) {
//							JOptionPane.showMessageDialog(null, "Your army is currently seiging "+c.getName(), "Error", JOptionPane.WARNING_MESSAGE);
//						}else {
//						if(onway(c)) {
//							JOptionPane.showMessageDialog(null, "Another army is currently on their way to "+ c.getName(), "Error", JOptionPane.WARNING_MESSAGE);
//						}else {
//						
//					game.targetCity(choice1, c.getName());
//					}}}
				}
				handlearmies();

			} else {
				if (game.getPlayer().getControlledCities().contains(c)) {
					if (c.getName().equalsIgnoreCase("Cairo")) {
						if (cairoview == null) {
							cairoview = new CityView(c, game, this);
						} else {
							cairoview.refreshprog();
							cairoview.getInitiatebut().setEnabled(false);
							cairoview.getcityview().setVisible(true);
							cairoview.handlearmies();
							cairoview.getArmyinfo2().removeAll();

						}
					} else {

						if (c.getName().equalsIgnoreCase("Rome")) {
							if (romeview == null) {
								romeview = new CityView(c, game, this);
							} else {
								romeview.refreshprog();
								romeview.getcityview().setVisible(true);
								romeview.getInitiatebut().setEnabled(false);
								romeview.handlearmies();
								romeview.getArmyinfo2().removeAll();

							}
						} else {
							if (c.getName().equalsIgnoreCase("Sparta")) {
								if (spartaview == null) {
									spartaview = new CityView(c, game, this);
								} else {
									spartaview.refreshprog();
									spartaview.getcityview().setVisible(true);
									spartaview.getInitiatebut().setEnabled(false);
									spartaview.handlearmies();
									spartaview.getArmyinfo2().removeAll();

								}
							} else {
								new CityView(c, game, this);
							}

						}
					}

					worldview.setVisible(false);
				} else {
					if (unitaction) {
						initiatebut.setEnabled(true);
						relocatebut.setEnabled(true);
					}

					JOptionPane.showMessageDialog(null, "You dont control this city", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}

		if (e.getSource() == settarget) {

			if (settingtarget) {
				settingtarget = false;
				settarget.setBackground(Color.white);
			} else {
				settingtarget = true;
				settarget.setBackground(Color.green);
			}
		}

		if (relocating)
			relocatebut.setEnabled(true);

	}

	public ArrayList<City> shouldFight() {
		ArrayList<City> fights = new ArrayList<City>();
		ArrayList<City> cities = game.getAvailableCities();
		for (int k = 0; k < cities.size(); k++) {
			if (cities.get(k).isUnderSiege() && cities.get(k).getTurnsUnderSiege() == 3) {
				fights.add(cities.get(k));
			}
		}
		return fights;
	}

	public void reloadarmies() {
		citylist.clear();
		avcities.removeAll();
		boolarray.clear();
		for (int i = 0; i < game.getAvailableCities().size(); i++) {
			JButton c = new JButton(game.getAvailableCities().get(i).getName());
			c.setPreferredSize(new Dimension(90, 50));
			c.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
			c.setBackground(Color.DARK_GRAY);
			c.setFocusable(false);
			c.setOpaque(false);
			c.setBorder(null);
			c.setForeground(Color.white);
			c.setHorizontalAlignment(JLabel.CENTER);
			c.addActionListener(this);
			if (game.getAvailableCities().get(i).getName().equalsIgnoreCase("Cairo")) {
				c.setBounds(200, 115, 90, 50);
			}
			if (game.getAvailableCities().get(i).getName().equalsIgnoreCase("Rome")) {
				c.setBounds(25, 35, 90, 50);

			}
			if (game.getAvailableCities().get(i).getName().equalsIgnoreCase("Sparta")) {
				c.setBounds(170, 20, 90, 50);
			}
			c.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent evt) {
					for (int z = 0; z < game.getPlayer().getControlledCities().size(); z++) {
						if (c.getText().equalsIgnoreCase(game.getPlayer().getControlledCities().get(z).getName()))
							c.setForeground(Color.green);
					}
					if (c.getForeground() != Color.green)
						c.setForeground(Color.red);
				}

				public void mouseExited(java.awt.event.MouseEvent evt) {
					c.setForeground(Color.WHITE);
				}
			});

			avcities.add(c);
			citylist.add(c);
			boolarray.add(false);
			worldview.revalidate();
			worldview.repaint();
		}
	}

	public void reloadunits(Army a) {
		unitlist.clear();
		for (int i = 0; i < a.getUnits().size(); i++) {
			settarget.setEnabled(true);
			Unit currunit = a.getUnits().get(i);
			JPanel infoholder = new JPanel();
			JButton unitcounter = new JButton("Unit " + (i + 1) + ":");
			infoholder.setLayout(new GridLayout(1, 0));
			unitcounter.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
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
			unittype.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			unittype.setHorizontalAlignment(JLabel.CENTER);
			infoholder.add(unittype);
			JLabel unitlevel = new JLabel("Level: " + currunit.getLevel());
			unitlevel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			unitlevel.setHorizontalAlignment(JLabel.CENTER);
			infoholder.add(unitlevel);
			JLabel unitcurrcount = new JLabel("Current Count : " + currunit.getCurrentSoldierCount());
			unitcurrcount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			unitcurrcount.setHorizontalAlignment(JLabel.CENTER);
			infoholder.add(unitcurrcount);
			JLabel unitmaxcount = new JLabel("Max Count : " + currunit.getMaxSoldierCount());
			unitmaxcount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			unitmaxcount.setHorizontalAlignment(JLabel.CENTER);
			infoholder.add(unitmaxcount);
			infoholder.setBorder(BorderFactory.createLineBorder(Color.black, 1));

			info.add(infoholder);
		}
	}

	public void refreshprog() {
		pgold.setText("Gold: " + game.getPlayer().getTreasury());
		pfood.setText("Food: " + game.getPlayer().getFood());
		pturn.setText("Current Turn: " + game.getCurrentTurnCount());

	}

	public boolean onway(City c) {
		for (int i = 0; i < game.getPlayer().getControlledArmies().size(); i++) {
			if (game.getPlayer().getControlledArmies().get(i).getTarget().equalsIgnoreCase(c.getName())) {

				return true;
			}
		}
		return false;
	}

	public void reset() {
		handlearmies();
		refreshprog();
		info.removeAll();
		r.setText("");
		army1.setText("");
		reloadarmies();

	}

	public void displayEndWindow(String message) {
		done = new JFrame();
		done.setDefaultCloseOperation(EXIT_ON_CLOSE);
		done.setTitle("The Conqueror");
		done.setExtendedState(JFrame.MAXIMIZED_BOTH);
		done.setLayout(null);
		JLabel result = new JLabel(message);
		result.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 27));
		result.setBounds(0, 300, 1500, 100);
		done.add(result);
		done.revalidate();
		done.repaint();
		done.setVisible(true);
	}

	public static void main(String[] args) {

		Game g = null;
		try {
			g = new Game("Ahmed", "Sparta");
			g.getAvailableCities().get(2).getDefendingArmy().setCurrentStatus(Status.BESIEGING);
			g.getAvailableCities().get(2).getDefendingArmy().setTarget("Cairo");
			g.getPlayer().getControlledArmies().add(g.getAvailableCities().get(2).getDefendingArmy());
			g.getPlayer().getControlledArmies().add(g.getAvailableCities().get(0).getDefendingArmy());
			g.getPlayer().getControlledCities().get(0)
					.setDefendingArmy(g.getAvailableCities().get(0).getDefendingArmy());
			g.getAvailableCities().get(0).getDefendingArmy().setCurrentLocation("Sparta");
//			g.getPlayer().getControlledArmies().add(g.getAvailableCities().get(0).getDefendingArmy());
//			System.out.println(g.getAvailableCities().get(0).getDefendingArmy().getUnits().size());
//			System.out.println(g.getPlayer().getControlledCities().size());

//			System.out.println((g.getAvailableCities().get(0).getDefendingArmy().getUnits().size()));
//			System.out.println(g.getPlayer().getControlledArmies().get(0).getUnits().size());
//			g.getPlayer().getControlledArmies().get(0).setCurrentLocation("Cairo");
			g.getAvailableCities().get(0).setDefendingArmy(g.getAvailableCities().get(1).getDefendingArmy());
			g.getAvailableCities().get(2).getDefendingArmy().setCurrentLocation("Rome");
//				System.out.println(g.getAvailableCities().get(2).getDefendingArmy().getUnits().size());
//				g.getPlayer().
//				System.out.println(g.getPlayer().getControlledCities().get(0));
//				for(int i =0;i<g.getPlayer().getControlledArmies().size();i++) 
//					System.out.println(g.getPlayer().getControlledArmies().get(i).getCurrentStatus() );

		} catch (IOException e) {
		}
		new MapView(g);
	}

}
