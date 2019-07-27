package Box;

public class BoxFather {
	public enum BoxType{
		gameField,
		simple
	}
	BoxType boxType;
	public BoxFather(BoxType type){
		this.boxType = type;
	}
	public void setBoxType ( BoxType boxType ) {
		this.boxType = boxType;
	}

	public BoxType getBoxType () {
		return boxType;
	}
	public Class type(BoxFather boxFather){
		return boxFather.getClass ();
	}
}
