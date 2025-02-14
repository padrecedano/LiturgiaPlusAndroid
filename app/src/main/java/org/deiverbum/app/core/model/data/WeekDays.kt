package org.deiverbum.app.core.model.data

/**
 * Esta clase se usa para retornar el número de día de la semana
 * según la convención adoptada en la lógica de la aplicación:
 * La semana empieza el **Domingo**, el cual tiene asignado el valor **`1`**
 * y en ese orden siguen los siguientes días:
 * Lunes = **`2`**, Martes = **`3`** y así sucesivamente.
 *
 **
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 *
 */
enum class WeekDays(val value: Int) {
    SUNDAY(1),
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7);
}
