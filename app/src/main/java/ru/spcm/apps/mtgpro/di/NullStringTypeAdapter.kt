package ru.spcm.apps.mtgpro.di

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class NullStringTypeAdapter : TypeAdapter<String>() {

    override fun write(out: JsonWriter?, value: String?) {
        out?.value(value)
    }

    override fun read(source: JsonReader?): String {
        if (source == null) return ""
        if (source.peek() == JsonToken.NULL) {
            source.nextNull()
            return ""
        }
        return source.nextString()
    }
}
