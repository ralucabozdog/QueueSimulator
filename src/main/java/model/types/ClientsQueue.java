package model.types;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

//clasa pentru definirea unei cozi
//implementeaza Runnable pentru ca fiecare coada va reprezenta, de fapt, un thread diferit
public class ClientsQueue implements Runnable{
    private BlockingQueue<Client> clients;          //o coada este caracterizata de clientii care asteapta
    private AtomicInteger waitingPeriod;            //si de timpul de asteptare

    //constructorul clasei
    public ClientsQueue(){
        this.clients = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
    }

    //metode getter pentru accesarea datelor specifice cozii
    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    //metoda pentru adaugarea unui client in aceasta coada
    public void addClient(Client c){
        this.clients.add(c);                                        //se adauga clientul transmis ca parametru la lista de clienti a cozii curente
        c.setWaitingTime(this.getWaitingPeriod().get());            //timpul de asteptare al clientului este egal cu timpul de asteptare al cozii la care tocmai a fost adaugat
        this.waitingPeriod.getAndAdd(c.getServiceTime().get());     //perioada de asteptare a cozii creste cu timpul de servire a clientului proaspat adaugat
    }

    //metoda clasei Runnable ce descrie comportarea in timp a unei cozi
    //va fi executata independent de fiecare coada, adica de fiecare thread definit
    public void run(){
        while(true){
            if(this.clients.isEmpty() == false) {                   //daca inca mai exista clienti in coada
                Client c = this.clients.peek();                     //retin clientul care est primul in coada (ceilalti sunt irelevanti pentru coada in acest moment
                    if(c.getServiceTime().get() > 0){               //daca inca nu s-a scurs intervalul de tip in care clientul trebuie sa fie servit
                        c.getServiceTime().getAndDecrement();       //atunci scad timpul clientului cu inca o secunda
                        this.waitingPeriod.getAndDecrement();       //si scad si timpul de asteptare la coada (a mai trecut o secunda => clientul care vine acum in coada are mai putin de asteptat)
                        if(c.getServiceTime().get() == 0)           //daca timpul de servire a ajuns la 0
                            this.clients.remove();                  //atunci elimin clientul din coada
                    }
            }
            try {
                Thread.sleep(1000);                           //opresc executia thread-ului cozii pentru o secunda
            } catch (InterruptedException e) {                      //sleep arunca exceptie => bloc try catch
                return;                                             //daca interceptez o exceptie, ma intorc din executia functiei
            }
        }
    }
}
