package padroescomportamentais.commandcart;

public class ChargeCommand implements Command {

    private final Wallet wallet;
    private final double amount;

    public ChargeCommand(Wallet wallet, double amount) {
        this.wallet = wallet;
        this.amount = amount;
    }

    @Override
    public void execute() {
        wallet.charge(amount);
    }

    @Override
    public void undo() {
        wallet.refund(amount);
    }
}
