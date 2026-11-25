package padroescomportamentais.commandcart;

public class ReserveStockCommand implements Command {

    private final Inventory inventory;
    private final String item;

    public ReserveStockCommand(Inventory inventory, String item) {
        this.inventory = inventory;
        this.item = item;
    }

    @Override
    public void execute() {
        inventory.reserve(item);
    }

    @Override
    public void undo() {
        inventory.release(item);
    }
}
