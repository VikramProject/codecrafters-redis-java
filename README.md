[![progress-banner](https://backend.codecrafters.io/progress/redis/8ccd6e8d-f466-406e-8506-da1861a58ec7)](https://app.codecrafters.io/users/VikramProject?r=2qF)

This is a starting point for Java solutions to the
["Build Your Own Redis" Challenge](https://codecrafters.io/challenges/redis).

In this challenge, you'll build a toy Redis clone that's capable of handling
basic commands like `PING`, `SET` and `GET`. Along the way we'll learn about
event loops, the Redis protocol and more.

**Note**: If you're viewing this repo on GitHub, head over to
[codecrafters.io](https://codecrafters.io) to try the challenge.

# Passing the first stage

The entry point for your Redis implementation is in `src/main/java/Main.java`.
Study and uncomment the relevant code, then run the command below to execute the
tests on our servers:

```sh
codecrafters submit
```

That's all!

# Stage 2 & beyond

Note: This section is for stages 2 and beyond.

1. Ensure you have `mvn` installed locally
1. Run `./your_program.sh` to run your Redis server, which is implemented in
   `src/main/java/Main.java`.
1. Run `codecrafters submit` to submit your solution to CodeCrafters. Test
   output will be streamed to your terminal.

# Changes

Implement mock Redis from scratch. Work completed so far:

- Split the `switch` in `CommandHandler` into one private method per command.
- Added a `Command` interface (`String handle(String[] command)`) in the
  `command` package, with one class per command: `PingCommand`, `EchoCommand`,
  `SetCommand` and `GetCommand`.
- Moved the in-memory maps (strings, expiry times, lists) into a shared
  `DataStore` that is passed to the commands that need it.
- Replaced the `switch` with a map from command name to `Command`, so a new
  command only needs a class and one `commands.put(...)` line.
- Created the `command.list` package for list commands and moved
  `RpushCommand` into it.
- Fixed `RPUSH` so every value is appended, not just the first.
- Added `LrangeCommand` (`LRANGE key start stop`): both ends included, negative
  indexes count from the end, out-of-range values are clamped, and an empty
  array is returned for a missing key or empty range.
- Moved the array encoding in `LrangeCommand` into its own `encodeRange` method.
