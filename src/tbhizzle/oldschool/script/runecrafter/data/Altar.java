package tbhizzle.oldschool.script.runecrafter.data;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Path;

/**
 * Created by Tom on 11/16/2015.
 */
public enum Altar {
    AIR(    new Tile(3012, 3356, 0),
            new Tile(2986, 3294, 0),
            new Tile(2843, 4832, 0),
            14399,14897,14841, Paths.AIR//14398
    ),
    EARTH(  new Tile(3253, 3421, 0),
            new Tile(3305, 3472, 0),
            new Tile(2659, 4838, 0),
            14405,14900,14844, Paths.EARTH

    ),
    FIRE(   new Tile(3382, 3268, 0),
            new Tile(3311, 3252, 0),
            new Tile(2583, 4841, 0),
            14407,14901,14845,Paths.FIRE

    ),
    BODY(   new Tile(3093, 3490, 0),
            new Tile(3055, 3443, 0),
            new Tile(2522, 4842, 0),
            14409,14902,14846, Paths.BODY

    ),

    MIND(   new Tile(2946, 3369, 0),
            new Tile(2980, 3513, 0),
            new Tile(2787, 4839, 0),
            14401,14898,14842, Paths.MIND

    );

    private final Tile bankTile, altarEntrance, altarCrafting;
    private final int altarEntranceId, altarId, portalId;
    private final Tile[] pathToAltar;


    Altar(Tile bank, Tile altarEntrance, Tile altarCrafting, int altarEntranceId, int altarId, int portalId, Tile[] pathToAltar){
        this.bankTile = bank;
        this.altarEntrance = altarEntrance;
        this.altarCrafting = altarCrafting;
        this.altarEntranceId = altarEntranceId;
        this.altarId = altarId;

        this.portalId = portalId;
        this.pathToAltar = pathToAltar;
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
    public Tile[] getPathToAltar() {
        return pathToAltar;
    }
}
