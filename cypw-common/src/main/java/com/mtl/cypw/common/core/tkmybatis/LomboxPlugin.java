//package org.mybatis.generator.plugins;
////package com.mtl.cypw.common.core.tkmybatis;
//
//import org.mybatis.generator.api.IntrospectedColumn;
//import org.mybatis.generator.api.IntrospectedTable;
//import org.mybatis.generator.api.Plugin;
//import org.mybatis.generator.api.PluginAdapter;
//import org.mybatis.generator.api.dom.java.*;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author tang.
// * @date 2019/11/18.
// */
//public class LomboxPlugin extends PluginAdapter {
//
//    @Override
//    public boolean validate(List<String> list) {
//        return true;
//    }
//
//    @Override
//    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
//        //添加domain的import
//        topLevelClass.addImportedType("lombok.Data");
////        topLevelClass.addImportedType("lombok.Builder");
////        topLevelClass.addImportedType("lombok.NoArgsConstructor");
////        topLevelClass.addImportedType("lombok.AllArgsConstructor");
//        topLevelClass.addImportedType("javax.persistence.*");
//
//        //添加domain的注解
//        topLevelClass.addAnnotation("@Data");
//        String tableName = introspectedTable.getFullyQualifiedTable().getIntrospectedTableName();
//        topLevelClass.addAnnotation("@Table(name = \"" + tableName + "\")");
////        topLevelClass.addAnnotation("@Builder");
////        topLevelClass.addAnnotation("@NoArgsConstructor");
////        topLevelClass.addAnnotation("@AllArgsConstructor");
//
//        //添加domain的注释
//        topLevelClass.addJavaDocLine("/**");
//        topLevelClass.addJavaDocLine(" * @author tang. ");
//        topLevelClass.addJavaDocLine(" * @date " + date2Str(new Date()));
//        topLevelClass.addJavaDocLine("*/");
//
//        return true;
//    }
//
//    @Override
//    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
//        //Mapper文件的注释
//        interfaze.addJavaDocLine("/**");
//        interfaze.addJavaDocLine(" * @author tang.");
//        interfaze.addJavaDocLine(" * @date " + date2Str(new Date()));
//        interfaze.addJavaDocLine("*/");
//
//        interfaze.addImportedType(new FullyQualifiedJavaType("com.mtl.cypw.common.core.tkmybatis.BaseMapper"));
//        interfaze.addSuperInterface(new FullyQualifiedJavaType("com.mtl.cypw.common.core.tkmybatis.BaseMapper<" + introspectedTable.getBaseRecordType() + ">"));
//        interfaze.getMethods().clear();
//        return true;
//    }
//
//    @Override
//    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
//        // 不生成getter
//        return false;
//    }
//
//    @Override
//    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
//        // 不生成setter
//        return false;
//    }
//
//    @Override
//    public boolean modelFieldGenerated(Field field,
//                                       TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
//                                       IntrospectedTable introspectedTable,
//                                       Plugin.ModelClassType modelClassType) {
//        if (introspectedColumn.isAutoIncrement() || introspectedColumn.getActualColumnName().equals("id")) {
//            field.addAnnotation("@Id");
//            field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
//        }
//        field.addAnnotation("@Column(name = \"" + introspectedColumn.getActualColumnName() + "\")");
//        return true;
//    }
//
//    private String date2Str(Date date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 ahh:mm:ss");
//        return sdf.format(date);
//    }
//}
