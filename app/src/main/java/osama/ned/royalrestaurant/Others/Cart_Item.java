package osama.ned.royalrestaurant.Others;

public class Cart_Item extends Menu_Item{
    private int userId;

    public Cart_Item(){
        super();
    }

    public Cart_Item(Menu_Item menu_item, int userId) {
        super(menu_item.getId(), menu_item.getImageResource(), menu_item.getName(), menu_item.getPrice(), menu_item.isInCart(), menu_item.isFavourite());
        this.userId = userId;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
