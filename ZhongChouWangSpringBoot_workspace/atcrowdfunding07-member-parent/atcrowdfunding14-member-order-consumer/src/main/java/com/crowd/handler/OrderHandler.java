package com.crowd.handler;

import com.crowd.api.MySQLRemoteService;
import com.crowd.constant.CrowdConstant;
import com.crowd.entity.vo.AddressVO;
import com.crowd.entity.vo.MemberLoginVO;
import com.crowd.entity.vo.OrderProjectVO;
import com.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;


    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO, HttpSession session){

        ResultEntity<String> resultEntity = mySQLRemoteService.saveAddressRemote(addressVO);

        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        Integer returnContent = orderProjectVO.getReturnContent();


        return "redirect:http://localhost:4001/order/confirm/order/" + returnContent;

    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(
            @PathVariable("returnCount") Integer returnCount,
            HttpSession session
    ){

        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        orderProjectVO.setReturnContent(returnCount);

        session.setAttribute("orderProjectVO", orderProjectVO);

        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        Integer memberId = memberLoginVO.getId();

        ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressVORemote(memberId);

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            List<AddressVO> list = resultEntity.getData();
            session.setAttribute("addressVOList",list);
        }

        return "confirm_order";
    }


    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(
            @PathVariable("projectId") Integer projectId,
            @PathVariable("returnId") Integer returnId,
            HttpSession session){

        ResultEntity<OrderProjectVO> resultEntity =
                mySQLRemoteService.getOrderProjectVORemote(projectId, returnId);

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            OrderProjectVO orderProjectVO = resultEntity.getData();
            session.setAttribute("orderProjectVO",orderProjectVO);
        }


        return  "confirm_return";

    }


}
