package org.deiverbum.app.core.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun ScreenC(
    onTopicClick: (String) -> Unit,
    //onComposing: (AppBarState) -> Unit,
    //navController: NavController
) {
    /*
        LaunchedEffect(key1 = true) {
            onComposing(
                AppBarState(
                    title = "My Screen B",
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null
                            )
                        }
                    }
                )
            )
        }
    */

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Por anticipado, Dios había destinado a Juan Bautista, a que viene para proclamar la alegría de los hombres y la alegría de los cielos. De su boca, la gente entendió las palabras admirables que anunciaban la presencia de nuestro Redentor, el Cordero de Dios (Jn 1,29). Mientras que sus padres, habían perdido toda esperanza de obtener una descendencia, el ángel, el mensajero de un gran misterio, lo envió para servir de precursor al Señor, incluso antes de nacer (Lc 1,41)…\n" +
                    "\n" +
                    "Llenó de alegría eterna el seno de su madre, cuando lo llevaba en su interior… En efecto, en el Evangelio, leemos estas palabras que Isabel le dice a María: «Cuando oí tu saludo, el niño se estremeció de alegría en mi vientre. ¿De dónde a mí, que la madre de mi Señor me visite? «(Lc 1,43-44)… Mientras que, en su vejez, se afligía por no haber dado un niño a su marido, de repente, dio a luz a un hijo, que era también el mensajero de la salvación eterna para el mundo entero. Y un mensajero tal, que antes de su nacimiento, ejerció el privilegio de su futuro ministerio, cuando difundió su espíritu profético por las palabras de su madre.\n" +
                    "\n" +
                    "Luego, por la fuerza del nombre, que el ángel le había dado por anticipado, abrió la boca de su padre cerrada por la incredulidad (Lc 1,13.20). Cuando Zacarías se quedó mudo, no fue para siempre, sino para recobrar divinamente el uso de la palabra y confirmar por un signo venido del cielo, que su hijo era un profeta. El Evangelio dice sobre Juan: » Este hombre no era la Luz, pero estaba allí para dar testimonio y que todos crean por él » (Jn 1,7-8). Ciertamente, no era la Luz, pero permanecía por entero en la luz, el que mereció dar testimonio de la Luz verdadera.\n" +
                    "\n" +
                    "Sermón: Es necesario que él crezca y yo disminuya\n" +
                    "\n" +
                    "Sermón 99 ; PL 57, 535\n" +
                    "\n" +
                    "Con razón, Juan Bautista puede decir del Señor nuestro Salvador: «hace falta que él crezca y que yo disminuya» (Jn 3,30). Esta afirmación se realiza en este mismo momento: al nacimiento de Cristo, los días aumentan; al de Juan, disminuyen… Cuando aparece el Salvador, el día, con toda evidencia, aumenta; retrocede en el momento en el que nace el último profeta, porque está escrito: «la Ley y los profetas reinaron hasta Juan» (Lc 16,16).\n" +
                    "\n" +
                    "Era inevitable que la observancia de la Ley se ensombrezca, en el momento en el que la gracia del Evangelio empieza a resplandecer; a la profecía del Antiguo Testamento le sucede la gloria del Nuevo… El evangelista dice a propósito del Señor Jesucristo: «Él era la luz verdadera que alumbra a todo hombre» (Jn 1,9)… Es en el momento en el que la oscuridad de la noche cubría casi el día entero, cuando la súbita llegada del Señor, lo convirtió todo en claridad. Si su nacimiento hizo desaparecer las tinieblas de los pecados de la humanidad, su llegada dio fin a la noche y trajo a los hombres la luz y el día…\n" +
                    "\n" +
                    "El Señor dice que Juan es una lámpara: «Él es la lámpara que arde y que alumbra» (Jn 5,35). La luz de la lámpara palidece cuando brillan los rayos del sol; la llama baja, vencida por el resplandor de una luz más radiante. ¿Qué hombre razonable se sirve de una lámpara a pleno sol?… ¿Quién vendría todavía para recibir el bautismo de penitencia de Juan (Mc 1,4), cuando el bautismo de Jesús aporta la salvación?\n" +
                    "\n" +
                    "San Gregorio Taumaturgo, obispo\n" +
                    "\n" +
                    "Homilía sobre la santa Teofanía\n" +
                    "\n" +
                    "Homilía (atribuida), 4, PG 10, 1181\n" +
                    "\n" +
                    "«Empezó a hablar bendiciendo a Dios»\n" +
                    "\n" +
                    "[Juan Bautista decía:] en tu presencia, Señor, no me puedo callar, porque «yo soy la voz, y la voz del que clama en el desierto: preparad el camino del Señor. Soy yo el que necesita que tú me bautices, ¿y tú vienes a mí?» (Mt 3,3.14).\n" +
                    "\n" +
                    "Cuando yo nací borré la esterilidad de la que me dio a luz; y cuando era un recién nacido, llevé el remedio para el mutismo de mi padre recibiendo de ti la gracia de este milagro. Pero tú, nacido de la Virgen María de la manera que tú has querido y que solo tú conoces, no has borrado su virginidad y la has protegido añadiéndole el título de madre; ni su virginidad ha impedido tu nacimiento, ni tu nacimiento ha ensuciado su virginidad. Estas dos realidades incompatibles, el dar a luz y la virginidad, se unieron en una armonía única lo cual sólo está al alcance del Creador de la naturaleza.\n" +
                    "\n" +
                    "Yo que soy un hombre, sólo participo de la gracia divina; pero tú eres a la vez Dios y hombre, porque por naturaleza eres el amigo de los hombres (cf Sab 1,6).\n" +
                    "\n" +
                    "Orígenes, presbítero\n" +
                    "\n" +
                    "Homilías sobre San Lucas\n" +
                    "\n" +
                    "Homilía 4, 4-6\n" +
                    "\n" +
                    "«Estaba yo en las entrañas maternas y el Señor me llamó»\n" +
                    "\n" +
                    "El nacimiento de Juan Bautista está lleno de milagros. Un arcángel anunció la venida de nuestro Señor y Salvador Jesucristo; igualmente un arcángel anunció el nacimiento de Juan (Lc, 1,13) y dijo: «Se llenará de Espíritu Santo ya en el vientre materno.» El pueblo judío no supo ver que nuestro Señor hiciera «milagros y prodigios» y curara sus enfermedades, pero Juan exulta de gozo cuando todavía está en el seno materno. No lo pudieron impedir y, al llegar la madre de Jesús, el niño intentó salir ya del seno de Isabel: «En cuanto tu saludo llegó a mis oídos, dijo Isabel, la criatura saltó de alegría en mi vientre» (Lc 1,44). Todavía en el seno de su madre Juan recibió ya el Espíritu Santo…\n" +
                    "\n" +
                    "La Escritura dice seguidamente que «convertirá muchos israelitas al Señor, su Dios» (Lc 1,16). Juan convirtió a «muchos»; el Señor, no a muchos, sino a todos. Esta es su obra: llevar todos los hombres a Dios Padre…\n" +
                    "\n" +
                    "Yo pienso que el misterio de Juan se realiza todavía hoy en el mundo. Cualquiera que está destinado a creer en Jesucristo, es preciso que antes el espíritu y el poder de Juan vengan a su alma a «preparar para el Señor un pueblo bien dispuesto» (Lc 1,17) y, «allanar los caminos, enderezar los senderos» (Lc 3,5) de las asperezas del corazón. No es solamente en aquel tiempo que «los caminos fueron allanados y enderezados los senderos» sino que todavía hoy el espíritu y la fuerza de Juan preceden la venida del Señor y Salvador. ¡Oh grandeza del misterio del Señor y de su designio sobre el mundo!\n" +
                    "\n" +
                    "San Agustín, obispo\n" +
                    "\n" +
                    "Sermón: La voz que clama en el desierto\n" +
                    "\n" +
                    "Sermón 293,1-3: PL 38,1327-1328 (Liturgia de las Horas)\n" +
                    "\n" +
                    "La Iglesia celebra el nacimiento de Juan como algo sagrado, y él es el único de los santos cuyo nacimiento se festeja; celebramos el nacimiento de Juan y el de Cristo. Ello no deja de tener su significado, y, si nuestras explicaciones no alcanzaran a estar a la altura de misterio tan elevado, no hemos de perdonar esfuerzo para profundizarlo y sacar provecho de él.\n" +
                    "\n" +
                    "Juan nace de una anciana estéril; Cristo, de una jovencita virgen. El futuro padre de Juan no cree el anuncio de su nacimiento y se queda mudo; la Virgen cree el del nacimiento de Cristo y lo concibe por la fe. Esto es, en resumen, lo que intentaremos penetrar y analizar; y, si el poco tiempo y las pocas facultades de que disponemos no nos permiten llegar hasta las profundidades de este misterio tan grande, mejor os adoctrinará aquel que habla en vuestro interior, aun en ausencia nuestra, aquel que es el objeto de vuestros piadosos pensamientos, aquel que habéis recibido en vuestro corazón y del cual habéis sido hechos templo.\n" +
                    "\n" +
                    "Juan viene a ser como la línea divisoria entre los dos Testamentos, el antiguo y el nuevo. Así lo atestigua el mismo Señor, cuando dice: La ley y los profetas llegaron hasta Juan. Por tanto, él es como la personificación de lo antiguo y el anuncio de lo nuevo. Porque personifica lo antiguo, nace de padres ancianos; porque personifica lo nuevo, es declarado profeta en el seno de su madre. Aún no ha nacido y, al venir la Virgen María, salta de gozo en las entrañas de su madre. Con ello queda ya señalada su misión, aun antes de nacer; queda demostrado de quién es precursor, antes de que él lo vea. Estas cosas pertenecen al orden de lo divino y sobrepasan la capacidad de la humana pequeñez. Finalmente, nace, se le impone el nombre, queda expedita la lengua de su padre. Estos acontecimientos hay que entenderlos con toda la fuerza de su significado.\n" +
                    "\n" +
                    "Zacarías calla y pierde el habla hasta que nace Juan, el precursor del Señor, y abre su boca. Este silencio de Zacarías significaba que, antes de la predicación de Cristo, el sentido de las profecías estaba en cierto modo latente, oculto, encerrado. Con el advenimiento de aquel a quien se referían estas profecías, todo se hace claro. El hecho de que en el nacimiento de Juan se abre la boca de Zacarías tiene el mismo significado que el rasgarse el velo al morir Cristo en la cruz. Si Juan se hubiera anunciado a sí mismo, la boca de Zacarías habría continuado muda. Si se desata su lengua es porque ha nacido aquel que es la voz; en efecto, cuando Juan cumplía ya su misión de anunciar al Señor, le dijeron: ¿Tú quién eres? Y él respondió: Yo soy la voz que grita en el desierto. Juan era la voz; pero el Señor era la Palabra que en el principio ya existía. Juan era una voz pasajera, Cristo la Palabra eterna desde el principio.\n" +
                    "\n" +
                    "Sermón para la Natividad de san Juan Bautista\n" +
                    "\n" +
                    "Sermón 289, 3º\n" +
                    "\n" +
                    "«Es necesario que Él crezca y yo disminuya» (Jn 3,30)\n" +
                    "\n" +
                    "El mayor de los hombres fue enviado para dar testimonio al que era más que un hombre. En efecto, cuando aquel que es «el mayor de entre los hijos de mujer» (Mt 11,11) dijo: «Yo no soy Cristo» (Jn 1,20) y se humilla ante Cristo, debemos entender que hay en Cristo más que un hombre… «de su plenitud todos hemos recibido» (Jn 1,16). ¿Qué es decir, «todos nosotros»? Es decir que los patriarcas, los profetas y los santos apóstoles, los que precedieron a la Encarnación o que han sido enviados después por el Verbo encarnado, «todos hemos recibido de su plenitud». Nosotros somos vasos, Él es la fuente. Por lo tanto…, Juan es un hombre, Cristo es Dios: es necesario que el hombre se humille, para que Dios sea exaltado.\n" +
                    "\n" +
                    "Para que el hombre aprenda a humillarse, Juan nació el día a partir del cual los días comienzan a disminuir; para mostrarnos que Dios debe ser exaltado, Jesucristo nació el día en que los días comienzan a crecer. Aquí hay una enseñanza profundamente misteriosa. Celebramos la natividad de Juan como la de Cristo, porque esta natividad está llena de misterio. ¿De qué misterio? Del misterio de nuestra grandeza. Disminuyamos nosotros mismos, para crecer en Dios; humillémonos en nuestra bajeza, para ser exaltados en su grandeza.\n" +
                    "\n" +
                    "San Efrén, diácono\n" +
                    "\n" +
                    "Himno\n" +
                    "\n" +
                    "Liturgia siríaca\n" +
                    "\n" +
                    "«Surgió un hombre enviado por Dios, que le llamaba Juan… vino para dar testimonio de la verdad»(Jn 1,6,7)\n" +
                    "\n" +
                    "Es a ti, Juan a quien reconocemos como al nuevo Moisés, porque tú has visto a Dios, no en símbolo, sino con toda claridad. Es a ti a quien miramos como a un nuevo Josué: tú no has pasado el Jordán desde una a otra orilla, pero con el agua del Jordán, tú has hecho pasar a los hombres de un mundo a otro… Tú eres el nuevo Samuel que no has ungido a David, pero has bautizado al Hijo de David. Tú eres el nuevo David, que no has sido perseguido por el mal rey Saúl, pero has sido muerto por Herodes. Tú eres el nuevo Elías, alimentado en el desierto no con pan y por un cuervo, sino de saltamontes y miel, por Dios Tú eres el nuevo Isaías que no has dicho: «Mirad, una virgen concebirá y dará a luz» (7,14), sino que has proclamado delante de todos: «Este es el Cordero de Dios que quita el pecado del mundo» (Jn1,29)\n" +
                    "\n" +
                    "¡Dichoso tú, Juan, elegido de Dios, tú, que has puesto la mano sobre tu Maestro, tú, que has cogido en tus manos la llama cuyo resplandor hace temblar a los ángeles! ¡Estrella de la mañana, has mostrado al mundo la Mañana verdadera; aurora gozosa, has manifestado el día de gloria; lámpara que brilla, has designado a la Luz sin igual! ¡Mensajero de la gran reconciliación con el Padre, el arcángel Gabriel ha sido enviado delante de ti para anunciarte a Zacarías, como un fruto fuera de tiempo… El más grande entre los hijos de los hombres (Mt 11,11) vienes delante del Emmanuel, de aquél que sobrepasa a toda criatura; primogénito de Elizabeth, tú precedes al Primogénito de toda la creación!\n" +
                    "\n" +
                    "San Juan Pablo II, papa\n" +
                    "\n" +
                    "Ángelus (24-06-1984)\n" +
                    "\n" +
                    "1. «El Señor me llamó en las entrañas de mi madre y pronunció mi nombre» (Is 49, 1).\n" +
                    "\n" +
                    "Hoy la Iglesia celebra la natividad de San Juan Bautista.\n" +
                    "\n" +
                    "Esta natividad es, al mismo tiempo, vocación. Dios llamó por su nombre a Juan en el seno de su madre Isabel, mujer de Zacarías.\n" +
                    "\n" +
                    "Él debía presentarse en el camino de la revelación divina como el último de los profetas de la Antigua Alianza y, a la vez, como el Precursor de Jesucristo, en quien se cumple la Nueva y Eterna Alianza de Dios con la humanidad.\n" +
                    "\n" +
                    "El día de la circuncisión de Juan, su padre Zacarías, en el himno de acción de gracias a Dios, pronunció las siguientes palabras:\n" +
                    "\n" +
                    "«Y a ti, niño, te llamarán profeta del Altísimo, / porque irás delante del Señor / a preparar sus caminos» (Lc 1, 76).\n" +
                    "\n" +
                    "La Iglesia, desde los tiempos más antiguos, ha rodeado de particular veneración a San Juan Bautista, su vocación y su misión especial.\n" +
                    "\n" +
                    "En esta vocación y misión la Iglesia vuelve a encontrarse a sí misma como heredera de la Antigua Alianza y, al mismo tiempo, se siente llamada a dar testimonio de Jesucristo, Cordero de Dios, que quita el pecado del mundo (cf. Jn 1, 29).\n" +
                    "\n" +
                    "«No ha nacido de mujer uno más grande que Juan el Bautista» (Mt 11, 11).\n" +
                    "\n" +
                    "2. Hoy queremos recordar también, con veneración y amor, al Papa Pablo VI, que recibió, el día de su bautismo, el nombre de Juan Bautista. Al encomendar su a alma al Eterno Padre, damos gracias por todo el ministerio que pudo realizar en la sede de Pedro durante los años 1963-1978, preparando el «camino del Señor» para la Iglesia en el mundo contemporáneo.\n" +
                    "\n" +
                    "3. Hoy, en Italia y en varias naciones, se celebra la solemnidad del «Santísimo Cuerpo y Sangre de Cristo», y los fieles están invitados a renovar y a vivir la fe en la presencia real, a recibir a Cristo Eucaristía, según su misma invitación: «El que come mi carne y bebe mi sangre habita en mi y yo en él» (Jn 6, 56).\n" +
                    "\n" +
                    "Manifestaciones particulares del culto eucarístico, como es sabido son los Congresos Eucarísticos, especialmente los Congresos Eucarísticos internacionales, que han de ser considerados como una etapa, en que la Iglesia universal está invitada a profundizar en algún aspecto del misterio del Cristo Eucarístico y a rendirle el homenaje de la pública y solemne adoración, con el vínculo de la caridad y de la unión.\n" +
                    "\n" +
                    "Por tanto, quisiera llamar ya desde ahora la atención de toda la Iglesia sobre el próximo Congreso Eucarístico internacional, que se celebrará el año próximo en Nairobi (Kenya) del 11 al 18 de agosto, y que tendrá como tema «La Eucaristía y la familia cristiana».\n" +
                    "\n" +
                    "Este gran acontecimiento eclesial debe interesar no sólo a Kenya y África, sino a toda la Iglesia: a cada diócesis, a cada parroquia, comunidades religiosas, asociaciones católicas, movimientos laicales; todos deben sentirse llamados a participar en él espiritualmente con una catequesis más intensa sobre la Eucaristía, como misterio de Cristo viviente y operante en la Iglesia y en las familias.\n" +
                    "\n" +
                    "Homilía (24-06-2001)\n" +
                    "\n" +
                    "SANTA MISA EN RITO LATINO EN EL AEROPUERTO DE CHAYKA\n" +
                    "\n" +
                    "1. «El Señor desde el seno materno me llamó; desde las entrañas de mi madre pronunció mi nombre» (Is 49, 1).\n" +
                    "\n" +
                    "Celebramos hoy la natividad de san Juan Bautista. Las palabras del profeta Isaías se aplican muy bien a esta gran figura bíblica que está entre el Antiguo y el Nuevo Testamento. En el gran ejército de profetas y justos de Israel, Juan «el Bautista» fue puesto por la Providencia inmediatamente antes del Mesías, para preparar delante de él el camino con la predicación y con el testimonio de su vida.\n" +
                    "\n" +
                    "Entre todos los santos y santas, Juan es el único cuya natividad celebra la liturgia. En la primera lectura hemos escuchado que el Señor llamó a su siervo «desde el seno materno». Esta afirmación se refiere, en plenitud, a Cristo, pero, por reflejo, se puede aplicar también a su precursor. Ambos nacieron gracias a una intervención especial de Dios:  el primero nace de la Virgen; el segundo, de una mujer anciana y estéril. Desde el seno materno Juan anuncia a Aquel que revelará al mundo la iniciativa de amor de Dios.\n" +
                    "\n" +
                    "2. «Desde el seno de mi madre me llamaste» (Salmo responsorial). Podemos hacer nuestra, hoy, esta exclamación del salmista. Dios nos conoció y amó antes aún que nuestros ojos pudieran contemplar las maravillas de la creación. Todo hombre al nacer recibe un nombre humano. Pero antes aún, posee un nombre divino:  el nombre con el cual Dios Padre lo conoce y lo ama desde siempre y para siempre. Eso vale para todos, sin excluir a nadie. Ningún hombre es anónimo para Dios. Todos tienen igual valor a sus ojos:  todos son diversos, pero iguales; todos están llamados a ser hijos en el Hijo.\n" +
                    "\n" +
                    "«Juan es su nombre» (Lc 1, 63). A sus parientes sorprendidos Zacarías confirma el nombre de su hijo escribiéndolo en una tablilla. Dios mismo, a través de su ángel, había indicado ese nombre, que en hebreo significa «Dios es favorable». Dios es favorable al hombre:  quiere su vida, su salvación. Dios es favorable a su pueblo:  quiere convertirlo en una bendición para todas las naciones de la tierra. Dios es favorable a la humanidad:  guía su camino hacia la tierra donde reinan la paz y la justicia. Todo esto entraña ese nombre:  Juan.\n" +
                    "\n" +
                    "Amadísimos hermanos y hermanas, Juan Bautista era el mensajero, el precursor:  fue enviado para preparar el camino a Cristo. ¿Qué nos dice la figura de san Juan Bautista precisamente aquí, en Kiev, al inicio de esta peregrinación a vuestra tierra?…\n" +
                    "\n" +
                    "5. Pueblo de Dios que crees, esperas y amas en tierra ucraniana, gusta de nuevo con alegría el don del Evangelio, que recibiste hace más de mil años. Contempla, en este día, a san Juan Bautista, modelo perenne de fidelidad a Dios y a su ley. Él preparó a Cristo el camino con el testimonio de su palabra y de su vida. Imítalo con dócil y confiada generosidad.\n" +
                    "\n" +
                    "San Juan Bautista es ante todo modelo de fe. Siguiendo las huellas del gran profeta Elías, para escuchar mejor la palabra del único Señor de su vida, lo deja todo y se retira al desierto, desde donde dirigirá  la invitación a preparar el camino  del  Señor (cf. Mt 3, 3 y paralelos).\n" +
                    "\n" +
                    "Es modelo de humildad, porque a cuantos lo consideran no sólo un profeta, sino incluso el Mesías, les responde:  «Yo no soy quien pensáis, sino que viene detrás de mí uno a quien no merezco desatarle las sandalias» (Hch 13, 25).\n" +
                    "\n" +
                    "Es modelo de coherencia y valentía para defender la verdad, por la que está dispuesto a pagar personalmente hasta la cárcel y la muerte.\n" +
                    "\n" +
                    "Tierra de Ucrania, impregnada de la sangre de los mártires, ¡gracias por el ejemplo de fidelidad al Evangelio que has dado a los cristianos de todo el mundo! Muchos de tus hijos e hijas han caminado con plena fidelidad a Cristo; muchos de ellos han llevado su coherencia hasta el sacrificio supremo. Su testimonio debe servir de ejemplo y acicate para los cristianos del tercer milenio.\n" +
                    "6. En la escuela de Cristo, siguiendo las huellas de san Juan Bautista, de los santos y de los mártires de esta tierra, tened también vosotros, amadísimos hermanos y hermanas, la valentía de poner siempre en primer lugar los valores espirituales.\n" +
                    "\n" +
                    "Queridos obispos, sacerdotes, religiosos y religiosas, que habéis acompañado fielmente a este pueblo a costa de sacrificios personales de todo tipo y lo habéis sostenido en los tiempos oscuros del terror comunista, os doy las gracias y os exhorto a seguir siendo testigos celosos de Cristo y buenos pastores de su grey en la amada Ucrania.\n" +
                    "\n" +
                    "Vosotros, queridos jóvenes, sed fuertes y libres. No os dejéis engañar por espejismos de felicidad barata. Seguid el camino de Cristo:  ciertamente, Cristo es exigente, pero puede haceros gustar el sentido pleno de la vida y la paz del corazón.\n" +
                    "\n" +
                    "Vosotros, queridos padres, preparad el camino del Señor ante vuestros hijos. Educadlos con amor y dadles un buen ejemplo de coherencia con los principios que enseñáis. Y vosotros, los que tenéis responsabilidades educativas y sociales, sentíos comprometidos a promover siempre el desarrollo integral de la persona humana, cultivando en los jóvenes un profundo sentido de justicia y solidaridad con los más débiles.\n" +
                    "\n" +
                    "Todos y cada uno sed «luz de las naciones» (Is 49, 6).\n" +
                    "\n" +
                    "7. Tú, ciudad de Kiev, sé «luz de Ucrania«. De ti salieron los evangelizadores que, en el decurso de los siglos, fueron los «Juan Bautista» de los pueblos que habitaban estas tierras. ¡Cuántos de entre ellos sufrieron, como Juan, por dar testimonio de la verdad y con su sangre se han convertido en semilla de nuevos cristianos. Ojalá que no falten en las nuevas generaciones hombres y mujeres del temple de estos gloriosos antepasados.\n" +
                    "\n" +
                    "Virgen santísima, protectora de Ucrania, tú desde siempre has guiado el camino del pueblo cristiano. Sigue velando sobre tus hijos. Ayúdales a no olvidar nunca el «nombre», la identidad espiritual que han recibido en el bautismo. Ayúdales a gozar siempre de la gracia inestimable de ser discípulos de Cristo (cf. Jn 3, 29). Sé tú la guía de cada uno. Tú, Madre de Dios y Madre nuestra, María.\n" +
                    "\n" +
                    "Benedicto XVI, papa\n" +
                    "\n" +
                    "Ángelus (24-06-2006)\n" +
                    "\n" +
                    "[…] Ayer la liturgia nos invitó a celebrar la Natividad de san Juan Bautista, el único santo cuyo nacimiento se conmemora, porque marcó el inicio del cumplimiento de las promesas divinas:  Juan es el «profeta», identificado con Elías, que estaba destinado a preceder inmediatamente al Mesías a fin de preparar al pueblo de Israel para su venida (cf. Mt 11, 14; 17, 10-13). Su fiesta nos recuerda que toda nuestra vida está siempre «en relación con» Cristo y se realiza acogiéndolo a él, Palabra, Luz y Esposo, de quien somos voces, lámparas y amigos (cf. Jn 1, 1. 23; 1, 7-8; 3, 29). «Es preciso que él crezca y que yo disminuya» (Jn 3, 30):  estas palabras del Bautista constituyen un programa para todo cristiano.\n" +
                    "\n" +
                    "Dejar que el «yo» de Cristo ocupe el lugar de nuestro «yo» fue de modo ejemplar el anhelo de los apóstoles san Pedro y san Pablo, a quienes la Iglesia venerará  con  solemnidad  el próximo 29 de junio. San Pablo escribió de sí mismo:  «Ya no vivo yo, sino que es Cristo quien vive en mí» (Ga 2, 20). Antes que ellos y que cualquier otro santo vivió esta realidad María santísima, que guardó en su corazón las palabras de su Hijo Jesús. Ayer contemplamos su Corazón inmaculado, Corazón de Madre, que sigue velando con tierna solicitud sobre todos nosotros. Que su intercesión nos obtenga ser siempre fieles a la vocación cristiana."
        )
        Button(onClick = {
            //navController.navigate("screen_a")
        }) {
            Text(text = "Navigate to Screen A")
        }
    }
}