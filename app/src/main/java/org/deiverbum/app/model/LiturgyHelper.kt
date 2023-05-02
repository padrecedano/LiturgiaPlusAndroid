package org.deiverbum.app.model

import java.util.*

/**
 *
 *
 * Reúne aquellos elementos que son comúnes a las diversas horas del Breviary.
 * Las clases de las diferentes horas extienden de ésta,
 * y cada una tendrá aquellos elementos que le sean propios.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class LiturgyHelper {
    //var myMap: Map<Int, String>? = null
    companion object {

        fun getMap(theKey: Int): String? {
            val myMap:HashMap<Int,String> = HashMap<Int,String>() //define empty hashmap

            myMap[0] = "mixto"
            myMap[1] = "oficio"
            myMap[2] = "laudes"
            myMap[3] = "tercia"
            myMap[4] = "sexta"
            myMap[5] = "nona"
            myMap[6] = "visperas"
            myMap[7] = "completas"
            return myMap[theKey]
        }
    }
    }
