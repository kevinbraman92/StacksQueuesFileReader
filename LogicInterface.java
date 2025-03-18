package com.KevinBraman.stacksAndQueues;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class LogicInterface {
    private Scanner userInput = new Scanner(System.in);
    private Stack<Character> inputStack = new Stack<>();
    private Queue<Character> inputQueue = new LinkedList<>();
    private Queue<Character> secondaryQueue = new LinkedList<>();
    private Boolean exceptionState = false;

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
                this.writeToDataStructures(command, new File(this.userInput.nextLine()));
        } else {
                System.out.print("\nPlease enter the name of the first text file to be read, including the extension: ");
                this.writeToDataStructures(command, new File(this.userInput.nextLine()));
                System.out.print("\nPlease enter the name of the second text file to be read, including the extension: ");
                this.writeToDataStructures(command, new File(this.userInput.nextLine()));
        }
    }

    private void writeToDataStructures(String command, File textFile) {
        try {
            FileReader fileReader = new FileReader(textFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            if (command.equalsIgnoreCase("reverse")) {
                while ((line = bufferedReader.readLine()) != null) {
                    for (char letter: line.stripTrailing().toCharArray()) {
                        this.inputStack.push(letter);
                    }
                    this.inputStack.push('\n');
                }
            } else if (command.equalsIgnoreCase("convert")) {
                while ((line = bufferedReader.readLine()) != null) {
                    for (char letter : line.toUpperCase().toCharArray()) {
                        this.inputQueue.add(letter);
                    }
                    this.inputQueue.add('\n');
                }
            } else if (command.equalsIgnoreCase("compare")) {
                if (this.inputQueue.isEmpty()) {
                    while ((line = bufferedReader.readLine()) != null) { 
                        for (char letter : line.toCharArray()) {
                            this.inputQueue.add(letter);
                        }
                    }
                } else { 
                    while ((line = bufferedReader.readLine()) != null) {                                   
                        for (char letter : line.toCharArray()) {
                            this.secondaryQueue.add(letter);
                        }
                    }
                }
            }
            fileReader.close();
        } catch (IOException e) {
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
                    this.inputStack.pop();
                    while (!this.inputStack.isEmpty()) {
                        newWriter.write(this.inputStack.pop());
                    }
                } else if (command.equalsIgnoreCase("convert")) {
                    while (!this.inputQueue.isEmpty()) {
                        newWriter.write(this.inputQueue.remove());
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
        if (this.inputQueue.equals(this.secondaryQueue)) {
            System.out.println("The two text files are equal.");
        } else {
            System.out.println("The two text files are not equal.");
        }
        this.clearQueues();
    }

    private void clearQueues() {
        this.inputQueue.clear(); this.secondaryQueue.clear();
    }
}
