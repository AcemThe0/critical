package acme.critical.module.settings;

import java.util.HashMap;

public class StringSetting extends Setting {
	private String val;

	public StringSetting(String name, String def) {
		super(name);
		this.val = def;
	}

	public String getVal() {
		return val;
	}

	public void add(String c) {
		val += c;
	}

	public void add(char c) {
		val += c;
	}

	public void rem() {
		val = val.substring(0, val.length() - 1);
	}

	public void set(String str) {
		val = str;
	}

	    public String getKeyChar(int key) {
        HashMap<Integer, String> keys = new HashMap<Integer, String>();
		keys.put(32, " ");
        keys.put(39, "'");
        keys.put(44, ",");
        keys.put(45, "-");
        keys.put(46, ".");
        keys.put(47, "/");
        keys.put(48, "0");
        keys.put(49, "1");
        keys.put(50, "2");
        keys.put(51, "3");
        keys.put(52, "4");
        keys.put(53, "5");
        keys.put(54, "6");
        keys.put(55, "7");
        keys.put(56, "8");
        keys.put(57, "9");
        keys.put(59, ";");
        keys.put(61, "=");

        keys.put(65, "a");
        keys.put(66, "b");
        keys.put(67, "c");
        keys.put(68, "d");
        keys.put(69, "e");
        keys.put(70, "f");
        keys.put(71, "g");
        keys.put(72, "h");
        keys.put(73, "i");
        keys.put(74, "j");
        keys.put(75, "k");
        keys.put(76, "l");
        keys.put(77, "m");
        keys.put(78, "n");
        keys.put(79, "o");
        keys.put(80, "p");
        keys.put(81, "q");
        keys.put(82, "r");
        keys.put(83, "s");
        keys.put(84, "t");
        keys.put(85, "u");
        keys.put(86, "v");
        keys.put(87, "w");
        keys.put(88, "x");
        keys.put(89, "y");
        keys.put(90, "z");

        keys.put(91, "[");
        keys.put(92, "\\");
        keys.put(93, "]");
        keys.put(96, "`");

        return keys.get(key)!=null?keys.get(key):"";
    }
}
