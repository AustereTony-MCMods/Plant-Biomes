package austeretony.plantbiomes.common.main;

public enum EnumModVersions {

    VER_1_2_0("1.2.0", false);

    public final String version;

    public final boolean needPatch;

    EnumModVersions(String version, boolean needPatch) {
        this.version = version;
        this.needPatch = needPatch;
    }

    public static boolean isOutdatedConfig(String configVersion) {
        for (EnumModVersions enumVer : values()) {
            if (enumVer.version.equals(configVersion))
                return enumVer.needPatch;
        }
        return true;
    }
}
