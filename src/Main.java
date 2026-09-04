
import java.util.Scanner;

public class Main {

    static final int MAX_HEALTH = 100;
    static final int STARTING_GOLD = 20;
    static final int ROWS = 5;
    static final int COLS = 11;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        printBanner("THE CHALLENGER");

        printTitle();
        System.out.println("Sand, torchlight, and a crowd that has already decided how this ends.");
        System.out.println("The gate opens.");
        System.out.println("");

        System.out.print("What is your name, challenger? ");
        String playerName = in.nextLine().trim();
        if (playerName.isEmpty()) {
            playerName = "Challenger";
        }
        int difficulty = readChoice(in, 1, 3);

        String difficultyName = switch (difficulty) {
            case 1 -> "Easy";
            case 2 -> "Normal";
            case 3 -> "Brutal";
            default -> "Unknown";
        };
        System.out.println("Difficulty: " + difficultyName);
        System.out.println("");

        int health = MAX_HEALTH;
        int potions = 2;
        int playerRow = 2, playerCol = 1;
        int enemyRow = 2, enemyCol = 9;

        String enemyName = "Cave Goblin";
        int enemyHealth = 30 + difficulty * 15;
        int enemyPower = 4 + difficulty * 3;

        System.out.printf("%-12s HP %3d/%3d  Gold %4d  Lv %d%n",
                playerName, health, MAX_HEALTH, STARTING_GOLD, 1);
        System.out.println("");

        System.out.printf("%s enters the arena. The %s has %d HP.%n",
                playerName, enemyName, enemyHealth);
        System.out.print("Press Enter to begin...");
        in.nextLine();
        System.out.println("");

        countdown(3);
        System.out.println("FIGHT!");
        System.out.println("");

        int turnNumber = 1;
        boolean playing = true;
        boolean fled = false;

        while (playing) {
            System.out.println("=".repeat(40));
            System.out.printf("  Turn %d%n", turnNumber);
            System.out.printf("%-12s HP %3d/%3d    %-14s HP %3d%n",
                    playerName, health, MAX_HEALTH, enemyName, enemyHealth);
            System.out.println("");

            drawArena(playerRow, playerCol, enemyRow, enemyCol);

            boolean adjacent = (playerRow == enemyRow) && (Math.abs(playerCol - enemyCol) == 1);
            int roll = (turnNumber * 3) % 10 + 1;
            int damage = 0;

            if (adjacent) {
                System.out.print("[A]ttack  [D]efend  [P]otion  [L]eft  [R]ight  [F]lee: ");
            } else {
                System.out.print("The " + enemyName + " is out of reach.  "
                        + "[L]eft  [R]ight  [D]efend  [P]otion  [F]lee: ");
            }
            String action = in.nextLine().trim().toUpperCase();

            switch (action) {
                case "A" -> {
                    if (!adjacent) {
                        System.out.println("You swing at empty air. Get closer first.");
                    } else {
                        damage = calculateDamage(enemyPower, roll);

                        if (roll >= 9) {
                            System.out.println("Critical Hit!");
                        } else if (roll >= 3) {
                            System.out.println("A solid hit");
                        } else {
                            System.out.println("You miss.");
                        }

                    }
                }
                case "L" -> {
                    if (playerCol - 1 < 1) {
                        System.out.println("The wall stops you.");
                    } else {
                        playerCol--;
                        System.out.println("You step left.");
                    }
                }
                case "R" -> {
                    if (playerCol + 1 > COLS - 2) {
                        System.out.println("The wall stops you.");
                    } else if (playerCol + 1 == enemyCol) {
                        System.out.println("The " + enemyName + " blocks your way.");
                    } else {
                        playerCol++;
                        System.out.println("You step right.");
                    }
                }
                case "D" -> {
                    health = heal(health, 5); // need to save back in health
                    System.out.println("You raise your guard and recover 5 HP.");
                }
                case "P" -> {
                    if (potions > 0) {
                        potions--;
                        health = heal(health, 25);
                        System.out.println("You drink a potion and recover 25 HP.");
                    } else {
                        System.out.println("You reach for a potion. There are none.");
                    }
                }
                case "F" -> {
                    fled = true;
                    System.out.println("You run for the gate. The crowd howls.");
                }
                default -> System.out.println("The crowd jeers. You hesitate and lose the turn.");
            }

            enemyHealth = applyDamage(enemyHealth, damage);

            if (!fled && isAlive(enemyHealth) && adjacent) {
                health = applyDamage(health, enemyPower);
                System.out.printf("The %s strikes back for %d.%n", enemyName, enemyPower);
            }

            if (health > MAX_HEALTH) {
                health = MAX_HEALTH;
            } else if (health < 0) {
                health = 0;
            }

            printHealthBar(health);

            if (fled) {
                System.out.println("You escape with your life, and nothing else.");
                playing = false;
            } else if (!isAlive(enemyHealth)) {
                System.out.printf("%nThe %s falls! You win on turn %d.%n", enemyName, turnNumber);
                playing = false;
            } else if (!isAlive(health)) {
                System.out.printf("%nYou have fallen on turn %d.%n", turnNumber);
                playing = false;
            }

            turnNumber++;
        }

        System.out.printf("%nThe arena empties after %d turns.%n", turnNumber - 1);
    }

    static void printBanner(String text) {
        System.out.println("=".repeat(40));
        System.out.printf(" %s%n", text);
        System.out.println("=".repeat(40));

    }

    static boolean isAlive(int hp) {
        return hp > 0;
    }

    static int calculateDamage(int power, int roll) {
        if (roll >= 9)
            return power * 2;
        if (roll >= 3)
            return power;
        return 0;
    }

    static int calculateDamage(int power, int roll, double critMultiplier) {
        if (roll >= 9)
            return (int) (power * critMultiplier);
        if (roll >= 3)
            return power;
        return 0;

    }

    static int readChoice(Scanner in, int min, int max) {
        int choice;

        do {

            System.out.printf("Choose Difficulty %d-%d: ", min, max);
            while (!in.hasNextInt()) {
                in.next();
                System.out.printf("Numbers only. Choose %d-%d: ", min, max);
            }

            choice = in.nextInt();
            in.nextLine();

        } while (choice < min || choice > max);
        return choice;
    }

    static int applyDamage(int hp, int damage) {
        return hp - damage;
    }

    static int heal(int hp, int amount) {
        return hp + amount;
    }

    static void tryToHeal(int hp) {
        hp += 50;
    }

    static void printHealthBar(int hp) {
        int bars = hp / 5;
        String bar = "#".repeat(bars) + "-".repeat(20 - bars);
        System.out.printf("[%s] %d%%%n", bar, hp);

    }

    static void printTitle() {
        System.out.print("""
                 ========================
                        THE ARENA
                ========================
                """);

    }

    static void countdown(int from) {
        for (int i = from; i > 0; i--) {
            System.out.println(i + "...");

        }

        System.out.println("FIGHT!");
        System.out.println("");
    }

    static void drawArena(int playerRow, int playerCol, int enemyRow, int enemyCol) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (r == playerRow && c == playerCol)
                    System.out.print('@');
                else if (r == enemyRow && c == enemyCol)
                    System.out.print('X');
                else if (r == 0 || r == ROWS - 1)
                    System.out.print('-');
                else if (c == 0 || c == COLS - 1)
                    System.out.print('|');
                else
                    System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println("");

    }

}
