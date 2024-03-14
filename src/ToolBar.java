import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar{
    
public ToolBar(FamTree famTree) {
// Tworzenie paska narzędziowego
JButton button1 = new JButton("Dodaj osobe");
button1.addActionListener(e->{
    famTree.addPerson();
    famTree.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
});
this.add(button1);

JButton button2 = new JButton("Dodaj relacje");
button2.addActionListener(e->{
    famTree.addRelationship();
});
this.add(button2);



}

}