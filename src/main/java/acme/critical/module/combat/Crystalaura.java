package acme.critical.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;

import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.utils.RotUtils;
import acme.critical.utils.FriendsUtils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Crystalaura extends Mod {
    private NumberSetting delay = new NumberSetting("Delay", 0, 5, 1, 1);
    private NumberSetting range = new NumberSetting("Range", 1, 10, 5, 0.1);
    private NumberSetting crrange = new NumberSetting("CRange", 2, 4, 2, 1.0);
    private NumberSetting fov = new NumberSetting("FOV/2", 5, 180, 180, 0.1);
    private BooleanSetting place = new BooleanSetting("Place", true);
    private int ticksPassed = 0;

    public Crystalaura() {
        super("Crystalaura", "Breaks crystals.", Category.COMBAT);
        addSettings(delay, range, crrange, fov, place, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
            if (ticksPassed >= delay.getValueInt()) {
                ticksPassed = 0;
            if (!place.isEnabled()) {
                breakCrystal();
            } else {
                List<Entity> targets = new ArrayList<>(); // Searches for hot targets in your area
                for (Entity entity : mc.world.getEntities()) {
                    if (entity instanceof LivingEntity && entity != mc.player &&
                    !FriendsUtils.friends.contains(entity.getEntityName()) &&
                    mc.player.distanceTo(entity) <= range.getValueFloat()) {
                        targets.add(entity);
                    }
                }

                if (targets.size() >= 1) {
                    targets.sort(Comparator.comparingDouble(ent -> mc.player.distanceTo(ent))); // Gets the hottest one; (Might wanna filter by health next)
                    placeCrystalFor(targets.get(0)); // Here's a present, biatch
                    breakCrystal();
                    targets.clear();
                }
            }
        }

        ticksPassed += 1;
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    void placeCrystalFor(Entity target) {
        List<BlockPos> optimal = new ArrayList<>();
        int crange = crrange.getValueInt();
        BlockPos Cmid = target.getBlockPos();
        BlockPos Cmin = Cmid.add(-crange, (int)(-crange/2), -crange);
        BlockPos Cmax = Cmid.add(crange, crange, crange);

        for (BlockPos blockpos : blocksInRange(Cmin, Cmax)) {
                if (isCrystalPlaceable(blockpos) && (Cmid.compareTo(blockpos) != 0) ) {
                    optimal.add(blockpos);
                }
            }

        if (optimal.size() >= 1) {
            optimal.sort(
                Comparator.comparingDouble(blockpos -> target.squaredDistanceTo(blockpos.getX(), blockpos.getY(), blockpos.getZ()))
            );
            optimal.sort(Comparator.comparingDouble(blockpos -> cdiff(blockpos, target)));
            placeCrystal(optimal.get(0));
        }
    }

    void breakCrystal() {
        for (Entity ent : mc.world.getEntities()) {
            float tfov = fov.getValueFloat() * 2.f;
            float tYaw =
                Math.abs(RotUtils.getYawToEnt(ent) - mc.player.getYaw());
            float tPitch = Math.abs(
                RotUtils.getPitchToEnt(ent, false) - mc.player.getPitch()
            );
            if (mc.player.distanceTo(ent) < range.getValueFloat() &&
                ent instanceof EndCrystalEntity && tPitch <= tfov &&
                tYaw <= tfov) {
                mc.interactionManager.attackEntity(mc.player, ent);
            }
        }
    }

    void placeCrystal(BlockPos pos) {
        if(mc.player.isHolding(Items.END_CRYSTAL)) {
            for (Direction side : Direction.values()) {
                //if(isClickable(pos.offset(side))) continue; // Who the FUCK cares about being able to do something
                // DON'T LET YOUR DREAMS BE DREAMS
                Vec3d hitvec3 = Vec3d.ofCenter(pos).add((Vec3d.of(side.getVector())).multiply(0.5));

                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND,
                    new BlockHitResult(hitvec3, side.getOpposite(), pos.offset(side), false)
                );
            }
        }
    }

    List<BlockPos> blocksInRange(BlockPos start, BlockPos end) {
        int sizeX = (int)(end.getX() - start.getX());
        int sizeY = (int)(end.getY() - start.getY());
        int sizeZ = (int)(end.getZ() - start.getZ());
        List<BlockPos> range = new ArrayList<>();
        
        for (int i = 0; i < sizeX; i++) { //make a fucking cube
            for (int j = 0; j < sizeY; j++) {
                for (int k = 0; k < sizeZ; k++) {
                    range.add(new BlockPos(i+start.getX(), j+start.getY(), k+start.getZ())); //map the fucking cube
                }
            }
        }
        return range;
    }

    boolean isCrystalPlaceable(BlockPos pos) {
        Block block = mc.world.getBlockState(pos.down()).getBlock();
        return block == Blocks.BEDROCK || block == Blocks.OBSIDIAN;
    }

    double cdiff(BlockPos block, Entity target) { // people really write 2k lines of shit that resumes to this:
        if (block.getY()-target.getY() <= 0){ return 0; } // in their defense, it is just barely more refined
        else { return target.squaredDistanceTo(block.getX(), block.getY(), block.getZ()); } // at the cost of efficiency
    }
}
