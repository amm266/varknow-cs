package game.Menu;

import game.engine.Animatable;
import game.engine.Game;

import java.awt.*;


public class SecondMenu_Setting implements Animatable {
    private int x;
    private int y;

    private boolean State = true;
    public static String LastTypeOfSpacecraft;
    public static String LastColorOfShoot;
    public static String LastTypeOfShoot;
    public static enum TypeOfRocket {
        RED ,
        GREEN ,
    };
    /*public static enum ColorOfShoot {
        RED ,
        GREEN ,
    };*/

    //public static ColorOfShoot colorOfShoot;
    public static boolean colorOfShoot=false;
    public static TypeOfRocket typeOfRocket1 ;
    public static boolean typeOfShoot=true;

    public boolean State (int x , int y){
        this.x=x;
        this.y=y;


        if ( (x < 980 & x > 900) & ( y <750 & y>700)){
            State = false;
        }

        if (State == false) {
            State = true;
            return false;


        }
        else {
            return true ;

        }


    }

    @Override
    public void paint(Graphics2D g2) {


        g2.drawImage(Game.bufferedImage_SeconfMenu_Setting_background, 0,0,1000,800 ,null);
        fillrect_TOR(x ,y ,g2);
        fillrect_COS(x,y,g2);
        fillrect_TOS(x,y,g2);
        drawPictures(g2);
        drawEveryThing(g2);

    }



    public void fillrect_TOR(int x , int y , Graphics2D g2){
        this.x= x;
        this.y=y;
        if((x <600 & x>550) & (y<350 & y>290) ){
            //rocket1
            g2.setColor(new Color(200, 0, 0));
            g2.fillRect(540 , 280 , Game.bufferedImage_SeconfMenu_Setting_Rocket1.getWidth() , Game.bufferedImage_SeconfMenu_Setting_Rocket1.getHeight());
            //change the rocket

            typeOfRocket1 = TypeOfRocket.RED;
            LastTypeOfSpacecraft="BlueSpacecraft";
            //System.out.println("bluerocket");
        }

        if((x <800 & x>750) & (y<350 & y>300)){
            //rocket2
            g2.setColor(new Color(200, 0, 0));
            g2.fillRect(730 , 280 , Game.bufferedImage_SeconfMenu_Setting_Rocket2.getWidth()+10 , Game.bufferedImage_SeconfMenu_Setting_Rocket2.getHeight());
            //change the rocket

            typeOfRocket1 = TypeOfRocket.GREEN;
            LastTypeOfSpacecraft="RedSpacecraft";
            //System.out.println("redrocket2");

        }
    }


    public void fillrect_TOS(int x , int y , Graphics2D g2){
        this.x= x;
        this.y=y;
        if((x <720 & x>540) & (y<515 & y>440) ){
            //single
            g2.setColor(new Color(200, 0, 0));
            g2.fillRect(538 , 440 , 180 , 75);
            //change the mode of shoot
            typeOfShoot = true;
            LastTypeOfSpacecraft="Single";

        }
        if((x <910 & x>730) & (y<520 & y>440)){
            //multiplxe
            g2.setColor(new Color(200, 0, 0));
            g2.fillRect(730 , 440 , 180 , 75);
            //change the mode of shoot
            typeOfShoot = false;
            LastTypeOfSpacecraft="multiplxe";
        }

    }


    public void fillrect_COS(int x , int y , Graphics2D g2){
        this.x= x;
        this.y=y;
        if((x <620 & x>550) & (y<660 & y>590) ){
            g2.setColor(new Color(200, 0, 0));
            g2.fillRect(550 , 590 , 70, 70);
            //change the clolr of shoot
            //colorOfShoot = ColorOfShoot.RED;
            colorOfShoot=true;
            LastColorOfShoot="RED";

        }
        if((x <810 & x>740) & (y<660 & y>590)){
            g2.setColor(new Color(200, 0, 0));
            g2.fillRect(740 , 590 , 70 , 70);
            //change the color of shoot
            //colorOfShoot = ColorOfShoot.GREEN;
            colorOfShoot=false;
            LastColorOfShoot="GREEN";
        }
    }



    public void drawPictures(Graphics2D g2){
        g2.drawImage(Game.bufferedImage_SeconfMenu_Setting_Rocket1, 550,290, null);
        g2.drawImage(Game.bufferedImage_SeconfMenu_Setting_Rocket2, 750,300,80,80, null);
        g2.drawImage(Game.bufferedImage_SeconfMenu_Setting_COS1, 560,600,50,50,null);
        g2.drawImage(Game.bufferedImage_SeconfMenu_Setting_COS2 , 750,600, 50,50,null);
    }

    public  void drawEveryThing(Graphics2D g2){
        g2.setColor(new Color(200, 0, 0));
        g2.setFont(new Font("Arial" , 250 , 40));
        g2.drawString("Type of Rocket" , 100 , 300 );
        g2.drawString("Type of Shoot" , 100 , 450 );
        g2.drawString("Color of Shoot " ,100 , 600 );
        g2.setColor(new Color(0, 200, 0));
        g2.fillRect(550,450,150,50);
        g2.fillRect(750,450,150,50);
        g2.setColor(new Color(0, 0, 0));
        g2.setFont(new Font("Arial" , 250 , 25));
        g2.drawString("Single",600,480);
        g2.drawString("multiplex",780,480);
        g2.setColor(new Color(200, 0, 0, 158));
        g2.fillRect(900,700,80,50);
        g2.setColor(new Color(0, 0, 0, 158));
        g2.drawString("Return",880,730);
    }


    @Override
    public void move() {

    }

    public static TypeOfRocket TypeOfRocket(){
        if (typeOfRocket1 == TypeOfRocket.RED){
            return TypeOfRocket.RED;
        }
        else{
            return TypeOfRocket.GREEN;
        }
    }

    /*public static ColorOfShoot ColorOfShoot(){
        if (colorOfShoot == ColorOfShoot().RED){
            return ColorOfShoot().RED;
        }
        else{
            return ColorOfShoot().GREEN;
        }
    }*/
    public static boolean ColorOfShoot(){
        if (colorOfShoot == true){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean TypeOfShoot(){
        if (typeOfShoot == true){
            return true;
        }
        else{
            return false;
        }
    }
}
