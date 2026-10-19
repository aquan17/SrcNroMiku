package boss;

/*
 *
 *
 * @author HBT
 */

public class ChristmasEventManager extends BossManager {

    private static ChristmasEventManager instance;

    public static ChristmasEventManager gI() {
        if (instance == null) {
            instance = new ChristmasEventManager();
        }
        return instance;
    }

}
