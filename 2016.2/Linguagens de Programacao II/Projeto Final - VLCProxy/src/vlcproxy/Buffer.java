/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vlcproxy;
import java.util.concurrent.Semaphore;
/**
 *
 * @author Alef Berg, Fábio Melo, Gustavo Perucci
 */
public class Buffer {
    private byte[][] buffer;
    private int n;
    private static int rear;
    private static int front;
    public static int leram;
    public static int consumidores;
    
    public Buffer(int tamanho, int n){
        buffer = new byte[n][tamanho];
        rear = 0;
        front = 0;
        this.n = n;;
        consumidores = 0;
        leram = 0;
    }
    
    public void Insere(byte[] data) throws InterruptedException{
        buffer[rear] = data;
        System.err.println("Escrevendo:"+buffer[rear].toString()+"na posicao:"+rear);
        rear = (rear + 1) % n;
        if(rear == front){Sincronizador.cheio.release(Sincronizador.cheio.getQueueLength());}//v(cheio)
    }
    
    public byte[] Ler() throws InterruptedException{
        byte[] temp = buffer[front];
        System.err.println("lendo:"+temp.toString()+"na posicao:"+front);
        leram++;
        if(leram == consumidores){ 
            leram = 0;
            front = (front + 1) % n;
            Sincronizador.vazio.release();//V(vazio)
        }
        if(rear == front){Sincronizador.vazio.release(); /*vazio = true;*/}
        return temp;
    }
}
