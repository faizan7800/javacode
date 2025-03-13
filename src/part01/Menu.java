package part01;

import java.util.Scanner;

public class Menu {
    private String[] options;
    private String title;
    private Scanner scanner;

    public Menu(String title, String[] options) {
        this.title = title;
        this.options = options;
        this.scanner = new Scanner(System.in);
    }

    public int getChoice() {
        System.out.println("\n" + title);
        for (int i = 0; i < title.length(); i++) {
            System.out.print("=");
        }
        System.out.println();

        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        System.out.print("\nEnter choice (1-" + options.length + "): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > options.length) {
                System.out.println("Invalid choice. Please try again.");
                return getChoice();
            }
            return choice;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return getChoice();
        }
    }

    public String getInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }
}