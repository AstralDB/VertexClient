/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.command.impl;

import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import vertex.client.feature.gui.screen.BindScreen;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleRegistry;
import vertex.client.helper.util.Utils;

public class Bind extends Command {
    public Bind() {
        super("Bind", "Sets the keybind of a module", "bind");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        if (index == 0) {
            return new PossibleArgument(ArgumentType.STRING, ModuleRegistry.getModules().stream().map(Module::getName).toList().toArray(String[]::new));
        }
        return super.getSuggestionsWithType(index, args);
    }

    @Override
    public ExamplesEntry getExampleArguments() {
        return new ExamplesEntry("Flight", "NoFall", "ClickGui");
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        validateArgumentsLength(args, 1, "Provide module name");
        String mn = args[0];
        Module module = ModuleRegistry.getByName(mn);
        if (module == null) {
            error("Module not found");
            return;
        }
        BindScreen bs = new BindScreen(module);
        Utils.TickManager.runInNTicks(5, () -> VertexMain.client.setScreen(bs));
    }
}
