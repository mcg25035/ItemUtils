package org.itemutils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemUtils extends JavaPlugin {
    public static org.bukkit.inventory.ItemStack nmsToBukkit(net.minecraft.world.item.ItemStack itemStack,boolean copyAmount){
        org.bukkit.inventory.ItemStack converted = CraftItemStack.asBukkitCopy(itemStack);
        if (!copyAmount){
            converted.setAmount(1);
        }
        return converted;
    }
    public static net.minecraft.world.item.ItemStack bukkitToNMS(org.bukkit.inventory.ItemStack itemStack,boolean copyAmount){
        net.minecraft.world.item.ItemStack converted = CraftItemStack.asNMSCopy(itemStack);
        if (!copyAmount){
            converted.setCount(1);
        }
        return CraftItemStack.asNMSCopy(itemStack);
    }

    public static CompoundTag nmsItemToNBT(net.minecraft.world.item.ItemStack item){
        CompoundTag result = new CompoundTag();
        result = item.save(result);
        return result;
    }
    public static net.minecraft.world.item.ItemStack nbtToNmsItem(CompoundTag nbt){
        String itemID = nbt.getString("id");
        if (itemID == null){
            itemID = "minecraft:air";
        }
        byte count = nbt.getByte("Count");
        if (count == 0){
            count = 1;
        }
        CompoundTag itemNbt;
        try {
            itemNbt = nbt.getCompound("tag");
        }
        catch (Exception e){
            itemNbt = new CompoundTag();
        }
        if (itemNbt == null){
            itemNbt = new CompoundTag();
        }

        org.bukkit.inventory.ItemStack bukkitItemStack = new org.bukkit.inventory.ItemStack(Material.getMaterial(itemID.replace("minecraft:","").toUpperCase()));
        bukkitItemStack.setAmount(count);
        net.minecraft.world.item.ItemStack item = bukkitToNMS(bukkitItemStack,true);
        item.setTag(itemNbt);
        return item;
    }

    public static String ItemToData(org.bukkit.inventory.ItemStack itemStack){
        return nmsItemToNBT(bukkitToNMS(itemStack,true)).toString();
    }
    public static org.bukkit.inventory.ItemStack dataToItem(String nbtFormatString){
        CompoundTag nbtData;
        try {
            nbtData = TagParser.parseTag(nbtFormatString);
        }
        catch (Exception e){
            nbtData = new CompoundTag();
        }
        return nmsToBukkit(nbtToNmsItem(nbtData),true);
    }

    public static String getDescriptionID(org.bukkit.inventory.ItemStack itemStack){
        return bukkitToNMS(itemStack, false).getDescriptionId();
    }
    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
