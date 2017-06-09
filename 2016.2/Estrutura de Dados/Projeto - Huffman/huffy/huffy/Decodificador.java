package huffy;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *  Classe Decodificador - lê arquivo e dicionário, monta a arvore e salva
 *  em um arquivo de texto
 * @author Fabs
 */
public class Decodificador {


    public static void decodificar(String file) throws IOException {
        /* declarando váriaveis package-private: montagem de árvore */
        Node tree = Codificador.treeRoot; //monta o 
        Node root = tree;
        FileInputStream in = null;
        FileOutputStream out = null;
        StringBuilder text = new StringBuilder();
        String fullText = "";


        try {
        /* Interface Gráfica para Seleção do Usuário */
        JFileChooser salvar = new JFileChooser();
        salvar.setCurrentDirectory(new java.io.File("."));
        salvar.setDialogTitle("Digite o nome do arquivo decodificado a ser salvo");
        salvar.setFileSelectionMode(JFileChooser.FILES_ONLY);
        salvar.setAcceptAllFileFilterUsed(false);
        salvar.showSaveDialog(null);

        in = new FileInputStream(file); //leitura
        out = new FileOutputStream(salvar.getSelectedFile().getAbsolutePath()); //escrita
            
            int c;
            while ((c = in.read()) != -1) {
                if (Integer.toBinaryString(c).length() == 8) {
                    text.append(Integer.toBinaryString(c));
                }
                else {
                    text.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
                    }
            }
            fullText = text.toString();
            char[] chars = new char[fullText.length()];
            chars = fullText.toCharArray();
            int contador = 0;

            /* Loop de travessia de árvore */
            while (contador < text.length()) {
                if (chars[contador] == '0') {
                    tree = tree.left; //se o byte for 0 vai para o no da esquerda
                }
                else {
                    tree = tree.right; //se for 1, vai para direita
                }
                if (tree.key != null) { //se ainda existe caractere
                    if (tree.key == 'µ') { //verifica se ocaractere de EOF foi encontrado.
                        break;
                    } else {
                        out.write(tree.key); //escreve o caractere no arquivo
                        tree = root; //move a leitura do nó para o próxima posição no arquivo
                    }
                }
                contador++; //incrementa contador
            }
            
             JOptionPane.showMessageDialog(null,"Decodificação concluida com Sucesso");

            
        }catch (IOException e) {}

    }
}
