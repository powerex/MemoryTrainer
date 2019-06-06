package ua.edu.npu;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ImageKeeper {

    List<Image> imageList = new ArrayList<>();

    public Image getReverseImage() {
        return reverseImage;
    }

    Image reverseImage;

    public ImageKeeper(String path) {

        try {
            reverseImage = new Image(new FileInputStream(Model.getInstance().getReverseImagePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().endsWith(".png")) {
                    try {
                        Image image = new Image(new FileInputStream(listOfFiles[i].getPath()));
                        imageList.add(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println();
                }
            }
        }
    }

    public Image getImage(int index) {
        return imageList.get(index);
    }

    public int getListSize() {
        return imageList.size();
    }

    public List<Image> getRandomArray(int size) {
        Collections.shuffle(imageList);
        return imageList.subList(0, size);
    }

}
