import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {
    private static final String FILE_NAME = "Hello.xlass";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = this.getClass().getResource(FILE_NAME).getPath();
        try {
            byte[] content = getContent(path);

            byte[] classContent = new byte[content.length];
            for (int i = 0; i < content.length; i++) {
                classContent[i] = (byte)(255-content[i]);
            }

            return defineClass(name, classContent, 0, classContent.length);
        } catch (IOException e) {
            log("Exception happens when find class " + name + ":" + e.getMessage());
            return null;
        }
    }

    private byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            throw new IOException("File too big");
        }

        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
            && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset != buffer.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        fi.close();
        return buffer;
    }

    private static void log(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        String className = "Hello";
        String methodName = "hello";

        MyClassLoader classLoader = new MyClassLoader();

        try {
            Class clazz = classLoader.findClass(className);
            if (clazz == null) {
                throw new ClassNotFoundException("Class Hello not found");
            }

            for (Method method : clazz.getMethods()) {
                log("Method found: " + method.getName());
            }

            Object instance = clazz.newInstance();
            Method method = clazz.getMethod(methodName);
            method.invoke(instance);
        } catch (Exception e) {
            log("Exception happens: " + e.getMessage());
        }
    }
}
