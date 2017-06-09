/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *  Arquivo: Método de Abertura e Leitura de Arquivos
 *  Para Facilitar a conversão, é feita a leitura do texto e convertida em uma 
 *  string atrávés do método toString() padrão;
 * @author Fabs
 */

public class Arquivo {
  
    public static String stringficar(String meuArquivo) { // é passado ao método o nome do arquivo a ser lido
        StringBuilder meuTexto = new StringBuilder();
        try {
            /* Leitura do Arquivo Fonte e montagem da String, salva no objeto StringBuilder */
            FileInputStream fstream = new FileInputStream(meuArquivo); 
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            /* Enquanto existir texto, o adiciona na string */
            String auxiliar;
            while ((auxiliar = br.readLine()) != null) {meuTexto.append(auxiliar);}
            br.close(); //fecha o buffer
        }
        catch (Exception e) { 
            JOptionPane.showMessageDialog(null, e.getMessage());
           }
        
        meuTexto.append('µ'); //adiciona um caractere para indicar o EOF
        return meuTexto.toString();
    }
}
