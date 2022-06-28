package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import engine.Game;
import view.CityView;
import view.GameView;
import view.MapView;

public class GUIcontrol implements ActionListener{
	private GameView gameview;
	private MapView mapview;
	private CityView cairoview;
	private CityView romeview;
	private CityView spartaview;
	private Game  game;
	private JButton buy;
	
	
	public GUIcontrol() {
		gameview = new GameView();

	}

	public void actionPerformed(ActionEvent e) {

	}
	
	
	
	public static void main(String[] args) {
		new GameView();
		
		
	}

	
	public CityView getCairoview() {
		return cairoview;
	}

	public void setCairoview(CityView cairoview) {
		this.cairoview = cairoview;
	}

	public CityView getRomeview() {
		return romeview;
	}

	public void setRomeview(CityView romeview) {
		this.romeview = romeview;
	}

	public CityView getSpartaview() {
		return spartaview;
	}

	public void setSpartaview(CityView spartaview) {
		this.spartaview = spartaview;
	}
	

}
