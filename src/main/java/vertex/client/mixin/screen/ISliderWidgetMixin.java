

package vertex.client.mixin.screen;

import net.minecraft.client.gui.widget.SliderWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SliderWidget.class)
public interface ISliderWidgetMixin {
    @Accessor("value")
    double getValue();
}
