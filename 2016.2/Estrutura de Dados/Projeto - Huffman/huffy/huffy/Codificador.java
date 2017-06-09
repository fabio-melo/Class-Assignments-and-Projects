package huffy;
import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *  Classe Codificador - cria a hashtable, popula a arvore, codifica o texto
 *  e salva em um arquivo.
 * @author Fabs
 */

public class Codificador {
    private static Hashtable<Character, String> characterTable; //cria a hashtable de caracteres
    private static String codedText = ""; //caracteres codificados
    private static byte[] byteArray; //nosso array de bits que será montado com os valores de huffman
    public static Node treeRoot; //raiz da arvore

    public static void codifica(String meuTexto) {  //codificação
        Hashtable minhaHashTable = criarHashtable(meuTexto);
        PriorityQueue<Node> filaPrioridade = criarFila(minhaHashTable);
        treeRoot = criarTree(filaPrioridade); //raiz da arvore
        characterTable = new Hashtable<Character, String>();
        criarCharacterKey(treeRoot, ""); //adiciona as chaves dos caracteres na arvore
        codedText = ""; //char inicial
        coder(meuTexto); //incrementa codedText
        JOptionPane.showMessageDialog(null,"<html><body><p style='width: 400px;'>"+"Tabela de Caracteres:    "+characterTable+"</p></body></html>");
        byteArray = criarArrayBytes(codedText);
        createFile(byteArray);
    }
    
    /* Método que cria e popula a Hashtable com Caracteres e a Frequencia*/
    public static Hashtable<Character, Integer> criarHashtable(String text) {
        Hashtable<Character, Integer> minhaHashtable = new Hashtable<Character, Integer>(); //criando a hashtable

        for (char c : text.toCharArray()) {
            if(minhaHashtable.get(c) != null) {
                Integer temp = minhaHashtable.get(c);
                minhaHashtable.put(c, temp + 1 );
            }
            else {
                minhaHashtable.put(c, new Integer(1));
            }
        }
        return minhaHashtable;
    }


    /* monta nossa fila de prioridade, para utilizar na criacao da arvore binaria */
    public static PriorityQueue<Node> criarFila(Hashtable<Character, Integer> minhaHashtable) {
        PriorityQueue<Node> filaCodificacao = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1,Node o2) {
                return o1.value.compareTo(o2.value);
            }
        });
        Set<Character> keys = minhaHashtable.keySet();
        Iterator<Character> itr = keys.iterator();
        while(itr.hasNext()) {
            Character c = itr.next();
            Node temp = new Node(c, minhaHashtable.get(c));
            filaCodificacao.add(temp);
        }
        return filaCodificacao;
    }
    
    /* Printa nossa PriorityQueue */
    public static void printQueue(PriorityQueue priorityQueue) {
        while(!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }
    }

    /* Cria Arvore de Huffman a partir da PriorityQueue */
    
    public static Node criarTree(PriorityQueue<Node> priorityQueue) {
      Character c = null;
        while(priorityQueue.size() > 1) {
            Node temp1 = priorityQueue.poll();
            Node temp2 = priorityQueue.poll();
            int combinedValue = temp1.getValue() + temp2.getValue();
            Node combinedNode = new Node(c, combinedValue);
            combinedNode.left = temp1;
            combinedNode.right = temp2;
            priorityQueue.add(combinedNode);
        }
        return priorityQueue.poll();
  }
    /* Travessia da Arvore de Human para Criar as keys dos caracteres */
    public static void criarCharacterKey(Node node, String bits) {
        if(node.left != null) {
            criarCharacterKey(node.left, bits + "0");
        }
        if(node.right != null) {
            criarCharacterKey(node.right, bits + "1");
        }
        if(node.key != null) {
            characterTable.put(node.key, bits);
        }

    }

    public static void coder(String meuTexto) {
        for(char c: meuTexto.toCharArray()) {
            codedText += characterTable.get(c);
        }
    }

    /* Monta o array de Bytes que será salvo no arquivo */
    
    public static byte[] criarArrayBytes(String codedText) {
        int byteArrayLength = codedText.length() / 8;
        if(codedText.length() % 8 != 0) {
            byteArrayLength++;
        }
        byte[] byteArray = new byte[byteArrayLength];
        int x = 0;
        byte numberByte;
        try {
            for (int i = 0; i < byteArrayLength; i++) {
                int end = (x + 8) > (codedText.length()) ? (codedText.length()) : x + 8;
                String temp = codedText.substring(x, end);
                int len = temp.length();
                if (len == 8) {
                    numberByte = (byte) Integer.parseInt(temp, 2); //so mode 2
                    byteArray[i] = numberByte;//Byte.parseByte(temp, 2);
                    x += 8;
                } else {
                    int zeros = 8 - len;
                    String zeroString = "";
                    for (int j = 0; j < zeros; j++) {
                        zeroString += "0";
                    }
                    temp += zeroString;
                    numberByte = (byte) Integer.parseInt(temp, 2); //so mode 2
                    byteArray[i] = numberByte;// Byte.parseByte(temp, 2);
                }
            }
        } catch(Exception e) {

        }
        return byteArray;
    }

    /* Cria o Arquivo e popula com o array de bytes */
    
    public static void createFile (byte[] byteArray) {
        try {
            JFileChooser salvar = new JFileChooser();
            salvar.setCurrentDirectory(new java.io.File("."));
            salvar.setDialogTitle("Digite o nome do arquivo codificado a ser salvo");
            salvar.setFileSelectionMode(JFileChooser.FILES_ONLY);
            salvar.setAcceptAllFileFilterUsed(false);
            salvar.showSaveDialog(null);
            
            File file = new File(salvar.getSelectedFile().getAbsolutePath());  //arquivo que vamos salvar

            if (!file.exists()) {
                file.createNewFile(); //se o arquivo nao existe, cria novo
            }
            FileOutputStream fos = new FileOutputStream(salvar.getSelectedFile().getAbsolutePath());
            fos.write(byteArray, 0, byteArray.length);  //escreve o array de bytes
            fos.close();


        JOptionPane.showMessageDialog(null,"Codificação concluida com Sucesso");

        } catch (IOException e) {}


    }
}