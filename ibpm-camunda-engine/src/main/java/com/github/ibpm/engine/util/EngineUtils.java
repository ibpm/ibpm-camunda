package com.github.ibpm.engine.util;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EngineUtils {

    private static final String PARAM_EXPRESSION = "^\\$\\{(.*)\\}$";

    public static String toBpmnXmlContent(String id, String name, String xmlContent) {
        BpmnModelInstance bpmnModelInstance = toBpmnModelInstance(xmlContent);
        Collection<Process> modelElements = bpmnModelInstance.getModelElementsByType(Process.class);
        for (Process process : modelElements) {
            process.builder().id(id).name(name);
            break;
        }
        return Bpmn.convertToString(bpmnModelInstance);
    }

    public static BpmnModelInstance toBpmnModelInstance(String xmlContent) {
        return Bpmn.readModelFromStream(new ByteArrayInputStream(xmlContent.getBytes(IoUtils.ENCODING_CHARSET)));
    }

    public static synchronized boolean isExpression(String expression) {
        Matcher matcher = Pattern.compile(PARAM_EXPRESSION).matcher(expression);
        return matcher.find();
    }

    public static synchronized String extractFromExpression(String expression) {
        Matcher matcher = Pattern.compile(PARAM_EXPRESSION).matcher(expression);
        return matcher.find() ? matcher.group(1) : expression;
    }
}
