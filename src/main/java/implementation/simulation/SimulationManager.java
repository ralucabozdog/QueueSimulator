package implementation.simulation;

import gui.simulation.SimulationFrame;
import model.generation.GenerateClient;
import gui.input.display.InputData;
import model.types.Client;
import implementation.strategy.SelectionPolicy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

//clasa pentru gestionarea efectiva a simularii
public class SimulationManager implements Runnable{
    private InputData inputData;
    private Scheduler scheduler;
    private AtomicInteger numberOfClients;
    private CopyOnWriteArrayList<Client> clientList;
    private FileWriter fileWriter;
    private BufferedWriter buffer;
    private SimulationFrame simulationFrame;
    private AtomicInteger currentTime;

    //constructorul clasei
    public SimulationManager(InputData inputData){
        this.inputData = inputData;                                                                                     //instantiez inputData cu Obiectul reprezentand interfata grafica pentru introducerea datelor
        this.numberOfClients = new AtomicInteger(Integer.parseInt(inputData.getClients().getText()));                   //numarul de clienti ce urmeaza a fi serviti este citit din datele de intrare
        this.clientList = new CopyOnWriteArrayList<>();                                                                 //instantierea listei de clienti ce urmeaza a fi serviti
        this.currentTime = new AtomicInteger();                                                                         //momentul curent de timp (initial 0)
        try {
            this.fileWriter = new FileWriter(this.inputData.getResultFile().getText(), true);                    //deschid (sau, daca nu exista, creez) fisierul cu numele dat de utilizator in interfata
            this.buffer = new BufferedWriter(fileWriter);                                                               //buffer-ul prin care se va face scrierea
            AtomicInteger queuesCount = new AtomicInteger(Integer.parseInt(this.inputData.getQueues().getText()));      //salvez numarul de cozi din interfata grafica
            this.scheduler = new Scheduler(queuesCount, this.chooseSelectionPolicy());                                  //instantiez planificatorul
            this.generateClients();                                                                                     //generez aleatoriu clientii ce vor fi serviti
            this.simulationFrame = new SimulationFrame(this);                                            //instantiez fereastra de simulare
            this.simulationFrame.setVisible(true);                                                                      //si o fac vizibila
        } catch (IOException e) { }                                                                                     //se poate genera o exceptie din cauza buffer-ului (si a fisierului ales ca fisier de iesire)
    }

    //metoda pentru generarea aleatoare a tuturor clientilor
    public void generateClients(){
        for(int i = 1; i <= this.numberOfClients.get(); i++) {              //stiind numarul de clienti ce trebuie generati
            clientList.add(GenerateClient.generate(this.inputData, i));     //apelez metoda statica pentru generarea unui client din clasa GenerateClient
        }
        Collections.sort(clientList);                                       //sortez clientii crescator
    }

    //metoda pentru printarea clientilor care inca nu sunt asignati unei cozi
    public void printWaitingClients() throws IOException {
        this.buffer.write("Waiting clients: ");

        for(Client c : this.clientList){
            c.print(this.buffer);
        }
        this.buffer.write("\n");
    }

    //metoda din interfata Runnable
    @Override
    public void run() {
        AtomicInteger totalWaitingTime = new AtomicInteger();                                           //timpul total de asteptare, initial 0
        AtomicInteger totalServiceTime = new AtomicInteger();                                           //timpul total de servire, initial 0
        AtomicInteger peakHour = new AtomicInteger();                                                   //momentul cel mai aglomerat, initial 0
        AtomicInteger maxNumber = new AtomicInteger();                                                  //numarul maxim de clienti ce se afla la rand in acelasi timp
        try{
            try {           //cat timp inca nu s-a terminat timpul de simulare si mai exista clienti care asteapta sa fie asignati unei cozi sau asteapta sa fie serviti intr-o coada
                while(currentTime.get() < (Integer.parseInt(this.inputData.getInterval().getText()) - 1) && !(this.clientList.size() == 0 && this.scheduler.getNumberOfClientsBeingServed() == 0)){
                    this.buffer.write("Time: " + currentTime.get() + "\n");                         //scriu in fisier momentul de timp curent
                    this.assignClientsUpdateParameters(totalWaitingTime, totalServiceTime, peakHour, maxNumber);      //actualizez datele de simulare si asignez la cozi clientii ce "au terminat cumparaturile"
                    this.simulationFrame.getSimulationPanel().repaintComponent(this.clientList, new CopyOnWriteArrayList<>(this.scheduler.getQueues()), currentTime);   //actualizez interfata de simulare
                    this.printWaitingClients();                                                         //scriu in fisier lista clientilor care inca nu sunt asignati unei cozi
                    this.scheduler.printQueues(this.buffer);                                            //scriu in fisier lista cozilor si a clientilor corespunzatori
                    currentTime.getAndIncrement();                                                      //cresc timpul cu o unitate
                    Thread.sleep(1000);                                                           //opresc executia pentru o secunda
                }
                if(currentTime.get() < Integer.parseInt(this.inputData.getInterval().getText())){       //daca simularea s-a terminat pentru ca nu mai exista deloc clienti (in nicio coada si nici in afara cozilor)
                    this.simulationFrame.getSimulationPanel().repaintComponent(this.clientList, new CopyOnWriteArrayList<>(this.scheduler.getQueues()), currentTime);   //mai merg inca un pas de simulare, pentru a vedea cozile goale
                    this.buffer.write("Time: " + currentTime.get() + "\n");                         //scriu in fisier momentul curent de timp
                    this.printWaitingClients();                                                         //scriu in fisier lista clientilor care inca nu sunt asignati unei cozi (lista va fi vida)
                    this.scheduler.printQueues(this.buffer);                                            //scriu in fisier lista cozilor si a clientilor corespunzatori (fiecare coada va fi goala)
                }
                this.scheduler.stopThreads();                                                           //opresc toate thread-urile din planificator
                this.displayResults(totalWaitingTime, totalServiceTime, peakHour);                      //scriu in fisier datele de final de simulare
                this.buffer.close();                                                                    //inchid buffer-ul folosit la scrierea in fisier
                this.fileWriter.close();                                                                //inchid fisierul de iesire
                Thread.currentThread().interrupt();                                                     //intrerup thread-ul curent
            } catch (InterruptedException e) {  //Thread.sleep poate genera exceptie => se iese din executie in acest caz
                return;
            }
        } catch (IOException e) { }             //scrierea in buffer poate genera exceptie (dar verificarile ca acest lucru sa nu se intample sunt deja facute)
    }

