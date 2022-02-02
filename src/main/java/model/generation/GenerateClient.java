package model.generation;

import gui.input.display.InputData;
import model.types.Client;

//clasa pentru generarea unui client cu caracteristici (arrival time si service time) aleatoare

public class GenerateClient {
    //metoda statica pentru generarea unui client
    public static Client generate(InputData inputData, int id){                         //ID-ul clientului se primeste ca parametru
        GenerateArrivalTime generateArrivalTime = new GenerateArrivalTime();
        GenerateServiceTime generateServiceTime = new GenerateServiceTime();

        int minArrival = Integer.parseInt(inputData.getMinArrival().getText());
        int maxArrival = Integer.parseInt(inputData.getMaxArrival().getText());
        int minService = Integer.parseInt(inputData.getMinService().getText());
        int maxService = Integer.parseInt(inputData.getMaxService().getText());

        int randomArrival = generateArrivalTime.generate(minArrival, maxArrival);       //se genereaza un arrival time aleatoriu intre timpul minim si timpul maxim din interfata pentru datele de intrare
        int randomService = generateServiceTime.generate(minService, maxService);       //se genereaza un service time aleatoriu intre minim si maxim din interfata pentru datele de intrare

        return new Client(id, randomArrival, randomService);                            //se returneaza clientul cu particularitatile de mai sus
    }
}
