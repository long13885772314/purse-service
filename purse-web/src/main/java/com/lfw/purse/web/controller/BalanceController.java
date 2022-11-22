package com.lfw.purse.web.controller;

import com.lfw.purse.infrastructure.dto.BalanceDetailDto;
import com.lfw.purse.service.BalanceDetailApplicationService;
import com.lfw.purse.service.UserBalanceApplicationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 龙发文
 * @ClassName PayController
 * @Description TODO
 * @date 2022/11/21 0021 17:15
 */
@RestController
@RequestMapping("/api-balance")
public class BalanceController {

    @Resource
    private UserBalanceApplicationService userBalanceApplicationService;

    @Resource
    private BalanceDetailApplicationService balanceDetailApplicationService;

    /**
     * 查询余额
     * @param request
     * @param response
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public BigDecimal getBalance(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @PathVariable("userId") Integer userId) {

        return userBalanceApplicationService.queryBalance(userId);
    }


    /**
     * 用户退款20元
     * @param request
     * @param response
     * @param userId
     * @return
     */
    @GetMapping("/increase")
    public Boolean increaseBalance(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam("userId") Integer userId,
                                   @RequestParam(value = "price",required = false) BigDecimal price) {
        if (null == price) {
            price = new BigDecimal("20.0000");
        }
        return userBalanceApplicationService.increase(userId, price, null);
    }


    /**
     * 用户消费100元
     * @param request
     * @param response
     * @param userId
     * @return
     */
    @GetMapping("/decrease")
    public Boolean decreaseBalance(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam("userId") Integer userId,
                                   @RequestParam(value = "price",required = false) BigDecimal price) {
        if (null == price) {
            price = new BigDecimal("100.0000");
        }
        return userBalanceApplicationService.decrease(userId, price, null);
    }

    /**
     * 查询用户钱包金额变动明细
     * @param request
     * @param response
     * @param userId
     * @return
     */
    @GetMapping("/query-details")
    public List<BalanceDetailDto> queryBalanceDetail(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     @RequestParam("userId") Integer userId) {
        BigDecimal bigDecimal = new BigDecimal("100.0000");
        return balanceDetailApplicationService.queryByUserId(userId);
    }

}
