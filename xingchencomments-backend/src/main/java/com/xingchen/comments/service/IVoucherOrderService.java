package com.xingchen.comments.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xingchen.comments.dto.Result;
import com.xingchen.comments.entity.VoucherOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IVoucherOrderService extends IService<VoucherOrder> {

    Result seckillVoucher(Long voucherId);


    public Result createVoucherOrder(VoucherOrder voucherOrder);

}
