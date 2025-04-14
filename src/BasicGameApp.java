//Basic Game Application
// Basic Object, Image, Movement
// Threaded

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//*******************************************************************************

public class BasicGameApp implements Runnable {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    Character dinosaur;
    Character meteorite;
    Character dinoEgg;
    boolean dinosaurVsMeteorite;
    boolean meteroriteVsdinoEgg;
    int imageChange = 0;

    Image backgroundPic;

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() { // BasicGameApp constructor

        setUpGraphics();

        backgroundPic = Toolkit.getDefaultToolkit().getImage("backgroundPic.jpg");
        //variable and objects
        //create (construct) the objects needed for the game

        dinosaur = new Character(600, 200, 3, 3, 250, 200);
        dinosaur.pic = Toolkit.getDefaultToolkit().getImage("dinosaur.png");

        meteorite = new Character(200, 500, 3, 3, 125, 100);
        meteorite.pic = Toolkit.getDefaultToolkit().getImage("meteorite.png");

        dinoEgg = new Character(400, 300, 3, 3, 120, 140);
        dinoEgg.pic = Toolkit.getDefaultToolkit().getImage("dinoEgg.png");

    } // end BasicGameApp constructor


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            collisions();
            render();  // paint the graphics
            pause(10); // sleep for 10 ms
        }
    }

    public void moveThings() {
        dinosaur.move();
        meteorite.wrap();
        dinoEgg.move();
        //call the move() code for each object
    }

    public void collisions() {
        if (dinosaur.hitbox.intersects(meteorite.hitbox) == true && dinosaurVsMeteorite == false) {

            dinosaurVsMeteorite = true;

            dinosaur.width = dinosaur.width + 20;
            dinosaur.height = dinosaur.height + 20;
        }
        if (dinosaur.hitbox.intersects(meteorite.hitbox) == false) {
            dinosaurVsMeteorite = false;
        }

        if (meteorite.hitbox.intersects(dinoEgg.hitbox) == true && meteroriteVsdinoEgg == false){

            meteroriteVsdinoEgg = true;

            imageChange = imageChange + 1;
            System.out.println("changing imageChange to " + imageChange);

            if (imageChange > 2) {
                imageChange = 0;
            }

            if (imageChange == 0){
                dinoEgg.pic = Toolkit.getDefaultToolkit().getImage("dinoEgg.png");
            }
            else if (imageChange == 1) {
                dinoEgg.pic = Toolkit.getDefaultToolkit().getImage("hatch.png");
            }
            else if (imageChange == 2) {
                dinoEgg.pic = Toolkit.getDefaultToolkit().getImage("finalDino.png");
            }


        }

        if (meteorite.hitbox.intersects(dinoEgg.hitbox) == false) {
            meteroriteVsdinoEgg = false;
        }

    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the images
        g.drawImage(backgroundPic,0,0,WIDTH, HEIGHT, null);

        g.drawImage(dinosaur.pic, dinosaur.hitbox.x, dinosaur.hitbox.y, dinosaur.width, dinosaur.height, null);
        g.drawImage(meteorite.pic, meteorite.hitbox.x, meteorite.hitbox.y, meteorite.width, meteorite.height, null);
        g.drawImage(dinoEgg.pic, dinoEgg.hitbox.x, dinoEgg.hitbox.y, dinoEgg.width, dinoEgg.height, null);

        g.dispose();
        bufferStrategy.show();
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

}
