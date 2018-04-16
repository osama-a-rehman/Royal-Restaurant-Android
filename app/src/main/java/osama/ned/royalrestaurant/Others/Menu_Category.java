package osama.ned.royalrestaurant.Others;

public class Menu_Category {
    private String name;
    private int thumbnail;

    public Menu_Category(String name, int thumbnail){
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
