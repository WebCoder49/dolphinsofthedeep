package io.github.webcoder49.dolphinsofthedeep.network.packet.c2s;

import io.github.webcoder49.dolphinsofthedeep.mixinInterfaces.network.AddServerPlayNetworkHandlerInterface;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;

public class RenameEntityC2SPacket implements Packet<ServerPlayPacketListener> {
    private final String name;
    private final int entityId;

    public RenameEntityC2SPacket(int entityId, String name) {
        this.entityId = entityId;
        this.name = name;
    }

    public RenameEntityC2SPacket(PacketByteBuf buf) {
        this.entityId = buf.readInt();
        this.name = buf.readString();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeString(this.name);
    }

    @Override
    public void apply(ServerPlayPacketListener listener) {
        ((AddServerPlayNetworkHandlerInterface)listener).onRenameEntity(this);
    }

    public String getName() {
        return this.name;
    }

    public int getEntityId() {
        return this.entityId;
    }
}
