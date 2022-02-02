package implementation.strategy.concrete;

import implementation.strategy.Strategy;
import model.types.Client;
import model.types.ClientsQueue;

import java.util.List;

//clasa pentru definirea strategiei "cel mai scurt timp"

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addClient(List<ClientsQueue> clientsQueues, Client client) {
        int minWaitingPeriod = Integer.MAX_VALUE;
        int index = 0;

        for(int i = 0; i < clientsQueues.size(); i++){
            if (clientsQueues.get(i).getWaitingPeriod().get() < minWaitingPeriod){          //caut coada cu cel mai scurt timp de asteptare
                minWaitingPeriod = clientsQueues.get(i).getWaitingPeriod().get();
                index = i;                                                                  //salvez indexul
            }
        }
        clientsQueues.get(index).addClient(client);                                         //adaug clientul curent in coada cu indexul salvat
    }
}
