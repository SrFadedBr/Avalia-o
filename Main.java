import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Aluno> alunos = new ArrayList<>();

        
        try (BufferedReader br = new BufferedReader(new FileReader("alunos.csv"))) {
            
            String cabecalho = br.readLine();

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    String matricula = dados[0].trim();
                    String nome = dados[1].trim();
                    double nota = Double.parseDouble(dados[2].trim().replace(",", ".")); // Substitui vírgula por ponto para formato correto de double

                    Aluno aluno = new Aluno(matricula, nome, nota);
                    alunos.add(aluno);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
            return;
        }

        
        int quantidadeAlunos = alunos.size();
        int quantidadeAprovados = 0;
        int quantidadeReprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0.0;

        for (Aluno aluno : alunos) {
            double nota = aluno.getNota();
            somaNotas += nota;
            if (nota >= 6.0) {
                quantidadeAprovados++;
            } else {
                quantidadeReprovados++;
            }
            if (nota < menorNota) {
                menorNota = nota;
            }
            if (nota > maiorNota) {
                maiorNota = nota;
            }
        }

        double mediaGeral = somaNotas / quantidadeAlunos;

        
        try (PrintWriter pw = new PrintWriter(new FileWriter("resumo.csv"))) {
            pw.println("Quantidade de alunos:," + quantidadeAlunos);
            pw.println("Aprovados:," + quantidadeAprovados);
            pw.println("Reprovados:," + quantidadeReprovados);
            pw.println("Menor nota:," + menorNota);
            pw.println("Maior nota:," + maiorNota);
            pw.println("Média geral:," + mediaGeral);

            
            pw.println("\nMatrícula;Nome;Nota");
            for (Aluno aluno : alunos) {
                pw.println(aluno);
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar arquivo: " + e.getMessage());
        }

        System.out.println("Processamento concluído. Resultados gravados em resumo.csv.");
    }
}
