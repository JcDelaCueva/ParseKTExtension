package com.juancarlos.parsektextension

import android.text.TextUtils
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*
import kotlin.reflect.KProperty

public open class BaseUser : ParseUser() {

    override fun <T> getList(key: String): List<T>? {
        val list = super.getList<T>(key)

        if (list != null) {
            var i = 0
            while (i < list.size) {
                if (list[i] == null) {
                    list.removeAt(i)
                } else {
                    i++
                }
            }
        }
        return list
    }

    override fun getString(key: String): String {
        var value = ""
        try {
            value = super.getString(key)
            if (TextUtils.isEmpty(value)) {
                value = ""
            }
        } catch (ise: IllegalStateException) {

        }

        return value
    }

    override fun getNumber(key: String): Number {
        var value: Number? = null
        try {
            value = super.getNumber(key)
        } catch (ise: IllegalStateException) {

        }

        return if (value != null) {
            value
        } else {
            0
        }
    }

    fun getParseFileUrl(key: String): String {
        var url = ""
        try {
            val parseFile = getParseFile(key)
            if (parseFile != null) {
                url = parseFile.url
            }
        } catch (ise: IllegalStateException) {
            ise.printStackTrace()
        }

        return url
    }
}

public open class BaseParseObject : ParseObject() {

    override fun <T> getList(key: String): List<T>? {
        val list = super.getList<T>(key)

        if (list != null) {
            var i = 0
            while (i < list.size) {
                if (list[i] == null) {
                    list.removeAt(i)
                } else {
                    i++
                }
            }
        }
        return list
    }

    override fun getString(key: String): String {
        var value = ""
        try {
            value = super.getString(key)
            if (TextUtils.isEmpty(value)) {
                value = ""
            }
        } catch (ise: IllegalStateException) {

        }

        return value
    }

    override fun getNumber(key: String): Number {
        var value: Number? = null
        try {
            value = super.getNumber(key)
        } catch (ise: IllegalStateException) {

        }

        return if (value != null) {
            value
        } else {
            0
        }
    }

    fun getParseFileUrl(key: String): String {
        var url = ""
        try {
            val parseFile = getParseFile(key)
            if (parseFile != null) {
                url = parseFile.url
            }
        } catch (ise: IllegalStateException) {
            ise.printStackTrace()
        }

        return url
    }
}

public class PointerObjectDelegate<out T>(private val key: String) {

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(parseObj: ParseObject, meta: KProperty<*>): T? {
        return parseObj.getParseObject(key) as? T
    }

    operator fun setValue(parseObj: ParseObject, propertyMetadata: KProperty<*>,
                          a: Any?) {
        if (a != null) {
            parseObj.put(key, a)
        }
    }
}

public class ObjectBasicDelegate<out T>(private val key: String,
                                 private val type: Class<T>) {

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(bParseObject: BaseParseObject,
                          meta: KProperty<*>): T? {
        return when (type) {
            String::class.java -> bParseObject.getString(key) as T
            List::class.java -> bParseObject.getList<T>(key) as T
            Int::class.java -> bParseObject.getNumber(key) as T
            Boolean::class.java -> bParseObject.getBoolean(key) as T
            Date::class.java -> bParseObject.getDate(key) as T
            else -> bParseObject.get(key) as T
        }
    }

    operator fun setValue(parseObj: BaseParseObject,
                          propertyMetadata: KProperty<*>, a: Any?) {
        if (a != null) {
            parseObj.put(key, a)
        }
    }
}

public class ObjectFileUrlDelegate(private val key: String) {

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(bParseObject: BaseParseObject,
                          meta: KProperty<*>): String {
        return bParseObject.getParseFileUrl(key)
    }

    operator fun setValue(parseObj: BaseParseObject,
                          propertyMetadata: KProperty<*>, a: Any?) {
        if (a != null) {
            parseObj.put(key, a)
        }
    }
}

public class UserBasicDelegate<out T>(private val key: String,
                               private val type: Class<T>) {

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(bParseObject: BaseUser, meta: KProperty<*>): T {
        return when (type) {
            String::class.java -> bParseObject.getString(key) as T
            List::class.java -> bParseObject.getList<T>(key) as T
            Int::class.java -> bParseObject.getNumber(key) as T
            Boolean::class.java -> bParseObject.getBoolean(key) as T
            Date::class.java -> bParseObject.getDate(key) as T
            else -> bParseObject.get(key) as T
        }
    }

    operator fun setValue(parseObj: BaseUser, propertyMetadata: KProperty<*>,
                          a: Any?) {
        if (a != null) {
            parseObj.put(key, a)
        }
    }
}

public class UserObjectFileUrlDelegate(private val key: String) {

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(bParseObject: BaseUser, meta: KProperty<*>): String {
        return bParseObject.getParseFileUrl(key)
    }

    operator fun setValue(parseObj: BaseUser, propertyMetadata: KProperty<*>,
                          a: Any?) {
        if (a != null) {
            parseObj.put(key, a)
        }
    }
}

public fun ParseException.isSessionError(): Boolean {
    return code == ParseException.SESSION_MISSING
            || code == ParseException.INVALID_SESSION_TOKEN
            || code == ParseException.INVALID_LINKED_SESSION
}