package Box;

import game.engine.Game;
import javafx.concurrent.Worker;

public class Box {
	public enum Ask {
		state,
		setLocation,
		move,
		fire,
		chick
	}
	public Box( Ask ask){
		this.ask = ask;
	}
	public Box(){
	}
	private Game.STAGE stage;
	private Ask ask;
	private int x;
	private int y;

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
