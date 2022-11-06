package acme.critical.module.visual;

import java.util.HashMap;
import java.util.ArrayList;
import acme.critical.module.Mod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.util.registry.Registry;
import acme.critical.module.visual.Nightvision;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

    public class Xray extends Mod {
    public static ArrayList<Block> blocks = new ArrayList<>();
    public static boolean xrayEnabled = false;
    
    public ModeSetting mode = new ModeSetting("Mode", "Coal", "Coal", "Iron", "Gold", "Lapis", "Emerald", "Redstone", "Diamond", "Quartz", "Debris", "All");
    public BooleanSetting containers = new BooleanSetting("Containers", true);
    public BooleanSetting other = new BooleanSetting("Other", true);

    public Xray() {
        super("Xray", "See blocks!", Category.VISUAL);
        addSettings(mode, containers, other, new KeybindSetting("Key", 0));
    }

    @Override
    public void onEnable() {
        mc.worldRenderer.reload();
        xrayEnabled = true;

        Registry.BLOCK.forEach(block -> {
            if (targetBlock(block)) {blocks.add(block);}else{blocks.remove(block);}
        });
        Nightvision.setGamma(255.0);
    }

    @Override
    public void onDisable() {
        xrayEnabled = false;

        if(Nightvision.nightvisionEnabled){Nightvision.setGamma(255.0f);}else{Nightvision.setGamma(1.0f);}
        mc.worldRenderer.reload();
    }

    boolean targetBlock(Block block) {
        boolean c1 = false, c2 = false, c3 = false;

        HashMap<String, Block> ores = new HashMap<String, Block>();
        ores.put("Coal", Blocks.COAL_ORE);
        ores.put("Iron", Blocks.IRON_ORE);
        ores.put("Gold", Blocks.GOLD_ORE);
        ores.put("Lapis", Blocks.LAPIS_ORE);
        ores.put("Emerald", Blocks.EMERALD_ORE);
        ores.put("Redstone", Blocks.REDSTONE_ORE);
        ores.put("Diamond", Blocks.DIAMOND_ORE);
        ores.put("Quartz", Blocks.NETHER_QUARTZ_ORE);
        ores.put("Debris", Blocks.ANCIENT_DEBRIS);

        if (mode.getMode() != "All") {
            c1 = block == ores.get(mode.getMode());
        } else {
            c1 = block instanceof OreBlock || block instanceof RedstoneOreBlock;
        }
        
        if (containers.isEnabled()) c2 = block == Blocks.FURNACE || block == Blocks.DISPENSER || block == Blocks.DROPPER;
        if (other.isEnabled()) c3 = block == Blocks.TNT || block == Blocks.OBSIDIAN || block == Blocks.BEDROCK || block == Blocks.COMMAND_BLOCK || block == Blocks.END_GATEWAY || block == Blocks.NETHER_PORTAL;

        return c1 || c2 || c3;
    }

}
