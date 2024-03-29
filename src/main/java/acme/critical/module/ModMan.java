package acme.critical.module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.MinecraftClient;

import acme.critical.module.Mod.Category;
import acme.critical.module.client.*;
import acme.critical.module.combat.*;
import acme.critical.module.misc.*;
import acme.critical.module.movement.*;
import acme.critical.module.visual.*;

public class ModMan {
    public static final ModMan INSTANCE = new ModMan();
    private List<Mod> modules = new ArrayList<>();
    private HashMap<String, Mod> namedmodules = new HashMap();

    public ModMan() {
        addModules();
        for (Mod mod : modules) {
            namedmodules.put(mod.getName(), mod);
        }
    }

    public <T extends Mod> T getMod(Class<T> clasS) {
        return (T)modules.stream()
            .filter(mod -> mod.getClass() == clasS)
            .findFirst()
            .orElse(null);
    }

    public List<Mod> getModules() { return modules; }

    public Mod getModByName(String str) { return namedmodules.get(str); }

    public Mod[] modSearch(String str, int count) {
        ArrayList<Mod> ret = new ArrayList();
        for (Mod mod : modules) {
            if (ret.size() >= count)
                break;
            if (mod.getName().toUpperCase().contains(str.toUpperCase()))
                ret.add(mod);
        }

        return ret.toArray(new Mod[ret.size()]);
    }

    public List<Mod> getEnabledModules() {
        List<Mod> enabledModules = new ArrayList<>();
        for (Mod module : modules) {
            if (module.isEnabled())
                enabledModules.add(module);
        }
        return enabledModules;
    }

    private Category category;
    public List<Mod> getModulesInCategory(Category category) {
        List<Mod> categoryModules = new ArrayList();

        for (Mod mod : modules) {
            if (mod.getCategory() == category) {
                categoryModules.add(mod);
            }
        }
        return categoryModules;
    }

    private void addModules() {
        modules.add(new ESP());
        modules.add(new Tracers());
        modules.add(new Auto());
        modules.add(new Mhop());
        modules.add(new Xray());
        modules.add(new Slip());
        modules.add(new Step());
        modules.add(new Zoom());
        modules.add(new Blink());
        modules.add(new Speed());
        modules.add(new Spoof());
        modules.add(new Totem());
        modules.add(new Vclip());
        modules.add(new Reach());
        modules.add(new Flight());
        modules.add(new Friend());
        modules.add(new Nofall());
        modules.add(new Antiaim());
        modules.add(new Autoeat());
        modules.add(new Autolog());
        modules.add(new Clicktp());
        modules.add(new Freecam());
        modules.add(new Infohud());
        modules.add(new Nodeath());
        modules.add(new Autofish());
        modules.add(new Clickgui());
        modules.add(new Killaura());
        modules.add(new Nametags());
        modules.add(new Norender());
        modules.add(new Scaffold());
        modules.add(new Anticheat());
        modules.add(new Criticals());
        modules.add(new Knockback());
        modules.add(new Antihunger());
        modules.add(new Fakeplayer());
        modules.add(new CoordsSaver());
        modules.add(new Nightvision());
        modules.add(new Autoclicker());
        modules.add(new Crystalaura());
        modules.add(new Timechanger());
        // monolith

        var tr = MinecraftClient.getInstance().textRenderer;
        modules.sort(
            Comparator
            .comparingInt(m -> (int)tr.getWidth(((Mod)m).getName()))
            .reversed()
        );
    }
}
