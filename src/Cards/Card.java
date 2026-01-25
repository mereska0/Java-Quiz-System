package Cards;

public class Card {
    private String face;
    private String back;
    private boolean showFace;

    public Card(String face, String back) {
        this.face = face;
        this.back = back;
        showFace = true;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setFace(boolean cond) {
        this.showFace = cond;
    }

    public boolean getShowFace() {
        return showFace;
    }


    public String getFace() {
        return face;
    }

    public String getBack() {
        return back;
    }
}
