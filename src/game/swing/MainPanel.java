package game.swing;



import game.Menu.FirstMenu;
import game.Menu.PauseMenu;
import game.Menu.SecondMenu;
import game.Menu.SecondMenu_Setting;
import game.engine.*;
import game.logger.Logger;
import game.logger.Read;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainPanel extends JPanel  {
    public JTextField jTextField0 = new JTextField();
    public JTextField jTextField1 = new JTextField();
    public JTextField jTextField2 = new JTextField();
    public JTextField jTextField3 = new JTextField();
    public JLabel jLabel = new JLabel();
    private boolean GameState=false;
    private boolean MenuState=false;
    public boolean runnng=true;
    private Game game;
    public static String NameOfPlayer ;
    SecondMenu SecondMenu = new SecondMenu();
    FirstMenu FirstMenu = new FirstMenu();
    public static int x;
    public static int y;
    public static long TimeOfShoot;
    public static int ShootCounter = 0;
    public static boolean WaitForShoot = true;
    public static long WaitForShootTime;
    public static int NumberOfShoot=10000;
    public static boolean BombState=false;
    public static boolean statePauseMenu = false;
    public static Logger log  ;
    public Read read;
    public Sound sound;
    private int ChickCounter=1;
    public static int Temprature=0;
    public static long MainTime=System.currentTimeMillis();
    private long TimeOfBombShoot = 0;


    private enum STATE {
        SEcondMenu ,
        FIrstMenu ,
        PauseMenu ,
        Game
    };
    private STATE state = STATE.FIrstMenu;


    public MainPanel() {
        setBounds(0, 0,2000,1100);
        game = new Game(2000, 1100);


        if(state == STATE.FIrstMenu){
            Jtextfieldinit(jTextField0,250,320,480,40,this);
            Jtextfieldinit(jTextField1,250,390,480,40,this);
            Jtextfieldinit(jTextField2,250,440,480,40,this);
            Jtextfieldinit(jTextField3,250,490,480,40,this);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        game.chick();
        super.paintComponent(g);
        if(state == STATE.Game) {
        game.paint((Graphics2D) g);
        /*if( ChickCounter==1 ) {
            game.chick();
            ChickCounter =0;
        }*/
    }

        if(state == STATE.FIrstMenu){
            FirstMenu.paint((Graphics2D) g);
            MenuState = FirstMenu.MenuState();
            if(MenuState){
               state = STATE.SEcondMenu;
            }

        }
        if(state == STATE.SEcondMenu) {
            this.remove(jTextField0);
            this.remove(jTextField1);
            this.remove(jTextField2);
            this.remove(jTextField3);
            SecondMenu.paint((Graphics2D) g);
            GameState = SecondMenu.GameState();
            if (GameState) {
                state = STATE.Game;
            }
        }


            addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                        x = e.getX();
                        y = e.getY();

                    if(state == STATE.Game) {
                        game.getRocket().setX(e.getX());
                        game.getRocket().setY(e.getY());
                    }
                }
            });



            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if(state == STATE.FIrstMenu){
                        FirstMenu.buttonClicked(x, y, jTextField0,jTextField1);
                        System.out.println( FirstMenu.counterOfPlayer);
                    }

                    if(state == STATE.SEcondMenu) {
                        SecondMenu.buttonClicked(x,y);
                    }
                    if(state==STATE.Game & statePauseMenu==true){
                        PauseMenu.ButtonClicked(x,y);
                    }

                    if(state == STATE.Game & statePauseMenu==false){
                        if (ShootCounter%2==0) {
                            game.fire ();

                            NumberOfShoot--;

                            TimeOfShoot = System.currentTimeMillis();
                            ShootCounter++;
                            Temprature+=5;
                        }
                        if((System.currentTimeMillis() - TimeOfShoot) >= 100 & WaitForShoot){
                            ShootCounter++;
                            Temprature+=5;
                        }



                    }

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if(SwingUtilities.isRightMouseButton(e)){

                            BombState = true;
                            if(System.currentTimeMillis() - TimeOfBombShoot >1000){
                                if(Game.stage== Game.STAGE.THIRD){Game.stage= Game.STAGE.FINAL;}
                                if(Game.stage== Game.STAGE.FINAL){FinalEgg.AmountOfLife -=50;}
                                if(Game.stage== Game.STAGE.SECOND){Game.stage= Game.STAGE.THIRD;}
                                if(Game.stage== Game.STAGE.FIRST){
                                    System.out.println("bomb exploded");
                                    Game.chickens.clear();}
                                Game.NumberOfBomb--;
                                TimeOfBombShoot = System.currentTimeMillis();

                            }




                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (state == STATE.Game) {
                        statePauseMenu = true;
                        Logger.getLogger().LocationOfRocket(Rocket.LastXRocket,Rocket.LastYRocket);
                        Logger.getLogger().TypeOfSpacecraft(SecondMenu_Setting.LastTypeOfSpacecraft);
                        Logger.getLogger().ColorOfShoot(SecondMenu_Setting.LastColorOfShoot);
                        Logger.getLogger().TypeOfShoot(SecondMenu_Setting.LastTypeOfShoot);
                        Logger.getLogger().NameOfPlayer(FirstMenu.NameOfPlayer);
                    }

                }

            });

    }

    public void moveGame() {
        game.move();
    }

    public boolean state(){
        if(state == STATE.Game) {
            return true;
        }
        else{
            return false;
        }
    }

    public static  int  ShootCounter(){

        return ShootCounter;
    }


        public JTextField Jtextfieldinit(JTextField jTextField,int x,int y,int width,int height ,MainPanel Mainpanel){
            jTextField.setLocation(x,y);
            jTextField.setSize(width,height);
            //jTextField.setText("pleas input your name :)");
            setVisible(true);
            setLayout(null);
            Mainpanel.add(jTextField);
            return jTextField;

        }


}
