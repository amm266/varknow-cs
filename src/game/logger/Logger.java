package game.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

public class Logger {
    private static Logger loggerInstance = null;
    public String typeOfShoot;
    private int x;
    private int y;

    public String colorOfShoot;
    public String nameOfPlayer;
    public String tempratureOfSpacecraft;
    public String typeOfSpacecraft;


    PrintWriter writer;
    public Logger() throws FileNotFoundException {
        writer = new PrintWriter(new File("resources/log.txt"));
    }

    public static Logger getLogger()  {
        if(loggerInstance == null) {
            try {
                loggerInstance = new Logger();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return loggerInstance;
    }

    public  void LocationOfRocket(int x, int y) {
        this.x=x;
        this.y=y;
        writer.println("LocationOfRocket"+": "+": "+ x + y);
        writer.flush();
    }

    public void TypeOfShoot(String typeOfShoot) {
        this.typeOfShoot=typeOfShoot;
        writer.println("TypeOfShoot"+":"+ typeOfShoot);
        writer.flush();
    }

    public void ColorOfShoot(String colorOfShoot) {
        this.colorOfShoot=colorOfShoot;
        writer.println("ColorOfShoot"+":"+ colorOfShoot);
        writer.flush();
    }
    public void TypeOfSpacecraft(String typeOfSpacecraft) {
        this.typeOfSpacecraft=typeOfSpacecraft;
        writer.println("TypeOfSpacecraft"+":"+ typeOfSpacecraft);
        writer.flush();
    }
    public void NameOfPlayer(String nameOfPlayer) {
        this.nameOfPlayer=nameOfPlayer;
        writer.println("NameOfPlayer"+":"+ nameOfPlayer);
        writer.flush();
    }
    public void CleanTextFile(){
        writer.println("");
        writer.flush();

    }
    /*public void TempratureOfSpacecraft(String tempratureOfSpacecraft) {
        this.tempratureOfSpacecraft=tempratureOfSpacecraft;
        writer.println("NameOfPlayer"+":"+ tempratureOfSpacecraft);
        writer.flush();
    }*/




}
