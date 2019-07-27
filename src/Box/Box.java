package Box;

import game.engine.*;
import game.swing.MainPanel;
import javafx.concurrent.Worker;

import java.util.ArrayList;

public class Box extends BoxFather {
	public enum Ask {
		state,
		setLocation,
		move,
		fire,
		chick,
		startNewGame,
	}
	public Box( Ask ask){
		super(BoxType.simple);
		this.ask = ask;
	}
	public Box(){
		super(BoxType.simple);
	}
	private MainPanel.STATE state;
	private Game.STAGE stage;
	private Ask ask;
	private int x;
	private int y;
	private boolean succes;
	private ArrayList<Chicken> chickens;
	private ArrayList<Tir> tirs;
	private ArrayList<Egg> eggs;
	private ArrayList<Rocket> rockets;

	public void setGameFields(ArrayList<Chicken> chickens,
			ArrayList<Tir> tirs,
			ArrayList<Egg> eggs,
			ArrayList<Rocket> rockets
	){
		this.chickens = chickens;
		this.tirs = tirs;
		this.eggs = eggs;
		this.rockets = rockets;
	}

	public void setSucces ( boolean succes ) {
		this.succes = succes;
	}

	public boolean isSucces () {
		return succes;
	}

	public void setChickens ( ArrayList<Chicken> chickens ) {
		this.chickens = chickens;
	}

	public void setEggs ( ArrayList<Egg> eggs ) {
		this.eggs = eggs;
	}

	public void setRockets ( ArrayList<Rocket> rockets ) {
		this.rockets = rockets;
	}

	public void setTirs ( ArrayList<Tir> tirs ) {
		this.tirs = tirs;
	}

	public ArrayList<Chicken> getChickens () {
		return chickens;
	}

	public ArrayList<Egg> getEggs () {
		return eggs;
	}

	public ArrayList<Rocket> getRockets () {
		return rockets;
	}

	public ArrayList<Tir> getTirs () {
		return tirs;
	}

	public void setState ( MainPanel.STATE state ) {
		this.state = state;
	}

	public MainPanel.STATE getState () {
		return state;
	}

	public void setStage ( Game.STAGE stage ) {
		this.stage = stage;
	}

	public Game.STAGE getStage () {
		return stage;
	}

	public void setY ( int y ) {
		this.y = y;
	}
	public void setX ( int x ) {
		this.x = x;
	}
	public int getY () {
		return y;
	}
	public int getX () {
		return x;
	}

	public void setAsk ( Ask ask ) {
		this.ask = ask;
	}

	public Ask getAsk () {
		return ask;
	}
}
