package com.sa.rezq.adapter.interfaces;


import com.sa.rezq.services.model.ProductModel;

public interface OnProductQuantityClickInvoke {
    void OnItemQuantityClickInvoke(int position, int quantity, ProductModel productModel, boolean isAddition);
}
