package command;

public class GetCommand implements Command {
    private final DataStore store;

    public GetCommand(DataStore store) {
        this.store = store;
    }

    @Override
    public String handle(String[] command) {
        if(command.length<2) {
            return "-ERR wrong number of arguments for 'get' command\r\n";
        }
        if(store.expiryStore.containsKey(command[1]) && System.currentTimeMillis()>store.expiryStore.get(command[1])) {
            store.expiryStore.remove(command[1]);
            store.dataStore.remove(command[1]);
        }
        if(!store.dataStore.containsKey(command[1])) {
            return "$-1\r\n";
        }
        String val = store.dataStore.get(command[1]);
        return "$" + val.length() + "\r\n" + val + "\r\n";
    }
}
