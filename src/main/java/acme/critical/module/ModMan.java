package acme.critical.module;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import acme.critical.module.misc.*;
import acme.critical.module.client.*;
import acme.critical.module.combat.*;
import acme.critical.module.visual.*;
import acme.critical.module.movement.*;
import acme.critical.module.Mod.Category;
import net.minecraft.client.MinecraftClient;

public class ModMan {
    private int arraylistpos = -1;
    private int watermarkpos = -1;

    public static final ModMan INSTANCE = new ModMan();
    private List<Mod> modules = new ArrayList<>();
    private MinecraftClient mc = MinecraftClient.getInstance();
    private Category category;

    public ModMan() {
        addModules();
    }

    public List<Mod> getModules() {
        return modules;
    }

    public List<Mod> getEnabledModules() {
        List<Mod> enabledModules = new ArrayList<>();
        for (Mod module : modules) {
            if (module.isEnabled()) enabledModules.add(module);
        }
        return enabledModules;
    }

    public List<Mod> getModulesInCategory(Category category) {
        List<Mod> categoryModules = new ArrayList();

        for (Mod mod : modules) {
            if (mod.getCategory() == category) {
                categoryModules.add(mod);
            }
        }
        return categoryModules;
    }

    public int getWatermarkpos(){ return watermarkpos;}

    public int getArraylistpos(){ return arraylistpos;}

    private void addModules() {
        modules.add(new Mhop());
        modules.add(new Xray());
        modules.add(new Speed());
        modules.add(new Totem());
        modules.add(new Flight());
        modules.add(new Friend());
        modules.add(new Nofall());
        modules.add(new Antiaim());
        modules.add(new Killaura());
        modules.add(new Scaffold());
        modules.add(new Wallhack());
        modules.add(new Arraylist());
        modules.add(new Watermark());
        modules.add(new Fakeplayer());
        modules.add(new CoordsSaver());
        modules.add(new Nightvision());

        modules.sort(Comparator.comparingInt(m->(int)mc.textRenderer.getWidth(((Mod)m).getName())).reversed());

        for (int i = 0; i<modules.size(); i++){
            if (modules.get(i) instanceof Arraylist) {
                arraylistpos = i;
            }

            if (modules.get(i) instanceof Watermark) {
                watermarkpos = i;
            }
        }
    }
}
