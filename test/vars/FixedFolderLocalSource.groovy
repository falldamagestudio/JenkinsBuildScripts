//package com.lesfurets.jenkins.unit.global.lib
import com.lesfurets.jenkins.unit.global.lib.SourceRetriever
import groovy.transform.CompileStatic
import groovy.transform.Immutable

@Immutable
@CompileStatic
class FixedFolderLocalSource implements SourceRetriever {

    String sourceURL

    @Override
    List<URL> retrieve(String repository, String branch, String targetPath) {
        def sourceDir = new File(sourceURL).toPath().toFile()
        if (sourceDir.exists()) {
            return [sourceDir.toURI().toURL()]
        }
        throw new IllegalStateException("Directory $sourceDir.path does not exists")
    }

    static FixedFolderLocalSource fixedFolderLocalSource(String source) {
        new FixedFolderLocalSource(source)
    }

    @Override
    String toString() {
        return "FixedFolderLocalSource{" +
                        "sourceURL='" + sourceURL + '\'' +
                        '}'
    }
}