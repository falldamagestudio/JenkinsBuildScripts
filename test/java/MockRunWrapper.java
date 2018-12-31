import hudson.model.Run;
import hudson.scm.ChangeLogSet;
import java.io.Serializable;
import java.util.List;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class MockRunWrapper implements Serializable {

    @CheckForNull public Run<?,?> getRawBuild() {
        throw new java.lang.UnsupportedOperationException();
    }

    @CheckForNull public String getResult() {
        throw new java.lang.UnsupportedOperationException();
        // Result result = build().getResult();
        // return result != null ? result.toString() : null;
    }

    @Nonnull public String getAbsoluteUrl() {
        throw new java.lang.UnsupportedOperationException();
    }

    public List<ChangeLogSet<? extends ChangeLogSet.Entry>> getChangeSets() throws Exception {
        throw new java.lang.UnsupportedOperationException();
    }
}