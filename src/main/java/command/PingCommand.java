package command;

public class PingCommand implements Command {
    @Override
    public String handle(String[] command) {
        return "+PONG\r\n";
    }
}
