package acme.critical.module;

import java.util.List;
import java.util.ArrayList;
import acme.critical.module.misc.*;
import acme.critical.module.client.*;
import acme.critical.module.combat.*;
import acme.critical.module.visual.*;
import acme.critical.module.movement.*;
import acme.critical.module.Mod.Category;

public class ModMan {

    public static final ModMan INSTANCE = new ModMan();
    private List<Mod> modules = new ArrayList<>();

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
        modules.add(new Fakeplayer());
        modules.add(new CoordsSaver());
        modules.add(new Nightvision());
	modules.add(new KillFX());
    }
}
