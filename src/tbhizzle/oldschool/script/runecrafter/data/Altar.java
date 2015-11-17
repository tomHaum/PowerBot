package tbhizzle.oldschool.script.runecrafter.data;

import org.powerbot.script.Tile;

/**
 * Created by Tom on 11/16/2015.
 */
public enum Altar {
    AIR(    new Tile(3012, 3356, 0),
            new Tile(2986, 3296, 0),
            new Tile(2843, 4832, 0),
            14399,14897,14841
    ),
    EARTH(  new Tile(3253, 3421, 0),
            new Tile(3303, 3470, 0),
            new Tile(2659, 4838, 0),
            14405,14900,14844
    );

    private final Tile bankTile, altarEntrance, altarCrafting;
    private final int altarEntranceId, altarId, portalId;
    Altar(Tile bank, Tile altarEntrance, Tile altarCrafting, int altarEntranceId, int altarId, int portalId){
        this.bankTile = bank;
        this.altarEntrance = altarEntrance;
        this.altarCrafting = altarCrafting;
        this.altarEntranceId = altarEntranceId;
        this.altarId = altarId;
        this.portalId = portalId;
    }

    public Tile getBankTile() {
        return bankTile;
    }

    public Tile getAltarEntrance() {
        return altarEntrance;
    }

    public Tile getAltarCrafting() {
        return altarCrafting;
    }

    public int getAltarEntranceId() {
        return altarEntranceId;
    }

    public int getAltarId() {
        return altarId;
    }
    public int getPortalId() {
        return portalId;
    }
}
