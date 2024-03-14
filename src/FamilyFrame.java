import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class FamilyFrame extends JFrame {

    public FamilyFrame() {
            this.setTitle("FAMILY TREE - IT'S ALL FOR THE FAMILY");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //NASZ PANEL Z RYSUNKAMI
            FamTree panel=new FamTree();
            JToolBar toolBar=new ToolBar(panel);
            //DODAJEMY NASZ PANEL DO PRZEWIJANEGO PANELU
            JPanel contentPanel=new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(toolBar, BorderLayout.PAGE_END);
            contentPanel.add(panel, BorderLayout.CENTER);

            this.setContentPane(contentPanel);

            this.setPreferredSize(new Dimension(800,600));
            this.pack();//ramka dostosuje sie do zawartosci
            this.setLocationRelativeTo(null);//wysrodkuje ogolne okno
            this.setVisible(true);
            
    }
    


    public static void main(String[] args) throws Exception {
        new FamilyFrame().setVisible(true);;
    }
}
