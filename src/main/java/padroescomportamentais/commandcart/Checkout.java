package padroescomportamentais.commandcart;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Checkout {

    public boolean run(List<Command> commands) {
        Deque<Command> executed = new ArrayDeque<>();
        try {
            for (Command command : commands) {
                command.execute();
                executed.push(command);
            }
            return true;
        } catch (IllegalStateException e) {
            while (!executed.isEmpty()) {
                executed.pop().undo();
            }
            return false;
        }
    }
}
