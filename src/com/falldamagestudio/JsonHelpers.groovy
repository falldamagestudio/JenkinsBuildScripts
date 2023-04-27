package com.falldamagestudio

class JsonHelpers {

    static Object readJsonSerializable(Object script, String filePath) {
        Object jsonContent = readJson(script, filePath);
        return toSerializable(jsonContent);
    }

    // toSerializable function works  with non-serializable objects. Cannot pause this method in jenkins. Adding NonCPS
    @NonCPS
    private static Object toSerializable(Object obj) {
        /* groovylint-disable-next-line Instanceof */
        if (obj instanceof groovy.json.internal.LazyValueMap || obj instanceof groovy.json.internal.LazyMap) {
            Map copy = [:];
            for (pair in (obj as Map)) {
                copy.put(pair.key, toSerializable(pair.value));
            }
            return copy;
        }

        /* groovylint-disable-next-line Instanceof */
        if (obj instanceof groovy.json.internal.ValueList) {
            List copy = [];
            for (item in (obj as List)) {
                copy.add(toSerializable(item));
            }
            return copy;
        }
        return obj;
    }

    private static Object readJson(Object script, String filePath) {
        String fileContent = script.readFile(filePath);
        return new groovy.json.JsonSlurper().parseText(fileContent);
    }

}
