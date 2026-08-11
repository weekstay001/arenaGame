import java.nio.file.SecureDirectoryStream;

public class App {

    static final int MAX_HEALTH = 100;
    static final int STARTING_GOLD = 20;

    public static void main(String[] args) throws Exception {
        System.out.println("The arena awaits");
        System.out.println("Sand, torchlight, and a crowd that has already decided how this ends");
        System.out.println("The gate opens");
        System.out.println("");

        String playerName = "Smiley";
        int health = MAX_HEALTH;
        int gold = STARTING_GOLD;
        int level = 1;
        boolean isAlive = true;
        double critChance = 0.15;

        // TODO: make a string called enemyName, assign your choice
        // TODO: enemyHealth of some int
        // TODO: enemyPower of some int between 1 and 10

        String enemyName = "Troll";
        int enemyHealth = 30;
        int enemyPower = 4;

        System.out.println("Fighter: " + playerName);
        System.out.println("Health: " + health + " / " + MAX_HEALTH);
        System.out.println("Gold: " + gold);
        System.out.println("Level: " + level);
        System.out.println("Alive: " + isAlive);
        System.out.println("critChance: " + critChance);
        System.out.println();

        System.out.println("Opponent: " + enemyName);
        System.out.println("Health: " + enemyHealth);
        System.out.printf("Power: %s%n", enemyPower);
        System.out.println();

        // The combat arithmetic
        int damage = enemyPower * 2;
        health -= damage;
        System.out.println("You take " + damage + " damage Health: " + health);

        int potion = 15;
        health += potion;
        level++;

        // A rhythm with %
        int turn = 6;
        boolean enrages = (turn % 3 == 0);
        System.out.println("Turn " + turn + " - enrages: " + enrages);
        System.out.println();

        // crit, and what cast casts
        double critDamage = damage * 1.75;
        int applied = (int) critDamage;
        System.out.println("Lost to the cast: " + (critDamage - applied));

    }

}
