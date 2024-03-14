

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;


import javax.swing.*;
public class FamTree extends JPanel {
    private double deltaX=0;//roznica potrzebna przy przesunieciu
    private double deltaY=0;//roznica przy rysowaniu przesuniecia
    private Point dragStartScreen;
    private double scaleFactor=1.0;//skala zoom
    private ArrayList<Person> people=new ArrayList<>();
    private ArrayList<Relationship> relationships=new ArrayList<>();
    private boolean addingPerson=false;
    private String personNameToAdd=null;
    private Person selectedPerson=null;
    FontMetrics metrics;

    public FamTree() {
        this.setLayout(null);
        addMouseListener(new MouseAdapter() {
        
            @Override
        public void mousePressed(MouseEvent e) {
            dragStartScreen=e.getPoint();//zapisz punkt w ktorym zaczelo sie przesuwanie
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//raczka kursor
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setCursor(Cursor.getDefaultCursor());//zmieniamy na norm kursor
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Point2D.Float transformedPoint=transformPoint(e.getPoint());
            if (!addingPerson) {
                for (Person person:people) {
                    if (person.contains(transformedPoint)) {
                        if (selectedPerson==null) {
                            selectedPerson=person;
                        } else {
                            relationships.add(new Relationship(selectedPerson, person));
                            selectedPerson=null;
                        }
                        repaint();
                        return;
                    }
                }
            }

            else {
                addPersonAtPoint(transformedPoint);
                repaint();
            }
        }
        });



        

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point dragEndScreen=e.getPoint();
                deltaX+=dragEndScreen.x-dragStartScreen.x;//o ile przesunieto
                deltaY+=dragEndScreen.y-dragStartScreen.y;
                dragStartScreen=dragEndScreen;
                repaint();
            }
        });
        
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double delta = -0.05f * e.getPreciseWheelRotation();
                double factor = 1 + delta;
        
                double newScaleFactor = Math.max(0.1, Math.min(10.0, scaleFactor * factor));
        
                // Pobranie aktualnej pozycji kursora względem komponentu
                Point cursorPosition = e.getPoint();
        
                // Obliczenie przesunięcia względem punktu, na który wskazuje kursor
                double xRelToCursor = (cursorPosition.x - deltaX) / scaleFactor;
                double yRelToCursor = (cursorPosition.y - deltaY) / scaleFactor;
        
                double newDeltaX = cursorPosition.x - (xRelToCursor * newScaleFactor);
                double newDeltaY = cursorPosition.y - (yRelToCursor * newScaleFactor);
        
                // Aktualizacja przesunięcia i skali
                deltaX = newDeltaX;
                deltaY = newDeltaY;
                scaleFactor = newScaleFactor;
        
                repaint();
            }
        });
        
        
        
    }


    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    AffineTransform at = new AffineTransform();
    at.translate(deltaX, deltaY);
    at.scale(scaleFactor, scaleFactor);
    g2d.transform(at);
    metrics = g.getFontMetrics();
    
    for (Person person : people) {
        
        g2d.drawRect(person.x, person.y, person.width, 30);
        g2d.drawString(person.name, person.x + 5, person.y + metrics.getAscent());
    }

    for (Relationship relationship : relationships) {
        Point fromCenter = getCenter(relationship.from);
        Point toCenter = getCenter(relationship.to);
        g2d.drawLine(fromCenter.x, fromCenter.y, toCenter.x, toCenter.y);
    }
}

    private Point getCenter(Person person) {
        return new Point(person.x+person.width/2, person.y+person.height/2);
    }

    public void setAddingPerson(boolean adding) {
        addingPerson=adding;
    }


    public void addPersonAtPoint(Float transformedPoint2) {
        if (personNameToAdd != null) {
            // Przekształć współrzędne punktu z uwzględnieniem przesunięcia i skalowania
            AffineTransform inverse = new AffineTransform();
            inverse.scale(1/scaleFactor, 1/scaleFactor);
            inverse.translate(-deltaX, -deltaY);
            Point2D.Float transformedPoint = new Point2D.Float();
            inverse.transform(transformedPoint2, transformedPoint);
            
            
            int textWidth = metrics.stringWidth(personNameToAdd);
            int rectangleWidth = Math.max(50, textWidth + 10);

            people.add(new Person(personNameToAdd,(int) rectangleWidth,(int) transformedPoint.x, (int) transformedPoint.y));
            personNameToAdd = null;
            repaint();
        }
        setAddingPerson(false);
        this.setCursor(Cursor.getDefaultCursor());
    }
    


    public void addPerson() {
        String name = JOptionPane.showInputDialog(this, "Podaj imię i nazwisko:");
        if (name != null && !name.trim().isEmpty()) {
            // Dodaj osobę do listy z domyślną pozycją
            // Można dodać logikę do ustalania pozycji
            personNameToAdd=name;
            setAddingPerson(true);
        }
    }

    public void addRelationship() {
        System.out.println(0);
    }

    private Point2D.Float transformPoint(Point point) {
        AffineTransform inverse=new AffineTransform();
        inverse.scale(1/scaleFactor,1/scaleFactor);
        inverse.translate(-deltaX,-deltaY);
        Point2D.Float transformedPoint=new Point2D.Float();
        inverse.transform(point, transformedPoint);
        return transformedPoint;


    }


}
