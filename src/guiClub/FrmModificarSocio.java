/**
 * FrmAltaSocio.java
 *
 * Created on 14 de noviembre de 2006, 17:38
 */

package guiClub;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import utils.Validador;
import utils.ValidadorException;

import main.*;

/**
 *
 * @author  
 */
public class FrmModificarSocio extends JDialog {
    
	
	
    /** Creates new form FrmAltaSocio */
    public FrmModificarSocio(Socio s, FrmGestionSocios ventana) {
        ventanaPadre = ventana;
        socio = s;
    	initComponents();
    	center();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    private void initComponents() {
    	
    	panelAltaSocio = new JPanel();
        apellido = new JTextField();
        labelApellido = new JLabel();
        labelNombre = new JLabel();
        labelTipoDocumento = new JLabel();
        nombre = new JTextField();
        numero = new JTextField();
        labelNumero = new JLabel();
        tipoDocumento = new JComboBox();
        labelFechaNacimiento = new JLabel();
        labelEdadAfiliacion = new JLabel();
        diaNacimiento = new JComboBox();
        mesNacimiento = new JComboBox();
        anioNacimiento = new JComboBox();
        labelBarra1 = new JLabel();
        labelBarra2 = new JLabel();
        labelCategoria = new JLabel();
        categoria = new JComboBox();
        edadAfiliacion = new JTextField();
        labelZona = new JLabel();
        zona = new JComboBox();
        aceptar = new JButton();
        cancelar = new JButton();

        getContentPane().setLayout(new AbsoluteLayout());

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        
        setTitle("Modificar socio");
        setResizable(false);
        panelAltaSocio.setLayout(new AbsoluteLayout());

        panelAltaSocio.add(apellido, new AbsoluteConstraints(80, 50, 310, -1));

        labelApellido.setText("Apellido: ");
        panelAltaSocio.add(labelApellido, new AbsoluteConstraints(30, 50, -1, -1));

        labelNombre.setText("Nombre: ");
        panelAltaSocio.add(labelNombre, new AbsoluteConstraints(30, 90, -1, -1));

        labelTipoDocumento.setText("Tipo documento: ");
        panelAltaSocio.add(labelTipoDocumento, new AbsoluteConstraints(30, 130, -1, -1));

        panelAltaSocio.add(nombre, new AbsoluteConstraints(80, 90, 310, -1));
        panelAltaSocio.add(numero, new AbsoluteConstraints(270, 130, 100, -1));

        labelNumero.setText("N\u00famero: ");
        panelAltaSocio.add(labelNumero, new AbsoluteConstraints(220, 130, -1, -1));

        tipoDocumento.setModel(new DefaultComboBoxModel(new String[] { "DNI", "LC", "LE", "EXT" }));
        panelAltaSocio.add(tipoDocumento, new AbsoluteConstraints(120, 130, 70, 20));

        labelFechaNacimiento.setText("Fecha nacimiento: ");
        panelAltaSocio.add(labelFechaNacimiento, new AbsoluteConstraints(30, 170, -1, -1));

        labelEdadAfiliacion.setText("Edad afiliación: ");
        panelAltaSocio.add(labelEdadAfiliacion, new AbsoluteConstraints(30, 210, -1, -1));

        diaNacimiento.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        panelAltaSocio.add(diaNacimiento, new AbsoluteConstraints(130, 170, 50, 20));

        mesNacimiento.setModel(new DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        panelAltaSocio.add(mesNacimiento, new AbsoluteConstraints(200, 170, 80, 20));

        Vector anios = new Vector();
        Date d = new Date(System.currentTimeMillis());
        int anio = d.getYear()+1900;
        
        for (int i=1900; i<=anio; i++){
        	anios.addElement(new Integer(i));
        	
        }
        
        anioNacimiento.setModel(new DefaultComboBoxModel(anios));
        panelAltaSocio.add(anioNacimiento, new AbsoluteConstraints(300, 170, 70, 20));

        labelBarra1.setText("/");
        panelAltaSocio.add(labelBarra1, new AbsoluteConstraints(190, 170, -1, -1));

        labelBarra2.setText("/");
        panelAltaSocio.add(labelBarra2, new AbsoluteConstraints(290, 170, -1, -1));

        labelCategoria.setText("Categor\u00eda: ");
        panelAltaSocio.add(labelCategoria, new AbsoluteConstraints(30, 250, -1, -1));

        //categoria.setModel(new DefaultComboBoxModel(new String[] { "Menor", "Cadete", "Mayor", "Vitalicio" }));
        List categorias = AdministradorDeCategorias.getCategorias();
        categorias.remove(AdministradorDeCategorias.getCategoriaFamiliar());
        categoria.setModel(new DefaultComboBoxModel(categorias.toArray()));

        panelAltaSocio.add(categoria, new AbsoluteConstraints(100, 250, 270, 20));
        panelAltaSocio.add(edadAfiliacion, new AbsoluteConstraints(130, 210, 40, -1));

        labelZona.setText("Zona: ");
        panelAltaSocio.add(labelZona, new AbsoluteConstraints(30, 290, -1, -1));
        zona.setModel(new DefaultComboBoxModel(AdministradorDeZonas.getZonas().toArray()));        
        panelAltaSocio.add(zona, new AbsoluteConstraints(100, 290, 80, 20));

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        panelAltaSocio.add(aceptar, new AbsoluteConstraints(110, 350, -1, -1));

        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });
        panelAltaSocio.add(cancelar, new AbsoluteConstraints(240, 350, -1, -1));

        getContentPane().add(panelAltaSocio, new AbsoluteConstraints(10, 10, 430, 400));
        cargarDatos();
        pack();
    }
    
