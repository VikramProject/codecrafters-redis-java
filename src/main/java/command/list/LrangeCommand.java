package command.list;

import command.Command;
import command.DataStore;

import java.util.List;

public class LrangeCommand implements Command {
    private final DataStore store;

    public LrangeCommand(DataStore store) {
        this.store = store;
    }

    @Override
    public String handle(String[] command) {
        if(command.length<4) {
            return "-ERR wrong number of arguments for 'lrange' command\r\n";
        }
        List<String> list = store.listStore.get(command[1]);
        if(list == null) {
            return "*0\r\n";
        }
        int size = list.size();
        int start = Integer.parseInt(command[2]);
        int stop = Integer.parseInt(command[3]);
        // Negative indexes count from the end of the list
        if(start<0) start = Math.max(size+start, 0);
        if(stop<0) stop = size+stop;
        if(stop>=size) stop = size-1;
        if(start>stop) {
            return "*0\r\n";
        }
        return encodeRange(list, start, stop);
    }

    private String encodeRange(List<String> list, int start, int stop) {
        StringBuilder sb = new StringBuilder("*" + (stop-start+1) + "\r\n");
        for(int i=start;i<=stop;i++) {
            String val = list.get(i);
            sb.append("$").append(val.length()).append("\r\n").append(val).append("\r\n");
        }
        return sb.toString();
    }
}
