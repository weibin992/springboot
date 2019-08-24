package cn.weibin.springboot.mapper.plugin;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 添加Example分页支持
 * @author weibin
 */
public class MybatisExamplePagingPlugin extends PluginAdapter {

	private String offsetName = "limitOffset";
	private String rowsName = "limitRows";
	
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		addPagingLimit(topLevelClass, introspectedTable);
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement isNotNullElement = new XmlElement("if");
		isNotNullElement.addAttribute(new Attribute("test", offsetName + " != null and " + rowsName + " != null"));
		isNotNullElement.addElement(new TextElement("limit ${" + offsetName + "}, ${" + rowsName + "}"));
		element.addElement(isNotNullElement);
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);

	}

	private void addPagingLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		CommentGenerator commentGenerator = context.getCommentGenerator();
		// add field
		Field offsetFiled = new Field();
		offsetFiled.setVisibility(JavaVisibility.PRIVATE);
		offsetFiled.setType(PrimitiveTypeWrapper.getIntegerInstance());
		offsetFiled.setName(offsetName);
		topLevelClass.addField(offsetFiled);
		commentGenerator.addFieldComment(offsetFiled, introspectedTable);
		// add get method
		Method methodGetOffset = new Method();
		methodGetOffset.setVisibility(JavaVisibility.PUBLIC);
		methodGetOffset.setName("get" + Character.toUpperCase(offsetName.charAt(0)) + offsetName.substring(1));
		methodGetOffset.addBodyLine("return this." + offsetName + ";");
		methodGetOffset.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());
		topLevelClass.addMethod(methodGetOffset);
		commentGenerator.addGeneralMethodComment(methodGetOffset, introspectedTable);
		
		Field rowsFiled = new Field();
		rowsFiled.setVisibility(JavaVisibility.PRIVATE);
		rowsFiled.setType(PrimitiveTypeWrapper.getIntegerInstance());
		rowsFiled.setName(rowsName);
		topLevelClass.addField(rowsFiled);
		commentGenerator.addFieldComment(rowsFiled, introspectedTable);
		Method methodGetRows = new Method();
		methodGetRows.setVisibility(JavaVisibility.PUBLIC);
		methodGetRows.setName("get" + Character.toUpperCase(rowsName.charAt(0)) + rowsName.substring(1));
		methodGetRows.addBodyLine("return this." + rowsName + ";");
		methodGetRows.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());
		topLevelClass.addMethod(methodGetRows);
		commentGenerator.addGeneralMethodComment(methodGetRows, introspectedTable);
		
		Method methodSetLimit = new Method();
		methodSetLimit.setVisibility(JavaVisibility.PUBLIC);
		methodSetLimit.setName("setPagingLimit");
		methodSetLimit.addParameter(new Parameter(PrimitiveTypeWrapper.getIntegerInstance(), offsetName));
		methodSetLimit.addParameter(new Parameter(PrimitiveTypeWrapper.getIntegerInstance(), rowsName));
		methodSetLimit.addBodyLine("this." + offsetName + " = " + offsetName + ";");
		methodSetLimit.addBodyLine("this." + rowsName + " = " + rowsName + ";");
		commentGenerator.addGeneralMethodComment(methodSetLimit, introspectedTable);
		topLevelClass.addMethod(methodSetLimit);
		commentGenerator.addGeneralMethodComment(methodSetLimit, introspectedTable);
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
}