

package vertex.client.feature.command.argument;

import vertex.client.feature.command.exception.CommandException;

public interface ArgumentParser<T> {
    T parse(String argument) throws CommandException;
}
