package processo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LeitorDeArquivo {
	private Path arquivo;
	private Stream<String> stream= null;
	private List<String> listaMatrizTransformada;
	private int linhaX;
	private String curso;
	private String matriz;
	private String periodo;
	
	public LeitorDeArquivo(Path arquivo) {
		this.arquivo = arquivo;
		listaMatrizTransformada = new ArrayList<>();
		linhaX = 0;
	}


	public List<String> executa() {
		try {
			stream = Files.lines(arquivo, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			System.out.println("ERRO ao ler arquivo: "+ arquivo);
		}
		
		
		stream.forEach((linha)->{
			//Pega o CURSO
			if(linhaX == 2){
				curso = linha.split(";")[0];
			}
			
			//Trata demais casos
			if(linhaX > 2){
				String[] campos = linha.split(";");
				if(!campos[1].isEmpty()){ //Se tem o período é um novo período
					periodo = campos[1];
					matriz = campos[12];
				}
				if(!campos[2].isEmpty()){ //Se tem o cód disciplina é uma nova disciplina
					listaMatrizTransformada.add(curso +";"+ matriz +";"+ periodo +";"+ campos[2] +";"+ 
								campos[5] +";"+  campos[11] +";"+ arquivo.toString());
				}
			}
			linhaX++;
			
//			0	GRADE CURRICULAR(2)- Curso(3)
//			1	Período
//			2	Cód disciplina
//			3	TPeríodo
//			4	Usuário
//			5	Disciplina
//			6	GRADE CURRICULAR(1)
//			7	Data
//			8	data-hora
//			9	TOTAL GERAL DO CURSO
//			10	SUBTOTAL
//			11	CH
//			12	Matriz

			
		});

		return listaMatrizTransformada;
		
	}

}
