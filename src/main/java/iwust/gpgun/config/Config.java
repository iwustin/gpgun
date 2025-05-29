package iwust.gpgun.config;

import iwust.gpgun.constant.Property;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import lombok.Getter;
import lombok.NonNull;

@Getter
public final class Config {
    @NonNull
    private final ConfigContainer config;

    public Config(@NonNull final String filePath) throws Exception {
        ObjectMapper mapper = YAMLMapper.builder()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
                .build();

        File configFile = new File(filePath);
        config = mapper.readValue(configFile, ConfigContainer.class);
    }

    public void check() {
        checkItemList();
    }

    private void checkItemList() {
        List<Common> items = config.getItems();
        Logger.info("Checking the '" + Property.ITEMS + "' property.");
        List<Common> invalidCommonList = new ArrayList<>();

        for(Common common : items) {
            if(common.getFactory().createItemStack(common) == null) {
                invalidCommonList.add(common);
            }
        }

        if(invalidCommonList.isEmpty()) {
            Logger.info("The '" + Property.ITEMS + "' property is OK.");
        } else {
            Logger.warning("Items with these '" + Property.ID + "' properties will not be loaded:");
            for(Common common : invalidCommonList) {
                Logger.warning("\t- \"" +  common.getId() + "\"");
                items.remove(common);
            }
        }
    }

    public void validate() throws Exception {
        validateItemList();
    }

    private void validateItemList() throws Exception {
        List<Common> items = config.getItems();
        for(Common common : items) {
            String id =  common.getId();
            if(id.isEmpty()) {
                Logger.severe("Empty '" + Property.ID + "' property found: \"" + id + "\"");
                throw new Exception("Empty id.");
            }
            for(Common c : items) {
                if(c.getId().equals(id) && c != common) {
                    Logger.severe("Duplicate '" + Property.ID + "' property found: \"" + id + "\"");
                    throw new Exception("Duplicate id.");
                }
            }
        }
    }
}