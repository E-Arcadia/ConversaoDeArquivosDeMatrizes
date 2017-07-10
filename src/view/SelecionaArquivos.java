package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SelecionaArquivos {

	public List<Path> get(String caminho) {
		String caminhoInicial = null;
		if (caminho != null)
			caminhoInicial = caminho;
		else
			caminhoInicial = System.getProperty("user.home");

		JFileChooser jfilechooser = new JFileChooser(caminhoInicial);
		jfilechooser.setSelectedFiles(
				new File(caminhoInicial).listFiles(new FiltroArquivos()));
		jfilechooser.setMultiSelectionEnabled(true);
		jfilechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		jfilechooser.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
					// File oldDir = (File) evt.getOldValue();
					File newDir = (File) evt.getNewValue();
					File file = new File(newDir.getAbsolutePath());
					JFileChooser jFileChooser = (JFileChooser) evt.getSource();
					jFileChooser.setSelectedFiles(file.listFiles(new FiltroArquivos()));
				}
			}
		});

		int retorno = jfilechooser.showOpenDialog(null);

		switch (retorno) {
		case JFileChooser.CANCEL_OPTION:
			JOptionPane.showMessageDialog(null, "Operação Cancelada");
			break;
		case JFileChooser.APPROVE_OPTION:
			File[] files = jfilechooser.getSelectedFiles();
			List<Path> pathProperties = new ArrayList<>();
			if (files != null || files.length > 0) {
				for (File file : files) {
					pathProperties.add(Paths.get(file.getAbsolutePath()));
				}
			}
			return pathProperties;
		case JFileChooser.ERROR_OPTION:
			JOptionPane.showMessageDialog(null, "Erro na Operação");
			break;
		}
		return null;
	}
	
	
	public class FiltroArquivos implements java.io.FileFilter{
		@Override
		public boolean accept(File f) {
			return f.isFile() & f.getName().endsWith(".csv") || f.getName().endsWith(".CSV");
		}
	}

}




