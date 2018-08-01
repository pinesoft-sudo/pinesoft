package org.pine.common.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@SuppressWarnings("unused")
public class XmlUtil {
	private static final XmlMapper xmlMapper = new XmlMapper();

	private static <T> boolean ValidateType(String xmlContent, Class<T> clazz) throws Exception {
		Document document = DocumentHelper.parseText(xmlContent);
		Element root = document.getRootElement();
		String rootName = root.getName().toLowerCase().trim();
		String className = (clazz.getSimpleName() + "List").toLowerCase().trim();
		if (rootName.equals(className)) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized static byte[] toNormalXml(Object object) throws Exception {
		return objectToXml(Include.ALWAYS, object);
	}

	private static <T> T xmlToObject(byte[] bytes, Class<T> clazz) throws Exception {
		try {
			return xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(bytes, clazz);
		} catch (Exception e) {
			throw new Exception("XmlToObject failed:"+ e);
		}
	}

	private static <T> T xmlToObject(String xmlContent, Class<T> clazz) throws Exception {
		try {
			return xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(xmlContent, clazz);
		} catch (Exception e) {
			throw new Exception("XmlToObject failed:"+ e);
		}
	}

	private static <T> List<T> xmlToList(String xmlContent, Class<T> clazz) throws Exception {
		JavaType javaType = xmlMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
		try {
			return xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(xmlContent, javaType);
		} catch (Exception e) {
			throw new Exception("xmlToList failed:"+ e);
		}
	}

	private static <T> String xmlToJson(String xmlContent, Class<T> clazz) throws Exception {
		try {
			T t = xmlToObject(xmlContent, clazz);
			return JSON.toJSONString(t);
		} catch (Exception e) {
			throw new Exception("xmlToJson failed:"+ e);
		}
	}

	private static <T> byte[] objectToXml(Include include, T object) throws Exception {
		try {
			return xmlMapper.setSerializationInclusion(include).writerWithDefaultPrettyPrinter()
					.writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			throw new Exception("objectToXml failed:"+ e);
		}
	}

	public static String objectToXml(Object object) throws Exception {
		try {
			return xmlMapper.writeValueAsString(object);

		} catch (JsonProcessingException e) {
			throw new Exception("objectToXml failed:"+ e);
		}
	}

	public static <T> String objectToXml(Object object, Class<T> clazz) throws Exception {
		try {
			String xmlStr = xmlMapper.writeValueAsString(object);
			xmlStr = xmlStr.replaceAll("ArrayList", clazz.getSimpleName() + "List");
			return xmlStr;
		} catch (JsonProcessingException e) {
			throw new Exception("objectToXml failed:"+ e);
		}
	}

}
