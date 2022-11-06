package io.github.webcoder49.dolphinsofthedeep.mixinInterfaces.network;

import io.github.webcoder49.dolphinsofthedeep.network.packet.c2s.RenameEntityC2SPacket;

public interface AddServerPlayNetworkHandlerInterface {
    void onRenameEntity(RenameEntityC2SPacket packet);
}
