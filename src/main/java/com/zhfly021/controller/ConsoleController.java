package com.zhfly021.controller;

import com.zhfly021.entity.Admin;
import com.zhfly021.entity.BwgzCount;
import com.zhfly021.entity.Role;
import com.zhfly021.entity.Student;
import com.zhfly021.service.ConsoleService;
import com.zhfly021.service.RoleService;
import com.zhfly021.service.StudentService;
import com.zhfly021.service.XxwhService;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2021-04-04 21:18
 */
@Controller
public class ConsoleController {

    @Autowired
    ConsoleService consoleService;

    @Autowired
    RoleService roleService;

    @Autowired
    StudentService studentService;

    @Autowired
    XxwhService xxwhService;

    /**
     * 跳转至 控制台
      * @return
     */
    @RequestMapping(value = "/back/home/console",method = RequestMethod.GET)
    public String adminHome1(HttpSession session, Model model){
        Admin adminLogin = (Admin) session.getAttribute("admin");
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        String loginRoleName = role.getRoleName();
        String faculty = null;
        String stuClass = null;
        if("超级管理员".equals(loginRoleName)){
        }else if("院级管理员".equals(loginRoleName)){
            faculty = adminLogin.getEmployer();
        }else{
            stuClass = adminLogin.getEmployer();
        }

        // 待办事项查询
        // 1. 综合测评
        List<Integer> matterList = new ArrayList<>();
        matterList.add(consoleService.countZhcpOfStatus("未提交", faculty, stuClass));
        matterList.add(consoleService.countZhcpOfStatus("已暂存", faculty, stuClass));
        matterList.add(consoleService.countZhcpOfStatus("已完成", faculty, stuClass));
        matterList.add(consoleService.countZhcpOfStatus("待复核", faculty, stuClass));
        matterList.add(consoleService.countZhcpOfStatus("待审核", faculty, stuClass));
        matterList.add(consoleService.countZhcpOfStatus("审核中", faculty, stuClass));
        matterList.add(consoleService.countZhcpOfStatus("已驳回", faculty, stuClass));
        matterList.add(consoleService.countZhcpOfStatus("已过审", faculty, stuClass));

        // 2. 报表数据
        matterList.add(consoleService.countBbtxDataOfStatus("未提交", faculty, stuClass));
        matterList.add(consoleService.countBbtxDataOfStatus("已完成", faculty, stuClass));
        matterList.add(consoleService.countBbtxDataOfStatus("待审核", faculty, stuClass));
        matterList.add(consoleService.countBbtxDataOfStatus("已驳回", faculty, stuClass));

        // 3.班级通知
        matterList.add(consoleService.countBjtz(faculty, stuClass));

        // 4.班级建议
        matterList.add(consoleService.countBjjsOfStatus("待回复", faculty, stuClass));

        // 5.工作反馈
        matterList.add(consoleService.countBwgzOfStatus("待回复", faculty, stuClass));

        // 6.班委测评
        matterList.add(consoleService.countBwmydcpDataOfStatus("未完成", faculty, stuClass));

        // 7.学生简历
        // 学生数
        int countStudent = studentService.countStudent(faculty, stuClass);
        // 简历数
        int countResume = xxwhService.countResume(faculty, stuClass);
        // 未提交（PS：因为学生简历由学生自主提交，数据库中并没有存储未提交的状态，所以通过班级人数-简历数来计算未提交数量）
        matterList.add(countStudent - countResume);
        matterList.add(consoleService.countResumeOfStatus("待审核", faculty, stuClass));
        matterList.add(consoleService.countResumeOfStatus("已驳回", faculty, stuClass));
        matterList.add(consoleService.countResumeOfStatus("已完成", faculty, stuClass));

        // 8.证明材料
        matterList.add(consoleService.countEvidenceOfStatus("未提交", faculty, stuClass));
        matterList.add(consoleService.countEvidenceOfStatus("待审核", faculty, stuClass));
        matterList.add(consoleService.countEvidenceOfStatus("已驳回", faculty, stuClass));
        matterList.add(consoleService.countEvidenceOfStatus("已完成", faculty, stuClass));

        model.addAttribute("matterList", matterList);

        if ("团支书,班长,组织委员,宣传委员,权益委员".contains(loginRoleName)){
            model.addAttribute("role",3);
        }else {
            model.addAttribute("role",0);
        }

        return "back/home/console";
    }

