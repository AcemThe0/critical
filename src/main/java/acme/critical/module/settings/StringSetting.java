package acme.critical.module.settings;

public class StringSetting extends Setting {
	private String val;

	public StringSetting(String name, String def) {
		super(name);
		this.val = def;
	}

	public String getVal() {
		return val;
	}

	public void add(String str) {
		val += str;
	}

	public void add(char c) {
		val += c;
	}

	public void del() {
		val = val.substring(0, val.length() - 1);
	}

	public void set(String str) {
		val = str;
	}
}
