package austeretony.plantbiomes.common.commands;

public enum EnumCommandPBArgs {

    HELP("help"),
    ENABLE("enable"),
    DISABLE("disable"),
    STATUS("status"),
    ENABLE_CONFIG("enable-conf"),
    DISABLE_CONFIG("disable-conf"),
    SETTINGS("settings"),
    BIOME("biome"),
    LATEST("latest"),
    DENY("deny"),
    DENY_GLOBAL("deny-global"),
    ALLOW("allow"),
    ALLOW_GLOBAL("allow-global"),
    CLEAR_LATEST("clear-latest"),
    CLEAR_ALL("clear-all"),
    SAVE("save"),
    BACKUP("backup");

    public final String arg;

    EnumCommandPBArgs(String arg) {
        this.arg = arg;
    }
    
    public static boolean isValid(String strArg) {
        for (EnumCommandPBArgs arg : values())
            if (arg.arg.equals(strArg))
                return true;
        return false;
    }

    public static EnumCommandPBArgs get(String strArg) {
        for (EnumCommandPBArgs arg : values())
            if (arg.arg.equals(strArg))
                return arg;
        return null;
    }
    
    @Override
    public String toString() {
        return this.arg;
    }
}
