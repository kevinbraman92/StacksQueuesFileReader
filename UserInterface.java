package com.KevinBraman.stacksAndQueues;
import java.util.Scanner;

public class UserInterface {
    private Scanner userInput = new Scanner(System.in);
    private LogicInterface logic = new LogicInterface();

    public UserInterface() {}

    public void runQuiz() {
        this.quizBody();
    }

    private void introMessage() {
        System.out.println("\n\nWelcome to the stacks & queues demonstration program!");
        System.out.println("This program can preform three functions wtih a file: reverse, convert, and compare.");
        System.out.println("Enter 'help' to see a list of commands or to learn more about the functions. Enter 'Exit' to quit at any time.");
    }

    private void help() {
        System.out.println("\nThe three fundamental functions of the program are as follows:");
        System.out.println("Reverse:    The program will read from the text file and print the contents in reverse order to a new text file.");
        System.out.println("Convert:    The program will read from the text file and print the contents in uppercase to a new text file.");
        System.out.println("Compare:    The program will read from two files and determine if the contents are equal.");
        System.out.println("\nAdditional commands:");
        System.out.println("Help:       Will output this commands list.");
        System.out.println("Exit:       Will exit the program.");
        System.out.println("Welcome:    Will reprint the introduction message.");
    }

    private void quizBody() {
        this.introMessage();
        while (true) {
            System.out.print("\nPlease enter a command: ");
            String command = userInput.nextLine();
            switch (command.equalsIgnoreCase("reverse") ? "reverse":
                    command.equalsIgnoreCase("convert") ? "convert": 
                    command.equalsIgnoreCase("compare") ? "compare":
                    command.equalsIgnoreCase("help") ? "help":
                    command.equalsIgnoreCase("commands") ? "commands":
                    command.equalsIgnoreCase("exit") ? "exit":
                    command.equalsIgnoreCase("quit") ? "quit":
                    command.equalsIgnoreCase("welcome") ? "welcome":
                    command.equalsIgnoreCase("intro") ? "intro": "invalid") {
                case "reverse":
                case "convert":
                case "compare":
                    this.logic.commandHandler(command);
                    break;
                case "help":
                case "commands":
                    this.help();
                    break;
                case "exit":
                case "quit":
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                case "welcome":
                case "intro":
                    this.introMessage();
                    break;
                default:
                    System.out.println("Invalid command! " + command + " is not accepted by this program.");
                    break;
            }
        }
    }
}
