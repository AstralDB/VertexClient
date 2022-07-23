

package vertex.client.feature.command.examples;

import vertex.client.feature.command.Command;

public class ExampleServer {
    public static Command.ExamplesEntry getPlayerNames() {
        return new Command.ExamplesEntry("Notch", "Herobrine", "Player123");
    }
}
