// Made with love by Bruno 🧡

import entities.Dictionary;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static int menu_current = 0;
    public static String[] menu_options = new String[]{"1. Add new word", "2. Delete word", "3. Get meaning", "4. Dictionary list", "5. Spell check a text file", "6. Exit"};
    public static Runnable[] menu_actions = new Runnable[]
            {
                    Main::addNewWord,
                    Main::deleteWord,
                    Main::getMeaning,
                    Main::dictionaryList,
                    Main::spellCheck,
                    Main::exit
            };
    public static Scanner scanner = new Scanner(System.in);
    public static Dictionary dict;

    public static void main(String[] args) {
        dict = Dictionary.loadDictionary();

        do {
            showMenu();
            menu_current = getIntInput("Enter an option: ", "Input was not in the correct format!");
            menu_current--;
            if (menu_current >= 0 && menu_current < menu_options.length) {
                menu_actions[menu_current].run();
            } else {
                System.out.println("Not a valid option!");
            }
        } while (menu_current != 5);
    }

    private static int getIntInput(String _message, String _error_message) {
        Integer res = null;
        while (res == null) {
            try {
                System.out.print(_message);
                res = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println(_error_message);
//                res = null;
            }
        }
        return res;
    }

    private static void showMenu() {
        for (int i = 0; i < menu_options.length; i++) {
            if (menu_current == i) System.out.println("> " + menu_options[i]);
            else System.out.println(menu_options[i]);
        }
    }

    private static void addNewWord() {
        String word = getInput("Enter a word: ");
        if (dict.exists(word)) {
            System.out.println("The word already exist in the dictionary!");
            return;
        }
        String meaning = getInput("Enter a meaning: ");
        if (dict.add(word, meaning)) {
            System.out.println("Successfully created!");
        } else {
            System.out.println("Word '" + word + "' could not be added!");
        }
    }

    private static void deleteWord() {
        String word = getInput("Enter a word: ");
        if (dict.delete(word)) {
            System.out.println("Successfully deleted!");
        } else {
            System.out.println("Word '" + word + "' could not be deleted!");
        }
    }

    private static void getMeaning() {
        String word = getInput("Enter a word: ");
        System.out.println(dict.getMeaning(word));
    }

    private static void dictionaryList() {
        System.out.println(dict.printDictionary());
    }

    private static void spellCheck() {
        String path = getInput("Enter a file path: ");
        File file = new File(path);
        String data = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] info = fis.readAllBytes();
            data = new String(info, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("File not found!");
            return;
        }

        data = data.replace(", ", " ").replace(".", " ").trim();
        List<String> words = Arrays.stream(data.split(" ")).filter(x -> !x.isBlank() && !x.isEmpty()).toList();
        StringBuilder sb = new StringBuilder();
        sb.append("Words that I don't have in my dictionary: ").append("\n");
        for (String word : words)
            if (!word.isBlank() && !word.isEmpty())
                if (!dict.exists(word.trim()))
                    sb.append(word.trim()).append("\n");

        System.out.println(sb);
    }

    private static String getInput(String _message) {
        System.out.print(_message);
        return scanner.nextLine().trim();
    }

    private static void exit() {
        System.exit(0);
    }
}