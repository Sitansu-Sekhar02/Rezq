package com.sa.rezq.adapter.interfaces;


import com.sa.rezq.services.model.OrderModel;

public interface OnOrderClickInvoke {
    void OnHelpInvoke(OrderModel orderModel);
    void OnCancelInvoke(OrderModel orderModel);
}
