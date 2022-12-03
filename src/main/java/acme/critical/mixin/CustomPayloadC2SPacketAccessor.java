package acme.critical.mixin;

import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;

@Mixin(CustomPayloadC2SPacket.class)
public interface CustomPayloadC2SPacketAccessor {
    @Accessor("channel")
    Identifier getChannel();

    @Accessor("data")
    PacketByteBuf getData();

    @Mutable
    @Accessor("data")
    void setData(PacketByteBuf data);
}