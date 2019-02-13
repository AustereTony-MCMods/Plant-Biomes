package austeretony.plantbiomes.common.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public enum EnumConfigSettings {

    EXTERNAL_CONFIG("main", "external_config"),
    CHECK_UPDATES("main", "custom_update_checker"),
    TRANSFORMERS_FILE("main", "transformers_file"),
    SETTINGS_TOOLTIPS("main", "settings_tooltips"),
    AUTOSAVE("settings", "enable_autosave"),
    SETTINGS_OVERLAY("settings", "settings_overlay"),
    TILES_OVERLAY("settings", "tiles_overlay"),
    SMOKE_OVER_TIME("plants", "smoke_over_time"),
    SMOKE_ON_BONEMEAL("plants", "smoke_on_bonemeal");

    public final String configSection, configKey;

    private boolean isEnabled;

    EnumConfigSettings(String configSection, String configKey) {
        this.configSection = configSection;
        this.configKey = configKey;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    private JsonElement init(JsonObject jsonObject) {
        return jsonObject.get(this.configSection).getAsJsonObject().get(this.configKey);
    }

    public boolean initBoolean(JsonObject jsonObject) {
        return this.isEnabled = this.init(jsonObject).getAsBoolean();
    }
}
