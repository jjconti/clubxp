package guiClub;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import utils.Validador;
import utils.ValidadorException;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import main.AdministradorDeLiquidaciones;
import main.AdministradorDeZonas;
import main.Recibo;
import main.Zona;

/*
 * FrmRendicion.java
 *
 * Created on 25 de noviembre de 2006, 18:10
 */


public class FrmRendicion extends JDialog {
    
    
	/** Creates new form FrmRendicion */
    public FrmRendicion() {
        initComponents();
        center();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        panelPrincipal = new JPanel();
        labelZona = new JLabel();
        zona = new JComboBox();
        labelNumero = new JLabel();
        numeroRecibo = new JTextField();
        devuelto = new JButton();
        pago = new JButton();
        jScrollPane = new JScrollPane();
        tabla = new JTable();
        rendicion = new JLabel();
        pesos = new JLabel();
        cerrar = new JButton();
        labelTituloTabla = new JLabel();

        getContentPane().setLayout(new AbsoluteLayout());

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rendici\u00f3n");
        setResizable(false);
        setModal(true);
        panelPrincipal.setLayout(new AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(10, 35));
        labelZona.setText("Zona:");
        panelPrincipal.add(labelZona, new AbsoluteConstraints(30, 20, -1, -1));

        zona.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                zonaActionPerformed(evt);
            }
        });
        zona.setModel(new DefaultComboBoxModel(AdministradorDeZonas.getZonas().toArray()));
        
        panelPrincipal.add(zona, new AbsoluteConstraints(70, 20, 50, -1));

        labelNumero.setText("N\u00famero de recibo:");
        panelPrincipal.add(labelNumero, new AbsoluteConstraints(30, 60, -1, -1));


        panelPrincipal.add(numeroRecibo, new AbsoluteConstraints(130, 60, 80, -1));

        devuelto.setText("Devuelto");
        devuelto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                devueltoActionPerformed(evt);
            }
        });

        panelPrincipal.add(devuelto, new AbsoluteConstraints(260, 60, -1, -1));

        pago.setText("Pago");
        pago.setMaximumSize(new java.awt.Dimension(75, 23));
        pago.setMinimumSize(new java.awt.Dimension(75, 23));
        pago.setPreferredSize(new java.awt.Dimension(75, 23));
        pago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pagoActionPerformed(evt);
            }
        });

        panelPrincipal.add(pago, new AbsoluteConstraints(370, 60, -1, -1));

        titulos = new Vector();
        titulos.addElement("N� Recibo");
        titulos.addElement("Mes");
        titulos.addElement("A�o");
        titulos.addElement("Valor");
        titulos.addElement("Id Socio");
        titulos.addElement("Apellido");
        titulos.addElement("Nombre");
       
        
        cargarDatos();
		tabla.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				reciboSeleccionado();
			}
		});
		
        jScrollPane.setViewportView(tabla);

        panelPrincipal.add(jScrollPane, new AbsoluteConstraints(30, 130, 670, 290));

        rendicion.setText("Rendici\u00f3n:  $");
        panelPrincipal.add(rendicion, new AbsoluteConstraints(30, 440, -1, -1));

        pesos.setText("0.0");
        panelPrincipal.add(pesos, new AbsoluteConstraints(100, 440, 150, -1));

        cerrar.setText("Cerrar");
        cerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cerrarActionPerformed(evt);
            }
        });

        panelPrincipal.add(cerrar, new AbsoluteConstraints(630, 440, -1, -1));

        labelTituloTabla.setText("Recibos devueltos");
        panelPrincipal.add(labelTituloTabla, new AbsoluteConstraints(30, 110, -1, -1));

        getContentPane().add(panelPrincipal, new AbsoluteConstraints(0, 0, 730, 480));

        pack();
    }

    protected void reciboSeleccionado() {
    	int row = tabla.getSelectedRow();
		if (row != -1){
			Integer reciboSeleccionado = (Integer)tabla.getModel().getValueAt(row,0);
			numeroRecibo.setText(reciboSeleccionado.toString());
		}
		
	}

	private void cargarDatos() {
    	
    	Vector datos = new Vector();
        
        List recibos = AdministradorDeLiquidaciones.getRecibosDevueltos(((Zona)zona.getSelectedItem()).getIdZona());
        Iterator i = recibos.iterator();
        while(i.hasNext()){
        	Recibo r = (Recibo) i.next();
        	Vector fila = new Vector();
        	
        	fila.addElement(r.getNumeroReciboI());
        	fila.addElement(r.getMesI());
        	fila.addElement(r.getAnioI());
        	fila.addElement(r.getValorF());
        	fila.addElement(r.getSocio().getIdSocioI());
        	fila.addElement(r.getSocio().getApellido());
        	fila.addElement(r.getSocio().getNombre());
        	
        	datos.addElement(fila);
        }
        
      
        tabla.setModel(new DefaultTableModel(datos,titulos) {
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        
        pesos.setText((new Float(AdministradorDeLiquidaciones.
        		calcularMontoRecibosNoDevueltos(((Zona)zona.getSelectedItem()).getIdZona()))).toString());
        
        numeroRecibo.setText("");
        numeroRecibo.requestFocus();
    
	}

	private void cerrarActionPerformed(ActionEvent evt) {
        dispose();
    }

    private void zonaActionPerformed(ActionEvent evt) {
        cargarDatos();
        zona.requestFocus();
    }

    private void devueltoActionPerformed(ActionEvent evt) {
        
    	try {
    		numeroRecibo.setText(numeroRecibo.getText().trim());
			Validador.validNumeroRecibo(numeroRecibo.getText());
			
			int n = new Integer(numeroRecibo.getText()).intValue();
			AdministradorDeLiquidaciones.setDevuelto(n, ((Zona) zona.getSelectedItem()).getIdZona(), true);
			cargarDatos();
    	
    	} catch (ValidadorException e) {
    		JOptionPane.showMessageDialog(this,e.getMensaje(),"Advertencia",JOptionPane.WARNING_MESSAGE);	
    		numeroRecibo.requestFocus();
		}
    }

    private void pagoActionPerformed(ActionEvent evt) {
    	try {
			Validador.validNumeroRecibo(numeroRecibo.getText());
			
			int n = new Integer(numeroRecibo.getText()).intValue();
			AdministradorDeLiquidaciones.setDevuelto(n, ((Zona) zona.getSelectedItem()).getIdZona(), false);
			cargarDatos();
			
    	} catch (ValidadorException e) {
    		JOptionPane.showMessageDialog(this,e.getMensaje(),"Advertencia",JOptionPane.WARNING_MESSAGE);
    		numeroRecibo.requestFocus();
		}
    }
 
    
    private void center(){
    	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    	Rectangle frame = getBounds();
    	setLocation((screen.width - frame.width)/2, (screen.height - frame.height)/2);
    }
    
    
    // Variables declaration - do not modify
    private JButton cerrar;
    private JButton devuelto;
    private JScrollPane jScrollPane;
    private JLabel labelNumero;
    private JLabel labelTituloTabla;
    private JLabel labelZona;
    private JTextField numeroRecibo;
    private JButton pago;
    private JPanel panelPrincipal;
    private JLabel pesos;
    private JLabel rendicion;
    private JTable tabla;
    private JComboBox zona;
    private Vector titulos;
    // End of variables declaration
    
}

