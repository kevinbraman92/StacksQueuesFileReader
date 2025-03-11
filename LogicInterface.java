import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class LogicInterface {
    private Scanner userInput = new Scanner(System.in);
    private Stack<String> inputStack = new Stack<>();
    private Queue<String> inputQueue = new LinkedList<>();
    private Queue<String> secondaryQueue = new LinkedList<>();
    private Boolean exceptionState = false;

    public LogicInterface() {}

    public void commandHandler(String command) {
        this.readFile(command);
        if (this.exceptionState) {
            this.exceptionState = false;
            return;
        }
        if (command.equalsIgnoreCase("reverse") || command.equalsIgnoreCase("convert")) {
            this.writeFile(command);
        } else {
            this.compareQueues();
        }
    }

    private void readFile(String command) {
        if (command.equalsIgnoreCase("reverse") ||command.equalsIgnoreCase("convert")) {
                System.out.print("\nPlease enter the name of the text file to be read, including the extension: ");
                File textFile = new File(this.userInput.nextLine());
                this.writeToDataStructures(command, textFile);
        } else {
                System.out.print("\nPlease enter the name of the first text file to be read, including the extension: ");
                File firstFile = new File(this.userInput.nextLine());
                System.out.print("\nPlease enter the name of the second text file to be read, including the extension: ");
                File secondFile = new File(this.userInput.nextLine());
                this.writeToDataStructures(command, firstFile);
                this.writeToDataStructures(command, secondFile);
        }
    }

    private void writeToDataStructures(String command, File textFile) {
        try {
            Scanner fileReader = new Scanner(textFile);
            if (command.equalsIgnoreCase("reverse")) {
                while (fileReader.hasNextLine()) { 
                    this.inputStack.push(fileReader.nextLine());
                }  
            } else if (command.equalsIgnoreCase("convert")) {
                while (fileReader.hasNextLine()) { 
                    this.inputQueue.add(fileReader.nextLine());
                }
            } else if (command.equalsIgnoreCase("compare")) {
                if (this.inputQueue.isEmpty()) {
                    while (fileReader.hasNextLine()) { 
                        this.inputQueue.add(fileReader.nextLine());
                    }
                } else { 
                    while (fileReader.hasNextLine()) { 
                        this.secondaryQueue.add(fileReader.nextLine());
                    }
                }
            } else {
                System.out.println("Unknown command!");
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(textFile.getName() + ".txt not found! Please ensure the text file is in the same directory as the Java files!");
            this.exceptionState = true;
            return;
        }
    }

    private void writeFile(String command) {
        try {
            if (command.equalsIgnoreCase("reverse") ||command.equalsIgnoreCase("convert")) {
                System.out.print("\nPlease enter the name of the output text file, including the extension: ");
                File newFile = new File(this.userInput.nextLine());
                FileWriter newWriter = new FileWriter(newFile);
                if (command.equalsIgnoreCase("reverse")) {
                    while (!this.inputStack.isEmpty()) {
                        newWriter.write(this.inputStack.pop() + "\n");
                    }
                } else if (command.equalsIgnoreCase("convert")) {
                    while (!this.inputQueue.isEmpty()) {
                        newWriter.write(this.inputQueue.remove().toUpperCase() + "\n");
                    }
                }
                newWriter.close();
                System.out.println(newFile.getName() + " has been created.");
            }
        } catch (IOException e) {
            System.out.println("An error has been encountered while attempting to write to file.");
            return;
        } 
    }

    private void compareQueues() {
        if (this.inputQueue.size() != this.secondaryQueue.size()) {
            System.out.println("The two text files are not equal.");
            this.clearQueues();
            return;
        }
        while (!this.inputQueue.isEmpty() && !this.secondaryQueue.isEmpty()) {
            String firstQueueElement = this.inputQueue.remove().trim();
            String secondQueueElement = this.secondaryQueue.remove().trim();
            if (!firstQueueElement.equalsIgnoreCase(secondQueueElement)) {
                System.out.println("The two text files are not equal.");
                break;
            }
        }
        if (this.inputQueue.isEmpty() && this.secondaryQueue.isEmpty()) {System.out.println("The two text files are equal.");}
        this.clearQueues();
    }

    private void clearQueues() {
        this.inputQueue.clear(); this.secondaryQueue.clear();
    }
}
