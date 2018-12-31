import hudson.model.User;
import hudson.scm.ChangeLogSet;
import java.util.Collection;

public class MockChangeLogSetEntry extends ChangeLogSet.Entry {

    private Collection<String> affectedPaths;
    private String commitId;
    private String msg;

    public MockChangeLogSetEntry(Collection<String> affectedPaths, String commitId, String msg)
    {
        this.affectedPaths = affectedPaths;
        this.commitId = commitId;
        this.msg = msg;
    }

    @Override public Collection<String> getAffectedPaths() {
        return affectedPaths;
    }

    @Override public User getAuthor() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override public String getCommitId() {
        return commitId;
    }

    @Override public String getMsg() {
        return msg;
    }

    @Override public long getTimestamp() {
        throw new java.lang.UnsupportedOperationException();
    }
}
