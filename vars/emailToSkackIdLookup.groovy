// toSerializable function works  with non-serializable objects. Cannot pause this method in jenkins. Adding NonCPS
@NonCPS
Object toSerializable(Object obj) {
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

Object readJson(String filePath) {
    String fileContent = readFile(filePath);
    return new groovy.json.JsonSlurper().parseText(fileContent);
}

Object readJsonSerializable(String filePath) {
    Object jsonContent = readJson(filePath);
    return toSerializable(jsonContent);
}

Map call() {
    String workspacePath = this.env.WORKSPACE;
    // we need to get object serializable in order to be able to feed it into Java library
    return readJsonSerializable("$workspacePath/JenkinsBuildScripts/emailToSlack.json");
}
