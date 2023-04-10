package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Constants.NBSP_4;

import androidx.room.Ignore;

import org.deiverbum.app.utils.ColorUtils;
import org.deiverbum.app.utils.Utils;

/**
 * <p>
 * Esta clase maneja el estado de la sincronización.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

public class SyncStatus {
    public String tableName = "";
    //@Ignore
    public Integer versionDB = 1;
    public String lastUpdate = "";

    @SuppressWarnings("unused")
    public SyncStatus() {
    }

    @Ignore
    public SyncStatus(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getAll(boolean isNightMode) {
        ColorUtils.isNightMode = isNightMode;
        return String.format("Última fecha disponible en el calendario: %s%s" +
                        "<b>%s</b> (*)" +
                        "%sÚltima sincronización realizada: %s" +
                        "<b>%s</b>" +
                        "%s<small>..............%s%s(*) El calendario se sincroniza periódicamente cuando tienes conexión a internet.</small>",
                BR, NBSP_4,
                Utils.toRedFont(tableName),
                BRS, NBSP_4,
                Utils.toRedFont(lastUpdate),
                BRS, BR, BR);
    }

    public String getLastUpdate(boolean isNightMode) {
        ColorUtils.isNightMode = isNightMode;
        return String.format("<p><b>%s</b></p>Es posible que la sincronización no esté activada. " +
                        "Sin esta opción no podrás recibir las fechas futuras del calendario ni la corrección de eventuales errores.%sPulsa en el botón <b>Sincronizar</b> para activarla."
                , Utils.toRedFont("AVISO"), BRS);
    }
}