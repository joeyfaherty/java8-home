package ocp8;

import java.util.Arrays;
import java.util.List;

/**
 * Created by joey on 5/16/16.
 */
public class Cities {


    public static void findCityImperative(List<String> cities) {
        // smelly mutable boolean flag
        boolean found = false;
        for (String city : cities) {
            if (city.equals("Chicago")) {
                found = true;
                break;
            }
        }
        System.out.println("City found? : " + found);
    }

    /**
     * benefits of using declarative style:
     * no messing around with mutable variables
     * Iteration steps wrapped under the hood
     * Less clutter
     * Better clarity; retains our focus
     * Less impedance; code closely trails the business intent
     * Less error prone
     * Easier to understand and maintain
     * @param cities
     */
    private static void findCityDelarative(List<String> cities) {
        System.out.println("City found? : " + cities.contains("Chicago"));
    }

    public static void main(final String[] args) {
        List<String> cities = Arrays.asList("Dublin", "Chicago", "Las Vegas");
        findCityImperative(cities);
        findCityDelarative(cities);
    }

}