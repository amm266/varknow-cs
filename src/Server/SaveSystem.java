package Server;

import com.gilecode.yagson.YaGson;

import java.io.*;
import java.sql.Statement;

public class SaveSystem {
	private static YaGson yaGson = new YaGson ();
	public static enum SaveMode{
		game
	}
	public static void save(SaveMode saveMode,Object object,String name) throws IOException {
		FileWriter fileWriter ;
			fileWriter = new FileWriter ( name );
		switch ( saveMode ){
			case game:
				ServerGame serverGame = (ServerGame ) object;
				GameForSave gameForSave = serverGame.saveGame ();
				String save = yaGson.toJson ( gameForSave );
				fileWriter.write ( save );
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
					client.setRocket (gameForSave.rockets);
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