    //metoda pentru afisarea rezultatelor la final de simulare
    public void displayResults(AtomicInteger totalWaitingTime, AtomicInteger totalServiceTime, AtomicInteger peakHour) throws IOException {
        float averageWaitingTime = (float)totalWaitingTime.get() / this.getNumberOfClients().get();     //calculez timpul mediu de asteptare la coada
        float averageServiceTime = (float)totalServiceTime.get() / this.getNumberOfClients().get();     //calculez timpul mediu de servire a unui client

        this.buffer.write("Average waiting time: " + averageWaitingTime + "\n");                    //scriu in fisier valoarea medie a timpului de asteptare
        this.buffer.write("Average service time: " + averageServiceTime + "\n");                    //scriu in fisier valoarea medie a timpului de servire
        this.buffer.write("Peak hour: " + peakHour.get() + "\n");                                   //scriu in fisier momentul la care "magazinul a fost cel mai aglomerat"
    }

    //metoda pentru alegerea politicii de selectie
    public SelectionPolicy chooseSelectionPolicy() throws IOException{
        SelectionPolicy selectionPolicy;
        int policy = this.inputData.getSelectionPolicy().getSelectedIndex();        //se extrage abordarea dorita de utilizator din interfata
        String message = new String();
        if (policy == 0){
            selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;                       //se stabileste politica de selectie "coada cea mai scurta"
            message = "SHORTEST QUEUE STRATEGY\n";                                  //se genereaza un mesaj corespunzator
        }
        else{
            selectionPolicy = SelectionPolicy.SHORTEST_TIME;                        //se stabileste politica de selectie "coada cu cel mai scurt timp de asteptare"
            message = "SHORTEST TIME STRATEGY\n";                                   //mesaj specific
        }
        this.buffer.write(message);                                                 //se scrie la inceputul fisierului care este abordarea ce urmeaza a fi simulata

        return selectionPolicy;                                                     //se returneaza politica de selectie
    }

    //metoda pentru actualizarea parametrilor ce vor fi scrisi la finalul fisierului text al simularii, si asignarea urmatorului client in coada corespunzatoare
    public void assignClientsUpdateParameters(AtomicInteger totalWaitingTime, AtomicInteger totalServiceTime, AtomicInteger peakHour, AtomicInteger maxNumber){
        for(Client c : this.clientList){                                        //dintre toti clientii care inca nu au fost asignati
            if(c.getArrivalTime().get() == currentTime.get()){                  //daca timpul de sosire este egal cu timpul curent
                this.scheduler.assignClient(c);                                 //assignez clientul la o coada
                this.clientList.remove(c);                                      //il sterg din lista clientilor care asteapta
                totalWaitingTime.getAndAdd(c.getWaitingTime().get());           //adaug timpul sau de asteptare la timpul total de asteptare
                totalServiceTime.getAndAdd(c.getServiceTime().get());           //adaug timpul sau de servire la timpul total
            }
        }
        if(this.scheduler.getNumberOfClientsBeingServed() > maxNumber.get()){   //daca numarul de clienti care sunt serviti in acest moment este mai mare decat numarul maxim
            peakHour.set(currentTime.get());                                    //actualizez ora de varf
            maxNumber.set(this.scheduler.getNumberOfClientsBeingServed());      //actualizez maximul
        }
    }

    //metode getter pentru accesarea variabilelor instanta ale clasei
    public AtomicInteger getNumberOfClients() {
        return numberOfClients;
    }

    public Scheduler getScheduler(){
        return scheduler;
    }

    public CopyOnWriteArrayList getClientList(){
        return this.clientList;
    }

    public AtomicInteger getCurrentTime(){
        return this.currentTime;
    }
}
