package padroescomportamentais.commandcart;

public class AddItemCommand implements Command {

    private final Cart cart;
    private final String item;

    public AddItemCommand(Cart cart, String item) {
        this.cart = cart;
        this.item = item;
    }

    @Override
    public void execute() {
        cart.add(item);
    }

    @Override
    public void undo() {
        cart.remove(item);
    }
}
