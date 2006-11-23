package guiClub;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import main.AdministradorDeLiquidaciones;


public class FrmPrincipal extends JFrame {
    
 
    public FrmPrincipal() {
        initComponents();
    }
    
   
    private void initComponents() {
        panelPrincipal = new JPanel();
        panelLiquidacion = new JPanel();
        botonLiquidar = new JButton();
        botonVerRecibos = new JButton();
        botonSeguimiento = new JButton();
        panelGestion = new JPanel();
        botonSocios = new JButton();
        botonGrupos = new JButton();
        labelTitulo = new JLabel();
        botonSalir = new JButton();
        panelLogo = new JPanel();

        getContentPane().setLayout(new AbsoluteLayout());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Gesti\u00f3n de C\u00e1tedra");
        panelPrincipal.setLayout(new AbsoluteLayout());

        panelPrincipal.setPreferredSize(new Dimension(400, 150));
        panelLiquidacion.setLayout(new AbsoluteLayout());

        panelLiquidacion.setBorder(new TitledBorder("Liquidaci\u00f3n"));
        botonLiquidar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botonLiquidarActionPerformed(evt);
            }
        });
        
        //Control botón Liquidar "año - mes"
        Integer mesProximaLiquidacion = AdministradorDeLiquidaciones.getMesProximaLiq();
        Integer anioProximaLiquidacion = AdministradorDeLiquidaciones.getAnioProximaLiq();
        
    	botonLiquidar.setText(mesProximaLiquidacion + " - " + anioProximaLiquidacion);
        
        if (AdministradorDeLiquidaciones.sePuedeLiquidar(Calendar.getInstance().getTime())){
        	botonLiquidar.setEnabled(true);
        } else {
        	botonLiquidar.setEnabled(false);
        }
        		
        panelLiquidacion.add(botonLiquidar, new AbsoluteConstraints(20, 20, 200, -1));

        botonVerRecibos.setText("Ver recibos de \u00faltima liquidaci\u00f3n");
            
        botonVerRecibos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //botonVerRecibosActionPerformed(evt);
            }
        });

        panelLiquidacion.add(botonVerRecibos, new AbsoluteConstraints(20, 50, 200, -1));

        botonSeguimiento.setText("Seguimiento de recibos");
        panelLiquidacion.add(botonSeguimiento, new AbsoluteConstraints(20, 80, 200, -1));

        if (!AdministradorDeLiquidaciones.existenLiquidaciones){
        	botonVerRecibos.setEnabled(false);
        	botonSeguimiento.setEnabled(false);
        }
        
        panelPrincipal.add(panelLiquidacion, new AbsoluteConstraints(20, 190, 240, 120));
        panelLiquidacion.getAccessibleContext().setAccessibleName("liquidacion");

        panelGestion.setLayout(new AbsoluteLayout());

        panelGestion.setBorder(new TitledBorder("Gestion"));
        botonSocios.setText("Gestion de Socios");
        botonSocios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botonSociosActionPerformed(evt);
            }
        });

        panelGestion.add(botonSocios, new AbsoluteConstraints(30, 20, 180, -1));

        botonGrupos.setText("Gestion de Grupos Familiares");
        botonGrupos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botonGruposActionPerformed(evt);
            }
        });

        panelGestion.add(botonGrupos, new AbsoluteConstraints(30, 50, 180, -1));

        panelPrincipal.add(panelGestion, new AbsoluteConstraints(20, 90, 240, 90));

        labelTitulo.setText("<html><font size=6><b>Sistema de Gestion del Club Americano");
        panelPrincipal.add(labelTitulo, new AbsoluteConstraints(40, 30, -1, -1));

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        panelPrincipal.add(botonSalir, new AbsoluteConstraints(440, 310, 60, -1));
        panelPrincipal.add(panelLogo, new AbsoluteConstraints(300, 100, 210, 190));

        panelLogo.setLayout(new GridLayout());
        panelLogo.add(new JLabel(new ImageIcon("americano.jpg")));
        panelPrincipal.add(panelLogo, new AbsoluteConstraints(300, 90, 210, 220));

        getContentPane().add(panelPrincipal, new AbsoluteConstraints(10, 10, 530, 350));
        pack();
        

    }
    
/*
    private void botonVerRecibosActionPerformed(ActionEvent evt) {
     new FrmVerRecibos().setVisible(true);  
    }
*/
    private void botonLiquidarActionPerformed(ActionEvent evt) {
     liquidar().setVisible(true);  
    }
    
    
    private JComponent liquidar() {
		
		return null;
	}


	private void botonSociosActionPerformed(ActionEvent evt) {
      new FrmGestionSocios().setVisible(true);
    }

    private void botonGruposActionPerformed(ActionEvent evt) {
      new FrmGestionGrupos().setVisible(true);
    }

    private void botonSalirActionPerformed(ActionEvent evt) {
    	System.exit(0);
    }
    
  
    private JButton botonGrupos;
    private JButton botonLiquidar;
    private JButton botonSalir;
    private JButton botonSeguimiento;
    private JButton botonSocios;
    private JButton botonVerRecibos;
    private JLabel labelTitulo;
    private JPanel panelGestion;
    private JPanel panelLiquidacion;
    private JPanel panelLogo;
    private JPanel panelPrincipal;

}
