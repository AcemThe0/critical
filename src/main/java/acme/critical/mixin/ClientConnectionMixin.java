package acme.critical.mixin;

import acme.critical.Critical;
import io.netty.channel.Channel;
import net.minecraft.network.Packet;
import io.netty.util.concurrent.Future;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import io.netty.channel.ChannelHandlerContext;
import acme.critical.event.events.EventPacket;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import io.netty.util.concurrent.GenericFutureListener;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    @Inject(method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At("HEAD"), cancellable = true)
    private void send(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> callback, CallbackInfo ci) {
        EventPacket.Send event = new EventPacket.Send(packet);
        Critical.eventBus.post(event);

        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
