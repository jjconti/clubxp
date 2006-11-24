package guiClub;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JRViewer;


public class FrmRecibos extends javax.swing.JDialog {
	
	
	final	String dbhost = "localhost";
	final	String dbname = "dbclub";
	final	String dbuser = "root";
	final	String dbpass = "";
	final	String dbdriver = "com.mysql.jdbc.Driver";
	final   String conurl = "jdbc:mysql://"+dbhost+"/"+dbname;
	
	Connection db;
	JasperReport reporteRecibos;
	JasperReport reporteIntegrantes;
	JasperPrint jasperPrint;
	HashMap params = new HashMap();
	
	private JButton cmdCerrar;
	//private JComboBox comboAño;
	//private JComboBox comboComision;
	private JLabel jLabel1;
	private JLabel jLabel3;
	private JPanel panelPrincipal;
	//private JPanel panelCardReporte;
	private JRViewer panelReporte; 
	//private JPanel panelAñoCom;
	//private JPanel panelAño;
	//private JPanel panelComision;
	private JPanel panelCerrar;
	
	
	public FrmRecibos() {
		inicializarComponentes();
		center();
	}
	
	
	private void inicializarComponentes() {
		try {
			
			 Class.forName(dbdriver).newInstance();
			 db = DriverManager.getConnection(conurl,dbuser,dbpass);
			
			 reporteRecibos = JasperCompileManager.compileReport("reciboTpMetodosAgiles.jrxml");
			 FileOutputStream out = new FileOutputStream("recibos.jasper");
			 ObjectOutputStream outs = new ObjectOutputStream(out);
			 outs.writeObject(reporteRecibos);
			 outs.close();
			 out.close();
			// NO DESCOMENTAR al menos que se pierda el archivo compilado
			 
			reporteIntegrantes = JasperCompileManager.compileReport("integrantesGrupoFamiliar.jrxml");
			params.put("SUBREPORT", reporteIntegrantes);
			
			panelPrincipal = new JPanel();
			//panelAñoCom = new JPanel();
			//panelAño = new JPanel();
			//panelComision = new JPanel();
			panelCerrar = new JPanel();
			//panelCardReporte = new JPanel();
			//comboComision = new JComboBox();
			jLabel3 = new JLabel();
			cmdCerrar = new JButton();
			jLabel1 = new JLabel();
			getContentPane().setLayout(new AbsoluteLayout());
			
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle("Listado de recibos");
			setModal(true);
			setResizable(false);
			
			panelPrincipal.setLayout(new BorderLayout());
			panelCerrar.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			
			try {
				jasperPrint = JasperFillManager.fillReport(
						new FileInputStream("recibos.jasper"), params, db);
			} catch (JRException e) {
				JOptionPane.showMessageDialog(this,"<html><b>JasperReports Exception</b>" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			panelReporte = new JRViewer(jasperPrint);
			
			//panelPrincipal.add(panelCardReporte,BorderLayout.CENTER);
			panelPrincipal.add(panelReporte,BorderLayout.CENTER);
			panelPrincipal.add(panelCerrar,BorderLayout.SOUTH);
			
			//panelCardReporte.add(panelReporte,"first");
			//panelCardReporte.add(panelReporte);
			
			cmdCerrar.setText("Cerrar");
			cmdCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					dispose();
				}
			});
			
			panelCerrar.add(cmdCerrar);
			
			getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
			getContentPane().add(panelPrincipal);
			Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
			int h = screenDim.height;
			int w = screenDim.width;
			screenDim = new Dimension(w*95/100, h*90/100);
			setBounds(new Rectangle(screenDim));
			//panelCardReporte.setPreferredSize(new Dimension(w*90/100, h*72/100));
			panelReporte.setPreferredSize(new Dimension(w*87/100, h*70/100));
			
			pack();
			
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(this,"<html><b>Excepción SQL</b><br>Verifiqué que la base de datos esté corriendo.","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (JRException e) {
			JOptionPane.showMessageDialog(this,"<html><b>JasperReports Exception</b><br>Verifique que exista el archivo .jrxml","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}
/*
	private void comboChangedActionPerformed() {
		String c = (String)comboComision.getSelectedItem();
		String a = (String)comboAño.getSelectedItem().toString();
		
		if(a !=null){ // c no puede ser null
			params.put("Anio",a);
			params.put("Comision",c);
			
			try {
				jasperPrint = JasperFillManager.fillReport(
						new FileInputStream("alumnos2.jasper"), params, db);
			} catch (JRException e) {
				JOptionPane.showMessageDialog(this,"<html><b>JasperReports Exception</b>" + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			JRViewer temp = new JRViewer(jasperPrint);
			
			Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
			int h = screenDim.height;
			int w = screenDim.width;
			temp.setPreferredSize(new Dimension(w*87/100, h*70/100));
			
			panelCardReporte.remove(panelReporte);
			panelCardReporte.add(temp);
			panelReporte = temp;
		}
	}
*/	
	 /**
     * Centers the frame on the screen.
     *

     * This centering service is more or less in {@link UiUtil}; this duplication 
     * is justified only because the use of {@link UiUtil} would entail more 
     * class loading, which is not desirable for a splash screen.
     */
     private void center(){
       Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
       Rectangle frame = getBounds();
       setLocation((screen.width - frame.width)/2, (screen.height - frame.height)/2);
     }
	
}



