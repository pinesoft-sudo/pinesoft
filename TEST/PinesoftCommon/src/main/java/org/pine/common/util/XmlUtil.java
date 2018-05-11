package org.pine.common.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class XmlUtil {
	private static final XmlMapper xmlMapper = new XmlMapper();

	private XmlUtil() {

	}

	public static <T> T toNormalObject(String xmlContent, Class<T> clazz) {
		return xmlToObject(xmlContent, clazz);
	}

	public static <T> List<T> toListObject(String xmlContent, Class<T> clazz) {
		return xmlToList(xmlContent, clazz);
	}

	public static <T> T toNormalObject(byte[] bytes, Class<T> clazz) {
		return xmlToObject(bytes, clazz);
	}

	public static <T> List<T> toListObjectValidate(String xmlContent, Class<T> clazz) throws Exception {
		if (ValidateType(xmlContent, clazz)) {
			return xmlToList(xmlContent, clazz);
		} else {
			throw new Exception("转换类型结构不一致，请选择正确的文本格式。");
		}
	}

	public static <T> T toNormalObjectValidate(String xmlContent, Class<T> clazz) throws Exception {
		if (ValidateType(xmlContent, clazz)) {
			return xmlToObject(xmlContent, clazz);
		} else {
			throw new Exception("转换类型结构不一致，请选择正确的文本格式。");
		}
	}

	private static <T> boolean ValidateType(String xmlContent, Class<T> clazz) throws DocumentException {
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

	public synchronized static byte[] toNormalXml(Object object) {
		return objectToXml(Include.ALWAYS, object);
	}

	private static <T> T xmlToObject(byte[] bytes, Class<T> clazz) {
		try {
			return xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(bytes, clazz);
		} catch (Exception e) {
			LogUtil.info("XmlToObject failed:", e);
		}
		return null;
	}

	private static <T> T xmlToObject(String xmlContent, Class<T> clazz) {
		try {
			return xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(xmlContent, clazz);
		} catch (Exception e) {
			LogUtil.info("XmlToObject failed:", e);
		}
		return null;
	}

	private static <T> List<T> xmlToList(String xmlContent, Class<T> clazz) {
		JavaType javaType = xmlMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
		try {
			return xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(xmlContent, javaType);
		} catch (Exception e) {
			LogUtil.info("XmlToObject failed:", e);
		}
		return null;
	}

	@SuppressWarnings("unused")
	private static <T> String xmlToJson(String xmlContent, Class<T> clazz) {

		return null;
	}

	private static <T> byte[] objectToXml(Include include, T object) {
		try {
			return xmlMapper.setSerializationInclusion(include).writerWithDefaultPrettyPrinter()
					.writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			LogUtil.info("ObjToXml failed:", e);
		}
		return null;
	}

	public static String objectToXml(Object object) {
		try {
			return xmlMapper.writeValueAsString(object);

		} catch (JsonProcessingException e) {
			LogUtil.info("ObjToXml failed:", e);
		}
		return null;
	}

	public static <T> String objectToXml(Object object, Class<T> clazz) {
		try {
			String xmlStr = xmlMapper.writeValueAsString(object);
			xmlStr = xmlStr.replaceAll("ArrayList", clazz.getSimpleName() + "List");
			return xmlStr;
		} catch (JsonProcessingException e) {
			LogUtil.info("ObjToXml failed:", e);
		}
		return null;
	}
}