    /**
     * 跳转至数据看板
     * @return
     */
    @RequestMapping(value = "/back/home/homepage2",method = RequestMethod.GET)
    public String adminHome3(HttpSession session, Model model){

        Admin adminLogin = (Admin) session.getAttribute("admin");
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        String loginRoleName = role.getRoleName();
        String faculty = null;
        String stuClass = null;
        if ("超级管理员".equals(loginRoleName)) {
        } else if ("院级管理员".equals(loginRoleName)) {
            faculty = adminLogin.getEmployer();
        } else {
            stuClass = adminLogin.getEmployer();
        }
        Map<String, Object> map=new HashMap<String, Object>();
        List<Integer> bjjsList = new ArrayList<Integer>();
        List<Integer> bwgzList = new ArrayList<Integer>();
        List<String> monthList = new ArrayList<String>();

//        获取当前时间
        String time = new SimpleDateFormat("yyyy-MM").format(new Date());
        String year = time.substring(0,4);  //当年
        int month = Integer.parseInt(time.substring(5));  //当月
        String months = year+"-0"+month;

        //查询所有
        model.addAttribute("allBjjs",consoleService.countBjjsOfDean(faculty,stuClass,null));
        model.addAttribute("allBwgz",consoleService.countBwgzOfDean(faculty,stuClass,null));
        //按年查找
        model.addAttribute("yearBjjs",consoleService.countBjjsOfDean(faculty,stuClass,year));
        model.addAttribute("yearBwgz",consoleService.countBwgzOfDean(faculty,stuClass,year));

        //当前月查找
        if (month >= 10)
            months = year + month;
        model.addAttribute("monthBjjs",consoleService.countBjjsOfDean(faculty,stuClass,months));
        model.addAttribute("monthBwgz",consoleService.countBwgzOfDean(faculty,stuClass,months));

        if (month-1 >= 10)
            months = year + (month-1);
        model.addAttribute("lastMonthBjjs",consoleService.countBjjsOfDean(faculty,stuClass,months));
        model.addAttribute("lastMonthBwgz",consoleService.countBwgzOfDean(faculty,stuClass,months));


        return "back/home/homepage2";
    }

