package guiClub;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import main.AdministradorDeFamilias;
import main.AdministradorDeSocios;
import main.Socio;

public class FrmGestionGrupos extends JDialog{

	public FrmGestionGrupos() {
		initComponents();
		center();
	}

	private void initComponents() {
		panelPrincipal = new JPanel();
		modificar = new JButton();
		eliminar = new JButton();
		cerrar = new JButton();
		nuevo = new JButton();
		jScrollPane1 = new JScrollPane();
		tablaGrupos = new JTable();
		lblTitulares = new JLabel();
		lblGrupo = new JLabel();
		jScrollPane2 = new JScrollPane();
		tablaIntegrantes = new JTable();

		getContentPane().setLayout(new AbsoluteLayout());

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModal(true);
		setTitle("Gestión grupos familiares");
		setResizable(false);
		panelPrincipal.setLayout(new AbsoluteLayout());

		modificar.setText("Modificar");
		modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				modificarActionPerformed(evt);
			}
		});

		panelPrincipal.add(modificar, new AbsoluteConstraints(210, 390, -1, -1));

		eliminar.setText("Eliminar");
		eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				eliminarActionPerformed(evt);
			}

		});

		panelPrincipal.add(eliminar, new AbsoluteConstraints(330, 390, -1, -1));

		cerrar.setText("Cerrar");
		cerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cerrarActionPerformed(evt);
			}
		});

		panelPrincipal.add(cerrar, new AbsoluteConstraints(440, 390, -1, -1));

		nuevo.setText("Nuevo");
		nuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				nuevoActionPerformed(evt);
			}
		});

		panelPrincipal.add(nuevo, new AbsoluteConstraints(100, 390, -1, -1));

		jScrollPane1.setViewportView(tablaGrupos);
		panelPrincipal.add(jScrollPane1, new AbsoluteConstraints(30, 30, 590, 210));

		lblGrupo.setText("Integrantes del Grupo seleccionado:");
		panelPrincipal.add(lblGrupo, new AbsoluteConstraints(30, 250, -1, -1));

		jScrollPane2.setBorder(null);
		tablaIntegrantes.setRowSelectionAllowed(false);
		jScrollPane2.setViewportView(tablaIntegrantes);

		lblTitulares.setText("Titulares de Grupos Familiares:");
		panelPrincipal.add(lblTitulares, new AbsoluteConstraints(30, 10, 170, -1));

		panelPrincipal.add(jScrollPane2, new AbsoluteConstraints(30, 270, 590, 90));

		titulos = new Vector();
		titulos.addElement("Id Titular");
		titulos.addElement("Apellido");
		titulos.addElement("Nombre");
		titulos.addElement("Tipo documento");
		titulos.addElement("Número");

		titulos2 = new Vector();
		titulos2.addElement("Nro. Socio");
		titulos2.addElement("Apellido");
		titulos2.addElement("Nombre");
		titulos2.addElement("Tipo documento");
		titulos2.addElement("Número");

		cargarDatosTitulares();

		tablaGrupos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent arg0) {
				int row = tablaGrupos.getSelectedRow();
				if (row != -1 && tablaGrupos.getModel().getRowCount() != 0){

					int titularSeleccionado = ((Integer)gruposModel.getValueAt(row,0)).intValue();
					cargarIntegrantesGrupo(titularSeleccionado);
				}
				
			}

		});

		/*
		gruposModel.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				int row = tablaGrupos.getSelectedRow();
				if (row != -1){

					int titularSeleccionado = ((Integer)gruposModel.getValueAt(row,0)).intValue();
					cargarIntegrantesGrupo(titularSeleccionado);
				}
			}
		});
		*/
		
		getContentPane().add(panelPrincipal, new AbsoluteConstraints(-10, -10, 640, 430));

		pack();
	}

	
	private void nuevoActionPerformed(ActionEvent evt) {
		new FrmCargaGrupo(this).setVisible(true);
	}

	private void eliminarActionPerformed(ActionEvent evt) {

		int row = tablaGrupos.getSelectedRow();
		if (row != -1){

			Integer idTitularElegido = (Integer)gruposModel.getValueAt(row,0);
			
			//No se eliminan los socios del grupo, sólo se desarma el grupo familiar.
			int opcion = JOptionPane.showConfirmDialog(this,"Seguro que desea desarmar el grupo familiar?",
					"Confirmación de Eliminación",  JOptionPane.YES_NO_OPTION);
			if(opcion == JOptionPane.OK_OPTION){
				AdministradorDeFamilias.EliminarFamilia(idTitularElegido.intValue());
				gruposModel.removeRow(row);
				
				//Borramos los integrantes de la tabla
				int cantFilas=tablaIntegrantes.getRowCount();
			
				for (int i=cantFilas - 1; i >= 0; i--){
					integrantesModel.removeRow(i);
				}
		
				if (tablaGrupos.getRowCount() == 0){
					eliminar.setEnabled(false);
					modificar.setEnabled(false);
				}else {
					eliminar.setEnabled(true);
					modificar.setEnabled(true);
				}	
			}
		}
	}

	private void cerrarActionPerformed(ActionEvent evt) {
		dispose();
	}

	private void modificarActionPerformed(ActionEvent evt) {
		int row = tablaGrupos.getSelectedRow();
		if(row != -1){
			Integer idTitularElegido = (Integer)gruposModel.getValueAt(row,0);
			Socio titularElegido = AdministradorDeSocios.ObtenerSocio(idTitularElegido.intValue());
			new FrmModificarGrupo(this, titularElegido).setVisible(true);
		}
	}

	public void cargarDatosTitulares() {

		List titulares = AdministradorDeFamilias.getTitulares();
		
		Vector data = new Vector();
		Iterator i = titulares.iterator();
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


		tablaGrupos.setModel(new DefaultTableModel(data,titulos){

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});

		if(data.size() == 0){ // no hay titulares
			eliminar.setEnabled(false);
			modificar.setEnabled(false);
		}else {
			eliminar.setEnabled(true);
			modificar.setEnabled(true);
		}

		gruposModel = (DefaultTableModel) tablaGrupos.getModel();

	}

	public void cargarIntegrantesGrupo(int idTitular) {

		List asociados = AdministradorDeFamilias.getAsociados(idTitular);

		Vector data = new Vector();
		Iterator i = asociados.iterator();
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

		tablaIntegrantes.setModel(new DefaultTableModel(data,titulos2){

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		
		tablaIntegrantes.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		/*
		if(data.size() == 0){ // no hay titulares
			eliminar.setEnabled(false);
			modificar.setEnabled(false);
		}else {
			eliminar.setEnabled(true);
			modificar.setEnabled(true);
		}*/

		integrantesModel = (DefaultTableModel) tablaIntegrantes.getModel();

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
	
	private JButton cerrar;
	private JButton eliminar;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JLabel lblGrupo;
	private JLabel lblTitulares;
	private JButton modificar;
	private JButton nuevo;
	private JPanel panelPrincipal;
	private JTable tablaGrupos;
	private JTable tablaIntegrantes;
	private Vector titulos;
	private Vector titulos2;
	private DefaultTableModel gruposModel;
	private DefaultTableModel integrantesModel;


}
