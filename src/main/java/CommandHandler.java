import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    // Simple in-memory data store
    private final Map<String, String> dataStore = new HashMap<>();

    public String handle(String[] command) {
        if (command == null || command.length == 0) {
            return "-ERR empty command\r\n";
        }
        return switch(command[0].toUpperCase()) {
            case "PING" -> "+PONG\r\n";
            case "ECHO" -> (command.length<2) 
                ? "-ERR wrong number of arguments for 'echo' command\r\n"
                : "$" + command[1].length() + "\r\n" + command[1] + "\r\n";
            case "SET" -> {
                if(command.length<3) {
                    yield "-ERR wrong number of arguments for 'set' command\r\n";
                }
                dataStore.put(command[1],command[2]);
                yield "+OK\r\n";
            }
            case "GET" -> {
                if(command.length<2) {
                    yield "-ERR wrong number of arguments for 'get' command\r\n";
                }
                if(!dataStore.containsKey(command[1])) {
                    yield "$-1\r\n";
                }
                String val = dataStore.get(command[1]);
                yield "$" + val.length() + "\r\n" + val + "\r\n";
            }
            default -> "-ERR unknown command '" + command[0] + "'\r\n";
        };
    }
}
