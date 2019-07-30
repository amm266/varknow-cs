package Box;

public class BoxFather {
	public enum BoxType{
		gameField,
		simple
	}
	BoxType boxType;
	boolean exeptAnAnswer;
	public BoxFather(BoxType type,boolean exeptAnAnswer ){
		this.boxType = type;
		this.exeptAnAnswer = exeptAnAnswer;
	}
	public void setBoxType ( BoxType boxType ) {
		this.boxType = boxType;
	}
	public boolean getexeptAnAnswer(){
		return exeptAnAnswer;
	}
	public BoxType getBoxType () {
		return boxType;
	}
	public Class type(BoxFather boxFather){
		return boxFather.getClass ();
	}
}
