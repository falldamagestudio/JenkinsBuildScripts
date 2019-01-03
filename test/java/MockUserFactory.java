import hudson.model.User;

import static org.mockito.Mockito.*;

public class MockUserFactory {

    public static User Create(String id) {
        User user = mock(User.class);
        when(user.getId()).thenReturn(id);
        return user;
    }
}
