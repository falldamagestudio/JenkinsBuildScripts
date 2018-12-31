import hudson.model.User;
import hudson.scm.ChangeLogSet;
import java.util.Collection;

public class MockChangeLogSetEntry extends ChangeLogSet.Entry {

    private Collection<String> affectedPaths;
    private String user;
    private String commitId;
    private String msg;

    public MockChangeLogSetEntry(Collection<String> affectedPaths, String user, String commitId, String msg)
    {
        this.affectedPaths = affectedPaths;
        this.user = user;
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

    // Get Plastic-specific username
    public String getUser() {
        return user;
    }
}
