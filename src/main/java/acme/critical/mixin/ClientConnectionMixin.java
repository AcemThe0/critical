package acme.critical.mixin;

import acme.critical.Critical;
import io.netty.channel.Channel;
import net.minecraft.network.packet.Packet;
import io.netty.util.concurrent.Future;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import io.netty.channel.ChannelHandlerContext;
import acme.critical.event.events.EventPacket;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.PacketCallbacks;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Shadow
    private Channel channel;

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        if (this.channel.isOpen() && packet != null) {
            EventPacket.Read event = new EventPacket.Read(packet);
            Critical.INSTANCE.eventBus.post(event);
            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }

    //Duct-taped together.
    @Inject(method = "Lnet/minecraft/network/ClientConnection;send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V", at = @At("HEAD"), cancellable = true)
    private void send(Packet<?> packet, PacketCallbacks callback, CallbackInfo ci) {
        EventPacket.Send event = new EventPacket.Send(packet);
        Critical.eventBus.post(event);

        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
