/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vlcproxy;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Alef Berg, Fábio Melo, Gustavo Perucci
 */
public class Produtor extends Thread implements Sincronizador{
    
    DatagramSocket server;
    DatagramPacket pacote;
    public Produtor(int porta, InetAddress addr) throws SocketException{
        this.server = new DatagramSocket(porta, addr);
    }
    
    @Override
    public void run(){
        byte data[] = new byte[BUFFERSIZE];
        pacote = new DatagramPacket(data,BUFFERSIZE);
        while(true){
            try{
                vazio.acquire();//p(vazio)
                server.receive(pacote);
                System.err.println("Recebi:" + pacote.toString());
                buffer.Insere(pacote.getData());
                cheio.release();//v(cheio)
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Produtor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}