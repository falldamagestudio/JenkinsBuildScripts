import hudson.model.Run;
import java.io.Serializable;
import javax.annotation.CheckForNull;

public class MockRunWrapper implements Serializable {

    public String result;

    public @CheckForNull Run<?,?> getRawBuild() {
        throw new java.lang.UnsupportedOperationException();
    }
}