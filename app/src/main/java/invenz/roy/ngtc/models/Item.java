package invenz.roy.ngtc.models;

public class Item {

    private int itemId;
    private String name;
    private String imagePath;

    public Item() {
    }



    public Item(int itemId, String name, String imagePath) {
        this.itemId = itemId;
        this.name = name;
        this.imagePath = imagePath;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

