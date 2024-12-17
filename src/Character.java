import java.awt.*;

public class Character {


    public String name;
    public Image pic;
    public int xpos;
    public int ypos;
    public int dx = 1;
    public int dy = 1;
    public int width = 150;
    public int height = 150;
    public boolean isAlive;


    public Character(int paramXpos, int paramYpos, int paramDx,
                     int paramDy, int paramWidth, int paramHeight) {
        xpos = paramXpos;
        ypos = paramYpos;
        dx = paramDx;
        dy = paramDy;
        width = paramWidth;
        height = paramHeight;

    }
    public void move() {

        xpos = xpos + dx;
        ypos = ypos + dy;

        if (ypos >= 699 - height) {
            dy = -dy;
        }
        if (xpos == 999 - width) {
            dx = -dx;

        }
        if (ypos <= 0) {
            dy = -dy;

        }
        if (xpos == 0) {
            dx = -dx;

        }
    }
}
