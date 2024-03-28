package my.test.framework;

import com.google.auto.service.AutoService;
import my.test.framework.annotations.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes({
        "my.test.framework.annotations.BeforeSuite",
        "my.test.framework.annotations.AfterSuite",
        "my.test.framework.annotations.Before",
        "my.test.framework.annotations.After",
        "my.test.framework.annotations.Test"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class TestProcessor extends AbstractProcessor {
    private boolean allMethodsAreTest = false;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        print("Annotations: " + annotations.toString());
        if (!annotations.isEmpty()) {
            Set<? extends Element> annotatedHandlers = roundEnv.getElementsAnnotatedWith(Test.class);
            annotatedHandlers.forEach(element -> {
                if (element.getKind().isClass()) {
                    allMethodsAreTest = true;
                }
                Test annotation = element.getAnnotation(Test.class);
                if (annotation.priority() < 1 || annotation.priority() > 10) {
                    print("priority must be in range from 1 to 10");
                }
            });
            annotatedHandlers = roundEnv.getElementsAnnotatedWith(Before.class);
            annotatedHandlers.forEach(element -> {
                if (element.getAnnotation(Test.class) == null && !allMethodsAreTest) {
                    print("@Before must be used with @Test");
                }
            });
            annotatedHandlers = roundEnv.getElementsAnnotatedWith(After.class);
            annotatedHandlers.forEach(element -> {
                if (element.getAnnotation(Test.class) == null && !allMethodsAreTest) {
                    print("@After must be used with @Test");
                }
            });
            if (roundEnv.getElementsAnnotatedWith(BeforeSuite.class).size() > 1) {
                print("@BeforeSuite can't be used more than once");
            }
            if (roundEnv.getElementsAnnotatedWith(AfterSuite.class).size() > 1) {
                print("@AfterSuite can't be used more than once");
            }
        }
        return false;
    }

    private void print(String string) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, string);
    }
}
