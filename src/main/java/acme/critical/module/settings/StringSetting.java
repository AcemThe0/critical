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

	public void set(String str) {
		val = str;
	}
}
