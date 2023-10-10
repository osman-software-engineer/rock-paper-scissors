package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name);

        Map<String, Integer> scores = new HashMap<>();
        try {
            Scanner fileScanner = new Scanner(new File("rating.txt"));
            while (fileScanner.hasNextLine()) {
                String[] line = fileScanner.nextLine().split(" ");
                scores.put(line[0], Integer.parseInt(line[1]));
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("rating.txt file not found, starting with score 0.");
        }
        int score = scores.getOrDefault(name, 0);

        System.out.print("Enter game options: ");
        String optionsInput = scanner.nextLine();
        List<String> options = optionsInput.isEmpty() ?
                Arrays.asList("rock", "paper", "scissors") :
                Arrays.asList(optionsInput.split(","));

        System.out.println("Okay, let's start");

        Random random = new Random();
        while (true) {
            String userChoice = scanner.nextLine().toLowerCase();

            if (userChoice.equals("!exit")) {
                System.out.println("Bye!");
                break;
            } else if (userChoice.equals("!rating")) {
                System.out.println("Your rating: " + score);
                continue;
            }

            if (!options.contains(userChoice)) {
                System.out.println("Invalid input");
                continue;
            }

            String computerChoice = options.get(random.nextInt(options.size()));

            if (userChoice.equals(computerChoice)) {
                System.out.println("There is a draw (" + computerChoice + ")");
                score += 50;
            } else {
                List<String> reorderedOptions = new ArrayList<>(options);
                Collections.rotate(reorderedOptions, -reorderedOptions.indexOf(userChoice) - 1);
                int computerChoiceIndex = reorderedOptions.indexOf(computerChoice);

                if (computerChoiceIndex < reorderedOptions.size() / 2) {
                    System.out.println("Sorry, but the computer chose " + computerChoice);
                } else {
                    System.out.println("Well done. The computer chose " + computerChoice + " and failed");
                    score += 100;
                }
            }
        }

        scanner.close();
    }
}
