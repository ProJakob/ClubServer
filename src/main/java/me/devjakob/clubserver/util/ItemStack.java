package me.devjakob.clubserver.util;

import dev.dewy.nbt.tags.collection.CompoundTag;

public class ItemStack {

    private final short id;
    private byte amount;
    private final short meta;
    private CompoundTag nbt;

    public ItemStack(short id, byte amount, short meta) {
        this.id = id;
        this.amount = amount;
        this.meta = meta;
    }

    public short getId() {
        return id;
    }

    public byte getAmount() {
        return amount;
    }

    public void setAmount(byte amount) {
        this.amount = amount;
    }

    public short getMeta() {
        return meta;
    }

    public CompoundTag getNbt() {
        return nbt;
    }

    public void setNbt(CompoundTag nbt) {
        this.nbt = nbt;
    }

    @Override
    public String toString() {
        return "ItemStack{" +
                "id=" + id +
                ", amount=" + amount +
                ", meta=" + meta +
                ", nbt=" + nbt +
                '}';
    }
}
