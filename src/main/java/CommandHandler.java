public class CommandHandler {
    public String handle(String[] command) {
        if (command == null || command.length == 0) {
            return "-ERR empty command\r\n";
        }

        String token = command[0].toUpperCase();
        switch (token) {
            case "PING":
                return "+PONG\r\n";

            case "ECHO":
                if (command.length < 2) {
                    return "-ERR wrong number of arguments for 'echo' command\r\n";
                }
                String argument = command[1];
                // Added missing trailing \r\n to conform to RESP bulk string protocol
                return "$" + argument.length() + "\r\n" + argument + "\r\n";

            default:
                return "-ERR unknown command '" + command[0] + "'\r\n";
        }
    }
}
