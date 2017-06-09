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
public interface Sincronizador {
    final int BUFFERSIZE = 1316;
    final int BUFFERLENGTH = 100;
    Buffer buffer = new Buffer(BUFFERSIZE, BUFFERLENGTH);
    Semaphore cheio = new Semaphore(BUFFERLENGTH);//bastao produtor
    Semaphore vazio = new Semaphore(0);//bastao consumidor
    Semaphore semPrioridade = new Semaphore(0);//cama dos sem prioridade
    Semaphore comPrioridade = new Semaphore(0);
}
