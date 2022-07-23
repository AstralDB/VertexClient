

package vertex.client.feature.command.coloring;

public class StaticArgumentServer {
    public static PossibleArgument serveFromStatic(int index, PossibleArgument... types) {
        if (index >= types.length) {
            return new PossibleArgument(null);
        }
        return types[index];
    }
}
