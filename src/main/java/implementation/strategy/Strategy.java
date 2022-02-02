package implementation.strategy;

import model.types.Client;
import model.types.ClientsQueue;

import java.util.List;

//interfata implementata de clasele ce definesc strategiile posibile

public interface Strategy {
    void addClient(List<ClientsQueue> clientsQueues, Client client);    //contine doar metoda abstracta de adaugare a unui client,
                                                                        // careia i se va oferi implementare in clasa de strategie concreta
}
