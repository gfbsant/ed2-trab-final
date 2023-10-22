import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Indexer {




    public static void exibirPalavrasMaisFrequentes(int n, File arquivo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            Map<String, Integer> frequenciaPalavras = new HashMap<>();
    
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] palavras = linha.split("\\s+"); 
                for (String palavra : palavras) {
                    palavra = palavra.toUpperCase();
                    frequenciaPalavras.put(palavra, frequenciaPalavras.getOrDefault(palavra, 0) + 1);
                }
            }
            reader.close();
    
            List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(frequenciaPalavras.entrySet());
            listaOrdenada.sort((a, b) -> b.getValue().compareTo(a.getValue()));
    
            System.out.println("As " + n + " palavras mais frequentes no arquivo são:");
            for (int i = 0; i < n && i < listaOrdenada.size(); i++) {
                Map.Entry<String, Integer> entrada = listaOrdenada.get(i);
                System.out.println(entrada.getKey() + ": " + entrada.getValue() + " vezes");
            }
        } catch (IOException e) {
            System.err.println("Erro de leitura do arquivo: " + e.getMessage());
        }
    }

    public static void pesquisarFrequenciaPalavras(String targetWord, File arquivo) {
        if (!arquivo.exists() || !arquivo.isFile()) {
            System.out.println("O arquivo não existe ou não é um arquivo válido.");
            return;
        }
    
        try {
            Map<String, Integer> frequenciaPalavras = new HashMap<>();
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String linha;
    
            while ((linha = br.readLine()) != null) {
                String[] palavras = linha.split(" ");
                for (String palavra : palavras) {
                    palavra = palavra.toLowerCase(); 
                    if (frequenciaPalavras.containsKey(palavra)) {
                        int frequencia = frequenciaPalavras.get(palavra);
                        frequenciaPalavras.put(palavra, frequencia + 1);
                    } else {
                        frequenciaPalavras.put(palavra, 1);
                    }
                }
            }
    
            br.close();
    
            int frequencia = frequenciaPalavras.getOrDefault(targetWord.toLowerCase(), 0);
            System.out.println("A palavra '" + targetWord + "' aparece " + frequencia + " vezes no arquivo.");
    
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Indexer indexer = new Indexer();
        File targetFile;
        String command = "";

        while (true && !command.equals("4")) {
            System.out.printf("\n\n");
            if (scanner.hasNext()) {
                scanner.next();
            }
            System.out.println("Comandos disponíveis (selecione o numero primeiro): ");
            System.out.println("1. indexer --freq N ARQUIVO");
            System.out.println("2. indexer --freq-word PALAVRA ARQUIVO");
            System.out.println("3. indexer --search TERMO ARQUIVO [ARQUIVO ...]");
            System.out.println("4. Sair (Digite 'exit')");

            command = scanner.nextLine();

            if (command.equals("4")) {
                System.out.println("Encerrando o programa.");
                break;
            }

            if (command.length() != 1 || !Character.isDigit(command.charAt(0)) || command.charAt(0) < '1'
                    || command.charAt(0) > '4') {
                System.err.println("Comando inválido. Verifique a sintaxe.");
                continue;
            }

            switch (command) {
                case "1":
                    targetFile = Indexer.listarArquivosTxt();
                    System.out.println("Quantas palavras mais frequentes você deseja ver?");
                    int targetAmount = scanner.nextInt();
                    Indexer.exibirPalavrasMaisFrequentes(targetAmount, targetFile);
                    break;
                case "2":
                    targetFile = Indexer.listarArquivosTxt();
                    System.out.println("Qual palavra você deseja ver a frequência?");
                    String targetWord = scanner.next();
                    Indexer.pesquisarFrequenciaPalavras(targetWord, targetFile);
                    break;
                case "3":
                    command = "indexer";
                    break;
                case "4":
                    command = "exit";
                    break;
                default:
                    System.err.println("Comando inválido. Verifique a sintaxe.");
                    continue;
            }

        }

    }

    public static File listarArquivosTxt() {
        String diretorio = "samples";

        List<File> arquivosTxt = new ArrayList<>();

        File pasta = new File(diretorio);
        File[] arquivos = pasta.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".txt")) {
                    arquivosTxt.add(arquivo);
                }
            }
        }

        if (arquivosTxt.isEmpty()) {
            System.out.println("Nenhum arquivo .txt encontrado na pasta.");
        } else {
            System.out.println("Arquivos .txt encontrados na pasta:");
            for (int i = 0; i < arquivosTxt.size(); i++) {
                System.out.println((i + 1) + ". " + arquivosTxt.get(i).getName());
            }

            System.out.print("Escolha o número do arquivo desejado: ");
            Scanner scanner = new Scanner(System.in);
            int escolha = scanner.nextInt();

            if (escolha >= 1 && escolha <= arquivosTxt.size()) {
                File arquivoEscolhido = arquivosTxt.get(escolha - 1);
                System.out.println("Você escolheu: " + arquivoEscolhido.getName());
                return arquivoEscolhido;
            } else {
                System.out.println("Escolha inválida.");
                listarArquivosTxt();
            }
        }
        return null;
    }
}
