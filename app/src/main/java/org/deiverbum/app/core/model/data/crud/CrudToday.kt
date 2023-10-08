package org.deiverbum.app.core.model.data.crud

import org.deiverbum.app.core.model.data.Today

class CrudToday {
    var c: MutableList<Today>? = null
    var u: MutableList<Today>? = null
    var d: MutableList<Today>? = null
    fun c(t: Today) {
        if (c == null) {
            c = ArrayList()
        }
        c!!.add(t)
    }

    fun addUpdate(t: Today) {
        if (u == null) {
            u = ArrayList()
        }
        u!!.add(t)
    }

    fun addCreate(t: Today) {
        if (c == null) {
            c = ArrayList()
        }
        c!!.add(t)
    }

    fun addDelete(t: Today) {
        if (d == null) {
            d = ArrayList()
        }
        d!!.add(t)
    }
}