package implementation.simulation;

import implementation.strategy.concrete.ConcreteStrategyQueue;
import implementation.strategy.concrete.ConcreteStrategyTime;
import implementation.strategy.SelectionPolicy;
import model.types.Client;
import model.types.ClientsQueue;
import implementation.strategy.Strategy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//clasa de gestionare a activitatii cozilor

public class Scheduler {
    private List<ClientsQueue> clientsQueues;               //lista tuturor cozilor
    private List<Thread> threads;                           //lista tuturor thread-urilor
    private Strategy strategy;                              //strategia de introducere a clientilor in coada

    //constructorul clasei
    public Scheduler(AtomicInteger maxQueues,  SelectionPolicy policy){
        this.clientsQueues = new ArrayList<ClientsQueue>(); //se instantiaza lista de cozi
        this.threads = new ArrayList<Thread>();             //se instantiaza lista de thread-uri
        this.chooseStrategy(policy);                        //se apeleaza metoda de alegere a strategiei in functie de politica de selectie trimisa ca parametru

        for(int i = 0; i < maxQueues.get(); i++){           //stiind numarul de cozi care trebuie sa fie create
            ClientsQueue q = new ClientsQueue();            //creez pe rand cate o coada
            this.clientsQueues.add(q);                      //adaug coada la lista tuturor cozilor
            Thread t = new Thread(q);                       //creez thread-ul care va corespunde cozii create mai sus
            this.threads.add(t);                            //adaug thread-ul la lista tuturor thread-urilor
            t.setName("Queue " + (i + 1));                  //dau nume thread-ului creat
            t.start();                                      //apelez indirect metoda run() a clasei ClientsQueue pentru coada curenta
        }
    }

    //metoda pentru alegerea strategiei de introducere a clientilor in coada
    public void chooseStrategy(SelectionPolicy policy){     //selectia se face in functie de parametrul SelectionPolicy (primit din interfata)
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();         //strategia aleasta este "coada cea mai scurta"
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();          //strategia aleasa este "cel mai scurt timp de asteptare"
        }
    }

    //metoda de asignare a unui client la coada potrivita
    public void assignClient(Client client){
        this.strategy.addClient(this.clientsQueues, client);    //se apeleaza metoda de adaugare a clientului corespunzatoare strategiei alese
    }

    //metoda pentru oprirea tuturor thread-urilor
    public void stopThreads() throws InterruptedException { //arunca o exceptie ce va fi gestionata in clasa apelanta
        for(Thread t : this.threads){
            t.interrupt();              //intrerup executia fiecarui thread
        }
    }

    //metoda pentru scrierea in fisier a datelor despre fiecare coada
    public void printQueues(BufferedWriter writer) throws IOException {
        for(int i = 0; i < this.clientsQueues.size(); i++){             //pentru fiecare coada
            String threadName = this.threads.get(i).getName();          //salvez numele thread-ului corepunzator

            if(this.clientsQueues.get(i).getClients().isEmpty()){       //daca aceasta coada este goala
                writer.write(threadName + ": closed\n");            //scriu in fisier acest lucru (ex: "Queue 5: closed")
            }
            else{                                                       //daca nu este goala
                writer.write(threadName + ": ");                    //scriu numele thread-ului corespunzator (numele cozii)

                for(Client c : this.clientsQueues.get(i).getClients()){ //pentru fiecare client din coada
                    c.print(writer);                                    //apelez metoda de scriere in fisier a datelor despre acest client
                }
                writer.write("\n");                                 //urmatoarea coada va fi scrisa pe rand nou
            }
        }
        writer.write("\n");                                         //rand nou la finalul scrieirii tuturor cozilor
    }

    //metoda pentru returnare numarului de cilenti ce sunt serviti la momentul de timp curent
    public int getNumberOfClientsBeingServed(){
        AtomicInteger totalNumber = new AtomicInteger();        //initializez numarul de clienti

        for(ClientsQueue q : this.clientsQueues)                //pentry fiecare coada
            if(!q.getClients().isEmpty())                       //daca nu este goala
                totalNumber.getAndAdd(q.getClients().size());   //adaug numarul de clienti ce asteapta acum in coada la numarul total

        return totalNumber.get();                               //returnez numarul total
    }

    //metoda getter pentru accesarea cozilor de clienti
    public List<ClientsQueue> getQueues(){
        return clientsQueues;
    }
}
