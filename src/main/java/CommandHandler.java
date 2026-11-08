import command.Command;
import command.DataStore;
import command.EchoCommand;
import command.GetCommand;
import command.PingCommand;
import command.RpushCommand;
import command.SetCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final DataStore store = new DataStore();
    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler() {
        commands.put("PING", new PingCommand());
        commands.put("ECHO", new EchoCommand());
        commands.put("SET", new SetCommand(store));
        commands.put("GET", new GetCommand(store));
        commands.put("RPUSH", new RpushCommand(store));
    }

    public String handle(String[] command) {
        if (command == null || command.length == 0) {
            return "-ERR empty command\r\n";
        }
        Command cmd = commands.get(command[0].toUpperCase());
        if (cmd == null) {
            return "-ERR unknown command '" + command[0] + "'\r\n";
        }
        return cmd.handle(command);
    }
}
