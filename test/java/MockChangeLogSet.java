import hudson.model.User;
import hudson.scm.ChangeLogSet;
import java.util.Collection;
import java.util.Iterator;

public class MockChangeLogSet extends ChangeLogSet
{
    private Collection<MockChangeLogSetEntry> entries;

    public MockChangeLogSet(Collection<MockChangeLogSetEntry> entries) {
        super(null, null);
        this.entries = entries;
    }

    @Override public boolean isEmptySet() {
        return entries.isEmpty();
    }

    public Iterator<MockChangeLogSetEntry> iterator() {
        return entries.iterator();
    }
}
