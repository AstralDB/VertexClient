

package vertex.client.feature.command.impl;

import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.feature.command.Command;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleRegistry;

public class Toggle extends Command {

    public Toggle() {
        super("Toggle", "Toggles a module", "toggle", "t");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index,
                new PossibleArgument(ArgumentType.STRING, ModuleRegistry.getModules().stream().map(Module::getName).toList().toArray(String[]::new))
        );
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide module name");
        Module m = ModuleRegistry.getByName(String.join(" ", args));
        if (m == null) {
            throw new CommandException("Module not found", "Specify a module name that exists");
        } else {
            m.toggle();
        }
    }
}
