package com.mundane.mail.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import com.mundane.mail.constant.CommonConstants;
import com.mundane.mail.dto.UserInfo;
import com.mundane.mail.entity.RedArchiveUser;
import com.mundane.mail.entity.RedBookClientEntity;
import com.mundane.mail.entity.RedBookItemEntity;
import com.mundane.mail.entity.RedBookOrderEntity;
import com.mundane.mail.mapper.RedArchiveUserMapper;
import com.mundane.mail.mapper.RedBookClientMapper;
import com.mundane.mail.mapper.RedBookItemMapper;
import com.mundane.mail.mapper.RedBookOrderMapper;
import com.mundane.mail.utils.CommonUtil;
import com.mundane.mail.utils.LantuPayUtil;
import com.mundane.mail.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private RedBookOrderMapper orderMapper;

    @Autowired
    private RedBookClientMapper clientMapper;

    @Autowired
    private RedBookItemMapper itemMapper;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RedArchiveUserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public OrderRespVo createPayUrl(OrderCreateVo vo) {
        StpUtil.checkLogin();

        Long userIdNew = StpUtil.getLoginIdAsLong();
        RedBookOrderEntity order = new RedBookOrderEntity();
        order.setUserIdNew(userIdNew);
        LocalDateTime now = LocalDateTime.now();
        String outTradeNo = "LTZF" + DateUtil.format(now, "yyyyMMddHHmmssSSS");
        RedBookItemEntity item = itemMapper.selectByPrimaryKey(vo.getItemType());
        order.setOutTradeNo(outTradeNo);
        order.setTotalPay(item.getDiscountPrice());
        order.setStatus(0);
        order.setItemType(vo.getItemType());
        order.setCreateTime(now);
        order.setUpdateTime(now);
        orderMapper.insert(order);
        String qrCodeUrl = LantuPayUtil.createQrCodeUrl(outTradeNo, order.getTotalPay().toString(), "RedArchive-小红书收藏导出");
        OrderRespVo result = new OrderRespVo();
        result.setQrCodeUrl(qrCodeUrl);
        result.setOutTradeNo(outTradeNo);
        return result;
    }

    public void handleNotify(Map<String, String> result) {
        String outTradeNo = result.get("out_trade_no");
        String orderNo = result.get("order_no");
        String payNo = result.get("pay_no");
        String totalFee = result.get("total_fee");
        RedBookOrderEntity order = orderMapper.selectByPrimaryKey(outTradeNo);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        LocalDateTime now = LocalDateTime.now();
        // 修改订单状态
        if (order.getStatus() != 1) {
            order.setStatus(1);
            order.setOrderNo(orderNo);
            order.setPayNo(payNo);
            order.setUpdateTime(now);
        }
        RedBookItemEntity item = itemMapper.selectByPrimaryKey(order.getItemType());
        RedArchiveUser user = userMapper.selectByPrimaryKey(order.getUserIdNew());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setUpdateTime(now);
        if (item.getMembershipType() == CommonConstants.LIFE_TIME) {
            user.setMembershipType(CommonConstants.LIFE_TIME);
            user.setMembershipExpiry(null);
        } else {
            user.setMembershipType(item.getMembershipType());
            LocalDateTime membershipExpiry = user.getMembershipExpiry();
            if (membershipExpiry != null && membershipExpiry.isAfter(now)) {
                user.setMembershipExpiry(membershipExpiry.plusDays(item.getDays()));
            } else {
                user.setMembershipExpiry(now.plusDays(item.getDays()));
            }
        }
        userMapper.updateByPrimaryKey(user);
        orderMapper.updateByPrimaryKey(order);
        try {
            scheduleService.sendPaySuccess(totalFee);
        } catch (Exception e) {
            log.error("发送支付成功邮件失败", e);
        }
    }

    public QueryOrderStatusRespVo queryOrderStatus(String outTradeNo) {
        StpUtil.checkLogin();
        QueryOrderStatusRespVo result = new QueryOrderStatusRespVo();
        RedBookOrderEntity order = orderMapper.selectByPrimaryKey(outTradeNo);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        RedArchiveUser user = userMapper.selectByPrimaryKey(order.getUserIdNew());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (order.getStatus() == 1) {
            result.setActive(true);
            result.setUserInfo(CommonUtil.getUserInfo(user));
            return result;
        }
        // 如果未支付，必须去查询支付状态
        JSONObject payOrder = LantuPayUtil.getPayOrder(outTradeNo);
        if (payOrder == null) {
            result.setActive(false);
            return result;
        }
        Integer payStatus = payOrder.getInt("pay_status");
        String orderNo = payOrder.getStr("order_no");
        String payNo = payOrder.getStr("pay_no");
        LocalDateTime now = LocalDateTime.now();
        if (payStatus != 1) {
            result.setActive(false);
            return result;
        }
        // 修改订单状态
        order.setStatus(1);
        order.setUpdateTime(now);
        order.setOrderNo(orderNo);
        order.setPayNo(payNo);
        // 修改用户表
        RedBookItemEntity item = itemMapper.selectByPrimaryKey(order.getItemType());
        user.setUpdateTime(now);
        if (item.getMembershipType() == CommonConstants.LIFE_TIME) {
            user.setMembershipType(CommonConstants.LIFE_TIME);
            user.setMembershipExpiry(null);
        } else {
            user.setMembershipType(item.getMembershipType());
            LocalDateTime membershipExpiry = user.getMembershipExpiry();
            if (membershipExpiry != null && membershipExpiry.isAfter(now)) {
                user.setMembershipExpiry(membershipExpiry.plusDays(item.getDays()));
            } else {
                user.setMembershipExpiry(now.plusDays(item.getDays()));
            }
        }
        userMapper.updateByPrimaryKey(user);
        orderMapper.updateByPrimaryKey(order);
        result.setActive(true);
        result.setUserInfo(CommonUtil.getUserInfo(user));
        return result;
    }

    public ActiveStatusVo queryActiveStatus(String clientId, String userId) {
        ActiveStatusVo result = new ActiveStatusVo();
        if (StringUtils.isEmpty(clientId)) {
            result.setStatus(0);
            return result;
        }
        RedBookClientEntity client = clientMapper.selectByClientId(clientId, userId);
        if (client == null) {
            result.setStatus(0);
            return result;
        }
        String endDateStr = client.getEndDate();
        result.setEndDate(endDateStr);
        result.setUserId(client.getId());
        if (StringUtils.equals("-1", endDateStr)) {
            result.setStatus(1);
        } else {
            DateTime endDate = DateUtil.parse(endDateStr, "yyyy-MM-dd");
            result.setStatus(endDate.isBefore(DateUtil.date()) ? 0 : 1);
        }
        return result;
    }
}
