package ut.com.dell.bitbucket.sanity.merge;

import org.junit.Test;
import com.dell.bitbucket.sanity.merge.api.MyPluginComponent;
import com.dell.bitbucket.sanity.merge.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}