package invenz.roy.ngtc.models;

public class MyItem {

    private int id;
    private String itemName;
    private byte[] imageByte;


    public MyItem() {
    }

    public MyItem(String itemName, byte[] imageByte) {
        this.itemName = itemName;
        this.imageByte = imageByte;
    }

    public MyItem(int id, String itemName, byte[] imageByte) {
        this.id = id;
        this.itemName = itemName;
        this.imageByte = imageByte;
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

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }
}
