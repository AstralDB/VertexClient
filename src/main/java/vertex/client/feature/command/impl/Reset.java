

package vertex.client.feature.command.impl;

import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.coloring.StaticArgumentServer;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.feature.command.Command;
import vertex.client.feature.config.SettingBase;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleRegistry;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Reset extends Command {
    long confirmationTimestampRequired = 0;

    public Reset() {
        super("Reset", "Resets a specified module (or * for all) to its default settings", "reset");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        return StaticArgumentServer.serveFromStatic(index, new PossibleArgument(ArgumentType.STRING, Util.make(() -> {
            List<String> a = new ArrayList<>(ModuleRegistry.getModules().stream().map(Module::getName).toList());
            a.add("*");
            return a;
        }).toArray(String[]::new)));
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide module name (or *)");
        String joined = String.join(" ", args);
        List<Module> targets = new ArrayList<>();
        if (joined.equals("*")) {
            boolean alright = System.currentTimeMillis() < confirmationTimestampRequired;
            if (!alright) {
                warn("THIS WILL RESET EVERY SINGLE MODULE TO ITS FACTORY DEFAULT");
                warn("RUN THIS COMMAND AGAIN TO CONFIRM THAT YOU WANT TO DO THIS");
                confirmationTimestampRequired = System.currentTimeMillis() + 10_000; // 10 second think time
                return;
            } else {
                warn("I hope you know what you're doing");
                targets.addAll(ModuleRegistry.getModules());
            }
        } else {
            Module byName = ModuleRegistry.getByName(joined);
            if (byName == null) {
                throw new CommandException("Module not found", null);
            }
            targets.add(byName);
        }
        for (Module target : targets) {
            if (target.isEnabled()) {
                target.setEnabled(false);
            }
            for (SettingBase<?> setting : target.config.getSettings()) {
                setting.reset();
            }
            success("Reset module " + target.getName());
        }
    }
}
