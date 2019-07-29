


package game.Menu;

        import game.Main;
        import game.engine.Animatable;
        import game.engine.Game;
        import game.swing.MainPanel;

        import javax.imageio.ImageIO;
        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
        import java.awt.font.FontRenderContext;
        import java.awt.font.GlyphVector;
        import java.awt.geom.AffineTransform;
        import java.awt.image.BufferedImage;
        import java.awt.image.BufferedImageOp;
        import java.awt.image.ImageObserver;
        import java.awt.image.RenderedImage;
        import java.awt.image.renderable.RenderableImage;
        import java.io.File;
        import java.io.IOException;
        import java.security.PrivateKey;
        import java.text.AttributedCharacterIterator;
        import java.util.Map;

public class SecondMenu extends JPanel implements Animatable,KeyListener,ActionListener {

    private SecondMenu_AboutUs secondMenu_aboutUs = new SecondMenu_AboutUs();
    private SecondMenu_Setting secondMenu_setting = new SecondMenu_Setting();
    private boolean AboutUs=false;
    private boolean Resume = false;
    private boolean Setting = false;
    private boolean StartNewGame = false;
    private int x;
    private int y;

    @Override
    public void keyTyped ( KeyEvent e ) {
        System.out.println ("save" );

    }

    @Override
    public void keyPressed ( KeyEvent e ) {
        System.out.println ("save" );

    }

    @Override
    public void keyReleased ( KeyEvent e ) {
        System.out.println ("save" );

    }

    @Override
    public void actionPerformed ( ActionEvent e ) {
        System.out.println ("save" );
    }

    private enum TypeOfRocket {
        RED ,
        GREEN ,
    };
    public TypeOfRocket typeOfRocket= TypeOfRocket.RED;
    public static long TimeOfStartGame;

    public SecondMenu() {


    }

    public void buttonClicked(int x ,int y) {
        this.x = x;
        this.y = y;
        if ((x > 100 & x < 180) & (y < 730 & y > 700)) {
            // EXIT
            System.exit(0);
        }

        if ((x > 800 & x < 880) & (y < 730 & y > 700)) {
            //ABOUT Us
            AboutUs = true;
        }

        if ((x > 440 & x < 560) & (y < 230 & y > 200)) {
            // RESUME
            Resume = true;

        }

        if ((x > 440 & x < 560) & (y < 330 & y > 300)) {
            //START NEW GAME
            StartNewGame=true;
            System.out.println ("new Game Button pressed!" );
            TimeOfStartGame=System.currentTimeMillis();
            MainPanel.startNewGame ();

        }

        if ((x > 440 & x < 560) & (y < 430 & y > 400)) {
            //RANKING

        }

        if ((x > 440 & x < 560) & (y < 530 & y > 500)) {
            //SETTING
            Setting = true;

        }

    }


    @Override
    public void paint(Graphics2D g2) {
        super.paintComponent(g2);

        g2.setColor(new Color(0, 0, 200));
        g2.fillRect(0, 0, 1000, 800);

        g2.drawImage(Game.bufferedImage_SecondMenu_background,0,0,1000,800,null);

        g2.setColor(new Color(0, 200, 0));
        g2.fillRect(100, 700, 80, 30);
        g2.fillRect(800, 700, 80, 30);
        g2.fillRect(440, 200, 130, 30);
        g2.fillRect(440, 300, 130, 30);
        g2.fillRect(440, 400, 130, 30);
        g2.fillRect(440, 500, 130, 30);


        g2.setColor(new Color(0, 0, 0));
        g2.drawString("EXIT", 115, 720);
        g2.drawString("ABOUT US", 815, 720);
        g2.drawString("RESUME", 460, 220);
        g2.drawString("START NEW GAME", 460, 320);
        g2.drawString("RANKING", 460, 420);
        g2.drawString("SETTING", 460, 520);
        if(AboutUs) {
            secondMenu_aboutUs.paint(g2);
            AboutUs = secondMenu_aboutUs.State(x , y);

        }

        if(Setting){
            secondMenu_setting.paint(g2);
            Setting = secondMenu_setting.State(x,y);

        }
    }

    @Override
    public void move() {

    }
    public boolean GameState(){
       if(StartNewGame){
           MainPanel.log.getLogger().CleanTextFile();
           return true;
       }
        if(Resume) {
           return true;
       }
       else {
           return false;
       }
    }
}

