

package vertex.client.feature.command.coloring;

import java.util.function.Supplier;

public record PossibleArgument(ArgumentType argType, Supplier<String[]> suggestionSupplier) {
    public PossibleArgument(ArgumentType type, String... suggestions) {
        this(type, () -> suggestions);
    }
}
