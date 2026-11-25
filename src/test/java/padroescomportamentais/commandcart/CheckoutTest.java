package padroescomportamentais.commandcart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    Cart cart;
    Inventory inventory;
    Wallet wallet;
    Checkout checkout;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        inventory = new Inventory("book", 1);
        wallet = new Wallet(50.0);
        checkout = new Checkout();
    }

    @Test
    void deveExecutarEDesfazerAddItemCommand() {
        Command command = new AddItemCommand(cart, "book");

        command.execute();
        assertEquals(Arrays.asList("book"), cart.getItems());

        command.undo();
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void deveExecutarEDesfazerReserveStockCommand() {
        Command command = new ReserveStockCommand(inventory, "book");

        command.execute();
        assertEquals(0, inventory.getStock("book"));

        command.undo();
        assertEquals(1, inventory.getStock("book"));
    }

    @Test
    void deveExecutarEDesfazerChargeCommand() {
        Command command = new ChargeCommand(wallet, 20.0);

        command.execute();
        assertEquals(30.0, wallet.getBalance());

        command.undo();
        assertEquals(50.0, wallet.getBalance());
    }

    @Test
    void deveConcluirCheckoutComSucesso() {
        boolean result = checkout.run(Arrays.asList(
                new AddItemCommand(cart, "book"),
                new ReserveStockCommand(inventory, "book"),
                new ChargeCommand(wallet, 20.0)
        ));

        assertTrue(result);
        assertEquals(Arrays.asList("book"), cart.getItems());
        assertEquals(0, inventory.getStock("book"));
        assertEquals(30.0, wallet.getBalance());
    }

    @Test
    void deveReverterTudoQuandoPagamentoFalha() {
        boolean result = checkout.run(Arrays.asList(
                new AddItemCommand(cart, "book"),
                new ReserveStockCommand(inventory, "book"),
                new ChargeCommand(wallet, 80.0)
        ));

        assertFalse(result);
        assertTrue(cart.getItems().isEmpty());
        assertEquals(1, inventory.getStock("book"));
        assertEquals(50.0, wallet.getBalance());
    }

    @Test
    void deveReverterQuandoNaoHaEstoque() {
        boolean result = checkout.run(Arrays.asList(
                new AddItemCommand(cart, "pen"),
                new ReserveStockCommand(inventory, "pen"),
                new ChargeCommand(wallet, 5.0)
        ));

        assertFalse(result);
        assertTrue(cart.getItems().isEmpty());
        assertEquals(50.0, wallet.getBalance());
    }

    @Test
    void deveDesfazerEmOrdemInversaSemDesfazerComandoQueFalhou() {
        List<String> log = new ArrayList<>();
        Command failing = new Command() {
            public void execute() { throw new IllegalStateException(); }
            public void undo() { log.add("undo C"); }
        };

        checkout.run(Arrays.asList(
                recorder(log, "A"),
                recorder(log, "B"),
                failing,
                recorder(log, "D")
        ));

        assertEquals(Arrays.asList("do A", "do B", "undo B", "undo A"), log);
    }

    private Command recorder(List<String> log, String name) {
        return new Command() {
            public void execute() { log.add("do " + name); }
            public void undo() { log.add("undo " + name); }
        };
    }
}
