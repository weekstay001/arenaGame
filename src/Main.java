
import java.util.Scanner;

public class Main {

    static final int MAX_HEALTH = 100;
    static final int STARTING_GOLD = 20;

    public static void main(String[] args) {

        
        Scanner in = new Scanner(System.in);

        String title = """
                ========================
                     THE ARENA
                ========================
                """;
        System.out.print(title);

        System.out.println("Sand, torchlight, and a crowd that has already decided how this ends.");
        System.out.println("The gate opens.");
        System.out.println("");

        
        System.out.print("What is your name, fighter?");
        String playerName = in.nextLine().trim(); // just trims off the excess spaces
        if (playerName.isEmpty()) {
            playerName = "Fighter";
        }

        System.out.print("What is your weapon of choice, " + playerName + "? (1 = sword, 2 = bow, 3 = magic");
        int weapon = in.nextInt();
        in.nextLine();

        
        System.out.print("Difficulty (1 = easy, 2 = normal, 3 = brutal); Enter to begim");
        int difficulty = in.nextInt();
        // consume the leftover newline
        in.nextLine();
        // Ask --> print + read

        int health = MAX_HEALTH;
        int gold = STARTING_GOLD;
        int level = 1;
        boolean alive = true;
        double critChance = 0.15 + weapon * 3;

        String enemyName = "Cave Troll";
        int enemyHealth = 30 + difficulty * 15;
        int enemyPower = 4 + difficulty * 3;

        System.out.printf("%-12s HP %3d/%3d  Gold %4d  Lv %d%n",
                playerName, health, MAX_HEALTH, gold, level);
        System.out.printf("Alive %-5b  Crit %.0f%%%n", alive, critChance * 100);
        System.out.println("");

        

        System.out.println(enemyName.toUpperCase() + " blocks your path!");
        System.out.printf("Opponent %-14s HP %3d  Power %2d%n",
                enemyName, enemyHealth, enemyPower);
        System.out.println("Name length: " + enemyName.length());

        boolean isBoss = enemyName.contains("Dragon");
        System.out.println("Boss fight: " + isBoss);

        if (enemyName.equalsIgnoreCase("cave troll")) {
            System.out.println("You have fought one of these before.");
        }
        System.out.println("");

        int Playerdamage = enemyPower * 2;
        health -= Playerdamage;
        System.out.println("You take " + Playerdamage + " damage. Health: " + health);

        int potion = 15;
        health += potion;
        level++;
        System.out.println("You drink a potion. Health: " + health);
        System.out.println("You reach level " + level + ".");
        System.out.println("");

        int hits = 3 + weapon * 2;
        int swings = 7 + weapon;
        double acc1 = (double) hits / swings * 100;
        System.out.printf("Accuracy: %.1f%%%n", acc1);
        System.out.println("");

        // the attack roll
        int roll = 7;
        int damage;

        if (roll >= 9) {
            damage = enemyPower * 2;
            System.out.println("CRITICAL HIT");
        } else if (roll >= 3) {
            damage = enemyPower;
            System.out.println("A solid hit.");
        } else {
            damage = 0;
            System.out.println("You miss.");
        }

        enemyHealth -= damage;

        if (enemyHealth <= 0) {
            System.out.println("The " + enemyName + " falls");
            alive = true;
        } else if (health <= 0) {
            System.out.println("You have fallen.");
            alive = false;
        }

        int turn = 6;
        boolean enrages = (turn % 3 == 0);
        System.out.println("Turn " + turn + " — enrages: " + enrages);
        System.out.println("");


        if (health > MAX_HEALTH) {
            health = MAX_HEALTH;

        } else if (health < 0) {
            health = 0;
        }

        int bars = health / 5;
        String bar = "#".repeat(bars) + "-".repeat(20 - bars);
        System.out.printf("[%s] %d%%%n", bar, health);
          

        if(health < MAX_HEALTH / 4 && gold >= 10){
            System.out.println("You should buy a potion.");
        }

        if(!alive || enemyHealth <= 0){
            System.out.println("The fight is over.");
        }
        
      

       

    }
}