    /**
     * 跳转至 控制台的bar
     * @return
     */
    @RequestMapping(value = "/back/home/bar",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> adminBar(HttpSession session){
        Admin adminLogin = (Admin) session.getAttribute("admin");
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        String loginRoleName = role.getRoleName();
        String faculty = null;
        String stuClass = null;
        if("超级管理员".equals(loginRoleName)){
        }else if("院级管理员".equals(loginRoleName)){
            faculty = adminLogin.getEmployer();
        }else{
            stuClass = adminLogin.getEmployer();
        }

        Map<String, Object> map=new HashMap<String, Object>();
        //未提交
        List<Integer> unCommit = new ArrayList<>();
        //待审核
        List<Integer> toAudit = new ArrayList<>();
        //已驳回
        List<Integer> rejected = new ArrayList<>();
        //已完成
        List<Integer> completed = new ArrayList<>();


        // 待办事项查询
        // 1. 综合测评

        unCommit.add(consoleService.countZhcpOfStatus("未提交", faculty, stuClass) + consoleService.countZhcpOfStatus("已暂存", faculty, stuClass));
        completed.add(consoleService.countZhcpOfStatus("已完成", faculty, stuClass));
        toAudit.add(consoleService.countZhcpOfStatus("待审核", faculty, stuClass) + consoleService.countZhcpOfStatus("待复核", faculty, stuClass) + consoleService.countZhcpOfStatus("审核中", faculty, stuClass));
        rejected.add(consoleService.countZhcpOfStatus("已驳回", faculty, stuClass));

        // 2. 报表数据
        unCommit.add(consoleService.countBbtxDataOfStatus("未提交", faculty, stuClass));
        completed.add(consoleService.countBbtxDataOfStatus("已完成", faculty, stuClass));
        toAudit.add(consoleService.countBbtxDataOfStatus("待审核", faculty, stuClass));
        rejected.add(consoleService.countBbtxDataOfStatus("已驳回", faculty, stuClass));


        // 7.学生简历
        // 学生数
        int countStudent = studentService.countStudent(faculty, stuClass);
        // 简历数
        int countResume = xxwhService.countResume(faculty, stuClass);
        // 未提交（PS：因为学生简历由学生自主提交，数据库中并没有存储未提交的状态，所以通过班级人数-简历数来计算未提交数量）
        unCommit.add(countStudent - countResume);
        toAudit.add(consoleService.countResumeOfStatus("待审核", faculty, stuClass));
        rejected.add(consoleService.countResumeOfStatus("已驳回", faculty, stuClass));
        completed.add(consoleService.countResumeOfStatus("已完成", faculty, stuClass));

        // 8.证明材料
        unCommit.add(consoleService.countEvidenceOfStatus("未提交", faculty, stuClass));
        toAudit.add(consoleService.countEvidenceOfStatus("待审核", faculty, stuClass));
        rejected.add(consoleService.countEvidenceOfStatus("已驳回", faculty, stuClass));
        completed.add(consoleService.countEvidenceOfStatus("已完成", faculty, stuClass));


        map.put("code","0");
        map.put("message","获取成功");
        map.put("unCommit",unCommit);
        map.put("toAudit",toAudit);
        map.put("rejected",rejected);
        map.put("completed",completed);


        return map;
    }

    /**
     * 控制台的饼状图
     * @param session
     * @return
     */
    @RequestMapping(value = "/back/home/pie", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> adminPid(HttpSession session){
        Admin adminLogin = (Admin) session.getAttribute("admin");
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        String roleName = role.getRoleName();

        //查询结果集
        List<BwgzCount> list = new ArrayList<>();
        //存放名字
        List<String> position = new ArrayList<>();
        //存放反馈量
        List<Integer> sum = new ArrayList<>();

        if("团支书,班长,组织委员,宣传委员,权益委员".contains(roleName)){
            list = consoleService.bwgzFeedBack(adminLogin.getEmployer());
            for (int i = 0; i<list.size(); i++){
                position.add(list.get(i).getFeedback());
                sum.add(list.get(i).getCount());
            }
        }

        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code","0");
        map.put("message","获取成功");
        map.put("position",position);
        map.put("sum",sum);

        return  map;
    }


    /**
     * 跳转至 数据看板的图
     * @return
     */
    @RequestMapping(value = "/back/home/line",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> adminLine(HttpSession session) {
        Admin adminLogin = (Admin) session.getAttribute("admin");
        Role role = roleService.queryRoleById(adminLogin.getRoleId());
        String loginRoleName = role.getRoleName();
        String faculty = null;
        String stuClass = null;
        if ("超级管理员".equals(loginRoleName)) {
        } else if ("院级管理员".equals(loginRoleName)) {
            faculty = adminLogin.getEmployer();
        } else {
            stuClass = adminLogin.getEmployer();
        }
        Map<String, Object> map=new HashMap<String, Object>();
        List<Integer> bjjsList = new ArrayList<Integer>();
        List<Integer> bwgzList = new ArrayList<Integer>();
        List<String> monthList = new ArrayList<String>();

//        获取当前时间
        String time = new SimpleDateFormat("yyyy-MM").format(new Date());
        String year = time.substring(0,4);  //当年
        int month = Integer.parseInt(time.substring(5));  //当月
        String months = year+"-0"+month;

        //按年月查找
        for (int i=1; i<=month; i++){
            months = year+"-0"+i;
            //月份大于10
            if (i >= 10)
                months = year +"-"+ i;
            //班级建设意见
            bjjsList.add(consoleService.countBjjsOfDean(faculty,stuClass,months));
            //班级工作反馈
            bwgzList.add(consoleService.countBwgzOfDean(faculty,stuClass,months));

            switch (i){
                case 1:
                    monthList.add("一月");
                    break;
                case 2:
                    monthList.add("二月");
                    break;
                case 3:
                    monthList.add("三月");
                    break;
                case 4:
                    monthList.add("四月");
                    break;
                case 5:
                    monthList.add("五月");
                    break;
                case 6:
                    monthList.add("六月");
                    break;
                case 7:
                    monthList.add("七月");
                    break;
                case 8:
                    monthList.add("八月");
                    break;
                case 9:
                    monthList.add("九月");
                    break;
                case 10:
                    monthList.add("十月");
                    break;
                case 11:
                    monthList.add("十一月");
                    break;
                case 12:
                    monthList.add("十二月");
            }

        }

        map.put("code","0");
        map.put("message","获取成功");
        map.put("bjjsList",bjjsList);
        map.put("bwgzList",bwgzList);
        map.put("monthList",monthList);
//        map.put("bjjsPro",bjjsList.get(bjjsList.size()-1)/bjjsList.get(bjjsList.size()));
//        map.put("bwgzPro",bwgzList.get(bwgzList.size()-1)/bwgzList.get(bwgzList.size()));

        return map;
    }

}
