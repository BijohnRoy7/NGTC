package invenz.roy.ngtc.models;

import android.graphics.Bitmap;

public class FetchItem {

    private int id;
    private String itemName;
    private Bitmap imageBitmap;

    public FetchItem(int id, String itemName, Bitmap imageBitmap) {
        this.id = id;
        this.itemName = itemName;
        this.imageBitmap = imageBitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}
