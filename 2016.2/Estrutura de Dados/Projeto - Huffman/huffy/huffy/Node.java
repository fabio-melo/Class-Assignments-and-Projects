package huffy;

/**
 * Classe que monta os nós da arvore.
 * 
 * @author Fabs
 */


public class Node {
    /* variaveis package-protected para simplicidade */
    
    Character key = null;
    Integer value = null;
    Node left = null;
    Node right = null;

    
    // construtor: recebe um código de caractere e um valor //
    public Node(Character key, Integer value) {
        this.key = key;
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    public String toString(){
        return this.key + ":" + this.value;
    }
}