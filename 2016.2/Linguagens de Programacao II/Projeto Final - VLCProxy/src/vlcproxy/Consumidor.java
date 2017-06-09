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
public class Consumidor extends Thread implements Sincronizador{
    DatagramSocket cliente;
    DatagramPacket pacote;
    InetAddress addr;
    int porta;
    boolean prioridade;
    static boolean temPrioridade = false;
    static Semaphore exclusao = new Semaphore(1);
    static Semaphore suicidio = new Semaphore(1);
    boolean suicida;
    
    public Consumidor(int porta, InetAddress addr, boolean prioridade) throws SocketException{
        cliente = new DatagramSocket();
        this.addr = addr;
        this.porta = porta;
        this.prioridade = prioridade;
        suicida = false;
        Buffer.consumidores++;
    }
    
    @Override
    public void run(){
        try {
            byte data[] = new byte[BUFFERSIZE];
            pacote = new DatagramPacket(data, BUFFERSIZE, addr, porta);
            while(temPrioridade && prioridade){System.err.println("dormi"); comPrioridade.acquire();}
            if(prioridade && !temPrioridade){System.err.println("adicionei"); temPrioridade = true;}
            while(true){
                try{
                    while(temPrioridade && !prioridade){/*System.err.println("Alguem com prioridade chegou");*/ semPrioridade.acquire(); }//P(vazio) await
                    cheio.acquire();//p(cheio)
                    exclusao.acquire();//<
                    pacote.setData(buffer.Ler()); //P(mutex) buffer = ler() v(mutex)
                    exclusao.release();//>
                    cliente.send(pacote);//envia
                    vazio.release();//v(vazio)
                    //System.err.println("Enviei:" + pacote.toString());
                    if(suicida) break;//se mata
                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                exclusao.acquire();//<
                Buffer.consumidores--;
                if(prioridade || comPrioridade.getQueueLength() > 0){temPrioridade = false; comPrioridade.release();}//-1 thread prioritaria
                else if(prioridade && semPrioridade.getQueueLength() > 0) semPrioridade.release(semPrioridade.getQueueLength());
                semPrioridade.release(semPrioridade.getQueueLength());//v(sem prioridae)
                exclusao.release();//>
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Suicida(){
        suicida = true;
    }
    
}
