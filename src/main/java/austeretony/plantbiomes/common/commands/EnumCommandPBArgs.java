package austeretony.plantbiomes.common.commands;

public enum EnumCommandPBArgs {

    HELP("help"),
    ENABLE("enable"),
    DISABLE("disable"),
    STATUS("status"),
    ENABLE_CONFIG_MODE("enable-conf"),
    DISABLE_CONFIG_MODE("disable-conf"),
    ENABLE_OVERLAY("enable-overlay"),
    DISABLE_OVERLAY("disable-overlay"),
    SETTINGS("settings"),
    BIOME("biome"),
    LATEST("latest"),
    DENY("deny"),
    ALLOW("allow"),
    DENY_GLOBAL("deny-global"),
    ALLOW_GLOBAL("allow-global"),
    ADD_VALID("add-valid"),
    REMOVE_VALID("rem-valid"),
    ALLOW_GROW_OVER_TIME("allow-got"),
    DENY_GROW_OVER_TIME("deny-got"),
    ALLOW_GROW_WITH_BONEMEAL("allow-gwb"),
    DENY_GROW_WITH_BONEMEAL("deny-gwb"),
    SET_MAIN("set-main"),
    RESET_MAIN("reset-main"),
    BIND_ITEM("bind-item"),
    UNBIND_ITEM("unbind-item"),
    CLEAR_DENIED("clear-denied"),
    CLEAR_VALID("clear-valid"),
    CLEAR_LATEST("clear-latest"),
    CLEAR_ALL("clear-all"),
    SAVE("save"),
    RELOAD("reload"),
    BACKUP("backup");

    public final String arg;

    EnumCommandPBArgs(String arg) {
        this.arg = arg;
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
