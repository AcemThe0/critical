package acme.critical.module.settings;

import java.util.HashMap;

public class KeybindSetting extends Setting {
    private int key;
    private boolean enabled;
    
    public KeybindSetting(String name, int defaultKey) {
        super(name);
        this.key = defaultKey;
    }

    public String getKeyChar(int key) {
        HashMap<Integer, String> keys = new HashMap<Integer, String>();
        keys.put(0, "None");
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

        keys.put(65, "A");
        keys.put(66, "B");
        keys.put(67, "C");
        keys.put(68, "D");
        keys.put(69, "E");
        keys.put(70, "F");
        keys.put(71, "G");
        keys.put(72, "H");
        keys.put(73, "I");
        keys.put(74, "J");
        keys.put(75, "K");
        keys.put(76, "L");
        keys.put(77, "M");
        keys.put(78, "N");
        keys.put(79, "O");
        keys.put(80, "P");
        keys.put(81, "Q");
        keys.put(82, "R");
        keys.put(83, "S");
        keys.put(84, "T");
        keys.put(85, "U");
        keys.put(86, "V");
        keys.put(87, "W");
        keys.put(88, "X");
        keys.put(89, "Y");
        keys.put(90, "Z");

        keys.put(91, "[");
        keys.put(92, "\\");
        keys.put(93, "]");
        keys.put(96, "`");
        keys.put(257, "Enter");
        keys.put(258, "Tab");
        keys.put(260, "Ins");
        keys.put(261, "Del");
        keys.put(262, "Right");
        keys.put(263, "Left");
        keys.put(264, "Down");
        keys.put(265, "Up");
        keys.put(268, "Home");
        keys.put(269, "End");
        keys.put(280, "CLock");
        keys.put(282, "NLock");

        keys.put(320, "Kp_0");
        keys.put(321, "Kp_1");
        keys.put(322, "Kp_2");
        keys.put(323, "Kp_3");
        keys.put(324, "Kp_4");
        keys.put(325, "Kp_5");
        keys.put(326, "Kp_6");
        keys.put(327, "Kp_7");
        keys.put(328, "Kp_8");
        keys.put(329, "Kp_9");

        keys.put(340, "LShift");
        keys.put(341, "LCtrl");
        keys.put(342, "LAlt");
        keys.put(344, "RShift");
        keys.put(345, "RCtrl");
        keys.put(346, "RAlt");

        return keys.get(key)!=null?keys.get(key):"UNK";
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public int getIndex() {
        return -1;
    }
}
