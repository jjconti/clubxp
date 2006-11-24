package guiClub;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import utils.ValidadorException;

import main.AdministradorDeFamilias;
import main.Socio;


public class FrmModificarGrupo extends JDialog{

	public FrmModificarGrupo(FrmGestionGrupos f, Socio t) {
		padre = f;
		titular = t;
		initComponents();
		center();
	}

	private void initComponents() {
		panelPrincipal = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tablaSocios = new javax.swing.JTable();
		jScrollPane2 = new javax.swing.JScrollPane();
		tablaGrupo = new javax.swing.JTable();
		quitar = new javax.swing.JButton();
		agregar = new javax.swing.JButton();
		labelSociosSinGrupo = new javax.swing.JLabel();
		labelIntegrantes = new javax.swing.JLabel();
		cancelar = new javax.swing.JButton();
		aceptar = new javax.swing.JButton();

		getContentPane().setLayout(new AbsoluteLayout());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setModal(true);
		
		setTitle("Modificar grupo familiar");
		setResizable(false);
		panelPrincipal.setLayout(new AbsoluteLayout());

		cargarDatosSocios();

		jScrollPane1.setViewportView(tablaSocios);

		panelPrincipal.add(jScrollPane1, new AbsoluteConstraints(10, 50, 570, 150));

		jScrollPane2.setBorder(null);

		cargarDatosIntegrantes();

		//tablaGrupo.setPreferredSize(new java.awt.Dimension(90, 64));
		jScrollPane2.setViewportView(tablaGrupo);

		panelPrincipal.add(jScrollPane2, new AbsoluteConstraints(10, 280, 570, 81));

		quitar.setText("Quitar");
		quitar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				quitarActionPerformed(evt);
			}
		});

		panelPrincipal.add(quitar, new AbsoluteConstraints(10, 370, 80, -1));

		agregar.setText("Agregar");
		agregar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				agregarActionPerformed(evt);
			}
		});

		panelPrincipal.add(agregar, new AbsoluteConstraints(10, 210, 80, -1));

		labelSociosSinGrupo.setText("Socios que no pertenecen a un Grupo Familiar:");
		panelPrincipal.add(labelSociosSinGrupo, new AbsoluteConstraints(10, 30, -1, -1));

		labelIntegrantes.setText("Integrantes del Grupo Familiar:");
		panelPrincipal.add(labelIntegrantes, new AbsoluteConstraints(10, 260, -1, -1));

		cancelar.setText("Cancelar");
		cancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelarActionPerformed();
			}
		});

		panelPrincipal.add(cancelar, new AbsoluteConstraints(320, 430, -1, -1));

		aceptar.setText("Aceptar");
		aceptar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				aceptarActionPerformed();
			}
		});

		panelPrincipal.add(aceptar, new AbsoluteConstraints(220, 430, -1, -1));

		getContentPane().add(panelPrincipal, new AbsoluteConstraints(10, 10, 600, 480));

		pack();
	}

	public void cargarDatosSocios() {

		titulos.addElement("Nro. Socio");
		titulos.addElement("Apellido");
		titulos.addElement("Nombre");
		titulos.addElement("Tipo documento");
		titulos.addElement("Número");    	

		List socios = AdministradorDeFamilias.getSociosSinGrupoFamiliar();

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

			data.addElement(aux);
		}

		tablaSocios.setModel(new DefaultTableModel(data,titulos){

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});

		if(data.size() == 0){ // no hay socios
			agregar.setEnabled(false);
		}else {
			agregar.setEnabled(true);
		}

		sociosModel = (DefaultTableModel) tablaSocios.getModel();

	}

	public void cargarDatosIntegrantes() {

		titulos2.addElement("Nro. Socio");
		titulos2.addElement("Apellido");
		titulos2.addElement("Nombre");
		titulos2.addElement("Tipo documento");
		titulos2.addElement("Número");
		titulos2.addElement("Titular");

		Vector data = new Vector();
		Vector aux = new Vector();

		//agregamos los datos del titular
		aux.addElement(titular.getIdSocioI());
		aux.addElement(titular.getApellido());
		aux.addElement(titular.getNombre());
		aux.addElement(titular.getTipoDocumento());
		aux.addElement(titular.getDniL());
		aux.addElement(new Boolean(true));
	
		data.addElement(aux);

		//agregamos los datos de los asociados del titular 
		Set asociados = titular.getSocios();

		Iterator i = asociados.iterator();
		while(i.hasNext()){
			Socio s = (Socio) i.next();
			Vector aux2 = new Vector();

			aux2.addElement(s.getIdSocioI());
			aux2.addElement(s.getApellido());
			aux2.addElement(s.getNombre());
			aux2.addElement(s.getTipoDocumento());
			aux2.addElement(s.getDniL());
			aux2.addElement(new Boolean(false));

			data.addElement(aux2);

		}

		tablaGrupo.setModel(new DefaultTableModel(data,titulos2){

			boolean[] canEdit = new boolean [] {
					false, false, false, false, false, true
			};

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}

			Class[] types = new Class [] {
					java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return types [columnIndex];
			}

		});

		grupoModel = (DefaultTableModel) tablaGrupo.getModel();

	}

	private void cancelarActionPerformed() {
		padre.cargarDatosTitulares();
		dispose();
	}

	private void aceptarActionPerformed() {

		Vector idsAsociados = new Vector();
		
		int titulares = 0;
		for(int i = 0; i < tablaGrupo.getRowCount(); i++){

			if (((Boolean)grupoModel.getValueAt(i,5)).booleanValue()){			
				titulares++;
			}	
		}	

		if (titulares == 1){

			//tiene que haber como mínimo dos integrantes en el grupo familiar
			if (tablaGrupo.getRowCount() >= 2){

				Integer idTitular = new Integer(0);
				
				for(int i = 0; i < tablaGrupo.getRowCount(); i++){

					if (!((Boolean)grupoModel.getValueAt(i,5)).booleanValue()){			
						idsAsociados.addElement(grupoModel.getValueAt(i,0));
					} else {
						idTitular = (Integer)grupoModel.getValueAt(i,0);
					}
				}	
				try {
					AdministradorDeFamilias.EliminarFamilia(titular.getIdSocio());
					AdministradorDeFamilias.CrearFamilia(idTitular.intValue(),idsAsociados);
					padre.cargarDatosTitulares();
					padre.cargarIntegrantesGrupo(titular.getIdSocio());
					dispose(); // luego de aceptar y que se carge con éxito se cierra esta ventana
				} catch (ValidadorException e) {
					JOptionPane.showMessageDialog(this,e.getMensaje(),"Advertencia",JOptionPane.WARNING_MESSAGE);
				}
	
			} else {
				JOptionPane.showMessageDialog(this,"No hay suficientes integrantes para el grupo.","Advertencia",JOptionPane.WARNING_MESSAGE);
			}

		}else{
			JOptionPane.showMessageDialog(this,"Debe seleccionar"+((titulares==0)?"":" sólo")+" un titular.","Advertencia",JOptionPane.WARNING_MESSAGE);
		}
	}

	private void agregarActionPerformed(java.awt.event.ActionEvent evt) {

		int[] filas=tablaSocios.getSelectedRows();

		if (filas.length != 0){

			for (int i=0; i< filas.length; i++){
				Vector aux = new Vector();
				aux.addElement(sociosModel.getValueAt(filas[i],0));
				aux.addElement(sociosModel.getValueAt(filas[i],1));
				aux.addElement(sociosModel.getValueAt(filas[i],2));
				aux.addElement(sociosModel.getValueAt(filas[i],3));
				aux.addElement(sociosModel.getValueAt(filas[i],4));
				aux.addElement(new Boolean(false));
				sociosModel.removeRow(filas[i]);	
				grupoModel.addRow(aux);
			}

		}	
	}


	private void quitarActionPerformed(java.awt.event.ActionEvent evt) {
		
		int[] filas=tablaGrupo.getSelectedRows();

		if (filas.length != 0){

			for (int i=0; i< filas.length; i++){
				Vector aux = new Vector();
				aux.addElement(grupoModel.getValueAt(filas[i],0));
				aux.addElement(grupoModel.getValueAt(filas[i],1));
				aux.addElement(grupoModel.getValueAt(filas[i],2));
				aux.addElement(grupoModel.getValueAt(filas[i],3));
				aux.addElement(grupoModel.getValueAt(filas[i],4));
				grupoModel.removeRow(filas[i]);	
				sociosModel.addRow(aux);
			}

		}	
	}

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
	
	private JButton aceptar;
	private JButton agregar;
	private JButton cancelar;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JLabel labelIntegrantes;
	private JLabel labelSociosSinGrupo;
	private JPanel panelPrincipal;
	private JButton quitar;
	private JTable tablaSocios;
	private JTable tablaGrupo;
	private DefaultTableModel sociosModel;
	private DefaultTableModel grupoModel;
	private Vector titulos = new Vector();
	private Vector titulos2 = new Vector();
	private FrmGestionGrupos padre;
	private Socio titular;
}  
