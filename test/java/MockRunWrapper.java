import hudson.model.Run;
import java.io.Serializable;
import javax.annotation.CheckForNull;

public class MockRunWrapper implements Serializable {

    public @CheckForNull Run<?,?> getRawBuild() {
        throw new java.lang.UnsupportedOperationException();
    }

    public @CheckForNull String getResult() {
        throw new java.lang.UnsupportedOperationException();
        // Result result = build().getResult();
        // return result != null ? result.toString() : null;
    }
}