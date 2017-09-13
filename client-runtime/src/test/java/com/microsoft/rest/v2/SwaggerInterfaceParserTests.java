package com.microsoft.rest.v2;

import com.microsoft.rest.v2.annotations.ExpectedResponses;
import com.microsoft.rest.v2.annotations.Host;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SwaggerInterfaceParserTests {

    interface TestInterface1 {
        String testMethod1();
    }

    @Host("https://management.azure.com")
    interface TestInterface2 {
    }

    @Test
    public void hostWithNoHostAnnotation() {
        final SwaggerInterfaceParser interfaceParser = new SwaggerInterfaceParser(TestInterface1.class);
        assertEquals(null, interfaceParser.host());
    }

    @Test
    public void hostWithHostAnnotation() {
        final SwaggerInterfaceParser interfaceParser = new SwaggerInterfaceParser(TestInterface2.class);
        assertEquals("https://management.azure.com", interfaceParser.host());
    }

    interface TestInterface3 {
        @ExpectedResponses({200})
        void testMethod3();
    }

    @Test
    public void methodParser() {
        final SwaggerInterfaceParser interfaceParser = new SwaggerInterfaceParser(TestInterface3.class);
        final Method testMethod3 = TestInterface3.class.getDeclaredMethods()[0];
        assertEquals("testMethod3", testMethod3.getName());

        final SwaggerMethodParser methodParser = interfaceParser.methodParser(testMethod3);
        assertNotNull(methodParser);
        assertEquals("com.microsoft.rest.v2.SwaggerInterfaceParserTests.TestInterface3.testMethod3", methodParser.fullyQualifiedMethodName());

        final SwaggerMethodParser methodDetails2 = interfaceParser.methodParser(testMethod3);
        assertSame(methodParser, methodDetails2);
    }
}