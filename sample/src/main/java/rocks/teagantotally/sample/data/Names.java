package rocks.teagantotally.sample.data;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by tglenn on 10/6/17.
 */

public abstract class Names {
    public static final List<String> NAMES = Arrays.asList(
              "Bob", "Jim", "Susie", "Kim", "Stephen", "Matt", "Adam",
              "Grace", "Stew", "James", "Kyle", "Diane", "Rebecca", "Tyson",
              "Gina", "Peyton", "Lucy", "Molly", "Demi", "Nick", "Erin"
    );

    public static String getRandomName() {
        return NAMES.get(new Random().nextInt(NAMES.size()));
    }
}
