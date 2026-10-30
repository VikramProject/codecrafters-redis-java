import java.util.HashMap;
import java.util.List;
import java.util.List;
import java.util.Map;

public class CommandHandler {
    // Simple in-memory data store
    private final Map<String, String> dataStore = new HashMap<>();
    private final Map<String, Long> expiryStore = new HashMap<>();
    private final Map<String, List<String>> ListStore = new HashMap<>();

    
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
                if(command.length>4 && command[3].equalsIgnoreCase("PX")) {
                    Long time = System.currentTimeMillis() + Long.parseLong(command[4]);
                    expiryStore.put(command[1],time);
                }
                dataStore.put(command[1],command[2]);
                yield "+OK\r\n";
            }
            case "RPUSH" -> {
                if(command.length<3) {
                    yield "-ERR wrong number of arguments for 'rpush' command\r\n";
                }
                if(!listStore.containsKey(command[1])){
                    listStore.put(command[1],new ArrayList<String>());
                }
                for(int i=2;i<command.length;i++) {
                    listStore.get(command[1]).add(command[2]);
                }
                yield "+OK\r\n";
            }
            case "GET" -> {
                if(command.length<2) {
                    yield "-ERR wrong number of arguments for 'get' command\r\n";
                }
                if(expiryStore.containsKey(command[1]) && System.currentTimeMillis()>expiryStore.get(command[1])) {
                    expiryStore.remove(command[1]);
                    dataStore.remove(command[1]);
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
