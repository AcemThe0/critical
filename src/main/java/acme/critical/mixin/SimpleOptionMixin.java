package acme.critical.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.option.SimpleOption;

@Mixin(SimpleOption.class)
public interface SimpleOptionMixin {
    @Accessor("value")
    <T> void forceSetValue(T value);
}
