import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class Lab_12_File_Away {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        Scanner inFile;
        String line = "";
        Path target = new File(System.getProperty("user.dir")).toPath();
        target = target.resolve("src");

        // set the chooser to the project src directory
        chooser.setCurrentDirectory(target.toFile());

        try  // Code that might trigger the exception goes here
        {

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                target = chooser.getSelectedFile().toPath();  // this is a File object not a String filename

                inFile = new Scanner(target);

                int numberOfLines = 0;
                int numberOfCharacters = 0;
                while(inFile.hasNextLine())
                {
                    numberOfLines = numberOfLines + 1;
                    line = inFile.nextLine();
                    numberOfCharacters += line.length();
                }

                File selectedFile = chooser.getSelectedFile();
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);
                Path file = selectedFile.toPath();
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                ArrayList<String> lines = new ArrayList<>();
                int arrayLength = 0;
                int numberOfWords = 0;

                String rec = "";

                // Finally we can read the file LOL!
                while(reader.ready())
                {
                    rec = reader.readLine();
                    lines.add(rec);  // read all the lines into memory in an array list
                    // echo to screen
                }
                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("\n\nData file read!");

                String[] fields;
                for(String l:lines)
                {
                    fields = l.split(" "); // Split the record into the fields
                    arrayLength= fields.length;
                    numberOfWords = numberOfWords + arrayLength;
                }

                inFile.close();

                System.out.println("The name of the file is " + target);
                System.out.println("The number of lines the file has is " + numberOfLines);
                System.out.println("The number of words the file has is " + numberOfWords);
                System.out.println("The number of characters the file has is " + numberOfCharacters);
            }
            else   // User did not pick a file, closed the chooser
            {
                System.out.println("Sorry, you must select a file! Terminating!");
                System.exit(0);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File Not Found Error");
            e.printStackTrace();
        }
        catch (IOException e) // code to handle this exception
        {
            System.out.println("IOException Error");
            e.printStackTrace();
        }
    }
}
