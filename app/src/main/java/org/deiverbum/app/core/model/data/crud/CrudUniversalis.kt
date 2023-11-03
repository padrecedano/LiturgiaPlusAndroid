package org.deiverbum.app.core.model.data.crud

import org.deiverbum.app.core.model.data.Universalis

class CrudUniversalis {
    var c: MutableList<Universalis>? = null
    var u: MutableList<Universalis>? = null
    var d: MutableList<Universalis>? = null
    fun c(t: Universalis) {
        if (c == null) {
            c = ArrayList()
        }
        c!!.add(t)
    }

    fun addUpdate(t: Universalis) {
        if (u == null) {
            u = ArrayList()
        }
        u!!.add(t)
    }

    fun addCreate(t: Universalis) {
        if (c == null) {
            c = ArrayList()
        }
        c!!.add(t)
    }

    fun addDelete(t: Universalis) {
        if (d == null) {
            d = ArrayList()
        }
        d!!.add(t)
    }
}