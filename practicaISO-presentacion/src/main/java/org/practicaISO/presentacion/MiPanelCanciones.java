package org.practicaISO.presentacion;

import javax.swing.JPanel;

import org.practicaISO.dominio.Cancion;
import org.practicaISO.dominio.Cliente;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

public class MiPanelCanciones extends JPanel {
	private JList list;
	private JLabel lblEstaEsTu;
	private JButton btnReproductor;
	private JButton btnParar;
	private JLabel lblReproduciendo;
	private JLabel label;
	private JLabel lblNick;
	private JButton btnActualizarLista;

	/**
	 * Create the panel.
	 */

	public MiPanelCanciones(Cliente c) {
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		{
			list = new JList();
			list.addListSelectionListener(new ListListSelectionListener());
			list.setFont(new Font("Tahoma", Font.BOLD, 17));
			list.setForeground(SystemColor.control);
			list.setBackground(Color.GRAY);
			list.setBounds(58, 168, 559, 233);
			add(list);
		}
		{
			lblEstaEsTu = new JLabel("Lista de canciones");
			lblEstaEsTu.setFont(new Font("Tahoma", Font.BOLD, 26));
			lblEstaEsTu.setForeground(Color.WHITE);
			lblEstaEsTu.setBounds(58, 26, 438, 58);
			add(lblEstaEsTu);
		}
		actualizarModelo(c);
		{
			btnReproductor = new JButton("Reproducir");
			btnReproductor.setIcon(
					new ImageIcon(MiPanelCanciones.class.getResource("/org/practicaISO/presentacion/IconoPlay.png")));
			btnReproductor.addActionListener(new BtnReproductorActionListener());
			btnReproductor.setEnabled(false);
			btnReproductor.setBounds(642, 170, 146, 51);
			add(btnReproductor);
		}
		{
			btnParar = new JButton("Parar");
			btnParar.setIcon(
					new ImageIcon(MiPanelCanciones.class.getResource("/org/practicaISO/presentacion/IconoParar.png")));
			btnParar.addActionListener(new BtnPararActionListener());
			btnParar.setEnabled(false);
			btnParar.setBounds(642, 251, 146, 51);
			add(btnParar);
		}
		{
			lblReproduciendo = new JLabel("");
			lblReproduciendo.setForeground(Color.WHITE);
			lblReproduciendo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 21));
			lblReproduciendo.setBounds(58, 441, 819, 51);
			add(lblReproduciendo);
		}
		{
			label = new JLabel("");
			label.setIcon(new ImageIcon(
					MiPanelCanciones.class.getResource("/org/practicaISO/presentacion/IconoSpotify.png")));
			label.setBounds(768, 0, 176, 127);
			add(label);
		}
		{
			lblNick = new JLabel((String) null);
			lblNick.setForeground(Color.WHITE);
			lblNick.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
			lblNick.setBounds(58, 89, 242, 38);
			lblNick.setText(c.getNick());
			add(lblNick);

		}
		{
			btnActualizarLista = new JButton("Actualizar lista");
			btnActualizarLista.setIcon(new ImageIcon(
					MiPanelCanciones.class.getResource("/org/practicaISO/presentacion/IconoActualizar.png")));
			btnActualizarLista.addActionListener(new BtnActualizarListaActionListener());
			btnActualizarLista.setBounds(642, 331, 189, 51);
			add(btnActualizarLista);
		}

	}

	public void actualizarModelo(Cliente c) {
		ArrayList<Cancion> ac = null;
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		try {
			ac = c.obtenerCanciones();
		} catch (Exception e) {
			System.out.println("Error al obtener canciones del usuario");
		}
		if (ac != null) {
			for (Cancion canc : ac) {
				canc = canc.obtenerCancionId();
				modelo.addElement(canc.toString());
			}
		} else {
			JOptionPane.showMessageDialog(this, "No hay canciones que mostrar");
		}
		list.setModel(modelo);

	}

	private class BtnPararActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnParar.setEnabled(false);
			lblReproduciendo.setText("");
			btnReproductor.setEnabled(true);
		}
	}

	private class ListListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			btnReproductor.setEnabled(true);

		}
	}

	private class BtnReproductorActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnReproductor.setEnabled(false);
			btnParar.setEnabled(true);
			String cancion = list.getSelectedValue().toString();
			StringTokenizer token = new StringTokenizer(cancion, "-");
			Cancion canc = new Cancion(Integer.parseInt(token.nextToken()));
			try {
				canc = canc.obtenerCancionId();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			lblReproduciendo.setText("Reproduciendo " + canc.getTitulo() + " - " + canc.getAutor());
		}
	}

	private class BtnActualizarListaActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Cliente client = new Cliente(lblNick.getText());
			actualizarModelo(client);
			btnReproductor.setEnabled(false);
			btnParar.setEnabled(false);
		}
	}
}
