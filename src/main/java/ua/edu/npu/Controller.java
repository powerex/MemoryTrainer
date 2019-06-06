package ua.edu.npu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class Controller implements Initializable {

    ImageView [][] field;
    boolean [] guessed;
    int[] numbers;
    List<Image> images;
    Model model = Model.getInstance();
    Random random = ThreadLocalRandom.current();
    int size = Model.getInstance().getSize();
    int numberOpenedImage = -1;
    int steps = 0;

    @FXML
    Button testButton;
    @FXML
    Pane gamePane;
    @FXML
    Label labelInfo;

    private ImageKeeper imageKeeper;

    public void testAction(ActionEvent actionEvent) {
        loadImageSet();
        setAllGuessedFalse();
        numberOpenedImage = -1;
        steps = 0;
        for (int i=0; i<size; i++)
        for (int j=0; j<size; j++) {
//            field[i][j].setImage(images.get(numbers[i*size+j]));
            field[i][j].setImage(imageKeeper.getReverseImage());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageKeeper = new ImageKeeper(model.getImagesPath());
        double imageSize = model.getImageSize();

        field = new ImageView[size][size];
        guessed = new boolean[size*size];
        setAllGuessedFalse();
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                field[i][j] = new ImageView();
                gamePane.getChildren().add(field[i][j]);
                field[i][j].setFitHeight(imageSize);
                field[i][j].setFitWidth(imageSize);
                field[i][j].setY(i*imageSize);
                field[i][j].setX(j*imageSize);
//                field[i][j].setImage(imageKeeper.getReverseImage());
            }
        }

        testAction(null);
    }

    private void loadImageSet() {
        int count = size*size / 2;
        numbers = new int[size*size];
        for (int i=0; i<count; i++) {
            numbers[2*i]   = i;
            numbers[2*i+1] = i;
        }

        int length = numbers.length;
        for (int i = 0; i< length; i++) {
            int index = random.nextInt(length);
            int tmp = numbers[i];
            numbers[i] = numbers[index];
            numbers[index] = tmp;
        }

        images = imageKeeper.getRandomArray(count);
    }

    public void paneClick(MouseEvent mouseEvent) {
        int sceneX = (int) mouseEvent.getSceneX();
        int sceneY = (int)mouseEvent.getSceneY();
        sceneX /= model.getImageSize();
        sceneY /= model.getImageSize();
        if (sceneX >= size) sceneX = size - 1;
        if (sceneY >= size) sceneY = size - 1;
        if (sceneX < 0) sceneX = 0;
        if (sceneY < 0) sceneY = 0;

        steps++;
        if (numberOpenedImage == - 1) {
            numberOpenedImage = sceneX*size +sceneY;
            if (guessed[numberOpenedImage]) {
                numberOpenedImage = -1;
                return;
            }
            field[sceneY][sceneX].setImage(images.get(numbers[numberOpenedImage]));
        } else {
            int openedImage = sceneX*size +sceneY;
            if (numberOpenedImage == openedImage || guessed[openedImage]) return;
            if (numbers[numberOpenedImage] == numbers[openedImage]) {
                field[sceneY][sceneX].setImage(images.get(numbers[openedImage]));
                guessed[numberOpenedImage] = true;
                guessed[openedImage] = true;
                if (isAllGuessed()) {
                    labelInfo.setText("Вгадано за\n" + steps + "\nкроків");
                }
            } else {
                field[numberOpenedImage%size][numberOpenedImage/size].setImage(imageKeeper.getReverseImage());
                field[numberOpenedImage%size][numberOpenedImage/size].setImage(imageKeeper.getReverseImage());
            }
            numberOpenedImage = -1;
        }
    }

    private void setAllGuessedFalse() {
        for (int i=0; i<size*size; i++)
            guessed[i] = false;
    }

    private boolean isAllGuessed() {
        int i = 0;
        while (i < size*size) {
            if (!guessed[i])
                return false;
            i++;
        }
        return true;
    }
}
