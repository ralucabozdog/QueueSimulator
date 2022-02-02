package model.types;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

//clasa pentru definirea unui client
//implementeaza interfata Comparable pentru a putea sorta clientii crescator in fuctie de timpul lor de sosire la coada

public class Client implements Comparable<Client>{
    private int id;                                 //fiecare client are un ID specific
    private AtomicInteger arrivalTime;              //un moment la care este pregatit sa intre in coada
    private AtomicInteger serviceTime;              //o perioada de timp pentru care este nevoit sa stea in fruntea cozii
    private AtomicInteger waitingTime;              //un interval de asteptare pana la momentul in care va ajunge sa fie primul in coada

    //contstructorul clasei
    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = new AtomicInteger(arrivalTime);
        this.serviceTime = new AtomicInteger(serviceTime);
        this.waitingTime = new AtomicInteger();             //timpul de asteptare depinde de ceilalti clienti => va fi stabilit doar la introucerea intr-o coada
    }

    //metode getter pentru datele specifice clientului
    public int getId() {
        return id;
    }

    public AtomicInteger getArrivalTime() {
        return arrivalTime;
    }

    public AtomicInteger getServiceTime() {
        return serviceTime;
    }

    public AtomicInteger getWaitingTime() {
        return waitingTime;
    }

    //metoda setter pentru timpul de asteptare (se va folosi la introducerea unui client intr-o coada)
    public void setWaitingTime(int waitingTime){
        this.waitingTime.set(waitingTime);
    }

    //metoda pentru printarea in fisier a datelor specifice clientului, sub forma ceruta => (id, arrival, service);
    public void print(BufferedWriter buffer) throws IOException {
        buffer.write("(" + this.getId() + ", " + this.getArrivalTime() + ", " + this.getServiceTime() + "); ");
    }

    //metoda compareTo din interfata Comparable
    @Override
    public int compareTo(Client o) {
        if (this.arrivalTime.get() < o.arrivalTime.get())       //returneaza o valoare negativa daca timpul de sosire al clientului curent
            return -1;                                          //este mai mic decat cel al clientului transmis ca parametru
        else
            if(this.arrivalTime.get() > o.arrivalTime.get())    //returneaza o valoare pozitiva daca timpul de sosire al clientului curent
                return 1;                                       //este mai mare decat cel al clientului transmis ca parametru
            return 0;                                           //returneaza 0 daca timpul de sosire al clientului curent este egal cu cel al clientului parametru
    }
}
