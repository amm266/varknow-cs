package Server;

import com.gilecode.yagson.YaGson;
import game.engine.Rocket;

import java.io.*;

public class SaveSystem {
	private static YaGson yaGson = new YaGson ();
	public static enum SaveMode{
		game
	}
	public static void save( SaveMode saveMode, Object object, String name, Rocket rocket ) throws IOException {
		FileWriter fileWriter ;
			fileWriter = new FileWriter ( name );
		switch ( saveMode ){
			case game:
				ServerGame serverGame = (ServerGame ) object;
				GameForSave gameForSave = serverGame.saveGame (rocket);
				String save = yaGson.toJson ( gameForSave );
				fileWriter.write ( save );
				fileWriter.close ();
				break;
		}
	}
	public static Object load(SaveMode saveMode,String name,Client client) throws IOException {
		File file = new File ( name );
		if ( file.exists () ){
			FileReader fileReader = new FileReader ( file );
			switch ( saveMode ){
				case game:
					GameForSave gameForSave =  yaGson.fromJson ( fileReader,GameForSave.class );
					int a=1;
					client.setRocket (gameForSave.rocket );
					return gameForSave;
			}
		}
		else {
			System.out.println ("fosh badddd" );
			return null;
		}
		return null;
	}
}
