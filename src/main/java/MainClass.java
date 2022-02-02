import gui.input.display.InputData;
import implementation.controller.Controller;

//clasa principala, cea a carei metoda main se apeleaza pentru a porni programul
public class MainClass {
    public static void main (String args[]){
        InputData inputData = new InputData();              //fereastra datelor de intrare
        Controller ctrl = new Controller(inputData);        //controller-ul specific ferestrei
        inputData.setVisible(true);                         //fereastra de date de intrare devine vizibila
    }
}
