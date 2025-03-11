package org.deiverbum.app.core.model.data.alteri

/**
 * Clase que representa el **`Rosario`** en la capa de datos externa.
 *
 * @property series Un objeto [RosariumSeries] con los misterios según el día de la semana.
 * @property mysterium Una lista de objetos del tipo  [RosariumMysterium] con los misterios correspondientes.
 *
 *  @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 * @see [Alteri]
 */

data class Rosarium(
    var series: RosariumSeries,
    var mysteriorum: List<RosariumMysteriumOrdo>,
    var aveMaria: String = "Dios te Salve, María, llena eres de gracia, el Señor es contigo. Bendita tú eres entre todas las mujeres y bendito es el fruto de tu vientre, Jesús. <br><br>Santa María, Madre de Dios, ruega por nosotros pecadores, ahora y en la hora de nuestra muerte. Amén.",

    override var typus: String = "rosarium"
) : Alteri(typus) {


    companion object {
        var LITANIAE = listOf(
            Litaniae("Señor, ten piedad.", "Señor, ten piedad."),
            Litaniae("Cristo, ten piedad.", "Cristo, ten piedad."),
            Litaniae("Señor, ten piedad.", "Señor, ten piedad."),
            Litaniae("Cristo, óyenos.", "Cristo, óyenos."),
            Litaniae("Cristo, escúchanos.", " Cristo, escúchanos."),
            Litaniae("Dios, Padre celestial,", "ten piedad de nosotros."),
            Litaniae("Dios, Hijo, Redentor del mundo, ", "ten piedad de nosotros."),
            Litaniae("Dios, Espíritu Santo, ", "ten piedad de nosotros."),
            Litaniae("Santísima Trinidad, un solo Dios,", "ten piedad de nosotros."),
            Litaniae("Santa María,", "ruega por nosotros."),
            Litaniae("Santa Madre de Dios,", "ruega por nosotros."),
            Litaniae("Santa Virgen de las Vírgenes,", "ruega por nosotros."),
            Litaniae("Madre de Cristo,", "ruega por nosotros."),
            Litaniae("Madre de la Iglesia,", "ruega por nosotros."),
            Litaniae("Madre de la divina gracia,", "ruega por nosotros."),
            Litaniae("Madre purísima,", "ruega por nosotros."),
            Litaniae("Madre castísima,", "ruega por nosotros."),
            Litaniae("Madre siempre virgen,", "ruega por nosotros."),
            Litaniae("Madre inmaculada,", "ruega por nosotros."),
            Litaniae("Madre amable,", "ruega por nosotros."),
            Litaniae("Madre admirable,", "ruega por nosotros."),
            Litaniae("Madre del buen consejo,", "ruega por nosotros."),
            Litaniae("Madre del Creador,", "ruega por nosotros."),
            Litaniae("Madre del Salvador,", "ruega por nosotros."),
            Litaniae("Madre de misericordia,", "ruega por nosotros."),
            Litaniae("Virgen prudentísima,", "ruega por nosotros."),
            Litaniae("Virgen digna de veneración,", "ruega por nosotros."),
            Litaniae("Virgen digna de alabanza,", "ruega por nosotros."),
            Litaniae("Virgen poderosa,", "ruega por nosotros."),
            Litaniae("Virgen clemente,", "ruega por nosotros."),
            Litaniae("Virgen fiel,", "ruega por nosotros."),
            Litaniae("Espejo de justicia,", "ruega por nosotros."),
            Litaniae("Trono de la sabiduría,", "ruega por nosotros."),
            Litaniae("Causa de nuestra alegría,", "ruega por nosotros."),
            Litaniae("Vaso espiritual,", "ruega por nosotros."),
            Litaniae("Vaso digno de honor,", "ruega por nosotros."),
            Litaniae("Vaso de insigne devoción,", "ruega por nosotros."),
            Litaniae("Rosa mística,", "ruega por nosotros."),
            Litaniae("Torre de David,", "ruega por nosotros."),
            Litaniae("Torre de marfil,", "ruega por nosotros."),
            Litaniae("Casa de oro,", "ruega por nosotros."),
            Litaniae("Arca de la Alianza,", "ruega por nosotros."),
            Litaniae("Puerta del cielo,", "ruega por nosotros."),
            Litaniae("Estrella de la mañana,", "ruega por nosotros."),
            Litaniae("Salud de los enfermos,", "ruega por nosotros."),
            Litaniae("Refugio de los pecadores,", "ruega por nosotros."),
            Litaniae("Consoladora de los afligidos,", "ruega por nosotros."),
            Litaniae("Auxilio de los cristianos,", "ruega por nosotros."),
            Litaniae("Reina de los Ángeles,", "ruega por nosotros."),
            Litaniae("Reina de los Patriarcas,", "ruega por nosotros."),
            Litaniae("Reina de los Profetas,", "ruega por nosotros."),
            Litaniae("Reina de los Apóstoles,", "ruega por nosotros."),
            Litaniae("Reina de los Mártires,", "ruega por nosotros."),
            Litaniae("Reina de los Confesores,", "ruega por nosotros."),
            Litaniae("Reina de las Vírgenes,", "ruega por nosotros."),
            Litaniae("Reina de todos los Santos,", "ruega por nosotros."),
            Litaniae("Reina concebida sin pecado original,", "ruega por nosotros."),
            Litaniae("Reina asunta a los Cielos,", "ruega por nosotros."),
            Litaniae("Reina del Santísimo Rosario,", "ruega por nosotros."),
            Litaniae("Reina de la familia,", "ruega por nosotros."),
            Litaniae("Reina de la paz.", "ruega por nosotros."),
            Litaniae("Cordero de Dios, que quitas el pecado del mundo,", "perdónanos, Señor."),
            Litaniae("Cordero de Dios, que quitas el pecado del mundo,", "escúchanos, Señor."),
            Litaniae(
                "Cordero de Dios, que quitas el pecado del mundo,",
                "ten misericordia de nosotros."
            ),
            Litaniae(
                "Ruega por nosotros, Santa Madre de Dios.",
                "Para que seamos dignos de las promesas de Cristo."
            )
        )
        val oratio: LiturgiaOratio = LiturgiaOratio(
            listOf(
                "Te rogamos nos concedas, Señor Dios nuestro,",
                "gozar de continua salud de alma y cuerpo,",
                "y por la gloriosa intercesión",
                "de la bienaventurada siempre Virgen María,",
                "vernos libres de las tristezas de la vida presente",
                "y disfrutar de las alegrías eternas.",
                "Por Cristo nuestro Señor.",
                "Amén."
            )
        )

        val salve: LiturgiaOratio = LiturgiaOratio(
            listOf(
                "Dios te Salve,",
                "Reina y Madre de misericordia,",
                "vida, dulzura y esperanza nuestra, Dios te salve.",
                "A ti llamamos los desterrados hijos de Eva;",
                "a ti suspiramos, gimiendo y llorando,",
                "en este valle de lágrimas.",
                "Ea, pues, Señora, abogada nuestra,",
                "vuelve a nosotros esos tus ojos misericordiosos,",
                "y, después de este destierro, muéstranos a Jesús,",
                "fruto bendito de tu vientre.",
                "¡Oh clementísima, oh piadosa, oh dulce Virgen María"
            )
        )
    }


    fun getForView(): StringBuilder {
        val sb = StringBuilder()
        mysteriorum.forEach {
            sb.append("${it.ordo} ")
            sb.append(it.mysterium.mysterium)
            sb.append("\n\n\n\n")
        }
        return sb
    }


}

data class Litaniae(
    var textus: String,
    var responsum: String
) {
    fun getList(): List<String> {
        return listOf(textus, responsum)
    }
}

data class LiturgiaOratio(
    var textus: List<String>
)