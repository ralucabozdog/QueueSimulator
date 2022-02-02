package gui.input.check;

//clasa de exceptie in cazul in care utilizatorul nu respecta formatul datelor de intrare => se va folosi la afisarea unei casete de dialog care sa semnaleze eroarea

public class InvalidInputException extends Exception{
    private String message;

    InvalidInputException(String message){
        this.message = message;
    }   //mesaj corespunzator in functie de tipul erorii

    //metoda getter de accesare a mesajului caracteristic exceptiei
    public String getMessage(){
        return this.message;
    }
}
