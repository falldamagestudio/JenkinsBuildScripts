// copy-pasted from
// https://github.com/benignbala/groovy-iniparser/blob/master/src/main/groovy/org/benignbala/iniparser/IniParser.groovy
@NonCPS
Map call(String text) {
    def sections = []
    def config = [:]
    String section = ''

    text.eachLine { line ->
        line.find(/\[(.*)\]/) { full, sec ->
            sections.add(sec)
            inSection = true;
            section = sec
            config[section] = [:]
        }
        line.find(/\s*(\S+)\s*=\s*(.*)?(?:#|$)/) { full, key, value ->
            if (config.get(section).containsKey(key)) {
                def v = config.get(section).get(key)
                if (v in Collection) {
                    def oldVal = config.get(section).get(key)
                    oldVal.add(value)
                    config[key] = oldVal
                    config.get(section).put(key, oldVal)
                } else {
                    def values = [];
                    values.add(v)
                    values.add(value)
                    config.get(section).put(key, values)
                }
            } else {
                config.get(section).put(key, value)
            }
            // println "Match: $full, Key: $key, Value: $value"
            // config.get(section).put(key, value)
        }
    }

    return config;
}
