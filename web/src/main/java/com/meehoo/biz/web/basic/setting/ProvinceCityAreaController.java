package com.meehoo.biz.web.basic.setting;

import com.meehoo.biz.core.basic.param.HttpResult;
import com.meehoo.biz.core.basic.service.setting.IProvinceCityAreaService;
import com.meehoo.biz.core.basic.vo.setting.ProvinceCityAreaSelectVO;
import com.meehoo.biz.core.basic.vo.setting.ProvinceSelectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-10-19.
 * 获取省市区信息
 */
@Api(tags = "省市区信息")
@Controller
@RequestMapping("/sysmanage/provinceCityArea")
public class ProvinceCityAreaController {

    private final IProvinceCityAreaService provinceCityAreaService;

    @Autowired
    public ProvinceCityAreaController(IProvinceCityAreaService provinceCityAreaService) {
        this.provinceCityAreaService = provinceCityAreaService;
    }

    /**
     * @api {get} /address/getProvinceCityArea 按省份名称查询
     * @apiName getProvinceCityArea
     * @apiGroup address
     * @apiVersion 0.1.0
     * @apiDescription 按省份名称查询
     *
     * @apiParam {string} provinceName 省份名称,精确查询
     *
     * @apiSuccess {String} flag 0成功 1失败 2未登录
     * @apiSuccess {String} msg 提示信息
     * @apiSuccess {Object[]} data 省市区数据
     *
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 200 OK
     * {
     *      "msg": "成功",
     *      "flag": "0",
     *      "data":[
     *          {
                    "label": "四川省",
                    "value": "p51c00a00",
                    "children":[
                        {
                        "label": "成都市",
                        "value": "p51c01a00",
                        "children":[
                            {"label": "锦江区", "value": "p51c01a04"},
                            {"label": "青羊区", "value": "p51c01a05"},
                            {"label": "金牛区", "value": "p51c01a06"},
                            {"label": "武侯区", "value": "p51c01a07"},
                            {"label": "成华区", "value": "p51c01a08"},
                            {"label": "龙泉驿区", "value": "p51c01a12"},
                            {"label": "青白江区", "value": "p51c01a13"},
                            {"label": "新都区", "value": "p51c01a14"},
                            {"label": "温江区", "value": "p51c01a15"},
                            {"label": "金堂县", "value": "p51c01a21"},
                            {"label": "双流县", "value": "p51c01a22"},
                            {"label": "郫县", "value": "p51c01a24"},
                            {"label": "大邑县", "value": "p51c01a29"},
                            {"label": "蒲江县", "value": "p51c01a31"},
                            {"label": "新津县", "value": "p51c01a32"},
                            {"label": "都江堰市", "value": "p51c01a81"},
                            {"label": "彭州市", "value": "p51c01a82"},
                            {"label": "邛崃市", "value": "p51c01a83"},
                            {"label": "崇州市", "value": "p51c01a84"}
                            ]
                        },
                        {"label": "自贡市", "value": "p51c03a00", "children":[{"label": "自流井区",…},
                        {"label": "攀枝花市", "value": "p51c04a00", "children":[{"label": "东区",…},
                        {"label": "泸州市", "value": "p51c05a00", "children":[{"label": "江阳区",…},
                        {"label": "德阳市", "value": "p51c06a00", "children":[{"label": "旌阳区",…},
                        {"label": "绵阳市", "value": "p51c07a00", "children":[{"label": "涪城区",…},
                        {"label": "广元市", "value": "p51c08a00", "children":[{"label": "利州区",…},
                        {"label": "遂宁市", "value": "p51c09a00", "children":[{"label": "船山区",…},
                        {"label": "内江市", "value": "p51c10a00", "children":[{"label": "市中区",…},
                        {"label": "乐山市", "value": "p51c11a00", "children":[{"label": "市中区",…},
                        {"label": "南充市", "value": "p51c13a00", "children":[{"label": "顺庆区",…},
                        {"label": "眉山市", "value": "p51c14a00", "children":[{"label": "东坡区",…},
                        {"label": "宜宾市", "value": "p51c15a00", "children":[{"label": "翠屏区",…},
                        {"label": "广安市", "value": "p51c16a00", "children":[{"label": "广安区",…},
                        {"label": "达州市", "value": "p51c17a00", "children":[{"label": "通川区",…},
                        {"label": "雅安市", "value": "p51c18a00", "children":[{"label": "雨城区",…},
                        {"label": "巴中市", "value": "p51c19a00", "children":[{"label": "巴州区",…},
                        {"label": "资阳市", "value": "p51c20a00", "children":[{"label": "雁江区",…},
                        {"label": "阿坝藏族羌族自治州", "value": "p51c32a00", "children":[{"label": "汶川县",…},
                        {"label": "甘孜藏族自治州", "value": "p51c33a00", "children":[{"label": "康定市",…},
                        {"label": "凉山彝族自治州", "value": "p51c34a00", "children":[{"label": "西昌市",…}
                    ]
                }
     *      ]
     *  }
     *
     */
    @ApiOperation("按省份名称查询")
    @GetMapping("getProvinceCityArea")
    @ResponseBody
    public HttpResult<List<ProvinceSelectVO>> getProvinceCityArea(String provinceName) {
        try {
            ProvinceCityAreaSelectVO provinceCityAreaSelectVO = provinceCityAreaService.getProvinceCityAreaSelectVOForApi(provinceName);
            List<ProvinceSelectVO> provinceSelectVOList = provinceCityAreaSelectVO.getProvinceSelectVOList();
            return HttpResult.success(provinceSelectVOList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @api {get} /address/getAllProvinceCityArea 获取所有省市区
     * @apiName getAllProvinceCityArea
     * @apiGroup address
     * @apiVersion 0.1.0
     * @apiDescription 获取所有省市区，提供给级联下拉菜单使用
     *
     *
     * @apiSuccess {String} flag 0成功 1失败 2未登录
     * @apiSuccess {String} msg 提示信息
     * @apiSuccess {Object[]} data 省市区数据
     *
     * @apiSuccessExample Success-Response:
     *  HTTP/1.1 200 OK
     * {
     *      "msg": "成功",
     *      "flag": "0",
     *      "data":[
     *          {
                    "label": "北京市",
                    "value": "p11c00a00",       //编号
                    "children":[
                        {
                        "label": "北京市",
                        "value": "p11c01a00",
                        "children":[
                            {"label": "东城区", "value": "p11c01a01"},
                            {"label": "西城区", "value": "p11c01a02"},
                            {"label": "朝阳区", "value": "p11c01a05"},
                            {"label": "丰台区", "value": "p11c01a06},
                            {"label": "石景山区", "value": "p11c01a07"},
                            {"label": "海淀区", "value": "p11c01a08"},
                            {"label": "门头沟区", "value": "p11c01a09"},
                            {"label": "房山区", "value": "p11c01a11"},
                            {"label": "通州区", "value": "p11c01a12"},
                            {"label": "顺义区", "value": "p11c01a13"},
                            {"label": "昌平区", "value": "p11c01a14"},
                            {"label": "大兴区", "value": "p11c01a15"},
                            {"label": "怀柔区", "value": "p11c01a16"},
                            {"label": "平谷区", "value": "p11c01a17"},
                            {"label": "密云县", "value": "p11c01a28"},
                            {"label": "延庆县", "value": "p11c01a29"}
                        ]
                        }
                    ]
                },
                {
                    "label": "重庆市",
                    "value": "p50c00a00",
                    "children":[
                        {
                        "label": "重庆市",
                        "value": "p50c01a00",
                        "children":[
                            {"label": "万州区", "value": "p50c01a01"},
                            {"label": "涪陵区", "value": "p50c01a02"},
                            {"label": "渝中区", "value": "p50c01a03"},
                            {"label": "大渡口区", "value": "p50c01a04"},
                            {"label": "江北区", "value": "p50c01a05"},
                            {"label": "沙坪坝区", "value": "p50c01a06"},
                            {"label": "九龙坡区", "value": "p50c01a07"},
                            {"label": "南岸区", "value": "p50c01a08"},
                            {"label": "北碚区", "value": "p50c01a09"},
                            {"label": "綦江区", "value": "p50c01a10"},
                            {"label": "大足区", "value": "p50c01a11"},
                            {"label": "渝北区", "value": "p50c01a12"},
                            {"label": "巴南区", "value": "p50c01a13"},
                            {"label": "黔江区", "value": "p50c01a14"},
                            {"label": "长寿区", "value": "p50c01a15"},
                            {"label": "江津区", "value": "p50c01a16"},
                            {"label": "合川区", "value": "p50c01a17"},
                            {"label": "永川区", "value": "p50c01a18"},
                            {"label": "南川区", "value": "p50c01a19"},
                            {"label": "璧山区", "value": "p50c01a20"},
                            {"label": "铜梁区", "value": "p50c01a51"},
                            {"label": "潼南区", "value": "p50c01a52"},
                            {"label": "荣昌区", "value": "p50c01a53"},
                            {"label": "开州区", "value": "p50c01a34"},
                            {"label": "梁平县", "value": "p50c01a28"},
                            {"label": "城口县", "value": "p50c01a29"},
                            {"label": "丰都县", "value": "p50c01a30"},
                            {"label": "垫江县", "value": "p50c01a31"},
                            {"label": "武隆县", "value": "p50c01a32"},
                            {"label": "忠县", "value": "p50c01a33"},
                            {"label": "云阳县", "value": "p50c01a35"},
                            {"label": "奉节县", "value": "p50c01a36"},
                            {"label": "巫山县", "value": "p50c01a37"},
                            {"label": "巫溪县", "value": "p50c01a38"},
                            {"label": "石柱土家族自治县", "value": "p50c01a40"},
                            {"label": "秀山土家族苗族自治县", "value": "p50c01a41"},
                            {"label": "酉阳土家族苗族自治县", "value": "p50c01a42"},
                            {"label": "彭水苗族土家族自治县", "value": "p50c01a43"}
                            ]
                        }
                    ]
                },
     *      ]
     *  }
     *
     */
    @ApiOperation("获取所有省市区")
    @GetMapping("getAllProvinceCityArea")
    @ResponseBody
    public HttpResult<List<ProvinceSelectVO>> getAllProvinceCityArea() {
        try {
            ProvinceCityAreaSelectVO provinceCityAreaSelectVO = provinceCityAreaService.getAllProvinceCityAreaSelectVO("");
            List<ProvinceSelectVO> provinceSelectVOList = provinceCityAreaSelectVO.getProvinceSelectVOList();
            List<ProvinceSelectVO> needFrontProvinceList = new ArrayList<>();
            List<ProvinceSelectVO> behindProvinceList = new ArrayList<>();
            for (ProvinceSelectVO provinceSelectVO : provinceSelectVOList) {
                switch (provinceSelectVO.getLabel()) {
                    case "重庆市":
                    case "四川省":
                    case "湖北省":
                    case "陕西省":
                    case "贵州省":
                    case "云南省":
                        needFrontProvinceList.add(provinceSelectVO);
                        break;
                    default:
                        behindProvinceList.add(provinceSelectVO);
                        break;
                }
            }
            needFrontProvinceList.addAll(behindProvinceList);
            return HttpResult.success(needFrontProvinceList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * @api {get} /address/getNextSingleMenu   获取省市区下拉列表中下一级
     * @apiGroup address
     * @apiName getNextSingleMenu
     * @apiVersion 0.1.1
     * @apiDescription 获取省市区下拉列表中下一级（只查一级）的列表内容（例如，查询河北省的所有地级市，查询石家庄市的所有区县）
     *
     * @apiParam {String} code 前台传来的查询编码，code为空表示查询所有的省，code为省编码表示查询这个省下面的所有地级市，code为市编码表示查询这个市下面的所有区县
     *
     * @apiSuccessExample Success-Response:
     *
     * {
    "msg": "操作成功",
    "flag": "0",
    "data":{
    "provinceSelectVOList":[
    {"label": "北京市", "value": "p11c00a00", "children": null},
    {"label": "天津市", "value": "p12c00a00", "children": null},
    {"label": "河北省", "value": "p13c00a00", "children": null},
    {"label": "山西省", "value": "p14c00a00", "children": null},
    {"label": "内蒙古自治区", "value": "p15c00a00", "children": null},
    {"label": "辽宁省", "value": "p21c00a00", "children": null},
    {"label": "吉林省", "value": "p22c00a00", "children": null},
    {"label": "黑龙江省", "value": "p23c00a00", "children": null},
    {"label": "上海市", "value": "p31c00a00", "children": null},
    {"label": "江苏省", "value": "p32c00a00", "children": null},
    {"label": "浙江省", "value": "p33c00a00", "children": null},
    {"label": "安徽省", "value": "p34c00a00", "children": null},
    {"label": "福建省", "value": "p35c00a00", "children": null},
    {"label": "江西省", "value": "p36c00a00", "children": null},
    {"label": "山东省", "value": "p37c00a00", "children": null},
    {"label": "河南省", "value": "p41c00a00", "children": null},
    {"label": "湖北省", "value": "p42c00a00", "children": null},
    {"label": "湖南省", "value": "p43c00a00", "children": null},
    {"label": "广东省", "value": "p44c00a00", "children": null},
    {"label": "广西壮族自治区", "value": "p45c00a00", "children": null},
    {"label": "海南省", "value": "p46c00a00", "children": null},
    {"label": "重庆市", "value": "p50c00a00", "children": null},
    {"label": "四川省", "value": "p51c00a00", "children": null},
    {"label": "贵州省", "value": "p52c00a00", "children": null},
    {"label": "云南省", "value": "p53c00a00", "children": null},
    {"label": "西藏自治区", "value": "p54c00a00", "children": null},
    {"label": "陕西省", "value": "p61c00a00", "children": null},
    {"label": "甘肃省", "value": "p62c00a00", "children": null},
    {"label": "青海省", "value": "p63c00a00", "children": null},
    {"label": "宁夏回族自治区", "value": "p64c00a00", "children": null},
    {"label": "新疆维吾尔自治区", "value": "p65c00a00", "children": null},
    {"label": "台湾省", "value": "p71c00a00", "children": null},
    {"label": "香港特别行政区", "value": "p81c00a00", "children": null},
    {"label": "澳门特别行政区", "value": "p82c00a00", "children": null}
    ]
    }
    }
     *
     * @apiSuccessExample Success-Response:
     *
     *{
    "msg": "操作成功",
    "flag": "0",
    "data":{
    "provinceSelectVOList":[
    {
    "label": "河北省",
    "value": "p13c00a00",
    "children":[
    {"label": "石家庄市", "value": "p13c01a00", "children": null},
    {"label": "唐山市", "value": "p13c02a00", "children": null},
    {"label": "秦皇岛市", "value": "p13c03a00", "children": null},
    {"label": "邯郸市", "value": "p13c04a00", "children": null},
    {"label": "邢台市", "value": "p13c05a00", "children": null},
    {"label": "保定市", "value": "p13c06a00", "children": null},
    {"label": "张家口市", "value": "p13c07a00", "children": null},
    {"label": "承德市", "value": "p13c08a00", "children": null},
    {"label": "沧州市", "value": "p13c09a00", "children": null},
    {"label": "廊坊市", "value": "p13c10a00", "children": null},
    {"label": "衡水市", "value": "p13c11a00", "children": null}
    ]
    }
    ]
    }
    }
     *
     * @apiSuccessExample Success-Response:
     *
     * {
    "msg": "操作成功",
    "flag": "0",
    "data":{
    "provinceSelectVOList":[
    {
    "label": "河北省",
    "value": "p13c00a00",
    "children":[
    {
    "label": "石家庄市",
    "value": "p13c01a00",
    "children":[
    {"label": "长安区", "value": "p13c01a02"},
    {"label": "桥西区", "value": "p13c01a04"},
    {"label": "新华区", "value": "p13c01a05"},
    {"label": "井陉矿区", "value": "p13c01a07"},
    {"label": "裕华区", "value": "p13c01a08"},
    {"label": "藁城区", "value": "p13c01a09"},
    {"label": "鹿泉区", "value": "p13c01a10"},
    {"label": "栾城区", "value": "p13c01a11"},
    {"label": "井陉县", "value": "p13c01a21"},
    {"label": "正定县", "value": "p13c01a23"},
    {"label": "行唐县", "value": "p13c01a25"},
    {"label": "灵寿县", "value": "p13c01a26"},
    {"label": "高邑县", "value": "p13c01a27"},
    {"label": "深泽县", "value": "p13c01a28"},
    {"label": "赞皇县", "value": "p13c01a29"},
    {"label": "无极县", "value": "p13c01a30"},
    {"label": "平山县", "value": "p13c01a31"},
    {"label": "元氏县", "value": "p13c01a32"},
    {"label": "赵县", "value": "p13c01a33"},
    {"label": "晋州市", "value": "p13c01a83"},
    {"label": "新乐市", "value": "p13c01a84"}
    ]
    }
    ]
    }
    ]
    }
    }
     *
     */
    @ApiOperation("获取省市区下拉列表中下一级")
    @GetMapping("getNextSingleMenu")
    @ResponseBody
    public HttpResult<ProvinceCityAreaSelectVO> getNextSingleMenu(String code){
        try {
            ProvinceCityAreaSelectVO provinceCityAreaSelectVO = null;
            if(code == null){
                //如果编码为空，说明查询的是省
                provinceCityAreaSelectVO = provinceCityAreaService.getOnlyProvince();
            }else if(code.indexOf("a00") > -1 && code.indexOf("c00") > -1){
                //如果编码包含a00和c00，说明查询的是市
                String provinceCode = code.substring(0, 3);
                provinceCityAreaSelectVO = provinceCityAreaService.getOnlyCity(provinceCode);
            }else{
                //否则，查询的是区
                String provinceCode = code.substring(0, 3);
                String cityCode = code.substring(3,6);
                provinceCityAreaSelectVO = provinceCityAreaService.getOnlyArea(provinceCode, cityCode);
            }
            return HttpResult.success(provinceCityAreaSelectVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


}
