package fr.paris.hashcode2016;

import fr.paris.hashcode2016.model.Delivery;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Axel on 25/04/2016.
 */
public class Main {

    private static final String FILE = "redundancy";

    public static final File INPUT_FILE = new File(FILE + ".in");
    public static final File OUTPUT_FILE = new File(FILE + ".out");

    public static void main(String[] args) throws IOException {
        Delivery delivery = new Delivery();
        delivery.init();
        delivery.start();

        int countLine = delivery.getOutput().length() - delivery.getOutput().replace("\n", "").length();
        String output = countLine + "\n" + delivery.getOutput();
        FileUtils.writeStringToFile(OUTPUT_FILE, output);
    }
}
