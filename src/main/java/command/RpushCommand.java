package command;

import java.util.ArrayList;

public class RpushCommand implements Command {
    private final DataStore store;

    public RpushCommand(DataStore store) {
        this.store = store;
    }

    @Override
    public String handle(String[] command) {
        if(command.length<3) {
            return "-ERR wrong number of arguments for 'rpush' command\r\n";
        }
        if(!store.listStore.containsKey(command[1])){
            store.listStore.put(command[1],new ArrayList<String>());
        }
        for(int i=2;i<command.length;i++) {
            store.listStore.get(command[1]).add(command[2]);
        }
        return ":"+store.listStore.get(command[1]).size()+"\r\n";
    }
}
