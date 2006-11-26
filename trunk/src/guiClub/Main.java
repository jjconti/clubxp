/**
 * Main.java
 *
 * Created on 14 de noviembre de 2006, 16:30
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package guiClub;

import javax.swing.*;
import com.sun.java.swing.*;

/**
 *
 * @author
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName() );
        } catch (Exception e) { }
        
          //FrmGestionSocios s1 = new FrmGestionSocios(); 
          //s1.setVisible(true);
          
          //FrmGestionGrupos g1 = new FrmGestionGrupos();
          //g1.setVisible(true);
          
         //FrmAltaSocio s2 = new FrmAltaSocio(); 
         //s2.setVisible(true);
        
        new FrmPrincipal().setVisible(true);
          
    }
    
}
