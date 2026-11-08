package command;

public class SetCommand implements Command {
    private final DataStore store;

    public SetCommand(DataStore store) {
        this.store = store;
    }

    @Override
    public String handle(String[] command) {
        if(command.length<3) {
            return "-ERR wrong number of arguments for 'set' command\r\n";
        }
        if(command.length>4 && command[3].equalsIgnoreCase("PX")) {
            Long time = System.currentTimeMillis() + Long.parseLong(command[4]);
            store.expiryStore.put(command[1],time);
        }
        store.dataStore.put(command[1],command[2]);
        return "+OK\r\n";
    }
}
