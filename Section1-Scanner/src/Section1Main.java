import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Section1Main {
    static public void main(String[] args) throws FileNotFoundException {
        int hi = 0;
        for (int i = 0; i < 4; i++) {
            if (true) {
                hi = 2;
            }
        }
        System.out.println(hi);
        Scanner sc = new Scanner(System.in);
        System.out.println("File name?");
        String fileName = sc.next();
        Scanner scannerTwo = new Scanner(new File(fileName));
        while (scannerTwo.hasNextLine()) {
            String line = scannerTwo.nextLine();
            System.out.println(line);
        }
    }

}
