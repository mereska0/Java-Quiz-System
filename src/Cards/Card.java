package Cards;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Card {
    private String face;
    private String content;
    private boolean showFace;
    private StackPane uiCard;

    public Card(String face, String content) {
        this.face = face;
        this.content = content;
        showFace = true;
        createUI();
    }

    public void setShowFace(String face) {
        this.face = face;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setShowFace(boolean cond) {
        this.showFace = cond;
    }

    public boolean getShowFace() {
        return showFace;
    }


    public String getFace() {
        return face;
    }

    public String getContent() {
        return content;
    }


    private void createUI() {
        Rectangle cardShape = new Rectangle(200,300);
        cardShape.setFill(Color.WHITE);
        cardShape.setStroke(Color.BLACK);
        cardShape.setStrokeWidth(5);

        Text displayText = new Text(face);
        displayText.setStyle("-fx-font-size: 20;");

        cardShape.setOnMouseClicked(e -> {
            if (showFace) {
                displayText.setText(content);
            } else {
                displayText.setText(face);
            }
        });

        uiCard = new StackPane();
        uiCard.getChildren().add(cardShape);
    }

    public StackPane getUICard() {
        return uiCard;
    }

}
