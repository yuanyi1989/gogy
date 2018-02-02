package com.github.gogy.maven.layout;

import org.springframework.boot.loader.tools.Layout;
import org.springframework.boot.loader.tools.LayoutFactory;
import org.springframework.boot.loader.tools.Layouts;
import org.springframework.boot.loader.tools.LibraryScope;

import java.io.File;

/**
 * @author yuanyi
 * @date 2018/1/30
 */
public class GogyLayoutFactory implements LayoutFactory {

    @Override
    public Layout getLayout(File file) {

        System.out.println(file.getName());
        return new Layouts.Expanded();
    }

    public class GogyJar extends Layouts.Jar {

        @Override
        public String getLibraryDestination(String libraryName, LibraryScope scope) {
            System.out.println("gogy jar; library ===> "+libraryName);
            return "BOOT-INF/lib/";
        }
    }
}
