/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.command.argument;

import vertex.client.feature.command.exception.CommandException;

public interface ArgumentParser<T> {
    T parse(String argument) throws CommandException;
}
