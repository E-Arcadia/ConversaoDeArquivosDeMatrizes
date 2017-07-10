import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import processo.LeitorDeArquivo;
import view.SelecionaArquivos;

public class Principal {

	private List<Path> listaDeArquivosImportar;
	private List<String> listaDeMatrizes;

	public Principal() {
		selecionaArquivos();
		processaArquivos();
		salvaArquivo();

		System.out.println("Total de arquivos: " + listaDeArquivosImportar.size());

	}

	private void salvaArquivo() {
		String novoArquivo = "matrizes.csv";
		try {
			Files.write(Paths.get(novoArquivo), listaDeMatrizes, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			System.out.println("Erro ao salvar arquivo: " + novoArquivo);
		}

		
	}

	private void processaArquivos() {
		listaDeMatrizes = new ArrayList<>();
		listaDeMatrizes.add("curso;matriz;periodo;cod_disciplina;Disciplina;CH;arquivo");
		for (Path arq : listaDeArquivosImportar) {
			System.out.print("Tratando Arquivo: " + arq.toString());
			List<String> listaNova = (new LeitorDeArquivo(arq)).executa();
			if (listaNova.size() > 0){
				if (listaDeMatrizes == null)
					listaDeMatrizes = listaNova;
				else
					listaDeMatrizes.addAll(listaNova);
				System.out.println(" - OK.");
			}else {
				System.out.println(" - ERRO.");
			}
		}
	}

	private void selecionaArquivos() {
		listaDeArquivosImportar = new SelecionaArquivos().get(null);
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		new Principal();

	}

}
