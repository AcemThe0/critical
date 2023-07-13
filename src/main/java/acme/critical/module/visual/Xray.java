package acme.critical.module.visual;

import java.util.HashMap;
import java.util.ArrayList;
import acme.critical.module.Mod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import acme.critical.module.ModMan;
import net.minecraft.registry.Registries;
import acme.critical.module.visual.Nightvision;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

    public class Xray extends Mod {
    public static ArrayList<Block> blocks = new ArrayList<>();
    public ModeSetting mode = new ModeSetting("Mode", "Coal", "Coal", "Iron", "Gold", "Lapis", "Emerald", "Redstone", "Diamond", "Quartz", "Debris", "All");
    public BooleanSetting containers = new BooleanSetting("Containers", true);
    public BooleanSetting other = new BooleanSetting("Other", true);
    //Nightvision nightvision = ModMan.INSTANCE.getMod(Nightvision.class);
    //For some reason, the above line leads to errors

    public Xray() {
        super("Xray", "See blocks!", Category.VISUAL);
        addSettings(mode, containers, other, new KeybindSetting("Key", 0));
    }

    @Override
    public void onEnable() {
        mc.chunkCullingEnabled = false;

        mc.worldRenderer.reload();

        Registries.BLOCK.forEach(block -> {
            if (targetBlock(block)) {blocks.add(block);}else{blocks.remove(block);}
        });
        Nightvision.setGamma(255.0);
    }

    @Override
    public void onDisable() {
        mc.chunkCullingEnabled = true;
        
        Nightvision.setGamma(Nightvision.nvEnabled ? 255.0f : Nightvision.origGamma);
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

        HashMap<String, Block> nuOres = new HashMap<String, Block>();
        nuOres.put("Coal", Blocks.DEEPSLATE_COAL_ORE);
        nuOres.put("Iron", Blocks.DEEPSLATE_IRON_ORE);
        nuOres.put("Gold", Blocks.DEEPSLATE_GOLD_ORE);
        nuOres.put("Lapis", Blocks.DEEPSLATE_LAPIS_ORE);
        nuOres.put("Emerald", Blocks.DEEPSLATE_EMERALD_ORE);
        nuOres.put("Redstone", Blocks.DEEPSLATE_REDSTONE_ORE);
        nuOres.put("Diamond", Blocks.DEEPSLATE_DIAMOND_ORE);

        if (mode.getMode() != "All") {
            c1 = block == ores.get(mode.getMode()) || block == nuOres.get(mode.getMode());
        } else {
	    c1 = false;
	    // net.minecraft.block.OreBlock was removed, breaking this
            /*c1 = block instanceof OreBlock || block instanceof RedstoneOreBlock;*/
        }
        
        if (containers.isEnabled()) c2 = block == Blocks.FURNACE || block == Blocks.DISPENSER || block == Blocks.DROPPER || block == Blocks.BARREL;
        if (other.isEnabled()) c3 = block == Blocks.TNT || block == Blocks.OBSIDIAN || block == Blocks.BEDROCK || block == Blocks.COMMAND_BLOCK || block == Blocks.END_GATEWAY || block == Blocks.NETHER_PORTAL;

        return c1 || c2 || c3;
    }

}
