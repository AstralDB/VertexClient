

package vertex.client.mixin.screen;

import net.minecraft.client.gui.screen.CommandSuggestor;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CommandSuggestor.class)
public interface ICommandSuggestorMixin {
    @Invoker("provideRenderText")
    OrderedText invokeProvideRenderText(String original, int index);
}
