package edu.school21.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.ExecutableType;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"edu.school21.reflection.annotations.HtmlForm", "edu.school21.reflection.annotations.HtmlInput"})
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class HtmlProcessor extends AbstractProcessor {

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        List<List<String>> listsHtmlInput = new ArrayList<>();
        List<List<String>> listsHtmlForm = new ArrayList<>();
        for(TypeElement annotation : annotations){
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element annotatedElement: annotatedElements) {
                for(AnnotationMirror mirror : annotatedElement.getAnnotationMirrors()){
                    if("edu.school21.reflection.annotations.HtmlInput".equals(mirror.getAnnotationType().toString())){
                        listsHtmlInput.add(new ArrayList<>());
                        for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> element: mirror.getElementValues().entrySet()){
                            listsHtmlInput.get(listsHtmlInput.size()-1).add(element.getValue().toString());
                        }
                    }else if("edu.school21.reflection.annotations.HtmlForm".equals(mirror.getAnnotationType().toString())){
                        listsHtmlForm.add(new ArrayList<>());
                        for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> element: mirror.getElementValues().entrySet()){
                            listsHtmlForm.get(listsHtmlForm.size()-1).add(element.getValue().toString());
                        }
                    }
                }
            }
        }
        return writeForm(listsHtmlForm, listsHtmlInput);
    }

    public boolean writeForm(List<List<String>> listsHtmlForm, List<List<String>> listsHtmlInput){
        boolean result = false;
        if(listsHtmlForm.size() != 0 && listsHtmlInput.size() != 0){
            try {
                for (int i = 0; i < listsHtmlForm.size(); i++){
                    BufferedWriter writer = new BufferedWriter(new FileWriter(listsHtmlForm.get(i).get(0), true));
                    writer.write("<form action = " + listsHtmlForm.get(i).get(1) + " method = " + listsHtmlForm.get(i).get(2) + ">\n");
                    for(int j = 0; j < listsHtmlInput.get(i).size(); j++){
                        writer.write("\t<input type = " + listsHtmlInput.get(j).get(0) + " name = " + listsHtmlInput.get(j).get(1) + " placeholder = " + listsHtmlInput.get(j).get(2) + ">\n");
                    }
                    writer.write("</form>\n");
                    writer.close();
                }
                result = true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
