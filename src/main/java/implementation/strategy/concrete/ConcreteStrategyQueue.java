package implementation.strategy.concrete;

import implementation.strategy.Strategy;
import model.types.Client;
import model.types.ClientsQueue;

import java.util.List;

//clasa pentru definirea strategiei "cea mai scurta coada"

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addClient(List<ClientsQueue> clientsQueues, Client client) {

        int minInQueue = Integer.MAX_VALUE;
        int index = 0;

        for(int i = 0; i < clientsQueues.size(); i++){
            if (clientsQueues.get(i).getClients().size() < minInQueue){         //caut coada in care sunt cei mai putini clienti
                minInQueue = clientsQueues.get(i).getClients().size();
                index = i;                                                      //salvez indexul
            }
        }
        clientsQueues.get(index).addClient(client);                             //adaug clientul curent in coada du indexul salvat
    }
}
