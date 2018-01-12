package fr.ughostephan.myappretrofit.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ughostephan on 12/01/2018.
 */
public class LoremPicsum {

    public static String getRandomImageUrl() {
        String[] imageTypes = {"nemo", "up", "futurama", "rio", "simpsons"};
        int imageId = ThreadLocalRandom.current().nextInt(1, 7);
        String imageType = imageTypes[new Random().nextInt(imageTypes.length)];
        return  "http://lorempicsum.com/" + imageType + "/800/600/" + imageId;
    }

}
