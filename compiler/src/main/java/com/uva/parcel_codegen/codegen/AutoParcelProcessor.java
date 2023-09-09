package com.uva.parcel_codegen.codegen;

import static java.lang.reflect.Modifier.PRIVATE;

import static javax.lang.model.element.Modifier.FINAL;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.uva.parcel_codegen.AutoParcel;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import jdk.internal.joptsimple.internal.Strings;

@SupportedAnnotationTypes("com.uva.parcel_codegen.AutoParcel")
public final class AutoParcelProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Collection<? extends Element> annotatedElements =
                roundEnvironment.getElementsAnnotatedWith(AutoParcel.class);
        List<TypeElement> types = ElementFilter.typesIn(annotatedElements);
        for (TypeElement type : types) {
            processType(type);
        }
        return true;
    }

    private void processType(TypeElement type) {
        String className = generatedSubclassName(type, 0);
        String source = generateClass(type, className);
        writeSourceFile(className, source, type);
    }

    private String generatedSubclassName(TypeElement type, int depth) {
        return generatedClassName(type, Strings.repeat('$', depth) + "AutoParcel_");
    }

    private String generateClass(TypeElement type, String className) {

        List<VariableElement> nonPrivateFields = getParcelableFieldsOrError(type);

//        ImmutableList<Property> properties = buildProperties(nonPrivateFields);

        // get the type adapters
//        ImmutableMap<TypeMirror, FieldSpec> typeAdapters = getTypeAdapters(properties);

        // get the parcel version
        //noinspection ConstantConditions
//        int version = type.getAnnotation(AutoParcel.class).version();

        // Generate the AutoParcel_??? class
        String pkg = TypeUtil.packageNameOf(type);
        TypeName classTypeName = ClassName.get(pkg, className);
        System.out.println("sdjksldjls " + classTypeName);
        TypeSpec.Builder subClass = TypeSpec.classBuilder(className)
                // Add the version
                .addField(TypeName.INT, "version", Modifier.PRIVATE)
                .addField(TypeName.INT, "TEXT_CODEGEN", Modifier.PUBLIC)
                // Class must be always final
                .addModifiers(FINAL);
                // extends from original abstract class
//                .superclass(ClassName.get(pkg, classToExtend))
                // Add the DEFAULT constructor
//                .addMethod(generateConstructor(properties))
                // Add the private constructor
//                .addMethod(generateConstructorFromParcel(processingEnv, properties, typeAdapters))
                // overrides describeContents()
//                .addMethod(generateDescribeContents())
                // static final CREATOR
//                .addField(generateCreator(processingEnv, properties, classTypeName, typeAdapters))
                // overrides writeToParcel()
//                .addMethod(generateWriteToParcel(version, processingEnv, properties, typeAdapters)); // generate writeToParcel()

//        if (!ancestoIsParcelable(processingEnv, type)) {
//            // Implement android.os.Parcelable if the ancestor does not do it.
//            subClass.addSuperinterface(ClassName.get("android.os", "Parcelable"));
//        }
//
//        if (!typeAdapters.isEmpty()) {
//            typeAdapters.values().forEach(subClass::addField);
//        }

        JavaFile javaFile = JavaFile.builder(pkg, subClass.build()).build();
        return javaFile.toString();
    }
    private List<VariableElement> getParcelableFieldsOrError(TypeElement type) {
        List<VariableElement> allFields = ElementFilter.fieldsIn(type.getEnclosedElements());
        List<VariableElement> nonPrivateFields = new ArrayList<>();

        for (VariableElement field : allFields) {
            if (!field.getModifiers().contains(PRIVATE)) {
                nonPrivateFields.add(field);
            } else {
                // return error, PRIVATE fields are not allowed
//                mErrorReporter.abortWithError("getFieldsError error, PRIVATE fields not allowed", type);
            }
        }

        return nonPrivateFields;
    }

    private String generatedClassName(TypeElement type, String prefix) {
        String name = type.getSimpleName().toString();
        while (type.getEnclosingElement() instanceof TypeElement) {
            type = (TypeElement) type.getEnclosingElement();
            name = type.getSimpleName() + "_" + name;
        }
        String pkg = TypeUtil.packageNameOf(type);
        String dot = pkg.isEmpty() ? "" : ".";
        return pkg + dot + prefix + name;
    }

    private void writeSourceFile(
            String className,
            String text,
            TypeElement originatingType) {
        try {
            JavaFileObject sourceFile =
                    processingEnv.getFiler().
                            createSourceFile(className, originatingType);
            try (Writer writer = sourceFile.openWriter()) {
                writer.write(text);
            }
        } catch (IOException e) {// silent}
        }
    }
}
