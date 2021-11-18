package com.meehoo.biz.common.io;

//import fr.opensagres.xdocreport.document.IXDocReport;
//import fr.opensagres.xdocreport.document.images.FileImageProvider;
//import fr.opensagres.xdocreport.document.images.IImageProvider;
//import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
//import fr.opensagres.xdocreport.template.TemplateEngineKind;
//import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class XDocUtil {

////    private final static String templatePath = "fireInvestigation-server-refactor\\util\\src\\main\\java\\com\\meehoo\\biz\\bos\\util\\template\\simpleFilreReport.docx";
////    private final static String nullPicPath = "fireInvestigation-server-refactor\\sysmanage-module\\src\\main\\webapp\\resources\\img\\error.png";
//    private final static String templatePath = "C:\\doctemplate\\simpleFilreReport.docx";
//    private final static String nullPicPath = "C:\\doctemplate\\error.png";
//
//    /**
//     * 生成doc文档
//     * @param context
//     * @param contractName
//     * @throws Exception
//     */
//    public static void generateDoc(Map<String,Object> context, String contractName, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        //获取模板路径
////        String clazzPath=request.getRealPath("/");
////        String path = clazzPath.substring(0, clazzPath.indexOf("fireInvestigation-server-refactor"));
//        //加载模板
////        InputStream in = new FileInputStream(path+templatePath);
//        InputStream in = new FileInputStream(templatePath);
//
//        IXDocReport report = XDocReportRegistry
//                .getRegistry()
//                .loadReport(in, TemplateEngineKind.Freemarker);
//
//        //填充数据
//        FieldsMetadata metadata = report.createFieldsMetadata();
//        metadata.addFieldAsImage("litigantSign");
//        metadata.addFieldAsImage("investigatorSign");
//        report.setFieldsMetadata(metadata);
//        //图片设置
//        IImageProvider litigantSign = new FileImageProvider(
//                new File(BaseUtil.stringNotNull((String) context.get("litigantSignUrl"))?(String) context.get("litigantSignUrl"):nullPicPath),
//                true);
//        litigantSign.setSize(200f, 100f);
//        IImageProvider investigatorSign = new FileImageProvider(
//                new File(BaseUtil.stringNotNull((String) context.get("litigantSignUrl"))?(String) context.get("investigatorSignUrl"):nullPicPath),
//                true);
//        investigatorSign.setSize(150f, 100f);
//
//        context.put( "litigantSign", litigantSign );
//        context.put( "investigatorSign", investigatorSign );
//
//        //设置响应头
//        setResponseHead(request,response,contractName);
//
//        OutputStream out = response.getOutputStream();
//        report.process(context, out);
//    }
//
//    /**
//     * 根据请求源设置响应头
//     * @param request
//     * @param response
//     * @param contractName
//     */
//    private static void setResponseHead(HttpServletRequest request,HttpServletResponse response,String contractName){
//        response.setContentType("application/x-msdownload");
//        try {
//            response.addHeader("Content-Disposition",
//                    "attachment; filename="+ (
//                            RequestHeadUtil.isMobileDevice(request)?
//                                    //手机
//                                    new String(contractName.getBytes("utf-8"),"ISO8859-1"):
//                                    //电脑
//                                    new String(contractName.getBytes("gb2312"), "ISO8859-1")
//                    ));
//        } catch (UnsupportedEncodingException e1) {
//            e1.printStackTrace();
//        }
//    }

}
