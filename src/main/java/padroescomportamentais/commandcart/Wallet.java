package padroescomportamentais.commandcart;

public class Wallet {

    private double balance;

    public Wallet(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void charge(double amount) {
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds");
        }
        balance -= amount;
    }

    public void refund(double amount) {
        balance += amount;
    }
}
