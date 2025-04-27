package iwust.gpgun.command.command;

import iwust.gpgun.annotation.GpgunCommand;
import iwust.gpgun.constant.Message;
import iwust.gpgun.constant.Property;
import iwust.gpgun.item.ItemManager;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.item.type.gun.Gun;
import iwust.gpgun.item.type.magazine.Magazine;
import iwust.gpgun.item.type.magazinegun.MagazineGun;
import iwust.gpgun.logger.Logger;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;

import javax.annotation.Nullable;

/* only test command */
@GpgunCommand(name = "s_config")
public final class ConfigCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NonNull CommandSender sender,
                             @NonNull Command command,
                             @NonNull String label,
                             @NonNull String @NonNull [] args) {
        if (!sender.isOp()) {
            sender.sendMessage(Message.NO_OP);
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("Использование: /s_config get <айди> <тип> <свойство>, /s_config set <айди> <тип> <свойство> <значение> или /s_config print");
            return true;
        }

        String action = args[0].toLowerCase();

        if (action.equals("print")) {
            if (args.length != 1) {
                sender.sendMessage("Использование: /s_config print");
                return true;
            }

            List<String> itemIds = ItemManager.getIdList();
            if (itemIds.isEmpty()) {
                Logger.warning("Список предметов пуст.");
                sender.sendMessage("Нет предметов для вывода.");
                return true;
            }

            for (String id : itemIds) {
                Common item = ItemManager.getCommonById(id, true);
                if (item == null) {
                    Logger.warning("Предмет с ID '" + id + "' не найден.");
                    continue;
                }

                String type = item instanceof MagazineGun ? Property.MAGAZINE_GUN :
                        item instanceof Magazine ? Property.MAGAZINE :
                                item instanceof Gun ? Property.GUN :
                                        Property.COMMON;

                StringBuilder logMessage = new StringBuilder("Предмет ID: " + id + ", Тип: " + type);
                logMessage.append(", Material: ").append(item.getMaterial());
                logMessage.append(", DisplayName: ").append(item.getDisplayName() != null ? item.getDisplayName() : "null");
                logMessage.append(", MaxStackSize: ").append(item.getMaxStackSize());

                if (item instanceof Gun gun) {
                    logMessage.append(", AmmoId: ").append(gun.getAmmoId());
                    logMessage.append(", MaxAmmo: ").append(gun.getMaxAmmo());
                    logMessage.append(", BulletDamage: ").append(gun.getBulletDamage());
                } else if (item instanceof Magazine magazine) {
                    logMessage.append(", AmmoId: ").append(magazine.getAmmoId());
                    logMessage.append(", MaxAmmo: ").append(magazine.getMaxAmmo());
                } else if (item instanceof MagazineGun magazineGun) {
                    logMessage.append(", AmmoId: ").append(magazineGun.getAmmoId());
                    logMessage.append(", MaxAmmo: ").append(magazineGun.getMaxAmmo());
                    logMessage.append(", BulletDamage: ").append(magazineGun.getBulletDamage());
                }

                Logger.warning(logMessage.toString());
            }
            sender.sendMessage("Информация о всех предметах выведена в лог.");
            return true;
        }

        if (action.equals("get") && args.length != 4 || action.equals("set") && args.length != 5) {
            sender.sendMessage("Использование: /s_config get <айди> <тип> <свойство> или /s_config set <айди> <тип> <свойство> <значение>");
            return true;
        }

        String id = args[1];
        String type = args[2].toLowerCase();
        String property = args[3].toLowerCase();

        // Проверка действия
        if (!action.equals("get") && !action.equals("set")) {
            sender.sendMessage("Неизвестное действие: " + action + ". Ожидается 'get', 'set' или 'print'.");
            return true;
        }

        // Проверка типа
        if (!Arrays.asList(Property.COMMON, Property.GUN, Property.MAGAZINE, Property.MAGAZINE_GUN).contains(type)) {
            sender.sendMessage("Неизвестный тип: " + type + ". Ожидается: common, gun, magazine, magazine_gun.");
            return true;
        }

        Common item = ItemManager.getCommonById(id, true);
        if (item == null) {
            sender.sendMessage("Предмет с ID '" + id + "' не найден.");
            return true;
        }

        // Проверка соответствия указанного типа фактическому типу предмета
        boolean typeMatches = switch (type) {
            case Property.COMMON -> !(item instanceof Gun || item instanceof Magazine || item instanceof MagazineGun);
            case Property.GUN -> item instanceof Gun;
            case Property.MAGAZINE -> item instanceof Magazine;
            case Property.MAGAZINE_GUN -> item instanceof MagazineGun;
            default -> false;
        };

        if (!typeMatches) {
            String actualType = item instanceof MagazineGun ? Property.MAGAZINE_GUN :
                    item instanceof Magazine ? Property.MAGAZINE :
                            item instanceof Gun ? Property.GUN :
                                    Property.COMMON;
            sender.sendMessage("Ошибка: Предмет с ID '" + id + "' имеет тип '" + actualType + "', а ожидался '" + type + "'.");
            return true;
        }

        if (action.equals("get")) {
            switch (type) {
                case Property.COMMON -> {
                    switch (property) {
                        case Property.ID -> sender.sendMessage("Значение: " + item.getId());
                        case Property.MATERIAL -> sender.sendMessage("Значение: " + item.getMaterial());
                        case Property.CUSTOM_NAME -> sender.sendMessage("Значение: " + item.getCustomName());
                        case Property.DISPLAY_NAME -> sender.sendMessage("Значение: " + item.getDisplayName());
                        case Property.ITEM_MODEL -> sender.sendMessage("Значение: " + item.getItemModel());
                        case Property.MAX_STACK_SIZE -> sender.sendMessage("Значение: " + item.getMaxStackSize());
                        case Property.MAX_DAMAGE -> sender.sendMessage("Значение: " + item.getMaxDamage());
                        case Property.DAMAGE -> sender.sendMessage("Значение: " + item.getDamage());
                        case Property.UNBREAKABLE -> sender.sendMessage("Значение: " + item.isUnbreakable());
                        case Property.USE_DELAY -> sender.sendMessage("Значение: " + item.getUseDelay());
                        case Property.EQUIP_DELAY -> sender.sendMessage("Значение: " + item.getEquipDelay());
                        default -> sender.sendMessage("Неизвестное свойство для Common: " + property);
                    }
                }
                case Property.GUN -> {
                    Gun gun = (Gun) item;
                    switch (property) {
                        case Property.AMMO_ID -> sender.sendMessage("Значение: " + gun.getAmmoId());
                        case Property.DISPLAY_AMMO_NAME -> sender.sendMessage("Значение: " + gun.getDisplayAmmoName());
                        case Property.DISPLAY_AMMO_AMOUNT -> sender.sendMessage("Значение: " + gun.getDisplayAmmoAmount());
                        case Property.DISPLAY_AMMO_UNLOAD -> sender.sendMessage("Значение: " + gun.getDisplayAmmoUnload());
                        case Property.MAX_AMMO -> sender.sendMessage("Значение: " + gun.getMaxAmmo());
                        case Property.AMMO -> sender.sendMessage("Значение: " + gun.getAmmo());
                        case Property.BULLET_AMOUNT -> sender.sendMessage("Значение: " + gun.getBulletAmount());
                        case Property.SCOPE -> sender.sendMessage("Значение: " + gun.getScope());
                        case Property.SPREAD -> sender.sendMessage("Значение: " + gun.getSpread());
                        case Property.VELOCITY -> sender.sendMessage("Значение: " + gun.getVelocity());
                        case Property.BULLET_DAMAGE -> sender.sendMessage("Значение: " + gun.getBulletDamage());
                        case Property.SHOT_DELAY -> sender.sendMessage("Значение: " + gun.getShotDelay());
                        default -> sender.sendMessage("Неизвестное свойство для Gun: " + property);
                    }
                }
                case Property.MAGAZINE -> {
                    Magazine magazine = (Magazine) item;
                    switch (property) {
                        case Property.AMMO_ID -> sender.sendMessage("Значение: " + magazine.getAmmoId());
                        case Property.DISPLAY_AMMO_NAME -> sender.sendMessage("Значение: " + magazine.getDisplayAmmoName());
                        case Property.DISPLAY_AMMO_AMOUNT -> sender.sendMessage("Значение: " + magazine.getDisplayAmmoAmount());
                        case Property.DISPLAY_AMMO_UNLOAD -> sender.sendMessage("Значение: " + magazine.getDisplayAmmoUnload());
                        case Property.MAX_AMMO -> sender.sendMessage("Значение: " + magazine.getMaxAmmo());
                        case Property.AMMO -> sender.sendMessage("Значение: " + magazine.getAmmo());
                        default -> sender.sendMessage("Неизвестное свойство для Magazine: " + property);
                    }
                }
                case Property.MAGAZINE_GUN -> {
                    MagazineGun magazineGun = (MagazineGun) item;
                    switch (property) {
                        case Property.AMMO_ID -> sender.sendMessage("Значение: " + magazineGun.getAmmoId());
                        case Property.DISPLAY_AMMO_NAME -> sender.sendMessage("Значение: " + magazineGun.getDisplayAmmoName());
                        case Property.DISPLAY_AMMO_STATE -> sender.sendMessage("Значение: " + magazineGun.getDisplayAmmoState());
                        case Property.DISPLAY_AMMO_STATE_YES -> sender.sendMessage("Значение: " + magazineGun.getDisplayAmmoStateYes());
                        case Property.DISPLAY_AMMO_STATE_NO -> sender.sendMessage("Значение: " + magazineGun.getDisplayAmmoStateNo());
                        case Property.DISPLAY_AMMO_UNLOAD -> sender.sendMessage("Значение: " + magazineGun.getDisplayAmmoUnload());
                        case Property.MAX_AMMO -> sender.sendMessage("Значение: " + magazineGun.getMaxAmmo());
                        case Property.AMMO -> sender.sendMessage("Значение: " + magazineGun.getAmmo());
                        case Property.BULLET_AMOUNT -> sender.sendMessage("Значение: " + magazineGun.getBulletAmount());
                        case Property.SCOPE -> sender.sendMessage("Значение: " + magazineGun.getScope());
                        case Property.SPREAD -> sender.sendMessage("Значение: " + magazineGun.getSpread());
                        case Property.VELOCITY -> sender.sendMessage("Значение: " + magazineGun.getVelocity());
                        case Property.BULLET_DAMAGE -> sender.sendMessage("Значение: " + magazineGun.getBulletDamage());
                        case Property.SHOT_DELAY -> sender.sendMessage("Значение: " + magazineGun.getShotDelay());
                        default -> sender.sendMessage("Неизвестное свойство для MagazineGun: " + property);
                    }
                }
            }
        } else if (action.equals("set")) {
            String value = args[4];
            switch (type) {
                case Property.COMMON -> {
                    Common common = item;
                    try {
                        switch (property) {
                            case Property.MATERIAL -> common.setMaterial(Material.valueOf(value.toUpperCase()));
                            case Property.CUSTOM_NAME -> common.setCustomName(value);
                            case Property.DISPLAY_NAME -> common.setDisplayName(value);
                            case Property.ITEM_MODEL -> common.setItemModel(value);
                            case Property.MAX_STACK_SIZE -> common.setMaxStackSize(Integer.parseInt(value));
                            case Property.MAX_DAMAGE -> common.setMaxDamage(Integer.parseInt(value));
                            case Property.DAMAGE -> common.setDamage(Integer.parseInt(value));
                            case Property.UNBREAKABLE -> common.setUnbreakable(Boolean.parseBoolean(value));
                            case Property.USE_DELAY -> common.setUseDelay(Float.parseFloat(value));
                            case Property.EQUIP_DELAY -> common.setEquipDelay(Float.parseFloat(value));
                            default -> {
                                sender.sendMessage("Неизвестное свойство для Common: " + property);
                                return true;
                            }
                        }
                        sender.sendMessage("Установлено " + property + " = " + value + " для Common с ID " + id);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage("Неверный формат значения для " + property + ": " + value);
                    }
                }
                case Property.GUN -> {
                    Gun gun = (Gun) item;
                    try {
                        switch (property) {
                            case Property.AMMO_ID -> gun.setAmmoId(value);
                            case Property.DISPLAY_AMMO_NAME -> gun.setDisplayAmmoName(value);
                            case Property.DISPLAY_AMMO_AMOUNT -> gun.setDisplayAmmoAmount(value);
                            case Property.DISPLAY_AMMO_UNLOAD -> gun.setDisplayAmmoUnload(value);
                            case Property.MAX_AMMO -> gun.setMaxAmmo(Integer.parseInt(value));
                            case Property.AMMO -> gun.setAmmo(Integer.parseInt(value));
                            case Property.BULLET_AMOUNT -> gun.setBulletAmount(Integer.parseInt(value));
                            case Property.SCOPE -> gun.setScope(Integer.parseInt(value));
                            case Property.SPREAD -> gun.setSpread(Float.parseFloat(value));
                            case Property.VELOCITY -> gun.setVelocity(Float.parseFloat(value));
                            case Property.BULLET_DAMAGE -> gun.setBulletDamage(Float.parseFloat(value));
                            case Property.SHOT_DELAY -> gun.setShotDelay(Float.parseFloat(value));
                            default -> {
                                sender.sendMessage("Неизвестное свойство для Gun: " + property);
                                return true;
                            }
                        }
                        sender.sendMessage("Установлено " + property + " = " + value + " для Gun с ID " + id);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage("Неверный формат значения для " + property + ": " + value);
                    }
                }
                case Property.MAGAZINE -> {
                    Magazine magazine = (Magazine) item;
                    try {
                        switch (property) {
                            case Property.AMMO_ID -> magazine.setAmmoId(value);
                            case Property.DISPLAY_AMMO_NAME -> magazine.setDisplayAmmoName(value);
                            case Property.DISPLAY_AMMO_AMOUNT -> magazine.setDisplayAmmoAmount(value);
                            case Property.DISPLAY_AMMO_UNLOAD -> magazine.setDisplayAmmoUnload(value);
                            case Property.MAX_AMMO -> magazine.setMaxAmmo(Integer.parseInt(value));
                            case Property.AMMO -> magazine.setAmmo(Integer.parseInt(value));
                            default -> {
                                sender.sendMessage("Неизвестное свойство для Magazine: " + property);
                                return true;
                            }
                        }
                        sender.sendMessage("Установлено " + property + " = " + value + " для Magazine с ID " + id);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage("Неверный формат значения для " + property + ": " + value);
                    }
                }
                case Property.MAGAZINE_GUN -> {
                    MagazineGun magazineGun = (MagazineGun) item;
                    try {
                        switch (property) {
                            case Property.AMMO_ID -> magazineGun.setAmmoId(value);
                            case Property.DISPLAY_AMMO_NAME -> magazineGun.setDisplayAmmoName(value);
                            case Property.DISPLAY_AMMO_STATE -> magazineGun.setDisplayAmmoState(value);
                            case Property.DISPLAY_AMMO_STATE_YES -> magazineGun.setDisplayAmmoStateYes(value);
                            case Property.DISPLAY_AMMO_STATE_NO -> magazineGun.setDisplayAmmoStateNo(value);
                            case Property.DISPLAY_AMMO_UNLOAD -> magazineGun.setDisplayAmmoUnload(value);
                            case Property.MAX_AMMO -> magazineGun.setMaxAmmo(Integer.parseInt(value));
                            case Property.AMMO -> magazineGun.setAmmo(Integer.parseInt(value));
                            case Property.BULLET_AMOUNT -> magazineGun.setBulletAmount(Integer.parseInt(value));
                            case Property.SCOPE -> magazineGun.setScope(Integer.parseInt(value));
                            case Property.SPREAD -> magazineGun.setSpread(Float.parseFloat(value));
                            case Property.VELOCITY -> magazineGun.setVelocity(Float.parseFloat(value));
                            case Property.BULLET_DAMAGE -> magazineGun.setBulletDamage(Float.parseFloat(value));
                            case Property.SHOT_DELAY -> magazineGun.setShotDelay(Float.parseFloat(value));
                            default -> {
                                sender.sendMessage("Неизвестное свойство для MagazineGun: " + property);
                                return true;
                            }
                        }
                        sender.sendMessage("Установлено " + property + " = " + value + " для MagazineGun с ID " + id);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage("Неверный формат значения для " + property + ": " + value);
                    }
                }
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender,
                                      @NonNull Command command,
                                      @NonNull String alias,
                                      @NonNull String @NonNull [] args) {
        if (!sender.isOp()) {
            return null;
        }

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("get", "set", "print"));
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("set"))) {
            completions.addAll(ItemManager.getIdList());
        } else if (args.length == 3 && (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("set"))) {
            completions.addAll(Arrays.asList(Property.COMMON, Property.GUN, Property.MAGAZINE, Property.MAGAZINE_GUN));
        } else if (args.length == 4 && (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("set"))) {
            String type = args[2].toLowerCase();
            switch (type) {
                case Property.COMMON -> completions.addAll(Arrays.asList(
                        Property.ID, Property.MATERIAL, Property.CUSTOM_NAME, Property.DISPLAY_NAME,
                        Property.ITEM_MODEL, Property.MAX_STACK_SIZE, Property.MAX_DAMAGE, Property.DAMAGE,
                        Property.UNBREAKABLE, Property.USE_DELAY, Property.EQUIP_DELAY
                ));
                case Property.GUN -> completions.addAll(Arrays.asList(
                        Property.AMMO_ID, Property.DISPLAY_AMMO_NAME, Property.DISPLAY_AMMO_AMOUNT,
                        Property.DISPLAY_AMMO_UNLOAD, Property.MAX_AMMO, Property.AMMO, Property.BULLET_AMOUNT,
                        Property.SCOPE, Property.SPREAD, Property.VELOCITY, Property.BULLET_DAMAGE, Property.SHOT_DELAY
                ));
                case Property.MAGAZINE -> completions.addAll(Arrays.asList(
                        Property.AMMO_ID, Property.DISPLAY_AMMO_NAME, Property.DISPLAY_AMMO_AMOUNT,
                        Property.DISPLAY_AMMO_UNLOAD, Property.MAX_AMMO, Property.AMMO
                ));
                case Property.MAGAZINE_GUN -> completions.addAll(Arrays.asList(
                        Property.AMMO_ID, Property.DISPLAY_AMMO_NAME, Property.DISPLAY_AMMO_STATE,
                        Property.DISPLAY_AMMO_STATE_YES, Property.DISPLAY_AMMO_STATE_NO, Property.DISPLAY_AMMO_UNLOAD,
                        Property.MAX_AMMO, Property.AMMO, Property.BULLET_AMOUNT, Property.SCOPE, Property.SPREAD,
                        Property.VELOCITY, Property.BULLET_DAMAGE, Property.SHOT_DELAY
                ));
            }
        }

        return completions.stream()
                .filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }
}