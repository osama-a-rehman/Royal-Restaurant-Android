package osama.ned.royalrestaurant.Others;

public class Menu_Item {

    private int id;
    private int imageResource;
    private String name;
    private float price;
    private boolean isInCart;
    private boolean isFavourite;

    public Menu_Item(){

    }

    public Menu_Item(int id, int imageResource, String name, float price, boolean isInCart, boolean isFavourite) {
        this.id = id;
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
        this.isInCart = isInCart;
        this.isFavourite = isFavourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageDrawable) {
        this.imageResource = imageDrawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isInCart() {
        return isInCart;
    }

    public void setInCart(boolean inCart) {
        isInCart = inCart;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Id: " + id + ", ");
        stringBuilder.append("Image Resource: " + imageResource + ", ");
        stringBuilder.append("Name: " + name + ", ");
        stringBuilder.append("Price: " + price + ", ");
        stringBuilder.append("isInCart: " + isInCart + ", ");
        stringBuilder.append("isFavourite: " + isFavourite);

        return stringBuilder.toString();
    }
}
