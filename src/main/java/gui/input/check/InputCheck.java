package gui.input.check;

import gui.input.display.InputData;

//clasa pentru validarea datelor de input transmise de utilizator

public class InputCheck {
    //metoda statica
    //arunca o exceptie in caz ca datele de intrare nu sunt valide
    public static void check(InputData inputData) throws InvalidInputException {
        int n = 0;
        int q = 0;
        int interval = 0;
        int minArr = 0;
        int maxArr = 0;
        int minService = 0;
        int maxService = 0;

        try {
            n = Integer.parseInt(inputData.getClients().getText());
            q = Integer.parseInt(inputData.getQueues().getText());
            interval = Integer.parseInt(inputData.getInterval().getText());
            minArr = Integer.parseInt(inputData.getMinArrival().getText());
            maxArr = Integer.parseInt(inputData.getMaxArrival().getText());
            minService = Integer.parseInt(inputData.getMinService().getText());
            maxService = Integer.parseInt(inputData.getMaxService().getText());
        }catch (NumberFormatException e){
            throw new InvalidInputException("Data can only be integers");           //daca se transmite, de exemplu, un field nu reprezinta un numar intreg, se va genera o exceptie
        };
        if(n < 0 || q < 0 || interval < 0 || minArr < 0 || maxArr < 0 || minService < 0 || maxService < 0)
            throw new InvalidInputException("Only positive integers are allowed");  //din punct de vedere logic, datele pot fi doar numere intregi pozitive
        if(minArr > maxArr)
            throw new InvalidInputException("Minimum arrival time <= maximum arrival time");
        if(minService > maxService)
            throw new InvalidInputException("Minimum service time >= to maximum service time");  //o valoare minima nu poate fi mai mare decat o valoare maxima
        if(inputData.getResultFile().getText().isEmpty())
            throw  new InvalidInputException("Result file name cannot be left void");            //trebuie sa se dea un nume de fisier de iesire
    }
}
