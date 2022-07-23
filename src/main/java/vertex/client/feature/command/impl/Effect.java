

package vertex.client.feature.command.impl;

import vertex.client.feature.command.argument.IntegerArgumentParser;
import vertex.client.feature.command.coloring.ArgumentType;
import vertex.client.feature.command.coloring.PossibleArgument;
import vertex.client.feature.command.exception.CommandException;
import vertex.client.VertexMain;
import vertex.client.feature.command.Command;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class Effect extends Command {

    public Effect() {
        super("Effect", "Gives you an effect client side", "effect", "eff");
    }

    @Override
    public ExamplesEntry getExampleArguments() {
        return new ExamplesEntry("give 3 100 255", "clear");
    }

    @Override
    public PossibleArgument getSuggestionsWithType(int index, String[] args) {
        if (index == 0) {
            return new PossibleArgument(ArgumentType.STRING, "give", "clear");
        } else if (args[0].equalsIgnoreCase("give")) {
            return switch (index) {
                case 1 -> new PossibleArgument(ArgumentType.NUMBER, "(effect id)");
                case 2 -> new PossibleArgument(ArgumentType.NUMBER, "(duration)");
                case 3 -> new PossibleArgument(ArgumentType.NUMBER, "(strength)");
                default -> super.getSuggestionsWithType(index, args);
            };
        }
        return super.getSuggestionsWithType(index, args);
    }

    @Override
    public void onExecute(String[] args) throws CommandException {
        if (VertexMain.client.player == null) {
            return;
        }
        validateArgumentsLength(args, 1, "Provide action");
        switch (args[0].toLowerCase()) {
            case "give" -> {
                validateArgumentsLength(args, 4, "Provide id, duration and strength");
                IntegerArgumentParser iap = new IntegerArgumentParser();
                int id = iap.parse(args[1]);
                int duration = iap.parse(args[2]);
                int strength = iap.parse(args[3]);
                StatusEffect effect = StatusEffect.byRawId(id);
                if (effect == null) {
                    error("Didnt find that status effect");
                    return;
                }
                StatusEffectInstance inst = new StatusEffectInstance(effect, duration, strength);
                VertexMain.client.player.addStatusEffect(inst);
            }
            case "clear" -> {
                for (StatusEffectInstance statusEffect : VertexMain.client.player.getStatusEffects().toArray(new StatusEffectInstance[0])) {
                    VertexMain.client.player.removeStatusEffect(statusEffect.getEffectType());
                }
            }
            default -> error("Choose one of \"give\" and \"clear\"");
        }
    }
}
