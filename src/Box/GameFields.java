package Box;

import game.engine.*;

import java.util.ArrayList;

public class GameFields extends BoxFather {
	public enum GameState {
		inGame,
		louse,
		win
	}

	private GameState gameState;
	private ArrayList<ChickenForSend> chickenForSends;
	private ArrayList<Chicken> chickens;
	private ArrayList<Tir> tirs;
	private ArrayList<Egg> eggs;
	private ArrayList<Rocket> rockets;
	private ArrayList<Stronge> stronges;
	private ArrayList<Coin> coins;
	private FinalEgg finalEgg;
	public GameFields ( GameState gameState,
			FinalEgg finalEgg
			, ArrayList<ChickenForSend> chickens ,
						ArrayList<Tir> tirs ,
						ArrayList<Egg> eggs ,
						ArrayList<Rocket> rockets ,
						ArrayList<Stronge> stronges ,
						ArrayList<Coin> coins
	) {
		super ( BoxType.gameField , false );
		this.gameState = gameState;
		this.chickenForSends = chickens;
		this.tirs = tirs;
		this.eggs = eggs;
		this.rockets = rockets;
		this.coins = coins;
		this.stronges = stronges;
		this.finalEgg = finalEgg;
	}

	public FinalEgg getFinalEgg () {
		return finalEgg;
	}

	public GameState getGameState () {
		return gameState;
	}

	public void setGameState ( GameState gameState ) {
		this.gameState = gameState;
	}

	public ArrayList<ChickenForSend> getChickenForSends () {
		return chickenForSends;
	}

	public ArrayList<Coin> getCoins () {
		return coins;
	}

	public ArrayList<Stronge> getStronges () {
		return stronges;
	}

	public GameFields () {
		super ( BoxType.gameField , false );
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