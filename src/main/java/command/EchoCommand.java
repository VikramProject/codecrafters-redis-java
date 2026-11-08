package command;

public class EchoCommand implements Command {
    @Override
    public String handle(String[] command) {
        if(command.length<2) {
            return "-ERR wrong number of arguments for 'echo' command\r\n";
        }
        return "$" + command[1].length() + "\r\n" + command[1] + "\r\n";
    }
}
