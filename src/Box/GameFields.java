package Box;

import game.engine.Chicken;
import game.engine.Egg;
import game.engine.Rocket;
import game.engine.Tir;

import java.util.ArrayList;

public class GameFields extends BoxFather {
	private ArrayList<Chicken> chickens;
	private ArrayList<Tir> tirs;
	private ArrayList<Egg> eggs;
	private ArrayList<Rocket> rockets;
	public  GameFields(ArrayList<Chicken> chickens,
							  ArrayList<Tir> tirs,
							  ArrayList<Egg> eggs,
							  ArrayList<Rocket> rockets
	){
		super(BoxType.gameField);
		this.chickens = chickens;
		this.tirs = tirs;
		this.eggs = eggs;
		this.rockets = rockets;
	}
	public GameFields(){
		super(BoxType.gameField);
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
}