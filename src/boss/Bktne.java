package boss;

/*
 *
 *
 * @author HBT
 */

public class Bktne extends BossManager {

    private static Bktne instance;

    public static Bktne gI() {
        if (instance == null) {
            instance = new Bktne();
        }
        return instance;
    }

}
