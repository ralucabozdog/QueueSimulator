package implementation.controller;

import gui.input.check.InputCheck;
import gui.input.check.InvalidInputException;
import gui.input.display.ErrorPopUp;
import gui.input.display.InputData;
import implementation.simulation.SimulationManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//clasa pentru definirea ActionListener-ului pentru interfata grafica de introduce a datelor de intrare
public class Controller {
    private InputData inputData;

    //constructorul clasei
    public Controller(InputData inputData) {
        this.inputData = inputData;                         //se instantiaza inputData
        this.inputData.addOkListener(new OkListener());     //se adauga ActionListener-ul pentru butonul OK al interfetei
    }

    //clasa interna ce implementeaza interfata ActionListener
    //pentru a stabili ce se va executa la actionarea butonului OK
    class OkListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                InputCheck.check(inputData);                                                //se verifica input-ul utilizatorului
                SimulationManager simulationManager = new SimulationManager(inputData);     //se defineste managerul de simulare
                Thread t = new Thread(simulationManager);                                   //thread-ul corespunzator lui simulationManager, care pune in miscare simularea
                t.start();                                                                  //este apelata indirect metoda run a clasei SimulationManager
            }catch(InvalidInputException exception){                                        //InputCheck.check poate genera o exceptie
                new ErrorPopUp(exception.getMessage()).setVisible(true);                    //caz in care se va afisa o caseta de dialog cu un mesaj ce va semnala eroarea
            }
        }
    }
}