    private void cargarDatos(){
    	
    	apellido.setText(socio.getApellido());
    	nombre.setText(socio.getNombre());
    	tipoDocumento.setSelectedItem(socio.getTipoDocumento());
    	numero.setText(socio.getDniL().toString());
    	
    	Date d = socio.getFechaNacimiento();
       	diaNacimiento.setSelectedIndex(d.getDate()-1);
    	mesNacimiento.setSelectedIndex(d.getMonth());
    	anioNacimiento.setSelectedItem(new Integer(d.getYear()+1900));
    	
    	edadAfiliacion.setText(socio.getEdadAfiliacionI().toString());
    	if (socio.getCategoria().getIdCategoria() == Categoria.FAMILIAR){
    		categoria.addItem(AdministradorDeCategorias.getCategoriaFamiliar());
    		categoria.setEnabled(false);
    	}
    	
    	categoria.setSelectedItem(socio.getCategoria());
    	
    	zona.setSelectedItem(socio.getZona());
    	    	
    }

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {

    	try{
			edadAfiliacion.setText(edadAfiliacion.getText().trim());
			numero.setText(numero.getText().trim());
    		Validador.validateApellido(apellido.getText());
    		Validador.validateNombre(nombre.getText());
    		Validador.validateDocumento((String)tipoDocumento.getSelectedItem(),numero.getText());
    		Validador.validateEdadAfiliacion(edadAfiliacion.getText());
    		Validador.validateFechaNacimiento(diaNacimiento.getSelectedIndex()+1, mesNacimiento.getSelectedIndex(),
    										((Integer)anioNacimiento.getSelectedItem()).intValue(),
    										Integer.valueOf(edadAfiliacion.getText()).intValue());
    		
    		Validador.validateCategoria(((Categoria)categoria.getSelectedItem()).getIdCategoria(), socio.isTitular(),
    				diaNacimiento.getSelectedIndex()+1,	mesNacimiento.getSelectedIndex(),
    				((Integer)anioNacimiento.getSelectedItem()).intValue());
        		
    		
    		Date d = new Date(((Integer)anioNacimiento.getSelectedItem()).intValue()-1900,
    				mesNacimiento.getSelectedIndex(),
    				diaNacimiento.getSelectedIndex()+1 );
    	    		
    		AdministradorDeSocios.ModificarSocio(socio.getIdSocio(), ((Zona)zona.getSelectedItem()).getIdZona(),
    				((Categoria)categoria.getSelectedItem()).getIdCategoria(),nombre.getText(), 
    				apellido.getText(), (String)tipoDocumento.getSelectedItem(),
    				Long.valueOf(numero.getText()).longValue(),d,Integer.valueOf(edadAfiliacion.getText()).intValue());
    		
    		ventanaPadre.cargarDatos();
			dispose();
    		
      	} catch (ValidadorException e){
    		JOptionPane.showMessageDialog(this,e.getMensaje(),"Advertencia",JOptionPane.WARNING_MESSAGE);
    	} catch (Exception e){
    		JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
    	}    	
    }

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {
    	ventanaPadre.cargarDatos();
    	dispose();
    }
    
	
    private void center(){
    	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    	Rectangle frame = getBounds();
    	setLocation((screen.width - frame.width)/2, (screen.height - frame.height)/2);
    }
            
    // Variables declaration 
    private JButton aceptar;
    private JComboBox anioNacimiento;
    private JTextField apellido;
    private JButton cancelar;
    private JComboBox categoria;
    private JComboBox diaNacimiento;
    private JTextField edadAfiliacion;
    private JLabel labelApellido;
    private JLabel labelBarra1;
    private JLabel labelBarra2;
    private JLabel labelCategoria;
    private JLabel labelEdadAfiliacion;
    private JLabel labelFechaNacimiento;
    private JLabel labelNombre;
    private JLabel labelNumero;
    private JLabel labelTipoDocumento;
    private JLabel labelZona;
    private JComboBox mesNacimiento;
    private JTextField nombre;
    private JTextField numero;
    private JPanel panelAltaSocio;
    private JComboBox tipoDocumento;
    private JComboBox zona;
    private FrmGestionSocios ventanaPadre;
    private Socio socio;
    // End of variables declaration
    
}
