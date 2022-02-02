package model.generation;

//interfata pentru generarea unor numere aleatoare intr-un interval dat

public interface RandomGenerate {
    default int generate(int min, int max){                             //contine metoda default de generare aleatoare de numere intregi
        int randomInt = (int)(Math.random() * (max - min + 1) + min);   //ce va fi implementata de clasele de generare
        return randomInt;
    }
}
