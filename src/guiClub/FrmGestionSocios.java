/**
 * FrmGestionSocios.java
 *
 * Created on 14 de noviembre de 2006, 16:40
 */

package guiClub;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import main.*;

/**
 *
 * @author  
 */
public class FrmGestionSocios extends JDialog {
    
    /** Creates new form FrmGestionSocios */
    public FrmGestionSocios() {
        initComponents();
        center();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    private void initComponents() {
        panelGestionSocios = new javax.swing.JPanel();
        nuevo = new javax.swing.JButton();
        modificar = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        cerrar = new javax.swing.JButton();
        jScrollPaneGestionSocios = new javax.swing.JScrollPane();
        tablaGestionSocios = new javax.swing.JTable();

        getContentPane().setLayout(new AbsoluteLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setTitle("Gesti\u00f3n de socios");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        panelGestionSocios.setLayout(new AbsoluteLayout());

        nuevo.setText("Nuevo");
        nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoActionPerformed(evt);
            }
        });

        panelGestionSocios.add(nuevo, new AbsoluteConstraints(80, 500, -1, -1));

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        panelGestionSocios.add(modificar, new AbsoluteConstraints(190, 500, -1, -1));

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        panelGestionSocios.add(eliminar, new AbsoluteConstraints(310, 500, -1, -1));

        cerrar.setText("Cerrar");
        cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarActionPerformed(evt);
            }
        });

        panelGestionSocios.add(cerrar, new AbsoluteConstraints(830, 500, -1, -1));
      
        titulos = new Vector();
		titulos.addElement("Nro. Socio");
		titulos.addElement("Apellido");
		titulos.addElement("Nombre");
		titulos.addElement("Tipo documento");
		titulos.addElement("N�mero");
		titulos.addElement("Fecha nacimiento");
		titulos.addElement("Edad de afiliaci�n");
		titulos.addElement("Categor�a");
		titulos.addElement("Titular grupo familiar");
		titulos.addElement("Zona");
		
	    cargarDatos();
       
	    tablaGestionSocios.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPaneGestionSocios.setViewportView(tablaGestionSocios);

        panelGestionSocios.add(jScrollPaneGestionSocios, new AbsoluteConstraints(30, 40, 920, 430));

        getContentPane().add(panelGestionSocios, new AbsoluteConstraints(0, 0, 972, 544));

        pack();
    }
    

    private void cerrarActionPerformed(java.awt.event.ActionEvent evt) {
    	dispose();
    }

    private void nuevoActionPerformed(java.awt.event.ActionEvent evt) {

       new FrmAltaSocio(this).setVisible(true);
    }

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {

    	int row = tablaGestionSocios.getSelectedRow();
		if (row != -1 && tablaGestionSocios.getModel().getRowCount() != 0){
			Integer idSocioElegido = (Integer)sociosModel.getValueAt(row,0);
    		int opcion = JOptionPane.showConfirmDialog(this,"�Seguro que desea eliminar el socio?","Confirmaci�n de Eliminaci�n",  JOptionPane.YES_NO_OPTION);
			if(opcion == JOptionPane.OK_OPTION){
				try {
					AdministradorDeSocios.EliminarSocio(idSocioElegido.intValue());
					sociosModel.removeRow(row);
					if (tablaGestionSocios.getRowCount() == 0){
						eliminar.setEnabled(false);
			    		modificar.setEnabled(false);
			    	}else {
			    		eliminar.setEnabled(true);
			    		modificar.setEnabled(true);
			    	}
				
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}		
			}
		}
    }

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {
    	
    	int row = tablaGestionSocios.getSelectedRow();
    	if(row != -1){
    		Integer idSocioElegido = (Integer)sociosModel.getValueAt(row,0);
    		Socio socioElegido = AdministradorDeSocios.ObtenerSocio(idSocioElegido.intValue());
    		new FrmModificarSocio(socioElegido,this).setVisible(true);
    	}
    }
    
    public void cargarDatos() {
    	
    	List socios = AdministradorDeSocios.getSocios();
		Vector data = new Vector();
		Iterator i = socios.iterator();
		while(i.hasNext()){
			Socio s = (Socio) i.next();
			Vector aux = new Vector();

			aux.addElement(s.getIdSocioI());
			aux.addElement(s.getApellido());
			aux.addElement(s.getNombre());
			aux.addElement(s.getTipoDocumento());
			aux.addElement(s.getDniL());
			
			Date d = s.getFechaNacimiento();
			int anio = d.getYear() + 1900;
			aux.addElement(Integer.toString(d.getDate())+ "/" +Integer.toString(d.getMonth() + 1)+ "/" +Integer.toString(anio));
			
			aux.addElement(s.getEdadAfiliacionI());
			aux.addElement(s.getCategoria().getNombre());
			if (s.getSocio() == null){
				aux.addElement(null);
			}else{
				aux.addElement(s.getSocio().getIdSocioI());
			}
				
			aux.addElement(s.getZona().getIdZonaI());
						
			data.addElement(aux);
		}
		
    	
    	tablaGestionSocios.setModel(new DefaultTableModel(data,titulos){
    		
    		public boolean isCellEditable(int rowIndex, int columnIndex) {
    			return false;
    		}
    	});
    	
    	tablaGestionSocios.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	
    	if(data.size() == 0){ // no hay socios
			eliminar.setEnabled(false);
    		modificar.setEnabled(false);
    	}else {
    		eliminar.setEnabled(true);
    		modificar.setEnabled(true);
    	}
    	
    	sociosModel = (DefaultTableModel) tablaGestionSocios.getModel();
    	    	
    }
    
    private void center(){
    	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    	Rectangle frame = getBounds();
    	setLocation((screen.width - frame.width)/2, (screen.height - frame.height)/2);
    }
    
    // Variables declaration 
    private javax.swing.JButton cerrar;
    private javax.swing.JButton eliminar;
    private javax.swing.JScrollPane jScrollPaneGestionSocios;
    private javax.swing.JButton modificar;
    private javax.swing.JButton nuevo;
    private javax.swing.JPanel panelGestionSocios;
    private javax.swing.JTable tablaGestionSocios;
    private DefaultTableModel sociosModel;
    private Vector titulos;
    // End of variables declaration   
}
