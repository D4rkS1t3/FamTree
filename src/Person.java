import java.awt.Point;
import java.awt.geom.Point2D;

public class Person {
    String name;
    int x, y,width, height=30; // Pozycja prostokąta dla osoby

    public Person(String name,int width, int x, int y) {
        this.name = name;
        this.width=width;
        this.x = x;
        this.y = y;
    }
    public boolean contains(Point2D point) {
        return point.getX() >= x && point.getX() <= x + width && point.getY() >= y && point.getY() <= y + height;
    }
    
    // Getters dla pozycji centrum prostokąta, mogą być użyte do rysowania linii
    public Point getCenter() {
        return new Point(x + width / 2, y + height / 2);
    }
}
