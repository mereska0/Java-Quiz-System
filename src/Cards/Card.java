package Cards;

public class Card {
    private String face;
    private String back;
    private boolean cond;

    public Card(String face, String back) {
        this.face = face;
        this.back = back;
        cond = false;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setCond(boolean cond) {
        this.cond = cond;
    }

    public boolean getCond() {
        return cond;
    }

    public String getFace() {
        return face;
    }

    public String getBack() {
        return back;
    }
}
