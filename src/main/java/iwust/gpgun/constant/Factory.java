package iwust.gpgun.constant;

import iwust.gpgun.item.type.common.CommonFactory;
import iwust.gpgun.item.type.gui.GuiFactory;
import iwust.gpgun.item.type.gun.GunFactory;
import iwust.gpgun.item.type.magazine.MagazineFactory;
import iwust.gpgun.item.type.magazinegun.MagazineGunFactory;

public final class Factory {
    public final static CommonFactory COMMON = new CommonFactory();
    public final static GuiFactory GUI = new GuiFactory();
    public final static GunFactory GUN = new GunFactory();
    public final static MagazineFactory MAGAZINE = new MagazineFactory();
    public final static MagazineGunFactory MAGAZINE_GUN = new MagazineGunFactory();
}
