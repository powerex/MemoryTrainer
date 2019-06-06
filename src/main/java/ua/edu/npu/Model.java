package ua.edu.npu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Model {

    Properties properties = new Properties();

    public static Model getInstance() {
        return ModelHolder.HOLDER_INSTANCE;
    }

    private Model() {
        try (InputStream inputStream = new FileInputStream("src/main/resources/settings.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApplicationFileName() {
        return properties.getProperty("mainFormFile");
    }

    public String getTitle() {
        return properties.getProperty("title");
    }

    public int getSize() {
        return Integer.parseInt(properties.getProperty("size"));
    }

    public String getImagesPath() {
        return properties.getProperty("smilesPath");
    }

    public double getImageSize() {
        return Double.parseDouble(properties.getProperty("imageSize"));
    }

    public String getReverseImagePath() {
        return properties.getProperty("reverseImagePath");
    }

    public static class ModelHolder {
        public static final Model HOLDER_INSTANCE = new Model();
    }

}
